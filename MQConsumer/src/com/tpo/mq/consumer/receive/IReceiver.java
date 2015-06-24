package com.tpo.mq.consumer.receive;

import com.tpo.mq.consumer.msg.adapter.IMsgAdapter;
import com.tpo.mq.consumer.msg.customer.IMsgCustomer;

public interface IReceiver<T>
{
	/**
	 * 接收处理消息
	 * 
	 * @description 接收来自 RabbitMQ 服务器的消息(二进制)，通过定义的消息适配器解析当前消息，并处理。<br/>
	 *              建议<b>异步处理</b>消息。
	 * @param msgAdapter
	 *            消息适配器
	 * @param msgCustomer
	 *            消息处理器
	 * @throws Exception
	 */
	public void receive(IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer) throws Exception;

	/**
	 * 关闭到 RabbitMQ 服务端的连接
	 * 
	 * @Description 顺序关闭客户端至服务器的 管道 和 链接
	 */
	public void close();

}
