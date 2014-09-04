package ummom.child.album;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ummom.child.APIHandler;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @clas gallerymaker
 * @desc 갤러리 만들어주는 클래스, 생성자 호출시에 생성자의 정보로 화면에 뿌려준다.
 *       가능하면 Fragment로 재작성해보자.
 * @author Lemoness
 *
 */
@SuppressWarnings("deprecation")
public class GalleryMaker{

	private ArrayList<String> mAL_Imglist;
	private ArrayList<ImageContainer> mAL_Data;
	private Context mC_Context;
	private int mI_dispw;
	private ImageAdapter mIA_Adapter;
	private Gallery mG_Gallery;
	private Options mO_Opt;
	
	public GalleryMaker(Context context, 
						Gallery gal, 
						final ImageView inpview,
						final TextView title, 
						final TextView desc){
		
		/* 전역변수, 필요한 변수 설정 */
		mAL_Imglist = new ArrayList<String>();
		mAL_Data = new ArrayList<ImageContainer>();
		mC_Context = context;
		mG_Gallery = gal;
		
		/* Bitmap 옵션 설정 */
		mO_Opt = new Options();
		mO_Opt.inPreferredConfig = Config.RGB_565;
		mO_Opt.inJustDecodeBounds = true;
		mO_Opt.inPurgeable = true;
		mO_Opt.inDither = true;
		mO_Opt.inJustDecodeBounds = false;
		
		/* 화면 사이즈 구하기 */
		Display disp = ((WindowManager) mC_Context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		mI_dispw = disp.getWidth();
		
		/* 갤러리 그림 간격 조정 */
		mG_Gallery.setSpacing(10);

		/* 파일 읽어오기 */
		//getfilelist();
		getImagelist();
		
		/* 어뎁터 설정 */
		mIA_Adapter = getAdapter();
		mG_Gallery.setAdapter(mIA_Adapter);
		
		/* 클릭 리스너 설정 */
		gal.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ImageView imgview = inpview;
				try {
					imgview.setImageBitmap(new resizeTask().execute(mIA_Adapter.getItem(arg2).getURL()).get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 클릭시 URL의 원본이미지 가져오기 & 띄우기
				
				title.setText(mIA_Adapter.getItem(arg2).getName());
				desc.setText("테스트용"+'\n'+"라인1"+'\n'+"라인2"+'\n'+"라인3");
			}
		});
	}
	
