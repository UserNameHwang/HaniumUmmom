package ummom.child.album;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ummom.child.APIHandler;
import ummom.child.ImageHandler;


import ummom.login.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @clas gallerymaker
 * @desc  앨범에서 받아온 섬네일의 사진을 표시해주는 클래스.
 *       새로운 Activity생성됨.
 * @author Lemoness
 *
 */
@SuppressWarnings("deprecation")
public class GalleryMaker extends FragmentActivity{

	private ArrayList<String> mAL_Imglist;
//	private ArrayList<ImageContainer> mAL_Data;
	private FragmentActivity mFA_act;
	private ImageAdapter mIA_Adapter;
	private Gallery mG_Gallery;
	private ImageHandler mImagehandler;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.fragment_gallery);
		
		mFA_act = this;
		mImagehandler = new ImageHandler(mFA_act);
		
		mG_Gallery = (Gallery) findViewById(R.id.fraggallery_gallery);
		final ImageView inpview = (ImageView) findViewById(R.id.fraggallery_imgview);
		final TextView title = (TextView) findViewById(R.id.fraggallery_titletext);
		final TextView desc = (TextView) findViewById(R.id.fraggallery_desctext);
		String dir = getIntent().getExtras().getString("dir");
		
		/* 갤러리 여백 설정 */
		mG_Gallery.setSpacing(10);

		/* 이미지 리스트 가져오는 부분 */
		getImagelist(dir.substring(21));//앞의 서버 주소를 제거하기 위해 substring
		
		/* 어뎁터 설정 */
		mIA_Adapter = new ImageAdapter(mFA_act);
		mG_Gallery.setAdapter(mIA_Adapter);
		
		/* 클릭시 이미지 확대용 클릭리스너 */
		mG_Gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ImageView imgview = inpview;
				try {
					imgview.setImageBitmap(new resizeTask().execute(mIA_Adapter.getItem(arg2).getURL()).get());
				} catch (Exception e){
					e.printStackTrace();
				}
				
				title.setText(mIA_Adapter.getItem(arg2).getName());
				desc.setText("날짜"+'\n'+"설명1"+'\n'+"설명2"+'\n'+"설명3");
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void getImagelist(String dir){
		new galleryinitTask().execute(dir);
	}
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		overridePendingTransition(R.anim.page_donmove, R.anim.page_disappear);
		finish();
	}

	
	/*
	 * 갤러리에서 이미지 클릭 시 이미지 뷰에 띄워주기 위해 리사이징 하는 스레드 클래스
	 * @params String url
	 * @return Bitmap image
	 */
	private class resizeTask extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.d("gallery","load start : " + params[0]);
			Bitmap bitmap = mImagehandler.resizer_bitmap(params[0]);
			Log.d("gallery","load end : " + params[0]);
			return bitmap;
		}
	}
	
	/*
	 * APIhandler를 이용해 param[0]에 해당하는 앨범 이미지 리스트를 
	 * 받아오는 스레드 클래스
	 */
	private class galleryinitTask extends AsyncTask<String, Void, Void>{
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			APIHandler handler = new APIHandler();
			mAL_Imglist = handler.parsingGallery(handler.getGallery(params[0]));
			Log.d("gallery",mAL_Imglist.toString());
			for(final String str : mAL_Imglist){
				new resizeGalleryTask().execute(str);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
	
	/*
	 * 갤러리에 띄위주기 위해 이미지 리사이징 해주는 스레드 클래스
	 * @params String
	 * @return null
	 * @postExec 어뎁터 최신화
	 */
	private class resizeGalleryTask extends AsyncTask<String, Void, Void>{
	
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.d("gallery","load start"+params[0]);
			Bitmap bitmap = mImagehandler.resizer_bitmap(params[0], 180, 180);
			mIA_Adapter.addItem(new ImageContainer(params[0], params[0], params[0], bitmap, null));
			return null;
		}
		//스레드 동작이후 데이터 최신화 & 첫번째 화면 표시
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Log.d("gallery","load end");
			super.onPostExecute(result);
			mIA_Adapter.notifyDataSetChanged();
			mG_Gallery.performItemClick(getCurrentFocus(), 0, 0);
		}
	}
}

/**
 * @class imageadapter
 * @desc 갤러리에 사용될 어뎁터 정의 클래스
 * @author Lemoness
 *
 */
class ImageAdapter extends BaseAdapter{

	private FragmentActivity mFA_act;
	private LayoutInflater mInflater;
	private ArrayList<ImageContainer> mData;
	
	public ImageAdapter(){
		
	}
	public ImageAdapter(FragmentActivity context){
		mFA_act = context;
		mInflater = (LayoutInflater) mFA_act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mData = new ArrayList<ImageContainer>();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mData == null) return 0;
		else return mData.size();
	}

	@Override
	public ImageContainer getItem(int arg0) {
		// TODO Auto-generated method stub
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return mData.indexOf(mData.get(arg0));
	}
	
	public String getName(int arg0){
		return mData.get(arg0).getName();
	}
	public String getExpr(int arg0){
		return mData.get(arg0).getExpr();
	}
	public String getURL(int arg0){
		return mData.get(arg0).getURL();
	}
	public Bitmap getThumnail(int arg0){
		return mData.get(arg0).getThumnail();
	}
	public Bitmap getImage(int arg0){
		return mData.get(arg0).getImage();
	}
	
	public void setName(int arg0, String name){
		mData.get(arg0).setName(name);
	}
	public void setExpr(int arg0,String expr){
		mData.get(arg0).setExpr(expr);
	}
	public void setURL(int arg0, String URL){
		mData.get(arg0).setURL(URL);
	}
	public void setThumnail(int arg0, Bitmap img){
		mData.get(arg0).setThumnail(img);
	}
	public void setImage(int arg0, Bitmap img){
		mData.get(arg0).setImage(img);
	}
	
	public void addItem(ImageContainer arg0){
		mData.add(arg0);
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ImageView i = new ImageView(mFA_act);
		i.setImageBitmap(mData.get(arg0).getThumnail());
		
		return i;
	}
	
}

/**
 * @class imagecontainer
 * @desc  이미지 저장용 클래스
 * @author Lemoness
 *
 */
class ImageContainer{
	private String mName;
	private String mExpr;
	private String mURL;
	private Bitmap mThumnail;
	private Bitmap mImg;
	
	public ImageContainer(){
		mName="";
		mExpr="";
		mURL="";
		mImg=null;
	}
	
	public ImageContainer(String name, String expr, String URL,Bitmap thumnail, Bitmap img){
		mName = name;
		mExpr = expr;
		mURL = URL;
		mThumnail = thumnail;
		mImg = img;
	}
	
	public String getName(){
		return mName;
	}
	public String getExpr(){
		return mExpr;
	}
	public String getURL(){
		return mURL;
	}
	public Bitmap getThumnail(){
		return mThumnail;
	}
	public Bitmap getImage(){
		return mImg;
	}
	
	public void setName(String name){
		mName = name;
	}
	public void setExpr(String expr){
		mExpr = expr;
	}
	public void setURL(String URL){
		mURL = URL;
	}
	public void setThumnail(Bitmap img){
		mThumnail = img;
	}
	public void setImage(Bitmap img){
		mImg = img;
	}
}