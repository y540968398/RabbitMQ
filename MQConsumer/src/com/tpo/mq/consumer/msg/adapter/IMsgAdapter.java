package com.tpo.mq.consumer.msg.adapter;

public interface IMsgAdapter<T>
{
	/**
	 * ��Ϣ����
	 * 
	 * @param byteMsg
	 *            ����RabbitMQ�������Ķ�������Ϣ
	 * @return T ��Ϣ���ͣ�������������Ҫ����Ϣ���ݽṹ
	 */
	public T parseMessage(byte[] byteMsg);
}
