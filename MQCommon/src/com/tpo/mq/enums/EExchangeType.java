package com.tpo.mq.enums;

public enum EExchangeType implements ITypeEnums
{

	FANOUT("fanout", "fanout ·������������·�ɼ���"), 
	DIRECT("direct", "direct ·����������·�ɼ�"), 
	TOPIC("topic", "topic ·������ʹ��'*', '#'ģʽƥ�� ������·�ɼ���");

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
