package ummom.teacher.register;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import ummom.login.R;
import ummom.teacher.etc.MD5;


import android.R.bool;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class RegisterActivity extends Activity implements OnTouchListener {

	private EditText edit_id;
	private EditText edit_password;
	private EditText edit_password2;
	private EditText edit_name;
	
	private EditText edit_year;
	private EditText edit_month;
	private EditText edit_day;
	
	private EditText edit_phone;
	
	ArrayAdapter<CharSequence> adspin;

	private TextView textView_error_id;
	private TextView textView_error_name;

	private TextView textView_error_pwd;
	private TextView textView_error_pwd2;
	private TextView textView_error_phone;
	
	private TextView textView_error_birth;

	private EditText edit_pwd;
	private EditText edit_pwd2;

	private EditText edit_before = null;

	Button btn_register;
	
	String type= "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		edit_id = (EditText) findViewById(R.id.editText_id);
		edit_password = (EditText) findViewById(R.id.editText_pwd);
		edit_password2 = (EditText) findViewById(R.id.editText_pwd2);
		edit_name = (EditText) findViewById(R.id.editText_name);
		edit_phone = (EditText) findViewById(R.id.editText_phone);
		
		edit_year = (EditText) findViewById(R.id.editText_birth_Year);
		edit_month = (EditText) findViewById(R.id.editText_birth_Month);
		edit_day = (EditText) findViewById(R.id.editText_birth_Day);
		
		Spinner spinner = (Spinner) findViewById (R.id.spinner);
		spinner.setPrompt("타입을 선택해 주세요.");
		
		adspin = ArrayAdapter.createFromResource(this, R.array.selected,  android.R.layout.simple_spinner_item);
		
		adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adspin);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				 Toast.makeText(RegisterActivity.this,
	                        adspin.getItem(position) + "을 선택 했습니다.", 1).show();
				 
				 type = (String) adspin.getItem(position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btn_register = (Button) findViewById(R.id.btn_registerOK);

		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RegisterThread thread = new RegisterThread();
				
				thread.start();
				
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		
		edit_id.setOnTouchListener(this);
		edit_password.setOnTouchListener(this);
		edit_password2.setOnTouchListener(this);
		edit_name.setOnTouchListener(this);
		edit_phone.setOnTouchListener(this);
		
		edit_year.setOnTouchListener(this);
		edit_month.setOnTouchListener(this);
		edit_day.setOnTouchListener(this);

		edit_pwd = edit_password;
		edit_pwd2 = edit_password2;

		textView_error_id = (TextView) findViewById(R.id.textView_error_id);
		textView_error_pwd = (TextView) findViewById(R.id.textView_error_pwd);
		textView_error_pwd2 = (TextView) findViewById(R.id.textView_error_pwd2);
		textView_error_name = (TextView) findViewById(R.id.textView_error_name);
		textView_error_phone = (TextView) findViewById(R.id.textView_error_phone);
		textView_error_birth = (TextView) findViewById(R.id.textView_error_birth);

		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6495ed")));
		bar.setTitle("회원가입");
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayUseLogoEnabled(false);

	}
	
	class RegisterThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {

				HttpClient client = new DefaultHttpClient();
				String path = "http://14.63.212.236/index.php/api_c/register";
				
				HttpPost post = new HttpPost(path);
				HttpConnectionParams.setConnectionTimeout(client.getParams(), 30000);

				String id = edit_id.getText().toString();
				String pwd = edit_pwd.getText().toString();
				String name = edit_name.getText().toString();
				String phone = edit_phone.getText().toString();
				String birth = edit_year.getText().toString() + "-" +
								edit_month.getText().toString() + "-" +
								edit_day.getText().toString();
				int user_type = 0;
				
				if(type.equals("부모님")){
					user_type = 1;
				}else if(type.equals("선생님")){
					user_type = 2;
				}else{
					user_type = 3;
				}
				
				Log.d("", "Type : "+ Integer.toString(user_type));
				Log.d("", "birth : " + birth );
				
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", id));
				params.add(new BasicNameValuePair("pwd", pwd));
				params.add(new BasicNameValuePair("name", name));
				params.add(new BasicNameValuePair("phone", phone));
				params.add(new BasicNameValuePair("birth", birth));
				params.add(new BasicNameValuePair("type", Integer.toString(user_type)));		

				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,	HTTP.UTF_8);
				post.setEntity(ent);

				HttpResponse httpResponse = client.execute(post);
				HttpEntity resEn = httpResponse.getEntity();

				String parse = EntityUtils.toString(resEn);

				JSONObject object = new JSONObject(parse);
				
				Log.d("@", object.toString());
				
				String result = object.getString("result");
				int reg = Integer.parseInt(result);
				
				if(reg == 400){
					
					finish();
				}	

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void setBackground(EditText edit) {

		if (edit_before == null) {
			edit_before = edit;
			edit.setBackground(getResources().getDrawable(
					R.drawable.edit_select));
		} else {
			checknull(edit_before);
			edit_before.setBackground(getResources().getDrawable(
					R.drawable.edit_defualt));
			edit.setBackground(getResources().getDrawable(
					R.drawable.edit_select));
			edit_before = edit;
		}

		comparePWD();

	}

	public void checknull(EditText edit) {
		String string_check = edit.getText().toString();
		String s_null = "입력필수 사항 입니다.";

		if (string_check.equals("")) {
			switch (edit.getId()) {
			case R.id.editText_name:
				textView_error_name.setText(s_null);
				break;

			case R.id.editText_pwd:
				textView_error_pwd.setText(s_null);
				break;

			case R.id.editText_id:
				textView_error_id.setText(s_null);
				break;

			case R.id.editText_phone:
				textView_error_phone.setText(s_null);
				break;
				
			case R.id.editText_birth_Year:
				textView_error_birth.setText(s_null);
				break;
			case R.id.editText_birth_Month:
				textView_error_birth.setText(s_null);
				break;
			case R.id.editText_birth_Day:
				textView_error_birth.setText(s_null);
				break;
				
			default:
				break;
			}
		} else {
			switch (edit.getId()) {
			case R.id.editText_name:
				textView_error_name.setText("");
				break;

			case R.id.editText_id:
				textView_error_id.setText("");
				break;

			case R.id.editText_phone:
				textView_error_phone.setText("");
				break;
				
			case R.id.editText_birth_Year:
				textView_error_birth.setText("");
				break;
			case R.id.editText_birth_Month:
				textView_error_birth.setText("");
				break;
			case R.id.editText_birth_Day:
				textView_error_birth.setText("");
				break;
				
			default:
				break;
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.editText_id:

			setBackground((EditText) v);

			break;

		case R.id.editText_pwd:

			setBackground((EditText) v);

			break;

		case R.id.editText_pwd2:
			setBackground((EditText) v);

			break;

		case R.id.editText_name:
			setBackground((EditText) v);

			break;

		case R.id.editText_phone:
			setBackground((EditText) v);
			break;
		case R.id.editText_birth_Year:
			setBackground((EditText) v);
			break;
			
		case R.id.editText_birth_Month:
			setBackground((EditText) v);
			break;
			
		case R.id.editText_birth_Day:
			setBackground((EditText) v);
			break;
			

		default:
			break;
		}
		return false;
	}

	public void comparePWD() {

		String pwd = edit_pwd.getText().toString();
		String pwd2 = edit_pwd2.getText().toString();

		if (pwd.equals(pwd2)) {

			if (pwd.equals("")) {
				// textView_error_pwd.setText("비밀번호를 입력해주세요.");
			} else {
				textView_error_pwd.setText("");
				textView_error_pwd2.setText("");
			}

		} else {
			textView_error_pwd.setText("");
			textView_error_pwd2.setText("비밀번호가 틀립니다.");
		}
	}

}
