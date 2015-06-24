package com.tpo.mq.consumer.receive;

import com.tpo.mq.consumer.msg.adapter.IMsgAdapter;
import com.tpo.mq.consumer.msg.customer.IMsgCustomer;

public interface IReceiver<T>
{
	/**
	 * ���մ�����Ϣ
	 * 
	 * @description �������� RabbitMQ ����������Ϣ(������)��ͨ���������Ϣ������������ǰ��Ϣ��������<br/>
	 *              ����<b>�첽����</b>��Ϣ��
	 * @param msgAdapter
	 *            ��Ϣ������
	 * @param msgCustomer
	 *            ��Ϣ������
	 * @throws Exception
	 */
	public void receive(IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer) throws Exception;

	/**
	 * �رյ� RabbitMQ ����˵�����
	 * 
	 * @Description ˳��رտͻ������������� �ܵ� �� ����
	 */
	public void close();

}
