package ummom.teacher.firstTab;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ummom.login.R;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi") 
public class ScheduleHomeworkFragment extends Fragment {
	
	private LinearLayout rel;
	private ImageView writeImg;
	private View view;
	
	private View child;
	
	private TextView title;
	private TextView des;
	
	private LayoutInflater inflater;
	private ViewGroup container;
	
	private ImageView edit;
	private ImageView delete;
	
	private String Sch_id;
	
	public String getSch_id() {
		return Sch_id;
	}

	public void setSch_id(String id) {
		this.Sch_id = id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		this.inflater = inflater;
		this.container = container;
		
		view = inflater.inflate(R.layout.schedule_homework, container,false);
		
		final DialogWrite dialogwrite = new DialogWrite(this);		
		rel = (LinearLayout) view.findViewById(R.id.list_relative);
		
		writeImg = (ImageView) view.findViewById(R.id.image_Homewrite);
		
		writeImg.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				dialogwrite.show(getActivity(), 1);					
            }
		});			
		initHomefragment(CalendarFragment.touchDay);
		
		return view;
	}
	
	public void initHomefragment(String date){
		CalendarThread calendarthread = new CalendarThread(date, "test");				
		calendarthread.start();					
		try {						
			calendarthread.join();						
			viewClear();						
			if (calendarthread.getCount() > 0) {
				JSONArray array = calendarthread.getJarray();										
				for(int i=0; i < calendarthread.getCount() ; i++){											
					JSONObject tmp = array.getJSONObject(i);											
					int type = Integer.parseInt(tmp.getString("schedule_type"));											
					if(type == 1){						
						setOnView(tmp.getString("schedule_title"), 
								tmp.getString("schedule_des"), tmp.getString("schedule_id"));
					}
					
				}
			}else {
				
			}
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void viewClear(){
		rel.removeAllViews();
	}

	public void setOnView(String data_title, String data_des, String schedule_id) {
		
		String list_title = data_title;
		String list_des = data_des;
		final String id = schedule_id;
		
		setSch_id(schedule_id);
		
		child = inflater.inflate(R.layout.homework_listitem, container ,false);
		
		title = (TextView) child.findViewById(R.id.textView_Schedulelist_title);
		des = (TextView) child.findViewById(R.id.textView_Schedulelist_des);
		
		edit = (ImageView) child.findViewById(R.id.img_icon_edit);
		delete = (ImageView) child.findViewById(R.id.img_icon_delete);
		
		edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("@", "edit_img" + id);
			}
		});
		
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub				
				DeleteThread deletethread = new DeleteThread();								
				deletethread.start();
				
				try {
					deletethread.join();					
					initHomefragment(CalendarFragment.click_date);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		title.setText(list_title);
		des.setText(list_des);
		
		rel.addView(child);
		
		view.invalidate();
	}
	
	class EditThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
	
	class DeleteThread extends Thread {
		public void run() {
			// TODO Auto-generated method stub
			try {
				
				HttpClient client = new DefaultHttpClient();
				String path = "http://14.63.212.236/index.php/schedule/deleteSchedule";
				
				HttpPost post = new HttpPost(path);
				HttpConnectionParams.setConnectionTimeout(client.getParams(), 30000);
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("schedule_id", getSch_id() ));		

				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,	HTTP.UTF_8);
				post.setEntity(ent);

				HttpResponse httpResponse = client.execute(post);
				HttpEntity resEn = httpResponse.getEntity();

				String parse = EntityUtils.toString(resEn);

				JSONObject object = new JSONObject(parse);
				
				String result = object.getString("result");
				int reg = Integer.parseInt(result);
				
				if(reg == 400){
								
					
				}	
	
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

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
		}
	}
}
