package com.tpo.mq.producer.publish;

import com.tpo.mq.producer.msg.ICallBack;

public interface IRPCPublish extends IPublish
{
	/**
	 * ���� RPC ���� RabbitMQ ������
	 * 
	 * @param publishMsg
	 *            ��Ϣ
	 */
	void publishMsg(byte[] publishMsg, ICallBack callBack);
}
