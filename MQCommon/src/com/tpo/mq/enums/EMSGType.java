package com.tpo.mq.enums;

public enum EMSGType implements ITypeEnums
{

	QUEUE("ququeMsg", "简单队列消息！"), 
	RPC("rpcMsg", "RPC消息类型！"),
	EXCHANGE("exchangeMsg", "路由器消息类型，使用路由器来接收处理消息！"); 

	private String confName;
	private String typeDesc;

	private EMSGType(String name, String desc)
	{
		this.confName = name;
		this.typeDesc = desc;
	}

	@Override
	public String confName()
	{
		return confName;
	}

	@Override
	public String typeDesc()
	{
		return typeDesc;
	}

	@Override
	public String toString()
	{
		return name() + ordinal() + confName + typeDesc;
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
