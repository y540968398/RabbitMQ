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
	 * 从配置文件中加载消息处理器
	 * 
	 * @description 加载配置文件中的 消息类型，并创建对应的消息接收器。<br/>
	 *              注意:在调用该方法前需要保证 配置文件已经被加载
	 * 
	 * @return IReceiver<T> 消息处理器
	 * @throws Exception
	 */
	public static <T> IReceiver<T> loadReceiver() throws Exception
	{
		// 获取配置文件中的 "消息类型" 定义
		EMSGType msgType = (EMSGType) EnumUtils.getEnum(EMSGType.class, CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE));
		// 验证 "消息类型" 是否支持
		if (null == msgType)
		{
			logger.error("Msg type [" + CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE) + "] is not defined !");
			throw new Exception("Msg type [" + CfgUtil.get(ConfConstant.GLOBAL_MSG_TYPE) + "] is not defined !");
		}

		// 根据 "消息类型" 配置初始化相应的消息接收器
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
	 * 队列 消息接受者
	 * 
	 * @return IReceiver<T> 消息接受者
	 * @throws Exception
	 *             创建消息接收者失败
	 */
	private static <T> IReceiver<T> queueRecv() throws Exception
	{
		// 连接到服务器 并 创建路由器
		QueueReceiver<T> queueReceiver = new QueueReceiver<T>(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// 初始化队列信息
		queueReceiver.initQueue(CfgUtil.get(ConfConstant.QUEUE_QUEUENAME),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_DURABLE), CfgUtil.getBoolean(ConfConstant.QUEUE_IS_EXCLUSIVE),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_AUTO_DELETE), CfgUtil.getMapConfig());

		return queueReceiver;
	}

	/**
	 * RPC 消息接受者
	 * 
	 * @return IReceiver<T> 消息接受者
	 * @throws Exception
	 *             创建消息接收者失败
	 */
	private static <T> IReceiver<T> rpcRecv() throws Exception
	{
		// 连接到服务器 并 创建路由器
		RPCReceiver<T> rpcReceiver = new RPCReceiver<T>(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// 初始化队列信息
		rpcReceiver.initQueue(CfgUtil.get(ConfConstant.QUEUE_QUEUENAME),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_DURABLE), CfgUtil.getBoolean(ConfConstant.QUEUE_IS_EXCLUSIVE),
		        CfgUtil.getBoolean(ConfConstant.QUEUE_IS_AUTO_DELETE), CfgUtil.getMapConfig());

		return rpcReceiver;
	}

	/**
	 * 路由器 消息接受者
	 * 
	 * @return IReceiver<T> 消息接受者
	 * @throws Exception
	 *             创建消息接收者失败
	 */
	private static <T> IReceiver<T> exchangeRecv() throws Exception
	{
		// 连接到服务器 并 创建路由器 及 队列
		ExchangeReceiver<T> exchangeReceiver = new ExchangeReceiver<T>(CfgUtil.get(ConfConstant.CONNECT_HOST),
		        CfgUtil.get(ConfConstant.CONNECT_USER), CfgUtil.get(ConfConstant.CONNECT_PASS));

		// 初始化 topic 路由及队列 参数
		exchangeReceiver.initExchange(CfgUtil.get(ConfConstant.EXCHANGE_NAME), CfgUtil.get(ConfConstant.EXCHANGE_TYPE),
		        CfgUtil.getBoolean(ConfConstant.EXCHANGE_IS_DURABLE), CfgUtil.get(ConfConstant.EXCHANGE_ROUNTING_KEY));

		return exchangeReceiver;
	}

}
