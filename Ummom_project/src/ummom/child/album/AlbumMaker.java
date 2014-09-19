package ummom.child.album;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ummom.child.APIHandler;

import ummom.login.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @class albummaker
 * @desc 앨범Fragment 관련 클래스
 * @author Lemoness
 *
 */
public class AlbumMaker extends Fragment{


	private FragmentActivity mFA_act;
	private View mView;
	private ListView mListview;
	private AlbumAdapter mAdapter;

	public AlbumMaker(){
		
	}
	public AlbumMaker(FragmentActivity act){
		mFA_act = act;
	}
	
	//Fragment에 View를 띄워주는 부분
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
		
		//Fragment로 시작될 경우 FragmentActivity 받아주는 부분.
		if(mFA_act == null){
			mFA_act = (FragmentActivity) getActivity();
		}
		// View가 처음 사용될 때 inflage
		if(mView == null){
			mView = inflater.inflate(R.layout.fragment_album, container, false);
			mListview = (ListView) mView.findViewById(R.id.fragment_album_list);
			mListview.setPadding(10, 10, 10, 0);
			
			Button btn_add = new Button(mFA_act);
			btn_add.setBackgroundResource(R.drawable.view_border);
			btn_add.setText("새 앨범 추가하기");
			
			Button btn_up = new Button(mFA_act);
			btn_up.setBackgroundResource(R.drawable.view_border);
			btn_up.setText("맨 위로");
			
			mAdapter = new AlbumAdapter(mFA_act, new ArrayList<AlbumDataset>());
			mListview.addHeaderView(btn_add);
			mListview.addFooterView(btn_up);
			
			//추가 버튼 클릭시
			btn_add.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_PICK);
					intent.setType(Media.CONTENT_TYPE);
					intent.setData(Media.INTERNAL_CONTENT_URI);
					startActivityForResult(intent, 0);
					
				}
			});
			
			//위로 버튼 클릭시
			btn_up.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					mListview.setScrollX(0);
				}
			});
			
			//리스트 아이템 클릭시
			mListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Toast.makeText(mFA_act, "list clicked!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(mFA_act, GalleryMaker.class);
					intent.putExtra("dir", ((AlbumDataset)mAdapter.getItem(arg2)).getThumnail());
					mFA_act.startActivity(intent);
					mFA_act.overridePendingTransition(R.anim.page_appear, R.anim.page_donmove);
				}
			});
		}
		
		//어뎁터 설정
		mListview.setAdapter(mAdapter);
		new getlistTask().execute("cest");		// 유저의 아이디가 다시 들어가야한다. 현재 테스트용 cest
		return mView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){
			if(resultCode == Activity.RESULT_OK){
				Toast.makeText(mFA_act, data.getData().toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if(mListview != null){
			mListview.removeAllViews();
		}
		if(mView != null){
			ViewGroup parent = (ViewGroup) mView.getParent();
			if(parent != null){
				parent.removeView(mView);
			}
		}
	}
	
	/*
	 * APIhandler 데이터 받아오기용 스레드
	 */
	class getlistTask extends AsyncTask<String, Void, Void>{

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			APIHandler handler = new APIHandler();
			Bundle result = handler.parsingAlbum(handler.getAlbum(params[0]));
			ArrayList<AlbumDataset> dataset = new ArrayList<AlbumDataset>();
			for(int i=0, max = result.getInt("count") ; i<max ; i++){
				dataset.add(new AlbumDataset(result.getStringArrayList("album_url").get(i),
											 result.getStringArrayList("title").get(i),
											 params[0],
											 result.getStringArrayList("album_reg").get(i)));
			}
			mAdapter.setData(dataset);
			return null;
		}
		// 스레드 종료이후 어뎁터 최신화
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			mAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}
}

/**
 * @class albumadapter 
 * @desc 앨범 표시해줄 어뎁터
 * @author Lemoness
 *
 */
class AlbumAdapter extends BaseAdapter{
	
