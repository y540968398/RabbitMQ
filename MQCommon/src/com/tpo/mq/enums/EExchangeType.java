package com.tpo.mq.enums;

public enum EExchangeType implements ITypeEnums
{

	FANOUT("fanout", "fanout 路由器，不处理路由键！"), 
	DIRECT("direct", "direct 路由器，处理路由键"), 
	TOPIC("topic", "topic 路由器，使用'*', '#'模式匹配 来处理路由键！");

	private String confName;
	private String typeDesc;

	private EExchangeType(String name, String desc)
	{
		this.confName = name;
		this.typeDesc = desc;
	}

	public String confName()
	{
		return confName;
	}

	public String typeDesc()
	{
		return typeDesc;
	}

	@Override
	public String toString()
	{
		return name() + " " + ordinal() + " " + confName + " " + typeDesc;
	}

	@Override
	public boolean same(String value)
	{
		if (name().equals(value) || confName.equals(value))
		{
			return true;
		}
		return false;
	}

}
