package ummom.teacher.firstTab;


import ummom.login.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

@SuppressLint({ "NewApi", "ValidFragment" })
public class DialogWrite extends Fragment {

	private ScheduleHomeworkFragment homeFrag;

	public DialogWrite(ScheduleHomeworkFragment scheduleHomeworkFragment) {
		// TODO Auto-generated constructor stub
		this.homeFrag = scheduleHomeworkFragment;
	}

	public void show(Activity act, int type) {
		AlertDialog.Builder mbuilder = new AlertDialog.Builder(act);
		Log.d("act", "" + act);
		View dialogView = act.getLayoutInflater().inflate(
				R.layout.dialog_homework, null);
		mbuilder.setView(dialogView);

		String val[] = CalendarFragment.touchDay.split("-");

		EditText year = (EditText) dialogView.findViewById(R.id.editText_year);
		EditText month = (EditText) dialogView
				.findViewById(R.id.editText_month);

		year.setText(val[0]);
		month.setText(val[1]);

		mbuilder.setCancelable(false)
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});

		mbuilder.setPositiveButton("확인", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				homeFrag.setOnView("Testing Success");
				
			}
		});

		mbuilder.show();
	}

}
