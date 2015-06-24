package com.tpo.util.datatype;

import com.tpo.mq.enums.ITypeEnums;

public class EnumUtils
{

	/**
	 * ��ֵ֤�Ƿ�Ϊָ��ö�ٵĶ���
	 * 
	 * @param enumClass
	 *            ָ��ö������
	 * @param value
	 *            ����֤��ֵ
	 * @return boolean true: ��ֵΪָ�����͵Ķ���ʱ
	 */
	@SuppressWarnings("rawtypes")
	public static boolean containsEnum(Class enumClass, String value)
	{
		ITypeEnums[] enumArr = (ITypeEnums[]) enumClass.getEnumConstants();
		for (int i = 0; i < enumArr.length; i++)
		{
			if (enumArr[i].same(value))
			{
				return true;
			}
		}
		return false;
	}

	public static ITypeEnums getEnum(Class enumClass, String value)
	{
		ITypeEnums[] enumArr = (ITypeEnums[]) enumClass.getEnumConstants();
		for (int i = 0; i < enumArr.length; i++)
		{
			if (enumArr[i].same(value))
			{
				return enumArr[i];
			}
		}
		return null;
	}

}
