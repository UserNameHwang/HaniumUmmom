package ummom.login;

import ummom.child.common.MainChild;
import ummom.parent.common.MainParent;
import ummom.teacher.register.RegisterActivity;
import ummom.teacher.mainsliding.MainTeacher;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends Activity implements OnClickListener{
	
	private Button btnLog;
	private EditText id_edit;
	private EditText password_edit;
	private Button btnRegister;
	static TextView test;
	
	private final int teacher = 1;
	private final int parent = 2;
	private final int child = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        id_edit = (EditText)findViewById(R.id.id_editText);
		password_edit = (EditText)findViewById(R.id.password_editText);
		
		btnLog = (Button) findViewById(R.id.btn_login);
		btnLog.setOnClickListener(this);
		
		btnRegister = (Button)findViewById(R.id.btn_register);
		btnRegister.setOnClickListener(this);
		
		ActionBar bar = getActionBar();
		bar.hide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
        
		case R.id.btn_login:
			login();
			break;
			
		case R.id.btn_register:
			register();
			break;

		default:
			break;
		}
		
	}
	
	public void login(){
    	LoginModel.id = id_edit.getText().toString();
		LoginModel.password = password_edit.getText().toString();	
		
		try {
			LoginThread loginT = new LoginThread();
			loginT.start();
			loginT.join();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		if(LoginModel.login_success==true)
        {
			if(LoginModel.mb_type == teacher){
				Intent intent = new Intent(LoginActivity.this, MainTeacher.class);
				startActivity(intent);                
			}
			else if(LoginModel.mb_type == child){
				Intent intent = new Intent(LoginActivity.this, MainChild.class);
				startActivity(intent);
			}
			else if(LoginModel.mb_type == parent){
				Intent intent = new Intent(LoginActivity.this, MainParent.class);
				startActivity(intent);
			}
        }
    }
    
    public void register(){
    	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);    	
    }
}

