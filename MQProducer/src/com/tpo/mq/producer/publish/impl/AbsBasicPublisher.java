package com.tpo.mq.producer.publish.impl;

import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.tpo.mq.producer.publish.IBasicPublish;

public class AbsBasicPublisher extends AbsPublisher implements IBasicPublish
{
	private Logger logger = Logger.getLogger(AbsBasicPublisher.class);

	/**
	 * ��ʼ����Ϣ������
	 * 
	 * @description ���ӵ� RabbitMQ ������������ͨ�� �ܵ�
	 * 
	 * @param host
	 *            RabbitMQ-VHost IP��ַ
	 * @param user
	 *            RabbitMQ-VHost �û���
	 * @param pass
	 *            RabbitMQ-VHost ����
	 */
	public AbsBasicPublisher(String host, String user, String pass)
	{
		super(host, user, pass);
	}

	@Override
	public void publishMsg(byte[] publishMsg)
	{
		// ��Ϣ�Ϸ�����֤
		if (ArrayUtils.isEmpty(publishMsg))
		{
			logger.error("Message can't be empty !");
			return;
		}

		// ������Ϣ�� RabbitMQ ������
		try
		{
			channel.basicPublish(exchangeName, routingKey, props, publishMsg);
		}
		catch (IOException e)
		{
			logger.error("Publish fanout exchange msg error !", e);
		}
		logger.info("Publish new messsage to server !");
	}
}
