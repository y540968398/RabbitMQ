package com.tpo.mq.producer.publish;

public interface IPublish
{

	/**
	 * 顺序关闭 管道 及 连接
	 */
	public void close();

}
