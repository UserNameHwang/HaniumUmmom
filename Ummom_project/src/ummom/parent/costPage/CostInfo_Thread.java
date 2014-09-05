package ummom.parent.costPage;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;


/**
 * @author Administrator
 *	서버로부터 지출 내용을 가져오기 위한
 *	별도의 스레드
 */
public class CostInfo_Thread extends AsyncTask<Void, Void, Void> {

	private int fin;
	private int cnt;
	
	private ArrayList<HashMap<String, String>> A_array = new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String, String>> R_array = new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String, String>> C_array = new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String, String>> E_array = new ArrayList<HashMap<String,String>>();
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 * 	HttpGet 요청을 통하여
	 *  JSON 형식의 오브젝트를 파싱하여 저장한다.
	 */
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		HttpClient client = new DefaultHttpClient();

		String getURL = "http://14.63.212.236/index.php/cost/getCost/?id="
				+ "pest" + "&month=" + 8;

		HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000);
		HttpGet get = new HttpGet(getURL);

		HttpResponse responseGet;
		
		try {
			responseGet = client.execute(get);
			HttpEntity resEn = responseGet.getEntity();
			JSONObject object = new JSONObject(EntityUtils.toString(resEn));
			
			JSONArray cost_info = new JSONArray(
					object.getString("cost"));
			
			cnt = object.getInt("count");
									
			for (int i=0; i < cnt; i++) {
				
				JSONObject data = cost_info.getJSONObject(i);
				
				ArrayList<HashMap<String, String>> cost_array = null;
				
				int type = Integer.parseInt(data.getString("cost_type"));
				
				if( type == 1 )
				{
					cost_array= A_array;
				}
				else if( type == 2 )
				{
					cost_array= R_array;
				}
				else if( type == 3 )
				{
					cost_array= C_array;
				}
				else if( type == 4 )
				{
					cost_array= E_array;
				}
				
				HashMap<String, String> cost_value = new HashMap<String, String>();
				
				cost_value.put("expense", data.getString("expense"));
				cost_value.put("month", data.getString("reg_month"));
				cost_value.put("descript", data.getString("cost_des"));
				
				cost_value.put("ID", ""+data.getInt("cost_id"));
				
				cost_array.add(cost_value);
								
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<HashMap<String, String>> getAList()
	{
		return A_array;
	}
	
	public ArrayList<HashMap<String, String>> getRList()
	{
		return R_array;
	}
	
	public ArrayList<HashMap<String, String>> getCList()
	{
		return C_array;
	}
	
	public ArrayList<HashMap<String, String>> getEList()
	{
		return E_array;
	}
	
	public int getFin()
	{
		return fin;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		fin = 1;
	}
}
