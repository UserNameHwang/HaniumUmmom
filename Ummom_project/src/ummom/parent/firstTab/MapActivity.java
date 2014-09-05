package ummom.parent.firstTab;

import ummom.login.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MapActivity extends FragmentActivity{
	
	private GoogleMap scheduleMap;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6495ed")));
		
		setContentView(R.layout.map_fragment);
		
		LatLng rest = new LatLng(37.496498, 126.956872);

		scheduleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.schedule_map)).getMap();

		scheduleMap.setMyLocationEnabled(true);
		scheduleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rest,
				16));
		scheduleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		overridePendingTransition(R.anim.page_donmove, R.anim.page_disappear);
		
		finish();
	}

}
