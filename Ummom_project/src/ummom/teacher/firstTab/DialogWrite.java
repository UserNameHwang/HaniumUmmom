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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import ummom.login.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class DialogWrite extends Fragment {
	
	private Fragment fragment;
	
	int type;
	
	EditText title;
	EditText des;
	EditText year;
	EditText month;
	EditText day;
	DialogThread dialogthread;
	
	
	public DialogWrite(Fragment fragment) {
		// TODO Auto-generated constructor stub
		this.fragment = fragment;
	}

	@SuppressLint("InflateParams") 
	public void show(Activity act, int type) {
		AlertDialog.Builder mbuilder = new AlertDialog.Builder(act);
		View dialogView = act.getLayoutInflater().inflate(R.layout.schedule_write_dialog, null );
		mbuilder.setView(dialogView);
		
		this.type = type;
		final int fragment_type = type;
		
		String val[] = CalendarFragment.touchDay.split("-");
		
		title = (EditText) dialogView.findViewById(R.id.editText_dialog_title);
		des = (EditText) dialogView.findViewById(R.id.editText_dialog_des);
		
		year = (EditText) dialogView.findViewById(R.id.editText_dialog_year);
		month = (EditText) dialogView.findViewById(R.id.editText_dialog_month);
		day = (EditText) dialogView.findViewById(R.id.editText_dialog_day);
		
		Log.d("@", "" + month.toString());
		
		year.setText(val[0]);
		month.setText(val[1]);
		
		
		mbuilder.setCancelable(false)
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});

		mbuilder.setPositiveButton("확인", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				dialogthread = new DialogThread();				
				dialogthread.start();
				
				try {
					dialogthread.join();
					
					String date = year.getText() + "-" + month.getText() + "-" + day.getText();
					
					if(date.equals(CalendarFragment.touchDay)){
						if(fragment_type == 1){
							((ScheduleHomeworkFragment) fragment).viewClear();
							((ScheduleHomeworkFragment) fragment).initHomefragment(date);
						}else{
							((ScheduleEventsFragment) fragment).viewClear();
							((ScheduleEventsFragment) fragment).initEventsFramget(date);
						}
					}
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

		mbuilder.show();
	}
	
	class DialogThread extends Thread{
		@Override
		public void run() {
				
			try{
				HttpClient client = new DefaultHttpClient();
				String path = "http://14.63.212.236/index.php/schedule/setSchedule";
				
				HttpPost post = new HttpPost(path);
				HttpConnectionParams.setConnectionTimeout(client.getParams(), 30000);
	
				String date = year.getText().toString() + "-" +
						  month.getText().toString() + "-" +
						  day.getText().toString();
				
				String s_title = title.getText().toString();
				String s_des = des.getText().toString();				
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("schedule_title", s_title ));
				params.add(new BasicNameValuePair("schedule_des", s_des));
				params.add(new BasicNameValuePair("schedule_date", date));
				
				params.add(new BasicNameValuePair("schedule_type", Integer.toString(type) ));
				params.add(new BasicNameValuePair("id", "test"));
	
				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,	HTTP.UTF_8);
				post.setEntity(ent);
	
				HttpResponse httpResponse = client.execute(post);
				HttpEntity resEn = httpResponse.getEntity();
	
				String parse = EntityUtils.toString(resEn);
	
				JSONObject object = new JSONObject(parse);
				String result = object.getString("result");
				
				int reg = Integer.parseInt(result);
				
				if(reg == 400){
					Log.d("@", object.toString());
					
					
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
