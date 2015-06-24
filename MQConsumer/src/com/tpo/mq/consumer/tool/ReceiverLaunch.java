package com.tpo.mq.consumer.tool;

import org.apache.log4j.Logger;

import com.tpo.mq.consumer.msg.adapter.IMsgAdapter;
import com.tpo.mq.consumer.msg.customer.IMsgCustomer;
import com.tpo.mq.consumer.receive.IReceiver;

public class ReceiverLaunch
{
	private static Logger logger = Logger.getLogger(ReceiverLaunch.class);

	/**
	 * 启动服务接收来自RabbitMQ的消息
	 *
	 * @description 使用配置文件中定义的接收器接收消息
	 * @param msgAdapter
	 *            消息适配器
	 * @param msgCustomer
	 *            消息处理器
	 * @throws Exception 
	 */
	public static <T> void launch(IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer) throws Exception
	{
		launch(null, msgAdapter, msgCustomer);
	}

	/**
	 * 启动服务接收来自RabbitMQ的消息
	 * 
	 * @param receiver
	 *            消息接收器
	 * @param msgAdapter
	 *            消息适配器
	 * @param msgCustomer
	 *            消息处理器
	 * @throws Exception 
	 */
	public static <T> void launch(IReceiver<T> receiver, IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer) throws Exception
	{
		if (!valiadteArgs(msgAdapter, msgCustomer))
		{
			logger.error("Arguments aren't valid !");
			return;
		}

		if (null == receiver)
		{
			// 消息处理器未定义时，从配置文件加载定义的处理器
			receiver = ReceiverFactory.loadReceiver();
			logger.info("Load receiver from config success !");
		}
		// 处理消息
		try
		{
			logger.info("Receiver start acept message from rabbit mq server !");
			receiver.receive(msgAdapter, msgCustomer);
		}
		catch (Exception e)
		{
			logger.error("Receiver deal msg error !", e);
		}
	}

	/**
	 * 参数验证
	 * 
	 * @param msgAdapter
	 *            消息适配器
	 * @param msgCustomer
	 *            消息处理器
	 * @return boolean false: 当 消息适配器 或 消息处理器 为空时
	 */
	private static <T> boolean valiadteArgs(IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer)
	{
		if (null == msgAdapter)
		{
			logger.error("Message adapter can't be null !");
			return false;
		}

		if (null == msgCustomer)
		{
			logger.error("Message customer can't be null !");
			return false;
		}
		return true;
	}

}
