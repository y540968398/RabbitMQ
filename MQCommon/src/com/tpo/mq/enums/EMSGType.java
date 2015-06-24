package com.tpo.mq.enums;

public enum EMSGType implements ITypeEnums
{

	QUEUE("ququeMsg", "�򵥶�����Ϣ��"), 
	RPC("rpcMsg", "RPC��Ϣ���ͣ�"),
	EXCHANGE("exchangeMsg", "·������Ϣ���ͣ�ʹ��·���������մ�����Ϣ��"); 

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
