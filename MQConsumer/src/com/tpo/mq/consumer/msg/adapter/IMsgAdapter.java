package com.tpo.mq.consumer.msg.adapter;

public interface IMsgAdapter<T>
{
	/**
	 * 消息适配
	 * 
	 * @param byteMsg
	 *            来自RabbitMQ服务器的二进制消息
	 * @return T 消息类型，第三方功能需要的消息数据结构
	 */
	public T parseMessage(byte[] byteMsg);
}
