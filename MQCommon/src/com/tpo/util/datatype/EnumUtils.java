package com.tpo.util.datatype;

import com.tpo.mq.enums.ITypeEnums;

public class EnumUtils
{

	/**
	 * 验证值是否为指定枚举的定义
	 * 
	 * @param enumClass
	 *            指定枚举类型
	 * @param value
	 *            待验证的值
	 * @return boolean true: 当值为指定类型的定义时
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
