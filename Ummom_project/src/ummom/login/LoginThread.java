package ummom.login;

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

import android.util.Log;


public class LoginThread extends Thread{
	
	String result_str;
    String des_str;
    int login_suc;
	
	public void run() {
		
		try
		{	
			HttpClient client = new DefaultHttpClient();  
			String getURL = "http://14.63.212.236/index.php/api_c/loginUser/" +
					"?username="+LoginModel.id+"&password="+LoginModel.password;
			
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 3000);
			HttpGet get = new HttpGet(getURL);
			
			try {
				HttpResponse responseGet = client.execute(get);
				HttpEntity resEn = responseGet.getEntity();
				String parse = EntityUtils.toString(resEn);
				
			
				JSONObject object = new JSONObject(parse);
				Log.d("@", parse);
				JSONObject login = new JSONObject(object.getString("login"));
				
				JSONArray user = new JSONArray(login.getString("user"));
				JSONObject info = (JSONObject) user.get(0);
				String result = login.getString("result");
				LoginModel.mb_type = info.getInt("mb_type");
				
				login_suc = Integer.parseInt(result);
				
				if(login_suc==400){
		        	LoginModel.login_success = true;
		        }
		        else if(login_suc==200){
		        	LoginModel.login_success = false;
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
