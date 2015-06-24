package com.tpo.mq.consumer.receive.impl;

import org.apache.log4j.Logger;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.tpo.mq.consumer.msg.adapter.IMsgAdapter;
import com.tpo.mq.consumer.msg.customer.IMsgCustomer;
import com.tpo.mq.consumer.receive.IReceiver;

public class RPCReceiver<T> extends QueueReceiver<T> implements IReceiver<T>
{
	private Logger logger = Logger.getLogger(RPCReceiver.class);

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
	public RPCReceiver(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	@Override
	public void receive(IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer) throws Exception
	{
		while (true)
		{
			logger.info("Receive new RPC request !");
			// �յ��ͻ��˵�һ�� RPC ����
			Delivery deliver = queueingConsumer.nextDelivery();

			// ���� RPC ��Ϣ
			byte[] result = msgCustomer.receiveMsg(msgAdapter.parseMessage(deliver.getBody()));

			// ��ʼ����������
			BasicProperties props = deliver.getProperties();
			BasicProperties responseProps = new BasicProperties.Builder().correlationId(props.getCorrelationId())
			        .build();

			// ���� RPC ���
			channel.basicPublish("", props.getReplyTo(), responseProps, result);
			logger.info("Response RPC request !");
		}
	}

}
