package ummom.child.schedule;

import ummom.login.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @class schedulemodule
 * @desc ������ ��� �޼���, ���� �ʿ�
 * @author inchang kim
 *
 */
public class ScheduleModule extends Fragment {
	
	private static View mView;
	private static LinearLayout rel;

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Fragment frag = (Fragment) getFragmentManager().findFragmentById(R.id.scedule_homework_fragment);
		
		if(rel != null){
			ViewGroup parent = (ViewGroup) rel.getParent();
			if(parent != null){
				parent.removeView(rel);
			}
		}
		if(mView != null){
			ViewGroup parent = (ViewGroup) mView.getParent();
			if(parent != null)
				parent.removeView(mView);
		}
		super.onDestroyView();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try{
			if(mView == null){
				mView = inflater.inflate(R.layout.fragment_schedule, container,false);
				rel = (LinearLayout) mView.findViewById(R.id.fragschedule_schedule_list_relative);
				rel.removeAllViews();
			}
			
		}catch(InflateException e){return null;}
		
		rel.addView(CreateItem(inflater, 
				container, 
				R.layout.fragment_schedule_item, 
				R.id.scedule_item_title, 
				"list1") , 0);

		rel.addView(CreateItem(inflater, 
				container, 
				R.layout.fragment_schedule_item, 
				R.id.scedule_item_title, 
				"list2") , 1);
		
		return mView;
	}
	
	private View CreateItem(LayoutInflater inflater, ViewGroup container, int resid0, int resid1, String S_text){
		View result = inflater.inflate(resid0, container, false);
		TextView title = (TextView) result.findViewById(resid1);
		title.setText(S_text);
		return result;
	}
	
}
