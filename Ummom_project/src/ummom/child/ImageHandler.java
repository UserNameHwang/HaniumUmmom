package ummom.child;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class ImageHandler {
	
	private Options mOption_fit;
	private Options mOption_small;
	
	public ImageHandler(){

		/* Bitmap 옵션 설정 - 화면에 맞춤 */
		mOption_fit = new Options();
		mOption_fit.inPreferredConfig = Config.RGB_565;
		mOption_fit.inJustDecodeBounds = true;
		mOption_fit.inPurgeable = true;
		mOption_fit.inDither = true;

		/* Bitmap 옵션 설정 - 크기에 맞춤 */
		mOption_small = new Options();
		mOption_small.inPreferredConfig = Config.RGB_565;
		mOption_small.inJustDecodeBounds = true;
		mOption_small.inPurgeable = true;
		mOption_small.inDither = true;
		mOption_small.inJustDecodeBounds = false;
		
	}
	
	public Bitmap resizer_bitmap(){
		Bitmap result = null;
		return result;
	}
}
