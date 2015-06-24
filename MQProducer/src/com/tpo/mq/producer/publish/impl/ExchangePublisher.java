package com.tpo.mq.producer.publish.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.tpo.mq.enums.EExchangeType;
import com.tpo.mq.producer.publish.IBasicPublish;
import com.tpo.util.datatype.EnumUtils;

public class ExchangePublisher extends AbsBasicPublisher implements IBasicPublish
{

	private Logger logger = Logger.getLogger(ExchangePublisher.class);

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
	public ExchangePublisher(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	/**
	 * 
	 * @param name
	 * @param type
	 * @param key
	 * @throws Exception
	 */
	public void initExchange(String name, String type, String key) throws Exception
	{
		exchangeName = name;
		exchangeType = type;
		routingKey = key;
		props = null;
		
		// 验证 "路由器类型" 是否合法
		if (!EnumUtils.containsEnum(EExchangeType.class, exchangeType))
		{
			logger.error("Exchange type " + exchangeType + " is not defined !");
			return;
		}
		
		try
		{
			channel.exchangeDeclare(exchangeName, exchangeType);
		}
		catch (IOException e)
		{
			logger.error("Init exhange msg publisher error !", e);
			throw new Exception("Init exhange msg publisher error !", e);
		}
		logger.info("Init exchange msg publisher success !");
	}

}
