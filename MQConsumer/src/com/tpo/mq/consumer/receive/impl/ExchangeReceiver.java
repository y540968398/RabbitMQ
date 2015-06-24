package com.tpo.mq.consumer.receive.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.rabbitmq.client.QueueingConsumer;
import com.tpo.mq.consumer.receive.IReceiver;
import com.tpo.mq.enums.EExchangeType;
import com.tpo.util.datatype.EnumUtils;

/**
 * topic ��Ϣ������
 * 
 * @Description topic ·������Ϣ�������������� ��Ϣ�Ƿ�־û���·�ɹ��� �� ·�������ơ� <br/>
 *              ��Ϣ���պ��Զ���������������
 * 
 * @author robert.gao
 *
 * @param <T>
 *            ��Ϣ���ͣ���Ҫ��Ϣ��������Ԫ��Ϣ(����������)���н���
 */
public class ExchangeReceiver<T> extends AbstractReceiver<T> implements IReceiver<T>
{
	private Logger logger = Logger.getLogger(ExchangeReceiver.class);

	/** ·�������� */
	String exchangeName;
	/** ·�������� */
	String exchangeType;

	// ��Ϣ��������
	String queueName;

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
	public ExchangeReceiver(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	/**
	 * ��ʼ����Ϣ·����
	 * 
	 * @description ����ָ�����͵���Ϣ·������������Ϣ��������·�����Ķ��С�<br/>
	 *              ��Ϣ���ܺ���Զ�����"ȷ��"��Ϣ����������
	 * @param name
	 *            ·��������
	 * @param type
	 *            ·��������(FANOUT, DIRECT, TOPIC)
	 * @param durable
	 *            ·�����е���Ϣ�����Ƿ�־û�
	 * @param routingKey
	 *            ·���� ��Ϣ·�ɹؼ���
	 * @throws Exception
	 *             ����·����ʧ��
	 */
	public void initExchange(String name, String type, boolean durable, String routingKey) throws Exception
	{
		exchangeName = name;
		exchangeType = type;

		// ��֤ "·��������" �Ƿ�Ϸ�
		if (!EnumUtils.containsEnum(EExchangeType.class, exchangeType))
		{
			logger.error("Exchange type " + exchangeType + " is not defined !");
			return;
		}

		try
		{
			// ����ùܵ����ӵ� topic ·����
			channel.exchangeDeclare(exchangeName, exchangeType, durable);
			// ��øùܵ����յ���Ϣ���У���·�������岢����
			queueName = channel.queueDeclare().getQueue();

			// ��ǰ�ܵ��� ·���� �� ����
			channel.queueBind(queueName, exchangeName, routingKey);

			// ��ǰ�ܵ��� ��Ϣ������
			queueingConsumer = new QueueingConsumer(channel);
			// ��ʼ������Ϣ
			channel.basicConsume(queueName, true, queueingConsumer);

		}
		catch (IOException e)
		{
			logger.error("Init exchange error !", e);
			throw new Exception("Init exchange error !", e);
		}
		logger.info("Init exchange success !");
	}

}
