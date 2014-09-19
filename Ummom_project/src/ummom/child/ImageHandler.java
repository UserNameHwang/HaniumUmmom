package ummom.child;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.WindowManager;

public class ImageHandler {
	
	private FragmentActivity mFA_act;
	private Options mOption_fit;
	private Options mOption_small;
	private int mI_dispw;
	
	public ImageHandler(FragmentActivity act){
		
		mFA_act = act;

		// Bitmap 옵션 설정 - 화면에 맞추기
		mOption_fit = new Options();
		mOption_fit.inPreferredConfig = Config.RGB_565;
		mOption_fit.inJustDecodeBounds = true;
		mOption_fit.inPurgeable = true;
		mOption_fit.inDither = true;

		// Bitmap 옵션 설정 - 작은 이미지
		mOption_small = new Options();
		mOption_small.inPreferredConfig = Config.RGB_565;
		mOption_small.inJustDecodeBounds = true;
		mOption_small.inPurgeable = true;
		mOption_small.inDither = true;
		mOption_small.inJustDecodeBounds = false;

		// 화면 사이즈 구하기
		Display disp = ((WindowManager) mFA_act.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		mI_dispw = disp.getWidth();
	}
	
	public Bitmap resizer_bitmap(String dir){
		BitmapFactory.decodeFile(dir, mOption_fit);
		float scalew = mOption_fit.outWidth / mI_dispw;
		
		if(scalew >= 8){
			mOption_fit.inSampleSize = 8;
		}else if(scalew >= 6){
			mOption_fit.inSampleSize = 6;
		}else if(scalew >= 4){
			mOption_fit.inSampleSize = 4;
		}else if(scalew >= 2){
			mOption_fit.inSampleSize = 2;
		}else{
			mOption_fit.inSampleSize = 1;
		}
		
		mOption_fit.inJustDecodeBounds = false;
		
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) new URL(dir).openConnection();
			connection.connect();
			return BitmapFactory.decodeStream(
							new BufferedInputStream(connection.getInputStream()),null,mOption_fit);
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Bitmap resizer_bitmap(String dir, int width, int height){
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) new URL(dir).openConnection();
			connection.connect();
			return Bitmap.createScaledBitmap(
						BitmapFactory.decodeStream(
							new BufferedInputStream(connection.getInputStream()),null,mOption_small),
						width,
						height,
						true);
		}  catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
