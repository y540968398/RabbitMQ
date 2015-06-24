package com.tpo.mq.producer.publish.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tpo.mq.producer.publish.IBasicPublish;

public class QueuePublish extends AbsBasicPublisher implements IBasicPublish
{
	private Logger logger = Logger.getLogger(QueuePublish.class);

	/**
	 * 初始化消息发送器
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
	public QueuePublish(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	/**
	 * 初始消息队列信息
	 * 
	 * @param name
	 *            消息队列名称
	 * @param durable
	 *            是否持久化
	 * @param exclusive
	 *            是否单一队列
	 * @param autoDelete
	 *            是否自动删除，当服务器空间不足时
	 * @param mapParam
	 *            扩展参数
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
			// 消息队列定义
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
