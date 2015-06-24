package com.tpo.mq.consumer.msg.customer;

public interface IMsgCustomer<T> 
{
	/**
	 * ������յ�����Ϣ
	 * 
	 * @description ��RPC��Ϣʱ������ʹ���߳��첽������Ϣ
	 * @param t
	 *            ��Ϣ��������������Ϣ��ʽ
	 * @return byte[] RPC ��Ϣ�������
	 */
	public byte[] receiveMsg(T t);

}
