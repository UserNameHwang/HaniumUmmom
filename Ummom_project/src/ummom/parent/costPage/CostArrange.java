package ummom.parent.costPage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Administrator
 *	준비물 리스트를 위한 클래스 정의
 */
public class CostArrange {

	private ArrayList<HashMap<String, String>> Arrlist = new ArrayList<HashMap<String, String>>();

	public ArrayList<HashMap<String, String>> getList()
	{
		return Arrlist;
	}
	
	public void setList(ArrayList<HashMap<String, String>> list)
	{
		this.Arrlist = list;
	}
}
