package com.tpo.mq.consumer.receive.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.tpo.mq.consumer.msg.adapter.IMsgAdapter;
import com.tpo.mq.consumer.msg.customer.IMsgCustomer;
import com.tpo.mq.consumer.receive.IReceiver;

public class AbstractReceiver<T> implements IReceiver<T>
{
	private Logger logger = Logger.getLogger(AbstractReceiver.class);

	// Socket���ӹ���
	ConnectionFactory connectionFactory;
	// �� RabbitMQ ����˵�����
	Connection connection;
	// �� RabbitMQ ����˵����ӹܵ�
	Channel channel;
	// �󶨵��ܵ��ϵ���Ϣ������
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
	public AbstractReceiver(String host, String user, String pass)
	{
		connectionFactory = new ConnectionFactory();
		logger.debug("Connection to server host, user, pass : " + host + ", " + user + ", " + pass);
		connectionFactory.setHost(host);
		connectionFactory.setUsername(user);
		connectionFactory.setPassword(pass);

		try
		{
			connection = connectionFactory.newConnection();
			channel = connection.createChannel();
		}
		catch (IOException e)
		{
			logger.error("Connction to RabbitMQ-VHost error !", e);
		}
	}

	@Override
	public void receive(IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer) throws Exception
	{
		while (true)
		{
			Delivery delivery = queueingConsumer.nextDelivery();
			byte[] msg = delivery.getBody();
			logger.debug("--->>> Receive msg from VHost ! msgLen : " + msg.length);

			msgCustomer.receiveMsg(msgAdapter.parseMessage(msg));
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
		logger.info("Close conneciton to server !");
	}

}
