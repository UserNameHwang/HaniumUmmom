package ummom.parent.managePage;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

import ummom.login.R;

import android.animation.LayoutTransition;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author Administrator
 *	학교 검색을 위해 정의된 클래스
 *	이너클래스 스레드
 */
public class SchoolSearch extends Activity {
	
	private SchoolInfo_Thread school_info;
	private final int success = 400;
	private Intent intent;
	private ListView search_list;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.school_search);
	    
	    ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6495ed")));
		bar.setTitle("학교 검색하기");
	    // TODO Auto-generated method stub
		
		intent = new Intent();
		
		final Context c = SchoolSearch.this;
		
		Button search_button = (Button)findViewById(R.id.School_searchbutton);
		final EditText search_edit = (EditText)findViewById(R.id.School_edit);
		search_list = (ListView)findViewById(R.id.School_List);
		
		search_button.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				school_info = new SchoolInfo_Thread(search_edit.getText().toString());

				school_info.start();
				
				try {
					school_info.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				search_list.setAdapter(null);
				
				ArrayList<ListData> listData = school_info.getArrayList();

				CustomAdapter c_adapter = new CustomAdapter(c, R.layout.school_search_list, listData);
				
				search_list.setAdapter(c_adapter);
			}
		});
		
		search_list.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Bundle bundle;
				HashMap<String, String> map;

				bundle = school_info.getBundle(position);
				
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
		
		LayoutTransition lt = new LayoutTransition();
		lt.disableTransitionType(LayoutTransition.DISAPPEARING);
		
		search_list.setLayoutTransition(lt);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 * 	화면 전환 애니메이션 재정의
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.page_donmove, R.anim.page_disappear);

		this.setResult(RESULT_CANCELED, intent);
		finish();
	}
	
	public class SchoolInfo_Thread extends Thread{

		private String School;
		private final int fail = 200;
		private Bundle[] bundle;
		private ArrayList<ListData> listData = new ArrayList<ListData>();

		private int cnt;

		public SchoolInfo_Thread(String School) {
			// TODO Auto-generated constructor stub
			this.School = School;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			HttpClient client = new DefaultHttpClient();

			String getURL = "http://14.63.212.236/index.php/api_c/searchSchool/?school="
					+ School;

			HttpConnectionParams.setConnectionTimeout(client.getParams(), 3000);
			HttpGet get = new HttpGet(getURL);

			HttpResponse responseGet;
			try {
				responseGet = client.execute(get);
				HttpEntity resEn = responseGet.getEntity();
				JSONObject object = new JSONObject(EntityUtils.toString(resEn));
				
				if( Integer.parseInt(object.getString("result").toString()) == fail)
				{
					
					SchoolSearch.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText( SchoolSearch.this, "검색에 실패하였습니다.", Toast.LENGTH_LONG).show();
						}
					});
					
					HashMap<String, String> value = new HashMap<String, String>();
					value.put("result", object.getString("result"));
					
					Bundle bundle = new Bundle();

					bundle.putSerializable("HashMap", value);

					return;
				}
				
				JSONArray teacher_info = new JSONArray(
						object.getString("info_school"));
				
				cnt = object.getInt("count");
				
				bundle = new Bundle[cnt];		
				
				listData = new ArrayList<ListData>();
				
				for (int i=0; i < cnt; i++) {
					
					String profile_str = "http://14.63.212.236/";

					bundle[i] = new Bundle();
					
					JSONObject data = teacher_info.getJSONObject(i);
					
					ListData list = new ListData(null, null);
					
					HashMap<String, String> mb_value = new HashMap<String, String>();
					HashMap<String, String> list_value = new HashMap<String, String>();
					
					list_value.put("name", data.getString("mb_name"));
					list_value.put("ID", data.getString("mb_id"));
					list_value.put("grade", data.getString("t_grade"));
					list_value.put("school", data.getString("t_school"));
					
					list.setData(list_value);
					
					mb_value.put("result", object.getString("result"));
					mb_value.put("name", data.getString("mb_name"));
					mb_value.put("grade", data.getString("t_grade"));
					mb_value.put("phone", data.getString("mb_phone"));
					
					profile_str += data.getString("profile_url");

					URL url = new URL(profile_str);
					HttpURLConnection connect = (HttpURLConnection) url
							.openConnection();

					connect.setDoInput(true);
					connect.connect();

					BufferedInputStream input = new BufferedInputStream(
							connect.getInputStream());

					Bitmap bitmap = BitmapFactory.decodeStream(input);
					
					list.setBitmap(bitmap);
					
					listData.add(list);
					
					bundle[i].putSerializable("HashMap", mb_value);
					bundle[i].putParcelable("Bitmap", bitmap);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public Bundle getBundle(int index) {
			return bundle[index];
		}
		
		public int getCount()
		{
			return cnt;
		}
		
		public ArrayList<ListData> getArrayList()
		{
			return listData;
		}
	}
}
