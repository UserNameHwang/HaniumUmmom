package ummom.alarm;

import ummom.login.R;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @class alarmCreater
 * @author Lemoness
 * @desc 알람 생성용 클래스
 */
public class alarmCreater extends DialogFragment {
	
	private View mView;
	private FragmentActivity mFA_act;
	
	public alarmCreater(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		if(mView == null){
			mView = inflater.inflate(R.layout.fragment_alarm, container, false);
			
		}
		return mView;
	}
	
	public void setAlarm(Time timeset){
		AlarmManager manager = (AlarmManager) mFA_act.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("ummom.login");
		PendingIntent pintent = PendingIntent.getActivity(mFA_act, 0, intent, 0);
		manager.set(AlarmManager.RTC, timeset.toMillis(true), pintent);
	}

}
