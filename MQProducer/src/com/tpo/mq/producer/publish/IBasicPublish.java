package com.tpo.mq.producer.publish;

public interface IBasicPublish extends IPublish
{

	/**
	 * 发送消息到 RabbitMQ 服务器
	 * 
	 * @param publishMsg
	 *            发布的消息
	 */
	void publishMsg(byte[] publishMsg);

}