	private ArrayList<AlbumDataset> mData;
	private Context mContext;
	private Options mO_Opt;
	
	public AlbumAdapter(){
		
	}
	public AlbumAdapter(Context context, ArrayList<AlbumDataset> data){
		mContext = context;
		mData = data;
		/* Bitmap 옵션 설정 */
		mO_Opt = new Options();
		mO_Opt.inPreferredConfig = Config.RGB_565;
		mO_Opt.inJustDecodeBounds = true;
		mO_Opt.inPurgeable = true;
		mO_Opt.inDither = true;
		mO_Opt.inJustDecodeBounds = false;
	}
	
	public void setData(ArrayList<AlbumDataset> data){
		mData = data;
	}
	public void addData(AlbumDataset data){
		mData.add(data);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	//화면에 뿌려주는 메서드, 으으...
	@Override
	@SuppressWarnings("deprecation")
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(arg1 == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg1 = inflater.inflate(R.layout.fragment_album_item, arg2, false);
		}
		RelativeLayout layout = (RelativeLayout) arg1.findViewById(R.id.fragalbum_item_layout);
		TextView title = (TextView) arg1.findViewById(R.id.fragalbum_item_title);
		TextView date = (TextView) arg1.findViewById(R.id.fragalbum_item_date);
		try {
			layout.setBackground(new BitmapDrawable(new getImageTask().execute(mData.get(arg0).getThumnail()).get()));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		title.setText(mData.get(arg0).getTitle());
		date.setText(mData.get(arg0).getDate_String());
		return arg1;
	}

	// 앨범 섬네일 이미지 줄여주기용 Resizer클래스, ummom.child.ImageHandler 사용해서 바꾸기
	private Bitmap Resizer(String dir, int width, int height){
		/* �ɼ� ���� */
		//return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(dir, mOpt), width, height, true);
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) new URL(dir).openConnection();
			connection.connect();
			return Bitmap.createScaledBitmap(
						BitmapFactory.decodeStream(
							new BufferedInputStream(connection.getInputStream()),null,mO_Opt),
						width,
						height,
						true);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * APIhandler 에서 이미지 가져우는 쓰레드
	 */
	class getImageTask extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			return Resizer(params[0], 360, 120);
		}
		
	}
}

/**
 * @class albumdataset
 * @desc 앨범 데이터 저장용 클래스
 * @author Lemoness
 *
 */
class AlbumDataset {
	
	private String mThumnailurl;
	private String mAlbumtitle;
	private String mAlbumuser;
	private Time mAlbumdate;
	
	public AlbumDataset(){
		
	}
	public AlbumDataset(String thumnail, String title, String user, String date){
		mThumnailurl = thumnail;
		mAlbumtitle = title;
		mAlbumuser = user;
		mAlbumdate = new Time();
		mAlbumdate.set(Integer.parseInt(date.substring(9,10)),
					   Integer.parseInt(date.substring(6, 7)),
					   Integer.parseInt(date.substring(1,4)));
	}
	
	public void setThumnail(String thumnail){
		mThumnailurl = thumnail;
	}
	public void setTitle(String title){
		mAlbumtitle = title;
	}
	public void setUser(String user){
		mAlbumuser = user;
	}
	public void setDate(String date){
		mAlbumdate = new Time();
		mAlbumdate.set(Integer.parseInt(date.substring(9,10)),
					   Integer.parseInt(date.substring(6, 7)),
					   Integer.parseInt(date.substring(1,4)));
	}
	
	public String getThumnail(){
		return mThumnailurl;
	}
	public String getTitle(){
		return mAlbumtitle;
	}
	public String getUser(){
		return mAlbumuser;
	}
	public Time getDate_Time(){
		return mAlbumdate;
	}
	public String getDate_String(){
		return mAlbumdate.year + "/" + (mAlbumdate.month+1) + "/" + mAlbumdate.monthDay;
	}
}