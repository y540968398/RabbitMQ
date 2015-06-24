package com.tpo.mq.producer.publish.impl;

import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.tpo.mq.producer.publish.IBasicPublish;

public class AbsBasicPublisher extends AbsPublisher implements IBasicPublish
{
	private Logger logger = Logger.getLogger(AbsBasicPublisher.class);

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
	public AbsBasicPublisher(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	@Override
	public void publishMsg(byte[] publishMsg)
	{
		// 消息合法性验证
		if (ArrayUtils.isEmpty(publishMsg))
		{
			logger.error("Message can't be empty !");
			return;
		}

		// 发送消息到 RabbitMQ 服务器
		try
		{
			channel.basicPublish(exchangeName, routingKey, props, publishMsg);
		}
		catch (IOException e)
		{
			logger.error("Publish fanout exchange msg error !", e);
		}
		logger.info("Publish new messsage to server !");
	}
}
