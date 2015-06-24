package com.tpo.mq.producer.tool;

import org.apache.log4j.Logger;

import com.tpo.mq.constant.ConfConstant;
import com.tpo.mq.enums.EMSGType;
import com.tpo.mq.producer.msg.ICallBack;
import com.tpo.mq.producer.publish.IBasicPublish;
import com.tpo.mq.producer.publish.IPublish;
import com.tpo.mq.producer.publish.IRPCPublish;
import com.tpo.util.conf.CfgUtil;
import com.tpo.util.datatype.EnumUtils;

public class PublisherLaunch
{
	private static Logger logger = Logger.getLogger(PublisherLaunch.class);

	/**
	 * ʹ�����ö������Ϣ������������Ϣ
	 * 
	 * @param message
	 *            ��Ϣ����
	 * @return
	 * @throws Exception
	 *             ������Ϣ����������
	 */
	public static IPublish sendMessage(byte[] message) throws Exception
	{
		return sendMessage(null, null, message);
	}

	/**
	 * ������Ϣ�� RabbitMQ ������
	 * 
	 * @param publish
	 *            ��Ϣ������
	 * @param callBack
	 *            ������Ϣ������
	 * @param message
	 *            ��Ϣ����
	 * @throws Exception
	 *             ������Ϣ����������
	 */
	public static IPublish sendMessage(IPublish publish, ICallBack callBack, byte[] message) throws Exception
	{
		if (null == publish)
		{
			// ������δ����ʱ��ʹ�������ļ����ض���ķ�����
			publish = PublisherFactory.loadPublisher();
			logger.info("Load publisher from config success !");
		}

		// ������Ϣ
		EMSGType msgType = (EMSGType) EnumUtils.getEnum(EMSGType.class, CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE));
		switch (msgType)
		{
		case RPC:
			// ���� RPC ��Ϣ
			((IRPCPublish) publish).publishMsg(message, callBack);
			break;
		case QUEUE:
		case EXCHANGE:
			// ������Ϣ
			((IBasicPublish) publish).publishMsg(message);
			break;
		}
		return publish;
	}

}
