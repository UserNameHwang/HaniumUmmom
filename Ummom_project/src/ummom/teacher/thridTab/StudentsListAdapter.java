package ummom.teacher.thridTab;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class StudentsListAdapter extends BaseAdapter {
	
	private Context mContext;

	private List<StudentsItem> mItems = new ArrayList<StudentsItem>();
	
	
	public StudentsListAdapter(Context context){
		mContext = context;		
	}

	public void addItem(StudentsItem it){
		mItems.add(it);
	}
	
	public void setList(List<StudentsItem> list){
		mItems = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		StudentsView stuView;
		if (convertView == null) {
			stuView = new StudentsView(mContext, mItems.get(position));
		} else {
			stuView = (StudentsView) convertView;
			
			stuView.setIcon(0, mItems.get(position).getImg());
			stuView.setText(0, mItems.get(position).getName());
			stuView.setText(1, mItems.get(position).getBirth());
			stuView.setText(2, mItems.get(position).getPhone());
			stuView.setIcon(1,mItems.get(position).getIcon());
			
		}

		return stuView;
	}
}
