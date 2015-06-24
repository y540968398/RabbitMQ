package com.tpo.mq.producer.publish.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tpo.mq.producer.publish.IBasicPublish;

public class QueuePublish extends AbsBasicPublisher implements IBasicPublish
{
	private Logger logger = Logger.getLogger(QueuePublish.class);

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
	public QueuePublish(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	/**
	 * ��ʼ��Ϣ������Ϣ
	 * 
	 * @param name
	 *            ��Ϣ��������
	 * @param durable
	 *            �Ƿ�־û�
	 * @param exclusive
	 *            �Ƿ�һ����
	 * @param autoDelete
	 *            �Ƿ��Զ�ɾ�������������ռ䲻��ʱ
	 * @param mapParam
	 *            ��չ����
	 * @throws Exception 
	 */
	public void initQueue(String name, boolean durable, boolean exclusive, boolean autoDelete,
	        Map<String, Object> mapParam) throws Exception
	{
		exchangeName = "";
		routingKey = "";
		props = null;
		try
		{
			// ��Ϣ���ж���
			channel.queueDeclare(name, durable, exclusive, autoDelete, mapParam);
		}
		catch (IOException e)
		{
			logger.error("Init exhange msg publisher error !", e);
			throw new Exception("Init exhange msg publisher error !", e);
		}
		logger.info("Init exchange msg publisher success !");
	}

}
