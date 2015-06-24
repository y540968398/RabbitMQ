package com.tpo.mq.consumer.receive.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rabbitmq.client.QueueingConsumer;
import com.tpo.mq.consumer.receive.IReceiver;

/**
 * 简单队列消息接收器
 * 
 * @description 接受指定队列的消息，并自动反馈“确认接收”给服务器。
 * @author robert.gao
 * 
 * @param <T>
 *            消息类型，需要消息适配器对元消息(二级制数据)进行解析
 */
public class QueueReceiver<T> extends AbstractReceiver<T> implements IReceiver<T>
{
	private Logger logger = Logger.getLogger(QueueReceiver.class);

	/**
	 * 初始化消息接收器
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
	public QueueReceiver(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	/**
	 * 定义队列及参数
	 * 
	 * @param queueName
	 *            队列名称
	 * @param durable
	 *            是否持久化队列
	 * @param exclusive
	 *            是否定义单一的队列，仅共当前客户端使用
	 * @param autoDelete
	 *            是否自动删除，当服务器空间不足时
	 * @param queueConfMap
	 *            其它队列参数
	 * @throws Exception
	 *             加载队列失败
	 */
	public void initQueue(String queueName, boolean durable, boolean exclusive, boolean autoDelete,
	        Map<String, Object> queueConfMap) throws Exception
	{
		try
		{
			// 定义连接到管道的队列
			channel.queueDeclare(queueName, durable, exclusive, autoDelete, queueConfMap);
			// 当前管道绑定消息接收器
			queueingConsumer = new QueueingConsumer(channel);
			// 开始接受消息
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
