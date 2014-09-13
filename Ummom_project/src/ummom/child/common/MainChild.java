package ummom.child.common;

import ummom.login.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainChild extends FragmentActivity {
			
	private SlidingTabsBasicFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_child);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new SlidingTabsBasicFragment();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	getActionBar().setDisplayShowHomeEnabled(false);
        getMenuInflater().inflate(R.menu.main, menu);        
        return true;
        
    }
    
}
