package com.tpo.mq.producer.msg;

public interface ICallBack
{

	/**
	 * 处理 RPC 结果
	 * 
	 * @description 发布消息到服务器，并处理服务器的反馈。<br/>
	 *              建议异步处理
	 * @param callBackMsg
	 *            RPC 结果
	 */
	void handleCallBack(byte[] callBackMsg);

}
