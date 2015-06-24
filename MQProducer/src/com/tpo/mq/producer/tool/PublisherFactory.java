package com.tpo.mq.producer.tool;

import org.apache.log4j.Logger;

import com.tpo.mq.constant.ConfConstant;
import com.tpo.mq.enums.EMSGType;
import com.tpo.mq.producer.publish.IBasicPublish;
import com.tpo.mq.producer.publish.IPublish;
import com.tpo.mq.producer.publish.IRPCPublish;
import com.tpo.mq.producer.publish.impl.ExchangePublisher;
import com.tpo.mq.producer.publish.impl.QueuePublish;
import com.tpo.mq.producer.publish.impl.RPCPuslisher;
import com.tpo.util.conf.CfgUtil;
import com.tpo.util.datatype.EnumUtils;

public class PublisherFactory
{
	private static Logger logger = Logger.getLogger(PublisherFactory.class);

	/**
	 * �������м�����Ϣ������
	 * 
	 * @return IPublish ��Ϣ������
	 * @throws Exception
	 *             ���ط���������
	 */
	public static IPublish loadPublisher() throws Exception
	{
		// ��ȡ�����ļ��е� "��Ϣ����" ����
		EMSGType msgType = (EMSGType) EnumUtils.getEnum(EMSGType.class, CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE));
		// ��֤ "��Ϣ����" �Ƿ�֧��
		if (null == msgType)
		{
			logger.error("Msg type [" + CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE) + "] is not defined !");
			return null;
		}

		// ���� "��Ϣ����" ���ó�ʼ����Ӧ����Ϣ������
		IPublish publish = null;
		switch (msgType)
		{
		case QUEUE:
			publish = queuePublisher();
			break;
		case RPC:
			publish = rpcPublisher();
			break;
		case EXCHANGE:
			publish = exchangePublisher();
			break;
		}
		logger.info("Load " + msgType.confName() + " publisher success !");
		return publish;
	}

	private static IBasicPublish queuePublisher() throws Exception
	{
		// ���� ���� ���������������� �ܵ�����
		QueuePublish queuePublish = new QueuePublish(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// ��ʼ�� ���� ��Ϣ
		queuePublish.initQueue(CfgUtil.get(ConfConstant.QUEUE_QUEUENAME),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_DURABLE), CfgUtil.getBoolean(ConfConstant.QUEUE_IS_EXCLUSIVE),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_AUTO_DELETE), CfgUtil.getMapConfig());

		return queuePublish;
	}

	private static IRPCPublish rpcPublisher() throws Exception
	{
		// ���� RPC ���������������� �ܵ�����
		RPCPuslisher rpcPuslisher = new RPCPuslisher(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// ��ʼ�� RPC���� ��Ϣ
		rpcPuslisher.initRpc(CfgUtil.get(ConfConstant.EXCHANGE_NAME), CfgUtil.get(ConfConstant.QUEUE_QUEUENAME));

		return rpcPuslisher;
	}

	private static IBasicPublish exchangePublisher() throws Exception
	{
		// ���� ·������Ϣ ���������������� �ܵ�����
		ExchangePublisher exchangePublisher = new ExchangePublisher(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// ��ʼ�� ·���� ��Ϣ
		exchangePublisher.initExchange(CfgUtil.get(ConfConstant.EXCHANGE_NAME),
		        CfgUtil.get(ConfConstant.EXCHANGE_TYPE), CfgUtil.get(ConfConstant.EXCHANGE_ROUNTING_KEY));

		return exchangePublisher;
	}
}
