package ummom.parent.costPage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Administrator
 *	용돈 리스트를 위한 클래스 정의
 */
public class CostCash {

	private ArrayList<HashMap<String, String>> Cashlist = new ArrayList<HashMap<String, String>>();

	public ArrayList<HashMap<String, String>> getList()
	{
		return Cashlist;
	}
	
	public void setList(ArrayList<HashMap<String, String>> list)
	{
		this.Cashlist = list;
	}
}
