package com.tpo.mq.producer.publish.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tpo.mq.producer.publish.IPublish;

public class AbsPublisher implements IPublish
{
	private Logger logger = Logger.getLogger(AbsPublisher.class);

	// Socket���ӹ���
	ConnectionFactory connectionFactory;
	// �� RabbitMQ ����˵�����
	Connection connection;
	// �� RabbitMQ ����˵����ӹܵ�
	Channel channel;

	// ·��������,���Ϊ���ַ���,���͵�Ĭ�ϵ�·����
	String exchangeName;
	// ·��������
	String exchangeType;
	// ·�ɼ�
	String routingKey;
	// RPC ��Ϣ��ʹ�õĸ�����Ϣ����
	AMQP.BasicProperties props;

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
	public AbsPublisher(String host, String user, String pass)
	{
		connectionFactory = new ConnectionFactory();
		logger.debug("Connection to server host, user, pass : " + host + ", " + user + ", " + pass);
		connectionFactory.setHost(host);
		connectionFactory.setUsername(user);
		connectionFactory.setPassword(pass);

		try
		{
			// ���ӷ������������ܵ�
			connection = connectionFactory.newConnection();
			channel = connection.createChannel();
		}
		catch (IOException e)
		{
			logger.error("Connction to RabbitMQ-VHost error !", e);
		}
	}

	@Override
	public void close()
	{
		try
		{
			channel.close();
		}
		catch (IOException e)
		{
			logger.error("Close channel error !", e);
		}
		logger.info("Close channel to server !");
		try
		{
			connection.close();
		}
		catch (IOException e)
		{
			logger.error("Close connection error !", e);
		}
		logger.info("Close connection to server !");
	}

}
