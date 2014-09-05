package ummom.parent.managePage;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class TeacherInfo_Thread extends Thread {

	String result_str;
	String ID;
	private final int fail = 200;
	private Bundle bundle;

	public TeacherInfo_Thread(String ID) {
		// TODO Auto-generated constructor stub
		this.ID = ID;
	}

	public void run() {
		HttpClient client = new DefaultHttpClient();

		String getURL = "http://14.63.212.236/index.php/api_c/getUserTeacher/?id="
				+ ID;

		// + "?username="+LoginModel.id+"&password="+LoginModel.password;

		HttpConnectionParams.setConnectionTimeout(client.getParams(), 3000);
		HttpGet get = new HttpGet(getURL);

		HttpResponse responseGet;
		try {
			String profile_str = "http://14.63.212.236/";

			responseGet = client.execute(get);
			HttpEntity resEn = responseGet.getEntity();
			JSONObject object = new JSONObject(EntityUtils.toString(resEn));
			JSONArray teacher_info = new JSONArray(
					object.getString("info_teacher"));
			
			if( Integer.parseInt(object.getString("result").toString()) == fail)
			{
			}
			
			
			JSONObject data = teacher_info.getJSONObject(0);
			Log.d("data", ""+data);
			HashMap<String, String> value = new HashMap<String, String>();

			value.put("result", object.getString("result"));
			value.put("name", data.getString("mb_name"));
			value.put("school", data.getString("mb_school"));
			value.put("grade", data.getString("t_grade"));
			value.put("phone", data.getString("t_phone"));
			profile_str += data.getString("profile_url");
	//		value.put("comments", data.getString("comments"));

			URL url = new URL(profile_str);
			HttpURLConnection connect = (HttpURLConnection) url
					.openConnection();

			connect.setDoInput(true);
			connect.connect();

			BufferedInputStream input = new BufferedInputStream(
					connect.getInputStream());

			Bitmap bitmap = BitmapFactory.decodeStream(input);
			
			bundle = new Bundle();
			
			bundle.putSerializable("HashMap", value);
			bundle.putParcelable("Bitmap", bitmap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Bundle getBundle() {
		return bundle;
	}
}
