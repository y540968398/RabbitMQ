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
	 * 从配置中加载消息发送器
	 * 
	 * @return IPublish 消息发送器
	 * @throws Exception
	 *             加载发送器错误
	 */
	public static IPublish loadPublisher() throws Exception
	{
		// 获取配置文件中的 "消息类型" 定义
		EMSGType msgType = (EMSGType) EnumUtils.getEnum(EMSGType.class, CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE));
		// 验证 "消息类型" 是否支持
		if (null == msgType)
		{
			logger.error("Msg type [" + CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE) + "] is not defined !");
			return null;
		}

		// 根据 "消息类型" 配置初始化相应的消息接收器
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
		// 创建 队列 发送器到服务器的 管道连接
		QueuePublish queuePublish = new QueuePublish(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// 初始化 队列 信息
		queuePublish.initQueue(CfgUtil.get(ConfConstant.QUEUE_QUEUENAME),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_DURABLE), CfgUtil.getBoolean(ConfConstant.QUEUE_IS_EXCLUSIVE),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_AUTO_DELETE), CfgUtil.getMapConfig());

		return queuePublish;
	}

	private static IRPCPublish rpcPublisher() throws Exception
	{
		// 创建 RPC 发送器到服务器的 管道连接
		RPCPuslisher rpcPuslisher = new RPCPuslisher(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// 初始化 RPC队列 信息
		rpcPuslisher.initRpc(CfgUtil.get(ConfConstant.EXCHANGE_NAME), CfgUtil.get(ConfConstant.QUEUE_QUEUENAME));

		return rpcPuslisher;
	}

	private static IBasicPublish exchangePublisher() throws Exception
	{
		// 创建 路由器消息 发送器到服务器的 管道连接
		ExchangePublisher exchangePublisher = new ExchangePublisher(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// 初始化 路由器 信息
		exchangePublisher.initExchange(CfgUtil.get(ConfConstant.EXCHANGE_NAME),
		        CfgUtil.get(ConfConstant.EXCHANGE_TYPE), CfgUtil.get(ConfConstant.EXCHANGE_ROUNTING_KEY));

		return exchangePublisher;
	}
}
