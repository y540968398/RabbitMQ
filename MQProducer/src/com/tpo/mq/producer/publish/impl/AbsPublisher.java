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

	// Socket连接工厂
	ConnectionFactory connectionFactory;
	// 到 RabbitMQ 服务端的链接
	Connection connection;
	// 到 RabbitMQ 服务端的连接管道
	Channel channel;

	// 路由器名称,如果为空字符串,则发送到默认的路由器
	String exchangeName;
	// 路由器类型
	String exchangeType;
	// 路由键
	String routingKey;
	// RPC 消息中使用的附加消息参数
	AMQP.BasicProperties props;

	/**
	 * 初始化消息发布器
	 * 
	 * @description 连接到 RabbitMQ 服务器并创建通信 管道
	 * 
	 * @param host
	 *            RabbitMQ-VHost IP地址
	 * @param user
	 *            RabbitMQ-VHost 用户名
	 * @param pass
	 *            RabbitMQ-VHost 密码
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
			// 连接服务器并建立管道
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
