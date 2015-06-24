package com.tpo.mq.consumer.msg.customer;

public interface IMsgCustomer<T> 
{
	/**
	 * 处理接收到的消息
	 * 
	 * @description 非RPC消息时，建议使用线程异步处理消息
	 * @param t
	 *            消息适配器解析的消息格式
	 * @return byte[] RPC 消息结果反馈
	 */
	public byte[] receiveMsg(T t);

}
