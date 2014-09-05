package ummom.parent.firstTab;


import ummom.login.R;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SceduleEventsFragment extends Fragment {
	
	ListView listView;
	SceduleAdapter adapter;
	LinearLayout rel;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.schedule_events, container,false);
		
		rel = (LinearLayout) view.findViewById(R.id.list_relative);
		View child = inflater.inflate(R.layout.homework_listitem, container ,false);
		View child2 = inflater.inflate(R.layout.homework_listitem, container ,false);
		
		TextView title = (TextView) child.findViewById(R.id.title);
		title.setText("asdasdasdasd");
		
		TextView title2 = (TextView) child2.findViewById(R.id.title);
		title2.setText("asdasdasdasd2");
		
		rel.addView(child , 0);
		rel.addView(child2, 1);
		
		
		return view;
	}
}
