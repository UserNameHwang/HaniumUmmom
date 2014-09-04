package ummom.child.weather;

import ummom.child.APIHandler;
import ummom.child.GMapHelper;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ummom.login.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * @class weather_forecast
 * @desc ���� ���� �ѷ��ִ� Fragment, sk weather planet api ���
 *       1���ϰ� �߱⿹��
 * @author Lemoness
 *
 */
public class Weather_forecast extends Fragment {
	
	private View mView;
	private LinearLayout mWeatherItem;
	private LayoutInflater mInflater;
	private ViewGroup mContainer;

	FragmentActivity mAct;
	
	public Weather_forecast(FragmentActivity act){
		mAct = act;
	}
	public Weather_forecast(){
		mAct = (FragmentActivity)getActivity();
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		//Fragment ���� Activity ������ �� ����
		if(mAct == null){
			Log.d("weather","mAct is null!");
			mAct = (FragmentActivity) getActivity();
		}
		// ó�� View ��� inflate
		if(mView == null){
			mView = inflater.inflate(R.layout.fragment_weather, container, false);
			mWeatherItem = (LinearLayout) mView.findViewById(R.id.fragweather_weatherlist);
		}
		mInflater = inflater;
		mContainer = container;
		
		Log.d("weather","weather parsing : ");
		
		// ��ġ���� ���ϱ�
		GMapHelper gmap = new GMapHelper(mAct);
		gmap.SettingGMap();
		LatLng lat = gmap.getMyLocation();
		try {
			new getWeatherTask().execute(lat);
		} catch (Exception e){}
		
		return mView;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		if(mView != null){
			ViewGroup parent = (ViewGroup) mView.getParent();
			if(parent != null){
				parent.removeView(mView);
			}
		}
		super.onDestroyView();
	}
	
	/*
	 * ���� ������ ����� �޼���
	 */
	private void setWeatherItem(Bundle data){
		if(data != null){ 
			Log.d("weather",data.toString());
			
			TextView weatherlist_title = (TextView) mView.findViewById(R.id.fragweather_weathertitle);
			TextView weatherlist_time = (TextView) mView.findViewById(R.id.fragweather_weathertime);
			
			weatherlist_title.setText(data.getString("location"));
			
			Time time = new Time();
			time.set(Integer.parseInt(data.getString("time").substring(17,19)),
					Integer.parseInt(data.getString("time").substring(14,16)),
					Integer.parseInt(data.getString("time").substring(11,13)),
					Integer.parseInt(data.getString("time").substring(8, 10)),
					Integer.parseInt(data.getString("time").substring(5, 7))-1,
					Integer.parseInt(data.getString("time").substring(0, 4)));
			
			
			weatherlist_time.setText((time.month+1) + "/" + time.monthDay + " " + time.hour + "시 기준");
			
			for(int i=0 ; i<8 ; i++){
				Time newtime = new Time();
				newtime.set(time.toMillis(false)+86400000*i);
				mWeatherItem.addView(weatherIconMaker(data.getStringArrayList("sky").get(i),
													  (newtime.month+1) + "/" + newtime.monthDay, 
													  data.getStringArrayList("tmin").get(i),
													  data.getStringArrayList("tmax").get(i)));
			}
			mView.refreshDrawableState();
		}
		else{
			Log.d("weather","null");
		}
	}
	
	/*
	 * ������ ã�Ƽ� setWeatherIcon�� �ѷ��ִ� �޼���
	 */
	private View weatherIconMaker(String inp, String date, String low, String high){
		View result = (View) mInflater.inflate(R.layout.fragment_weather_item, mContainer, false);
		TextView title = (TextView) result.findViewById(R.id.fragweather_item_weather_text);
		TextView datetext = (TextView) result.findViewById(R.id.fragweather_item_weather_time);
		ImageView image = (ImageView) result.findViewById(R.id.fragweather_item_weather_icon);
		TextView tmin = (TextView) result.findViewById(R.id.fragweather_item_weather_templow);
		TextView tmax = (TextView) result.findViewById(R.id.fragweather_item_weather_temphigh);
		
		datetext.setText(date);
		tmin.setText(low);
		tmax.setText(high);
		
		if(inp.endsWith("01")){
			title.setText(R.string.Weather_SKY_A01);
			image.setImageResource(R.drawable.weather_sun);
		}else if(inp.endsWith("02")){
			title.setText(R.string.Weather_SKY_A02);
			image.setImageResource(R.drawable.weather_partcloud);
		}else if(inp.endsWith("03")){
			title.setText(R.string.Weather_SKY_A03);
			image.setImageResource(R.drawable.weather_cloud);
		}else if(inp.endsWith("04")){
			title.setText(R.string.Weather_SKY_A04);
			image.setImageResource(R.drawable.weather_littlerain);
		}else if(inp.endsWith("05")){
			title.setText(R.string.Weather_SKY_A05);
			image.setImageResource(R.drawable.weather_littlesnow);			
		}else if(inp.endsWith("06")){
			title.setText(R.string.Weather_SKY_A06);
			image.setImageResource(R.drawable.weather_littlerain);			
		}else if(inp.endsWith("07")){
			title.setText(R.string.Weather_SKY_A07);
			image.setImageResource(R.drawable.weather_cloud);			
		}else if(inp.endsWith("08")){
			title.setText(R.string.Weather_SKY_A08);
			image.setImageResource(R.drawable.weather_littlerain);			
		}else if(inp.endsWith("09")){
			title.setText(R.string.Weather_SKY_A09);
			image.setImageResource(R.drawable.weather_littlesnow);			
		}else if(inp.endsWith("10")){
			title.setText(R.string.Weather_SKY_A10);
			image.setImageResource(R.drawable.weather_littlerain);			
		}else if(inp.endsWith("11")){
			title.setText(R.string.Weather_SKY_A11);
			image.setImageResource(R.drawable.weather_storm);			
		}else if(inp.endsWith("12")){
			title.setText(R.string.Weather_SKY_A12);
			image.setImageResource(R.drawable.weather_hardrain);			
		}else if(inp.endsWith("13")){
			title.setText(R.string.Weather_SKY_A13);
			image.setImageResource(R.drawable.weather_snow);			
		}else if(inp.endsWith("14")){
			title.setText(R.string.Weather_SKY_A14);
			image.setImageResource(R.drawable.weather_hardrain);
		}else{
			title.setText(R.string.Weather_SKY_A01);
			image.setImageResource(R.drawable.weather_sun);
		}

		Log.d("weather","item added : " + inp + ":" + low + "/" + high);
		return result;
	}
	
	/*
	 * �������� �˻� ������ �̳�Ŭ����
	 */
	private class getWeatherTask extends AsyncTask<LatLng, Void, Bundle>{
		
		// api handler�� �������� �˻�, ���� bundle�� �����ؼ� ����
		@Override
		protected Bundle doInBackground(LatLng... params) {
			// TODO Auto-generated method stub
			APIHandler handler = new APIHandler();
			Bundle bundle = handler.parsingWeatherInfo(handler.getWeather_Forecast(params[0], null));
			
			return bundle;
		}
		
		// �˻����� ������ �ѷ���
		@Override
		protected void onPostExecute(Bundle result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			setWeatherItem(result);
		}
		
	}
}