	/*
	 * 디버깅용 메소드
	 * /핸드폰의 DCIM/Camera/test 의 사진 목록을 가져온다.
	 * 현재 사용하지 않음.
	 */
	public void getfilelist(){
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()
					  + File.separator
					  + "Camera"
					  + File.separator
					  + "test";
		File list = new File(path);
		
		File[]strlist = list.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String filename) {
				// TODO Auto-generated method stub
				boolean bOK = false;
				if(filename.toLowerCase().endsWith(".png")) bOK = true;
				if(filename.toLowerCase().endsWith(".jpg")) bOK = true;
				if(filename.toLowerCase().endsWith(".jpeg")) bOK = true;
				if(filename.toLowerCase().endsWith(".bmp")) bOK = true;
				if(filename.toLowerCase().endsWith(".gif")) bOK = true;
				return bOK;
			}
		});
		
		
		for(int i=0 ; i < strlist.length ; i++){
			mAL_Imglist.add(strlist[i].toString());
		}
		
	}
	
	public void getImagelist(){
//		APIHandler handler = new APIHandler();
//		mImglist = handler.parsingGallery(handler.getGallery("test"));
		new getimageTask().execute();
	}
	
	// 이미지 크기 조절룡 메서드, 크기는 화면에 맞춰서 자동조절, 옵션은 내부에서 지정해 사용한다.
	private Bitmap Resizer(String dir){
		/* 옵션 설정 */
		Options opt = new Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inJustDecodeBounds = true;
		opt.inPurgeable = true;
		opt.inDither = true;
		
		BitmapFactory.decodeFile(dir, opt);
		float scalew = opt.outWidth / mI_dispw;
		
		if(scalew >= 8){
			opt.inSampleSize = 8;
		}else if(scalew >= 6){
			opt.inSampleSize = 6;
		}else if(scalew >= 4){
			opt.inSampleSize = 4;
		}else if(scalew >= 2){
			opt.inSampleSize = 2;
		}else{
			opt.inSampleSize = 1;
		}
		
		opt.inJustDecodeBounds = false;
		//return BitmapFactory.decodeFile(dir, opt);
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) new URL(dir).openConnection();
			connection.connect();
			return BitmapFactory.decodeStream(new BufferedInputStream(connection.getInputStream()),null,opt);
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

	// 이미지 크기 조절룡 메서드, 입력바든 크기로 리사이즈 시켜준다.
	private Bitmap Resizer(String dir, int width, int height){
		/* 옵션 설정 */
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

	public ArrayList<ImageContainer> getimage(){
		for(final String str : mAL_Imglist){
			new galleryinitTask().execute(str);
		}
		Log.d("debuging:gallery","empty : "+mAL_Data.isEmpty());
		return mAL_Data;
	}
	
	public ImageAdapter getAdapter(){
		ImageAdapter adapter = new ImageAdapter(mC_Context, mAL_Data);
		return adapter;
	}
	
	/*
	 * 이미지 크기 조절용 스레드 클래스 (갤러리 리스트에 출력용)
	 * 메인스레드에서 동작시 속도가 느리므로 스레드로 빼준다.
	 * 180,180  크기로 지정해 조절해준다.
	 * @param String 이미지 url
	 * @return 없이 스레드 종료후 어뎁터에 리스트 변경 알려줌
	 */
	private class galleryinitTask extends AsyncTask<String, Void, Void>{
	
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.d("gallery","load start"+params[0]);
			Bitmap bitmap = Resizer(params[0],180,180);
			mIA_Adapter.addItem(new ImageContainer(params[0], params[0], params[0], bitmap, null));
			return null;
		}
		//이미지 로딩 이후 어뎁터에 알려줌
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Log.d("gallery","load end");
			super.onPostExecute(result);
			mIA_Adapter.notifyDataSetChanged();
		}
	}
	
	/*
	 * 이미지 크기 조절용 스레드 클래스 (리스트 클릭시 화면에 출력용)
	 * 메인스레드에서 동작시 속도가 느리므로 스레드로 빼준다.
	 * 화면 크기에 맞춰서 옵션을 통해 자동조절
	 * @param String 이미지 url
	 * @return Bitmap 크기조절된 비트맵 이미지
	 */
	private class resizeTask extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.d("gallery","load start"+params[0]);
			Bitmap bitmap = Resizer(params[0]);
			return bitmap;
		}
	}
	
	/*
	 * APIhandler 이용해서 이미지 가져오는 스레드 클래스
	 * 인터넷 연결이므로 스레드로 빼줌
	 */
	private class getimageTask extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			APIHandler handler = new APIHandler();
			mAL_Imglist = handler.parsingGallery(handler.getGallery());
			for(String string : mAL_Imglist){
				Log.d("gallery","parsed JSON : " + string);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			getimage();
		}

	}
}

/**
 * @class imageadapter
 * @desc 이미지를 갤러리에 뿌려주는 어뎁터
 * @author Lemoness
 *
 */
class ImageAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<ImageContainer> mData;
	
	public ImageAdapter(){
		
	}
	public ImageAdapter(Context context, ArrayList<ImageContainer> data){
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		mData = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
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
		ImageView i = new ImageView(mContext);
		
		i.setImageBitmap(mData.get(arg0).getThumnail());
		
		return i;
	}
	
}

/**
 * @class imagecontainer
 * @desc 갤러리 이미지 정보 저장 클래스
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