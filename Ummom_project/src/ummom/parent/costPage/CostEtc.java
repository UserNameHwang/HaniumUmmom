package ummom.parent.costPage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Administrator
 *	기타 비용 리스트를 위한 클래스
 */
public class CostEtc {

	private ArrayList<HashMap<String, String>> Etclist = new ArrayList<HashMap<String, String>>();

	public ArrayList<HashMap<String, String>> getList()
	{
		return Etclist;
	}
	
	public void setList(ArrayList<HashMap<String, String>> list)
	{
		this.Etclist = list;
	}
}
