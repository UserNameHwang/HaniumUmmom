package ummom.teacher.thridTab;

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

import ummom.login.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class StudentsListFragment extends Fragment{
	
	ListView listView;
	StudentsListAdapter adapter;
	private FragmentActivity frag;
	View view ;
	
	Button btn;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(frag == null){
			frag = (FragmentActivity) getActivity();
		}
		
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.teacher_thirdtab_studentslist, container,false);
		listView = (ListView) view.findViewById(R.id.listView);		
		adapter = new StudentsListAdapter(view.getContext());
		
		btn = (Button) view.findViewById(R.id.testBtn);
		
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addAdapter();				
			}
		});
		
		int img = R.drawable.sample_view;
		adapter.addItem(new StudentsItem(img, "김인창", 
				"91.02.27", "010-3306-5990", R.drawable.info_grey));
		
		int img2 = R.drawable.sample_view1;
		adapter.addItem(new StudentsItem(img2, "황두연", 
				"92.02.27", "010-3306-5990", R.drawable.info_grey));
		
		int img3 = R.drawable.sample_view2;
		adapter.addItem(new StudentsItem(img3, "김준석", 
				"91.02.27", "010-3306-5990", R.drawable.info_grey));
		
		int img4 = R.drawable.sample_view3;
		adapter.addItem(new StudentsItem(img4, "한민지", 
				"91.02.27", "010-3306-5990", R.drawable.info_grey));
		
		int img5 = R.drawable.sample_view4;
		adapter.addItem(new StudentsItem(img5, "박근언", 
				"91.02.27", "010-3306-5990", R.drawable.info_grey));
		
		adapter.addItem(new StudentsItem(img, "김인창", 
				"91.02.27", "010-3306-5990", R.drawable.info_grey));		
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				// TODO Auto-generated method stub
				Intent intent = new Intent(frag, StudentsDetail.class);				
				
				startActivity(intent);
				frag.overridePendingTransition(R.anim.page_appear, R.anim.page_donmove);
			}
		});
		return view;
	}
	
	public void addAdapter(){
		
		int img5 = R.drawable.sample_view4;
		adapter.addItem(new StudentsItem(img5, "김동은2", 
				"91.02.27", "010-3306-5990", R.drawable.info_grey));
		
		listView.setAdapter(adapter);
		view.invalidate();
		
	}
	
	class ThreadStudentsList extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			HttpClient client = new DefaultHttpClient();
			String path = "http://14.63.212.236/index.php/api_c/register";
			
			HttpPost post = new HttpPost(path);
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 30000);

			String id = "test";
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("mb_id", id));
					

			UrlEncodedFormEntity ent;
			try {
				ent = new UrlEncodedFormEntity(params,	HTTP.UTF_8);
				post.setEntity(ent);
				HttpResponse httpResponse = client.execute(post);
				HttpEntity resEn = httpResponse.getEntity();
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
}
