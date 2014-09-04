package ummom.teacher.thridTab;

import ummom.login.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StudentsView extends LinearLayout{
	
	private ImageView mIcon;
	private TextView name;
	private TextView birth;
	private TextView phone;
	private ImageView mIcon2;
	
	public StudentsView(Context context, StudentsItem mItem) {
		super(context);
		// TODO Auto-generated constructor stub
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.teacher_thirdtab_studentsrow, this, true);
		
		mIcon = (ImageView)findViewById(R.id.img);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mItem.getImg());
		mIcon.setImageBitmap(getRoundedCornerBitmap(bitmap));
		
		name = (TextView)findViewById(R.id.name);
		name.setText(mItem.getName());
		
		birth = (TextView) findViewById(R.id.birth);
		birth.setText(mItem.getBirth());
		
		phone = (TextView) findViewById(R.id.phone);
		phone.setText(mItem.getPhone());
		
		mIcon2 = (ImageView) findViewById(R.id.next_icon);
		Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), mItem.getIcon());
		mIcon2.setImageBitmap(getRoundedCornerBitmap(bitmap2));
		
	}
	
	public void setText(int index, String data) {
		if (index == 0) {
			name.setText(data);
		} else if (index == 1) {
			birth.setText(data);
		} else if (index == 2) {
			phone.setText(data);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * set Icon
	 *
	 * @param drawable
	 */
	public void setIcon(int index ,int drawable) {
		if( index ==0 ){
			
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawable);
			mIcon.setImageBitmap(getRoundedCornerBitmap(bitmap));
			
		}else{
			
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawable);
			mIcon2.setImageBitmap(bitmap);
		}
		
	}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 550;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
	

}
