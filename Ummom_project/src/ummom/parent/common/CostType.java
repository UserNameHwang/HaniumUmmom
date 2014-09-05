package ummom.parent.common;

import java.util.List;

public class CostType {

	String Type;
	List<CostName> Array;
	
	public CostType(String type, List<CostName> array)
	{
		this.Type = type;
		this.Array = array;
	}
}
