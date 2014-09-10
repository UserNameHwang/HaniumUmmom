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

import ummom.login.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IDSearch extends Activity {

	private final int success = 400;
	private Intent intent;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.id_search);
	    
	    ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6495ed")));
		bar.setTitle("ID 검색하기");
		
	    // TODO Auto-generated method stub
		
		intent = new Intent();
		Button search_button = (Button)findViewById(R.id.ID_searchbutton);
		final EditText search_edit = (EditText)findViewById(R.id.ID_edit);
		
		search_button.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				TeacherInfo_Thread teacher_info = 
						new TeacherInfo_Thread(search_edit.getText().toString());
				HashMap<String, String> map;
				Bundle bundle;

				teacher_info.start();
				
				try {
					teacher_info.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bundle = teacher_info.getBundle();
				
				map = (HashMap<String, String>) bundle.getSerializable("HashMap");
				if( Integer.parseInt(map.get("result").toString()) == success)
				{
					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					
					overridePendingTransition(R.anim.page_appear, R.anim.page_disappear);

					finish();
				}
			}
		});


	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.page_donmove, R.anim.page_disappear);

		this.setResult(RESULT_CANCELED, intent);
		finish();
	}
	
	public class TeacherInfo_Thread extends Thread {

		private String ID;
		private final int fail = 300;
		private Bundle bundle;

		public TeacherInfo_Thread(String ID) {
			// TODO Auto-generated constructor stub
			this.ID = ID;
		}

		public void run() {
			HttpClient client = new DefaultHttpClient();

			String getURL = "http://14.63.212.236/index.php/api_c/getUserTeacher/?id="
					+ ID;

			HttpConnectionParams.setConnectionTimeout(client.getParams(), 3000);
			HttpGet get = new HttpGet(getURL);

			HttpResponse responseGet;
			try {
				String profile_str = "http://14.63.212.236/";

				responseGet = client.execute(get);
				HttpEntity resEn = responseGet.getEntity();
				JSONObject object = new JSONObject(EntityUtils.toString(resEn));
				
				if( Integer.parseInt(object.getString("result").toString()) == fail)
				{
					
					Log.d("res", ""+Integer.parseInt(object.getString("result").toString()));
					IDSearch.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText( IDSearch.this, "검색에 실패하였습니다.", Toast.LENGTH_LONG).show();
						}
					});
					HashMap<String, String> value = new HashMap<String, String>();
					value.put("result", object.getString("result"));
					
					bundle = new Bundle();

					bundle.putSerializable("HashMap", value);

					return ;
				}
				
				JSONArray teacher_info = new JSONArray(
						object.getString("info_teacher"));
				
				
				JSONObject data = teacher_info.getJSONObject(0);
				Log.d("data", ""+data);
				HashMap<String, String> value = new HashMap<String, String>();

				value.put("result", object.getString("result"));
				value.put("name", data.getString("mb_name"));
				value.put("grade", data.getString("t_grade"));
				value.put("phone", data.getString("mb_phone"));
				profile_str += data.getString("profile_url");

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
}
