package ummom.parent.common;

import java.util.HashMap;

import ummom.parent.costPage.CostDetail;
import ummom.parent.firstTab.MapActivity;
import ummom.parent.managePage.IDSearch;
import ummom.parent.managePage.SchoolSearch;

import ummom.login.R;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainParent extends FragmentActivity {

	private SlidingTabsBasicFragment fragment;

	int isOpened;
	private final long INTERVAL = 2000;
	private long backTime = 0;

	int cnt2;
	private final static int ID_ACTIVITY = 1;
	private final static int SCH_ACTIVITY = 2;

	private int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_parent);
		
		// GCM 등록 과정
		if (checkPlayServices()) {
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);
			final String regId = GCMRegistrar.getRegistrationId(this);

			if (regId.equals("")) 
				GCMRegistrar.register(this, "925295520154");
			
		}
		
		SharedPreferences GCM_regId = getSharedPreferences("GCM_regId",
				MODE_PRIVATE);

		Log.e("==============", "ID : " + GCM_regId.getString("GCM_regId", ""));
		// 등록 끝
		
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		fragment = new SlidingTabsBasicFragment();
		transaction.replace(R.id.sample_content_fragment, fragment);
		transaction.commit();
		
	}
	
	// GCM 서비스가 가능 여부를 리턴하는 메서드
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i("Main", "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
	public void onSearch(View v)
	{
		Context c = getApplicationContext();
		Intent intent = new Intent(c, MapActivity.class);
		
		startActivity(intent);
		
		overridePendingTransition(R.anim.page_appear, R.anim.page_donmove);
	}
	
	public void onDetail(View v)
	{
		Context c = getApplicationContext();
		Intent intent = new Intent(c, CostDetail.class);
		
		startActivity(intent);
		
		overridePendingTransition(R.anim.page_appear, R.anim.page_donmove);
	}
	
	public void onID(View v)
	{
		Context c = getApplicationContext();
		Intent intent = new Intent(c, IDSearch.class);
		
		startActivityForResult(intent, ID_ACTIVITY);
		
		overridePendingTransition(R.anim.page_appear, R.anim.page_donmove);
	}
	
	public void onSchool(View v)
	{
		Context c = getApplicationContext();
		Intent intent = new Intent(c, SchoolSearch.class);
		
		startActivityForResult(intent, SCH_ACTIVITY);
		overridePendingTransition(R.anim.page_appear, R.anim.page_donmove);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);

		if( requestCode == ID_ACTIVITY )
		{
			if( resultCode == RESULT_OK )
			{
				Bundle bundle = intent.getExtras();
				HashMap<String, String> map = (HashMap<String, String>) bundle.getSerializable("HashMap");
				ViewFlipper title, content;
				
				TextView name = (TextView)findViewById(R.id.teacher_namecon);
				TextView grade = (TextView)findViewById(R.id.teacher_classcon);
				TextView contract = (TextView)findViewById(R.id.teacher_contactcon);
				ImageView image = (ImageView)findViewById(R.id.teacher_image);
			//	TextView comment = (TextView)findViewById(R.id.teacher_today);
				
				name.setText(map.get("name"));
				grade.setText(map.get("grade"));
				contract.setText(map.get("phone"));
				
			//	Log.d("comments", ""+map.get("comments"));
				
				Bitmap profile = bundle.getParcelable("Bitmap");
				image.setImageBitmap(profile);
				
			//	comment.setText(map.get("comments"));
				title = (ViewFlipper)findViewById(R.id.titleFlipper);
				content = (ViewFlipper)findViewById(R.id.contentFlipper);
				
				if (title.getDisplayedChild() == 0) {
					title.showNext();
					content.showNext();
				}
			}
		}
		
		else if( requestCode == SCH_ACTIVITY)
		{
			if( resultCode == RESULT_OK)
			{
				Bundle bundle = intent.getExtras();
				HashMap<String, String> map = (HashMap<String, String>) bundle.getSerializable("HashMap");
				ViewFlipper title, content;
				
				TextView name = (TextView)findViewById(R.id.teacher_namecon);
				TextView grade = (TextView)findViewById(R.id.teacher_classcon);
				TextView contract = (TextView)findViewById(R.id.teacher_contactcon);
				ImageView image = (ImageView)findViewById(R.id.teacher_image);
			//	TextView comment = (TextView)findViewById(R.id.teacher_today);
				
				name.setText(map.get("name"));
				grade.setText(map.get("grade"));
				contract.setText(map.get("phone"));
				
			//	Log.d("comments", ""+map.get("comments"));
				
				Bitmap profile = bundle.getParcelable("Bitmap");
				image.setImageBitmap(profile);
				
			//	comment.setText(map.get("comments"));
				title = (ViewFlipper)findViewById(R.id.titleFlipper);
				content = (ViewFlipper)findViewById(R.id.contentFlipper);
				
				if (title.getDisplayedChild() == 0) {
					title.showNext();
					content.showNext();
				}
			}
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		long tempTime = System.currentTimeMillis();
		long interval = tempTime - backTime;

		if (interval >= 0 && INTERVAL >= interval) {
			super.onBackPressed();
			
			overridePendingTransition(R.anim.page_donmove, R.anim.page_disappear);
			
			finish();
		} else {
			backTime = tempTime;
			Toast.makeText(MainParent.this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		menu.findItem(R.id.action_person).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem arg0) {
				
				Toast.makeText(getApplicationContext(), 
						"Menu Click", Toast.LENGTH_SHORT).show();
				
				return false;
			}
		});
		
		return true;

	}

}

