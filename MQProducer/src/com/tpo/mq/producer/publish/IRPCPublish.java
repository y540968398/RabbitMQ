package com.tpo.mq.producer.publish;

import com.tpo.mq.producer.msg.ICallBack;

public interface IRPCPublish extends IPublish
{
	/**
	 * 发送 RPC 请求到 RabbitMQ 服务器
	 * 
	 * @param publishMsg
	 *            消息
	 */
	void publishMsg(byte[] publishMsg, ICallBack callBack);
}
