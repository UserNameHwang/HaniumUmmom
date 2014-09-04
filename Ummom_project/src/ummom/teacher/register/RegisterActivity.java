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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class RegisterActivity extends Activity implements OnTouchListener {

	private EditText edit_id;
	private EditText edit_password;
	private EditText edit_password2;
	private EditText edit_name;
	private EditText edit_birth_yy;
	private EditText edit_birth_mm;
	private EditText edit_birth_dd;
	private EditText edit_phone;
	private EditText edit_type;

	private TextView textView_error_id;
	private TextView textView_error_name;

	private TextView textView_error_pwd;
	private TextView textView_error_pwd2;
	private TextView textView_error_phone;

	private EditText edit_pwd;
	private EditText edit_pwd2;

	private EditText edit_before = null;

	Button btn_register;

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

		btn_register = (Button) findViewById(R.id.btn_registerOK);

		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				register();

			}
		});

		edit_id.setOnTouchListener(this);
		edit_password.setOnTouchListener(this);
		edit_password2.setOnTouchListener(this);
		edit_name.setOnTouchListener(this);
		edit_phone.setOnTouchListener(this);

		edit_pwd = edit_password;
		edit_pwd2 = edit_password2;

		textView_error_id = (TextView) findViewById(R.id.textView_error_id);
		textView_error_pwd = (TextView) findViewById(R.id.textView_error_pwd);
		textView_error_pwd2 = (TextView) findViewById(R.id.textView_error_pwd2);
		textView_error_name = (TextView) findViewById(R.id.textView_error_name);
		textView_error_phone = (TextView) findViewById(R.id.textView_error_phone);

		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6495ed")));
		bar.setTitle("회원가입");
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayUseLogoEnabled(false);

	}

	public void register() {

		try {

			HttpClient client = new DefaultHttpClient();
			String path = "http://14.63.212.236/index.php/api_c/register";
			
			HttpPost post = new HttpPost(path);
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 30000);

			String id = edit_id.getText().toString();
			String pwd = edit_pwd.getText().toString();
			String name = edit_name.getText().toString();
			String phone = edit_phone.getText().toString();
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("pwd", pwd));
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("phone", phone));

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,	HTTP.UTF_8);
			post.setEntity(ent);

			HttpResponse httpResponse = client.execute(post);
			HttpEntity resEn = httpResponse.getEntity();

			String parse = EntityUtils.toString(resEn);

			JSONObject object = new JSONObject(parse);
			JSONObject register = new JSONObject(object.getString("register"));
			String result = register.getString("result");
			int reg = Integer.parseInt(result);
			
			if(reg == 400){
				Toast.makeText(getApplicationContext(), "OK ...!! ", Toast.LENGTH_SHORT).show();
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
