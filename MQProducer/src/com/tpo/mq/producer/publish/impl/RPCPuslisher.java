package com.tpo.mq.producer.publish.impl;

import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.tpo.mq.producer.msg.ICallBack;
import com.tpo.mq.producer.publish.IRPCPublish;

public class RPCPuslisher extends AbsPublisher implements IRPCPublish
{
	private Logger logger = Logger.getLogger(RPCPuslisher.class);

	// 队列名称
	String queueName;
	// 消息标识符
	String correlationId;
	// RPC 反馈消息接收器
	QueueingConsumer queueingConsumer;

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
	public RPCPuslisher(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	/**
	 * 初始 RPC 请求参数
	 * 
	 * @param exchangeName
	 *            路由器名称
	 * @param queueName
	 *            RPC消息队列名称
	 * @throws Exception 
	 */
	public void initRpc(String exchangeName, String queueName) throws Exception
	{
		super.exchangeName = exchangeName;
		this.queueName = queueName;
		try
		{
			// 由路由器创建队列
			this.queueName = channel.queueDeclare().getQueue(); 
		}
		catch (Exception e)
		{
			logger.error("Init rpc publisher error !", e);
			throw new Exception("Init rpc publisher error !", e);
		}
	}

	@Override
	public void publishMsg(byte[] publishMsg, ICallBack callBack)
	{
		// 参数校验
		if (!validateRpcArgs(publishMsg, callBack))
		{
			return;
		}

		// 初始化RPC参数
		String correlationId = UUID.randomUUID().toString();
		props = new BasicProperties.Builder().replyTo(queueName).correlationId(correlationId).build();
		queueingConsumer = new QueueingConsumer(channel);

		// 发送 RPC 请求
		try
		{
			channel.basicPublish(exchangeName, queueName, props, publishMsg);
			channel.basicConsume(queueName, queueingConsumer);
		}
		catch (Exception e)
		{
			logger.error("Publis msg to rpc server error !", e);
		}

		// 等待 RPC 结果反馈 并 处理
		while (true)
		{
			Delivery delivery = null;
			try
			{
				delivery = queueingConsumer.nextDelivery();
			}
			catch (Exception e)
			{
				logger.error("Receive msg from RPC service error !", e);
			}
			logger.info("Receive RPC response from service !");

			// 验证消息
			if (delivery.getProperties().getCorrelationId().equals(correlationId))
			{
				callBack.handleCallBack(delivery.getBody());
			}
			logger.info("Handle RPC response success !");
		}
	}

	/**
	 * RPC 参数校验
	 * 
	 * @param publishMsg
	 *            RPC请求消息
	 * @param callBack
	 *            PRC反馈处理器
	 * @return boolean false:当消息未空 或 反馈处理器为空时
	 */
	private boolean validateRpcArgs(byte[] publishMsg, ICallBack callBack)
	{
		// 消息验证
		if (ArrayUtils.isEmpty(publishMsg))
		{
			logger.error("Message can't be empty !");
			return false;
		}

		// 反馈处理器验证
		if (null == callBack)
		{
			logger.error("CallBack can't be null !");
			return false;
		}
		return true;
	}

}
