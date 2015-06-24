package com.tpo.mq.consumer.receive.impl;

import org.apache.log4j.Logger;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.tpo.mq.consumer.msg.adapter.IMsgAdapter;
import com.tpo.mq.consumer.msg.customer.IMsgCustomer;
import com.tpo.mq.consumer.receive.IReceiver;

public class RPCReceiver<T> extends QueueReceiver<T> implements IReceiver<T>
{
	private Logger logger = Logger.getLogger(RPCReceiver.class);

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
	public RPCReceiver(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	@Override
	public void receive(IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer) throws Exception
	{
		while (true)
		{
			logger.info("Receive new RPC request !");
			// 收到客户端的一次 RPC 请求
			Delivery deliver = queueingConsumer.nextDelivery();

			// 处理 RPC 消息
			byte[] result = msgCustomer.receiveMsg(msgAdapter.parseMessage(deliver.getBody()));

			// 初始化反馈参数
			BasicProperties props = deliver.getProperties();
			BasicProperties responseProps = new BasicProperties.Builder().correlationId(props.getCorrelationId())
			        .build();

			// 反馈 RPC 结果
			channel.basicPublish("", props.getReplyTo(), responseProps, result);
			logger.info("Response RPC request !");
		}
	}

}
