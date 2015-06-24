package com.tpo.mq.producer.publish.impl;

import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.tpo.mq.producer.msg.ICallBack;
import com.tpo.mq.producer.publish.IRPCPublish;

public class RPCPuslisher extends AbsPublisher implements IRPCPublish
{
	private Logger logger = Logger.getLogger(RPCPuslisher.class);

	// ��������
	String queueName;
	// ��Ϣ��ʶ��
	String correlationId;
	// RPC ������Ϣ������
	QueueingConsumer queueingConsumer;

	/**
	 * ��ʼ����Ϣ������
	 * 
	 * @description ���ӵ� RabbitMQ ������������ͨ�� �ܵ�
	 * 
	 * @param host
	 *            RabbitMQ-VHost IP��ַ
	 * @param user
	 *            RabbitMQ-VHost �û���
	 * @param pass
	 *            RabbitMQ-VHost ����
	 */
	public RPCPuslisher(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	/**
	 * ��ʼ RPC �������
	 * 
	 * @param exchangeName
	 *            ·��������
	 * @param queueName
	 *            RPC��Ϣ��������
	 * @throws Exception 
	 */
	public void initRpc(String exchangeName, String queueName) throws Exception
	{
		super.exchangeName = exchangeName;
		this.queueName = queueName;
		try
		{
			// ��·������������
			this.queueName = channel.queueDeclare().getQueue(); 
		}
		catch (Exception e)
		{
			logger.error("Init rpc publisher error !", e);
			throw new Exception("Init rpc publisher error !", e);
		}
	}

	@Override
	public void publishMsg(byte[] publishMsg, ICallBack callBack)
	{
		// ����У��
		if (!validateRpcArgs(publishMsg, callBack))
		{
			return;
		}

		// ��ʼ��RPC����
		String correlationId = UUID.randomUUID().toString();
		props = new BasicProperties.Builder().replyTo(queueName).correlationId(correlationId).build();
		queueingConsumer = new QueueingConsumer(channel);

		// ���� RPC ����
		try
		{
			channel.basicPublish(exchangeName, queueName, props, publishMsg);
			channel.basicConsume(queueName, queueingConsumer);
		}
		catch (Exception e)
		{
			logger.error("Publis msg to rpc server error !", e);
		}

		// �ȴ� RPC ������� �� ����
		while (true)
		{
			Delivery delivery = null;
			try
			{
				delivery = queueingConsumer.nextDelivery();
			}
			catch (Exception e)
			{
				logger.error("Receive msg from RPC service error !", e);
			}
			logger.info("Receive RPC response from service !");

			// ��֤��Ϣ
			if (delivery.getProperties().getCorrelationId().equals(correlationId))
			{
				callBack.handleCallBack(delivery.getBody());
			}
			logger.info("Handle RPC response success !");
		}
	}

	/**
	 * RPC ����У��
	 * 
	 * @param publishMsg
	 *            RPC������Ϣ
	 * @param callBack
	 *            PRC����������
	 * @return boolean false:����Ϣδ�� �� ����������Ϊ��ʱ
	 */
	private boolean validateRpcArgs(byte[] publishMsg, ICallBack callBack)
	{
		// ��Ϣ��֤
		if (ArrayUtils.isEmpty(publishMsg))
		{
			logger.error("Message can't be empty !");
			return false;
		}

		// ������������֤
		if (null == callBack)
		{
			logger.error("CallBack can't be null !");
			return false;
		}
		return true;
	}

}
