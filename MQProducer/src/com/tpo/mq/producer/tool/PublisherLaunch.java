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
	 * 使用配置定义的消息发送器发送消息
	 * 
	 * @param message
	 *            消息内容
	 * @return
	 * @throws Exception
	 *             加载消息发送器出错
	 */
	public static IPublish sendMessage(byte[] message) throws Exception
	{
		return sendMessage(null, null, message);
	}

	/**
	 * 发送消息到 RabbitMQ 服务器
	 * 
	 * @param publish
	 *            消息发送器
	 * @param callBack
	 *            反馈消息处理器
	 * @param message
	 *            消息内容
	 * @throws Exception
	 *             加载消息发送器出错
	 */
	public static IPublish sendMessage(IPublish publish, ICallBack callBack, byte[] message) throws Exception
	{
		if (null == publish)
		{
			// 发送器未定义时，使用配置文件加载定义的发送器
			publish = PublisherFactory.loadPublisher();
			logger.info("Load publisher from config success !");
		}

		// 发送消息
		EMSGType msgType = (EMSGType) EnumUtils.getEnum(EMSGType.class, CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE));
		switch (msgType)
		{
		case RPC:
			// 发送 RPC 消息
			((IRPCPublish) publish).publishMsg(message, callBack);
			break;
		case QUEUE:
		case EXCHANGE:
			// 发送消息
			((IBasicPublish) publish).publishMsg(message);
			break;
		}
		return publish;
	}

}
