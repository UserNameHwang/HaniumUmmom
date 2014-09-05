package ummom.parent.costPage;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Administrator
 *	지출내역의 추가, 변경을 위한
 *	별도의 Dialog UI를 정의한 클래스 
 */
public class DialogUI {

	LinearLayout layout;
	EditText dateEdit;
	EditText descriptEdit;
	EditText expenseEdit;

	Date nowdate;
	Calendar cal;
	
	protected int MODE_ADD = 0;
	protected int MODE_EDIT = 1;

	public DialogUI(Context c, DisplayMetrics dm, int mode, HashMap<String, String> map) {
		// TODO Auto-generated constructor stub

		layout = new LinearLayout(c);

		LinearLayout layoutMonth = new LinearLayout(c);
		LinearLayout layoutDescript = new LinearLayout(c);
		LinearLayout layoutExpense = new LinearLayout(c);

		TextView descript = new TextView(c);
		descript.setText("지출 내역 : ");
		TextView date = new TextView(c);
		date.setText("날짜 : ");
		TextView expense = new TextView(c);
		expense.setText("금액 : ");

		TextView month = new TextView(c);
		TextView year = new TextView(c);
		TextView day = new TextView(c);
		TextView won = new TextView(c);
		
		// 현재 시간을 msec으로 구한다.
		long now = System.currentTimeMillis();

		// 현재 시간을 저장 한다.
		nowdate = new Date(now);
		cal = Calendar.getInstance();
		cal.setTime(nowdate);
		
		year.setText((cal.get(Calendar.YEAR))+"년 ");
		month.setText((cal.get(Calendar.MONTH)+1)+"월 ");
		day.setText("일");

		won.setText("원");
		
		dateEdit = new EditText(c);
		descriptEdit = new EditText(c);
		expenseEdit = new EditText(c);

		// PX를 DP단위로
		
		int pixel = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, dm);
		
		int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, dm);
		
		int content_pixel = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, dm);
		
		int spend_pixel = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, dm);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				pixel,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		
		LinearLayout.LayoutParams content_params = new LinearLayout.LayoutParams(
				content_pixel,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams spend_params = new LinearLayout.LayoutParams(
				spend_pixel,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		
		descriptEdit.setLayoutParams(content_params);
		dateEdit.setLayoutParams(params);
		dateEdit.setGravity(Gravity.CENTER);
		expenseEdit.setLayoutParams(spend_params);

		if (mode == MODE_ADD) {
			dateEdit.setHint("1");
			descriptEdit.setHint("ex) 내역");
			expenseEdit.setHint("ex) 200000");
		}
		else if ( mode == MODE_EDIT ) {
			descriptEdit.setText( map.get("descript") );
		}
		
		// 구성된 View들을 Layout에 배치시킨다.
		layoutDescript.setOrientation(LinearLayout.HORIZONTAL);
		layoutDescript.setPadding(padding, padding, padding, 0);
		layoutDescript.addView(descript);
		layoutDescript.addView(descriptEdit);
		
		layoutMonth.setOrientation(LinearLayout.HORIZONTAL);
		layoutMonth.setPadding(padding, 0, padding, 0);
		layoutMonth.addView(date);
		layoutMonth.addView(year);
		layoutMonth.addView(month);
		layoutMonth.addView(dateEdit);
		layoutMonth.addView(day);
		
		layoutExpense.setOrientation(LinearLayout.HORIZONTAL);
		layoutExpense.setPadding(padding, 0, padding, padding);
		layoutExpense.addView(expense);
		layoutExpense.addView(expenseEdit);
		layoutExpense.addView(won);

		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER);
		layout.addView(layoutDescript);
		layout.addView(layoutMonth);
		layout.addView(layoutExpense);
	}

	public LinearLayout getLayout() {
		return layout;
	}

	public String getMonth() {
		String month = ""+cal.get(Calendar.MONTH)+1;
		String date = dateEdit.getText().toString();
		int parseDate = Integer.parseInt(date);
		
		if( parseDate <= 9)
		{
			date = "0"+parseDate;
		}
		else if( parseDate >= 31)
		{
			date = ""+cal.getActualMaximum(Calendar.DATE);
		}
		
		if( cal.get(Calendar.MONTH)+1 <= 9 )
		{
			month = "0"+(cal.get(Calendar.MONTH)+1);
		}
		return cal.get(Calendar.YEAR)+"-"+ month +"-"+ date;
	}

	public String getDescript() {
		return descriptEdit.getText().toString();
	}

	public String getExpense() {
		String expense = expenseEdit.getText().toString();
		String rtString = "";
		int size = expense.length();
		int times = (size-1)/3;
		int start = 0, end = size%3;
		
		if( end == 0 )
		{
		//	rtString = expense.substring(0,3);
			end = 3;
		}
		
		// ex) 2000 -> 2,000
		for( int i=0; i<times; i++)
		{
			rtString += expense.substring(start, end)+",";
			start = end;
			end += 3;
		}
		
		rtString += expense.substring(start, end);
		
		return rtString+"원";
	}
}
