package com.tpo.mq.enums;

public interface ITypeEnums
{
	/**
	 * ��������
	 * 
	 * @return String �����������ļ��е���������
	 */
	String confName();

	/**
	 * ���ͺ���
	 * 
	 * @return String ����������Ϣ
	 */
	String typeDesc();

	/**
	 * �ж��Ƿ�ͬ������ͬ
	 * 
	 * @param value
	 *            ���Ͷ����ֵ
	 * @return boolean true:ֵ�������͵Ķ���ʱ
	 */
	boolean same(String value);

}
