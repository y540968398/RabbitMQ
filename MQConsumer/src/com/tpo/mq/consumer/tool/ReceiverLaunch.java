package com.tpo.mq.consumer.tool;

import org.apache.log4j.Logger;

import com.tpo.mq.consumer.msg.adapter.IMsgAdapter;
import com.tpo.mq.consumer.msg.customer.IMsgCustomer;
import com.tpo.mq.consumer.receive.IReceiver;

public class ReceiverLaunch
{
	private static Logger logger = Logger.getLogger(ReceiverLaunch.class);

	/**
	 * ���������������RabbitMQ����Ϣ
	 *
	 * @description ʹ�������ļ��ж���Ľ�����������Ϣ
	 * @param msgAdapter
	 *            ��Ϣ������
	 * @param msgCustomer
	 *            ��Ϣ������
	 * @throws Exception 
	 */
	public static <T> void launch(IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer) throws Exception
	{
		launch(null, msgAdapter, msgCustomer);
	}

	/**
	 * ���������������RabbitMQ����Ϣ
	 * 
	 * @param receiver
	 *            ��Ϣ������
	 * @param msgAdapter
	 *            ��Ϣ������
	 * @param msgCustomer
	 *            ��Ϣ������
	 * @throws Exception 
	 */
	public static <T> void launch(IReceiver<T> receiver, IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer) throws Exception
	{
		if (!valiadteArgs(msgAdapter, msgCustomer))
		{
			logger.error("Arguments aren't valid !");
			return;
		}

		if (null == receiver)
		{
			// ��Ϣ������δ����ʱ���������ļ����ض���Ĵ�����
			receiver = ReceiverFactory.loadReceiver();
			logger.info("Load receiver from config success !");
		}
		// ������Ϣ
		try
		{
			logger.info("Receiver start acept message from rabbit mq server !");
			receiver.receive(msgAdapter, msgCustomer);
		}
		catch (Exception e)
		{
			logger.error("Receiver deal msg error !", e);
		}
	}

	/**
	 * ������֤
	 * 
	 * @param msgAdapter
	 *            ��Ϣ������
	 * @param msgCustomer
	 *            ��Ϣ������
	 * @return boolean false: �� ��Ϣ������ �� ��Ϣ������ Ϊ��ʱ
	 */
	private static <T> boolean valiadteArgs(IMsgAdapter<T> msgAdapter, IMsgCustomer<T> msgCustomer)
	{
		if (null == msgAdapter)
		{
			logger.error("Message adapter can't be null !");
			return false;
		}

		if (null == msgCustomer)
		{
			logger.error("Message customer can't be null !");
			return false;
		}
		return true;
	}

}
