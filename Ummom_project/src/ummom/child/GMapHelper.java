package ummom.child;

import java.io.IOException;
import java.util.Locale;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @class gmaphelper
 * @desc 구글 맵 컨트롤용 클래스
 * 		 현재 위치정보 가져오기용으로 사용.
 * @author Lemoness
 *
 */
public class GMapHelper implements LocationListener{

	private LatLng mSSULoc = new LatLng(37.496499, 126.956871);
	private GoogleMap mMap;
	private LocationManager mLocationManager;
	private String mProvider;
	private LatLng mLatLng;
	

	/*
	 * motherclass data
	 */
	private FragmentActivity mFA_act;
	private int mID_fragmap;
	public GMapHelper(FragmentActivity mAct){
		mFA_act = mAct;
		mID_fragmap = 0;
		mLatLng = new LatLng(0,0);
	}

	//�� ���ÿ��� ���۸� fragment�� ���̵� ���������.
	public void CreateGMap(int mapID){
		mID_fragmap = mapID;
		
		if(mID_fragmap == 0){
			return;
		}
		
		this.SettingGMap();

		mLocationManager.requestLocationUpdates(mProvider, 1, 1, this);
		mMap = ((SupportMapFragment) mFA_act.getSupportFragmentManager().findFragmentById(mID_fragmap)).getMap();
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mSSULoc, 16));
		if(mMap != null){
			mMap.setMyLocationEnabled(true);
			mMap.getMyLocation();
		}
	}

	// 클래스 세팅용 메소드
	public void SettingGMap(){
		GooglePlayServicesUtil.isGooglePlayServicesAvailable(mFA_act);
		mLocationManager = (LocationManager) mFA_act.getSystemService(Context.LOCATION_SERVICE);
		mProvider = mLocationManager.getBestProvider(new Criteria(), true);

		// gps 꺼져있을때
		if(mProvider == null){
			setProvider();
		}
		if(mProvider != null){
			//Toast.makeText(mFA_act, "google map is available", Toast.LENGTH_SHORT).show();
			mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 1, 1, this);
		}
	}
	
	// 위치찾기용 메서드
	public LatLng getMyLocation(){
		mLocationManager.requestLocationUpdates(mProvider, 1, 1, this);
		Location location = mLocationManager.getLastKnownLocation(mProvider);
		if(location != null)
			mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
		return mLatLng;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		mLatLng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public void setLoc(LatLng latlng){
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));
		mMap.clear();
		mMap.addMarker(new MarkerOptions().position(latlng).draggable(false));
	}

	private void setProvider(){
		new AlertDialog.Builder(mFA_act)
		.setTitle("위치정보 사용 동의")
		.setMessage("위치정보 사용에 동의하시겠습니까?. 3G/4G 사용시 데이터 요금이 발생할 수 있습니다.")
		.setNeutralButton("동의" ,new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mFA_act.startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
			}
		}).setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				mFA_act.finish();
			}
		})
		.show();
	}
	
	public String getLocalString(LatLng latlng){
		String result = null;
		Geocoder geocoder = new Geocoder(mFA_act, Locale.KOREAN);
		try {
			Address address = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1).get(0);
			if(address.getThoroughfare() != null) result = address.getThoroughfare();
			else if(address.getLocality() != null) result = address.getLocality();
			else result = null;
		} catch (IOException e) {}
		
		return result;
	}
}
