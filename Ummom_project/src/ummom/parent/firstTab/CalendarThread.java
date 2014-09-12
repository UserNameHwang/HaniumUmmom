package ummom.parent.firstTab;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.transition.Scene;
import android.util.Log;

public class CalendarThread extends Thread{
	
	private String schedule_date = "";
	private String mb_id = "";
	
	private JSONArray jarray;
	private int count;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public JSONArray getJarray() {
		return jarray;
	}

	public void setJarray(JSONArray jarray) {
		this.jarray = jarray;
	}

	public CalendarThread(String date, String id){
		schedule_date = date;
		mb_id = id;		
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			
			HttpClient client = new DefaultHttpClient();  
			String getURL = "http://14.63.212.236/index.php/schedule/getSchedule/?mb_id="+mb_id
												+"&schedule_date=" + schedule_date;
			
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 3000);
			HttpGet get = new HttpGet(getURL);
			
			try {
				HttpResponse responseGet = client.execute(get);
				HttpEntity resEn = responseGet.getEntity();
				String parse = EntityUtils.toString(resEn);
				
				JSONObject object = new JSONObject(parse);
				
				String result = object.getString("result");				
				int success = Integer.parseInt(result);			
				
				if(success == 400){
					JSONArray schedule = object.getJSONArray("schedule");
					this.setCount(object.getInt("count"));
					this.setJarray(schedule);				
				}
					
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

}
