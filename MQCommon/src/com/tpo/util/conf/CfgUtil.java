package com.tpo.util.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CfgUtil
{
	private static Logger logger = Logger.getLogger(CfgUtil.class);

	/** ϵͳ���� */
	static Properties properties = new Properties();
	/** ��չ�������� */
	static Properties properties2Map = new Properties();

	/**
	 * ��ʼ����ͬ������Ϣ
	 */
	public static void initConfig()
	{
		logger.info("Loading config files !");
		// log4j ��־����
		initLogConf();
		// ϵͳ��������
		initSysConf();
		// �����������ã�ת��ΪMap(��ѡ)
		initMapConf();
		logger.info("Load config files success !");
	}

	/**
	 * ���� log4j ��־���ò���
	 * 
	 * @description ��־·����/conf/log4j.properties
	 */
	private static void initLogConf()
	{
		FileInputStream inputStream = null;
		try
		{
			try
			{
				String logConfPath = System.getProperty("user.dir") + "/conf/log4j.properties";
				System.out.println("log4jPath : " + logConfPath);
				inputStream = new FileInputStream(new File(logConfPath));
				PropertyConfigurator.configure(inputStream);
			}
			finally
			{
				inputStream.close();
			}
		}
		catch (Exception e)
		{
			logger.error("Load log4j.properties failed !", e);
		}
		logger.debug("Load log4j.properties file success !");
	}

	/**
	 * ���� ϵͳ ϵͳ ���ò���
	 * 
	 * @description ��־·����/conf/sysConfig.properties
	 */
	private static void initSysConf()
	{
		InputStream confInputStream = null;
		try
		{
			try
			{
				String sysConfPath = System.getProperty("user.dir") + "/conf/sysConfig.properties";
				logger.debug("sysConfigPath : " + sysConfPath);
				confInputStream = new FileInputStream(new File(sysConfPath));
				properties.load(confInputStream);
			}
			finally
			{
				confInputStream.close();
			}
		}
		catch (Exception e)
		{
			logger.error("Load sysConfig failed !", e);
		}
		logger.debug("Load sysConfig file success !");
	}

	/**
	 * ���� RabbitMQ��Ϣ�ͻ��� �������� ����
	 * 
	 * @description ��־·����/conf/msgConfig.properties
	 */
	private static void initMapConf()
	{
		File mapConfFile = new File(System.getProperty("user.dir") + "/conf/mapConfig.properties");
		if (!mapConfFile.exists())
		{
			logger.debug("Msg queue conf not exists !");
			return;
		}

		InputStream msgConfInputStream = null;
		try
		{
			try
			{
				logger.debug("sysConfigPath : " + mapConfFile.getPath());
				msgConfInputStream = new FileInputStream(mapConfFile);
				properties2Map.load(msgConfInputStream);
			}
			finally
			{
				msgConfInputStream.close();
			}
		}
		catch (Exception e)
		{
			logger.error("Load msgConfig failed !", e);
		}
		logger.debug("Load msgConfig file success !");
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param key
	 *            ������Ϣ��
	 * @return String ������Ϣ��ֵ
	 */
	public static String get(String key)
	{
		return properties.getProperty(key);
	}

	/**
	 * ��ȡ������Ϣ�� ����ֵ
	 * 
	 * @param key
	 *            ������Ϣ��
	 * @return boolean ������Ϣ��ֵ
	 */
	public static boolean getBoolean(String key)
	{
		return Boolean.parseBoolean(properties.getProperty(key));
	}

	/**
	 * ��չ����תΪ Map ��ʽ
	 * 
	 * @return Map<String, Object> ��չ����Map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> getMapConfig()
	{
		if (properties2Map.isEmpty())
		{
			return null;
		}
		return new HashMap<String, Object>((Map) properties2Map);
	}

	// public static void main(String[] args)
	// {
	// ConfigUtil.initConfig();
	// logger.debug(ConfigUtil.getProperty("certifyPath"));
	// logger.debug(ConfigUtil.getProperty("certifyPass"));
	// }

}
