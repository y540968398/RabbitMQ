package com.tpo.mq.consumer.tool;

import org.apache.log4j.Logger;

import com.tpo.mq.constant.ConfConstant;
import com.tpo.mq.consumer.receive.IReceiver;
import com.tpo.mq.consumer.receive.impl.ExchangeReceiver;
import com.tpo.mq.consumer.receive.impl.QueueReceiver;
import com.tpo.mq.consumer.receive.impl.RPCReceiver;
import com.tpo.mq.enums.EMSGType;
import com.tpo.util.conf.CfgUtil;
import com.tpo.util.datatype.EnumUtils;

public class ReceiverFactory
{
	private static Logger logger = Logger.getLogger(ReceiverFactory.class);

	/**
	 * �������ļ��м�����Ϣ������
	 * 
	 * @description ���������ļ��е� ��Ϣ���ͣ���������Ӧ����Ϣ��������<br/>
	 *              ע��:�ڵ��ø÷���ǰ��Ҫ��֤ �����ļ��Ѿ�������
	 * 
	 * @return IReceiver<T> ��Ϣ������
	 * @throws Exception
	 */
	public static <T> IReceiver<T> loadReceiver() throws Exception
	{
		// ��ȡ�����ļ��е� "��Ϣ����" ����
		EMSGType msgType = (EMSGType) EnumUtils.getEnum(EMSGType.class, CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE));
		// ��֤ "��Ϣ����" �Ƿ�֧��
		if (null == msgType)
		{
			logger.error("Msg type [" + CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE) + "] is not defined !");
			throw new Exception("Msg type [" + CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE) + "] is not defined !");
		}

		// ���� "��Ϣ����" ���ó�ʼ����Ӧ����Ϣ������
		IReceiver<T> receiver = null;

		switch (msgType)
		{
		case QUEUE:
			receiver = queueRecv();
			break;
		case RPC:
			receiver = rpcRecv();
			break;
		case EXCHANGE:
			receiver = exchangeRecv();
			break;
		}
		logger.info("Load " + msgType.confName() + " receiver success !");
		return receiver;
	}

	/**
	 * ���� ��Ϣ������
	 * 
	 * @return IReceiver<T> ��Ϣ������
	 * @throws Exception
	 *             ������Ϣ������ʧ��
	 */
	private static <T> IReceiver<T> queueRecv() throws Exception
	{
		// ���ӵ������� �� ����·����
		QueueReceiver<T> queueReceiver = new QueueReceiver<T>(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// ��ʼ��������Ϣ
		queueReceiver.initQueue(CfgUtil.get(ConfConstant.QUEUE_QUEUENAME),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_DURABLE), CfgUtil.getBoolean(ConfConstant.QUEUE_IS_EXCLUSIVE),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_AUTO_DELETE), CfgUtil.getMapConfig());

		return queueReceiver;
	}

	/**
	 * RPC ��Ϣ������
	 * 
	 * @return IReceiver<T> ��Ϣ������
	 * @throws Exception
	 *             ������Ϣ������ʧ��
	 */
	private static <T> IReceiver<T> rpcRecv() throws Exception
	{
		// ���ӵ������� �� ����·����
		RPCReceiver<T> rpcReceiver = new RPCReceiver<T>(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// ��ʼ��������Ϣ
		rpcReceiver.initQueue(CfgUtil.get(ConfConstant.QUEUE_QUEUENAME),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_DURABLE), CfgUtil.getBoolean(ConfConstant.QUEUE_IS_EXCLUSIVE),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_AUTO_DELETE), CfgUtil.getMapConfig());

		return rpcReceiver;
	}

	/**
	 * ·���� ��Ϣ������
	 * 
	 * @return IReceiver<T> ��Ϣ������
	 * @throws Exception
	 *             ������Ϣ������ʧ��
	 */
	private static <T> IReceiver<T> exchangeRecv() throws Exception
	{
		// ���ӵ������� �� ����·���� �� ����
		ExchangeReceiver<T> exchangeReceiver = new ExchangeReceiver<T>(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// ��ʼ�� topic ·�ɼ����� ����
		exchangeReceiver.initExchange(CfgUtil.get(ConfConstant.EXCHANGE_NAME), CfgUtil.get(ConfConstant.EXCHANGE_TYPE),
		        CfgUtil.getBoolean(ConfConstant.EXCHANGE_IS_DURABLE), CfgUtil.get(ConfConstant.EXCHANGE_ROUNTING_KEY));

		return exchangeReceiver;
	}

}
