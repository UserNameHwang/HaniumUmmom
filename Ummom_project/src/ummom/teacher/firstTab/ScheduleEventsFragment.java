package ummom.teacher.firstTab;



import ummom.login.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi") 
public class ScheduleEventsFragment extends Fragment {
	
	ListView listView;
	LinearLayout rel;
	
	TextView title;
	TextView title2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.schedule_events, container,false);
		
		rel = (LinearLayout) view.findViewById(R.id.list_relative);
		View child = inflater.inflate(R.layout.homework_listitem, container ,false);
		View child2 = inflater.inflate(R.layout.homework_listitem, container ,false);
		
		title = (TextView) child.findViewById(R.id.title);
		title.setText("asdasdasdasd");
		
		title2 = (TextView) child2.findViewById(R.id.title);
		title2.setText("ClickCLick");
		
		rel.addView(child);
		rel.addView(child2);
		
		
		return view;
	}
	
	public void setOnView(String date){
		
		//rel.removeAllViews();		
		title.setText("qweqweqweqwe");
		title2.setText("PushPushPush");
	}
	
}
