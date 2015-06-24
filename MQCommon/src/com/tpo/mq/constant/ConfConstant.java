package com.tpo.mq.constant;

public interface ConfConstant
{
	/** 全局消息类型 (queueMsg, exchangeMsg, rpcMsg) */
	String GLOBAL_MSG_TYPE = "msgType";

	// 连接配置参数
	/** RabbitMQ 服务器ip */
	String CONNECT_HOST = "host";
	/** 服务器登录用户名 */
	String CONNECT_USER = "user";
	/** 服务器登录密码 */
	String CONNECT_PASS = "pass";

	// 队列配置参数
	/** 队列名称 */
	String QUEUE_QUEUENAME = "queueName";
	/** 是否持久化队列 */
	String QUEUE_IS_DURABLE = "queueOsDurable";
	/** 是否单一队列 */
	String QUEUE_IS_EXCLUSIVE = "queueisExclusive";
	/** 是否自动删除，当服务器空间不足时 */
	String QUEUE_IS_AUTO_DELETE = "queueisAutoDelete";

	// 路由器相关参数
	/** 路由器名称 */
	String EXCHANGE_NAME = "exchangeName";
	/** 路由器类型 */
	String EXCHANGE_TYPE = "exchangeType";
	/** 路由消息是否持久化 */
	String EXCHANGE_IS_DURABLE = "exchangeIsDurable";
	/** 消息路由关键字 */
	String EXCHANGE_ROUNTING_KEY = "exchangeRoutingKey";
}
