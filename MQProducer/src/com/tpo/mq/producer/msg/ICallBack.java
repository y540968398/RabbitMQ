package com.tpo.mq.producer.msg;

public interface ICallBack
{

	/**
	 * ���� RPC ���
	 * 
	 * @description ������Ϣ����������������������ķ�����<br/>
	 *              �����첽����
	 * @param callBackMsg
	 *            RPC ���
	 */
	void handleCallBack(byte[] callBackMsg);

}
