package com.tpo.mq.consumer.receive.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.rabbitmq.client.QueueingConsumer;
import com.tpo.mq.consumer.receive.IReceiver;
import com.tpo.mq.enums.EExchangeType;
import com.tpo.util.datatype.EnumUtils;

/**
 * topic 消息接收器
 * 
 * @Description topic 路由器消息接受器，可配置 消息是否持久化、路由规则 及 路由器名称。 <br/>
 *              消息接收后将自动反馈给服务器。
 * 
 * @author robert.gao
 *
 * @param <T>
 *            消息类型，需要消息适配器对元消息(二级制数据)进行解析
 */
public class ExchangeReceiver<T> extends AbstractReceiver<T> implements IReceiver<T>
{
	private Logger logger = Logger.getLogger(ExchangeReceiver.class);

	/** 路由器名称 */
	String exchangeName;
	/** 路由器类型 */
	String exchangeType;

	// 消息队列名称
	String queueName;

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
	public ExchangeReceiver(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	/**
	 * 初始化消息路由器
	 * 
	 * @description 创建指定类型的消息路由器，并绑定消息接收器到路由器的队列。<br/>
	 *              消息接受后会自动反馈"确认"消息给服务器。
	 * @param name
	 *            路由器名称
	 * @param type
	 *            路由器类型(FANOUT, DIRECT, TOPIC)
	 * @param durable
	 *            路由器中的消息队列是否持久化
	 * @param routingKey
	 *            路由器 消息路由关键字
	 * @throws Exception
	 *             加载路由器失败
	 */
	public void initExchange(String name, String type, boolean durable, String routingKey) throws Exception
	{
		exchangeName = name;
		exchangeType = type;

		// 验证 "路由器类型" 是否合法
		if (!EnumUtils.containsEnum(EExchangeType.class, exchangeType))
		{
			logger.error("Exchange type " + exchangeType + " is not defined !");
			return;
		}

		try
		{
			// 定义该管道连接的 topic 路由器
			channel.exchangeDeclare(exchangeName, exchangeType, durable);
			// 获得该管道接收的消息队列，由路由器定义并创建
			queueName = channel.queueDeclare().getQueue();

			// 当前管道绑定 路由器 及 队列
			channel.queueBind(queueName, exchangeName, routingKey);

			// 当前管道绑定 消息接收器
			queueingConsumer = new QueueingConsumer(channel);
			// 开始接受消息
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
