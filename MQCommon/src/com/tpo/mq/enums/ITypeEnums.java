package com.tpo.mq.enums;

public interface ITypeEnums
{
	/**
	 * 配置名称
	 * 
	 * @return String 类型在配置文件中的配置名称
	 */
	String confName();

	/**
	 * 类型含义
	 * 
	 * @return String 类型描述信息
	 */
	String typeDesc();

	/**
	 * 判断是否同定义相同
	 * 
	 * @param value
	 *            类型定义的值
	 * @return boolean true:值符合类型的定义时
	 */
	boolean same(String value);

}
