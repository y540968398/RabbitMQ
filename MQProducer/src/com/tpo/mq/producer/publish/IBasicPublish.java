package com.tpo.mq.producer.publish;

public interface IBasicPublish extends IPublish
{

	/**
	 * ������Ϣ�� RabbitMQ ������
	 * 
	 * @param publishMsg
	 *            ��������Ϣ
	 */
	void publishMsg(byte[] publishMsg);

}
