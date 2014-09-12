package ummom.teacher.thridTab;



import ummom.login.R;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class StudentsDetail extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.students_detail);
		
		ActionBar actionbar = getActionBar();
		actionbar.hide();		
		Intent intent = getIntent();
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();		
		overridePendingTransition(R.anim.page_donmove, R.anim.page_disappear );	      
	    finish();
	    
	}
}
