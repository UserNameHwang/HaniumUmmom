package ummom.teacher.thridTab;

import ummom.login.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class StudentsListFragment extends Fragment{
	
	ListView listView;
	StudentsListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.teacher_thirdtab_studentslist, container,false);
		listView = (ListView) view.findViewById(R.id.listView);
		
		adapter = new StudentsListAdapter(view.getContext());
		
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
		
		return view;
	}	
	
}
