package ummom.teacher.firstTab;


import java.util.Calendar;

import ummom.login.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi") 
public class ScheduleHomeworkFragment extends Fragment {
	
	private LinearLayout rel;
	private ImageView writeImg;
	private View view;
	
	private View child;
	
	private TextView textView_title;
	
	private LayoutInflater inflater;
	private ViewGroup container;
	
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
		
		return view;
	}
	
	class ScheduleThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}

	public void setOnView(String string) {
		// TODO Auto-generated method stub		
		child = inflater.inflate(R.layout.homework_listitem, container ,false);
		textView_title = (TextView) child.findViewById(R.id.title);
	
		textView_title.setText(string);
		
		rel.addView(child);
		
		view.invalidate();
	}
}
