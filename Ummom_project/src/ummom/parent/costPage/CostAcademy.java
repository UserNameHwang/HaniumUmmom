package ummom.parent.costPage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Administrator
 *	학원비 리스트를 위한 클래스 정의
 */
public class CostAcademy {

	
	private ArrayList<HashMap<String, String>> Acalist = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> cost_id = new ArrayList<HashMap<String,String>>();
	
	public ArrayList<HashMap<String, String>> getList()
	{
		return Acalist;
	}
	
	public void setList(ArrayList<HashMap<String, String>> list)
	{
		this.Acalist = list;
	}
	
	public ArrayList<HashMap<String, String>> getIDList()
	{
		return cost_id;
	}
	
	public void setIDList(ArrayList<HashMap<String, String>> list)
	{
		this.cost_id = list;
	}
}
