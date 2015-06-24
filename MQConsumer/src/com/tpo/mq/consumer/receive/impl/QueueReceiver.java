package com.tpo.mq.consumer.receive.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rabbitmq.client.QueueingConsumer;
import com.tpo.mq.consumer.receive.IReceiver;

/**
 * �򵥶�����Ϣ������
 * 
 * @description ����ָ�����е���Ϣ�����Զ�������ȷ�Ͻ��ա�����������
 * @author robert.gao
 * 
 * @param <T>
 *            ��Ϣ���ͣ���Ҫ��Ϣ��������Ԫ��Ϣ(����������)���н���
 */
public class QueueReceiver<T> extends AbstractReceiver<T> implements IReceiver<T>
{
	private Logger logger = Logger.getLogger(QueueReceiver.class);

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
	public QueueReceiver(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	/**
	 * ������м�����
	 * 
	 * @param queueName
	 *            ��������
	 * @param durable
	 *            �Ƿ�־û�����
	 * @param exclusive
	 *            �Ƿ��嵥һ�Ķ��У�������ǰ�ͻ���ʹ��
	 * @param autoDelete
	 *            �Ƿ��Զ�ɾ�������������ռ䲻��ʱ
	 * @param queueConfMap
	 *            �������в���
	 * @throws Exception
	 *             ���ض���ʧ��
	 */
	public void initQueue(String queueName, boolean durable, boolean exclusive, boolean autoDelete,
	        Map<String, Object> queueConfMap) throws Exception
	{
		try
		{
			// �������ӵ��ܵ��Ķ���
			channel.queueDeclare(queueName, durable, exclusive, autoDelete, queueConfMap);
			// ��ǰ�ܵ�����Ϣ������
			queueingConsumer = new QueueingConsumer(channel);
			// ��ʼ������Ϣ
			channel.basicConsume(queueName, true, queueingConsumer);
		}
		catch (IOException e)
		{
			logger.error("Init queue receiver error !", e);
			throw new Exception("Init queue receiver error !", e);
		}
		logger.info("Init queue receiver success !");
	}

}
