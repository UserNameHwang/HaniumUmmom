package ummom.parent.common;

import java.util.Iterator;

import ummom.login.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String tag = "GCMIntentService";
	private static final String PROJECT_ID = "subtle-hydra-629";

	private static String new_assign = "새로운 숙제가 등록되었습니다.";

	// 구글 api 페이지 주소 [https://code.google.com/apis/console/#project:긴 번호]
	// #project: 이후의 숫자가 위의 PROJECT_ID 값에 해당한다

	// public 기본 생성자를 무조건 만들어야 한다.

	public GCMIntentService() {
		this(PROJECT_ID);
	}

	public GCMIntentService(String project_id) {
		super(project_id);
	}

	private static void generateNotification(Context context, String message) {

		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();

		
		if( message.equals("assignment notify"))
			message = new_assign;
		
		
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification(icon, message, when);

		String title = context.getString(R.string.app_name);

		Intent notificationIntent = new Intent(context, MainParent.class);

		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, title, message, intent);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		
		Vibrator viber = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		
		long []term = {300,300};
		viber.vibrate(term, 1);
		
		notificationManager.notify(0, notification);
		
	}

	/** 푸시로 받은 메시지 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		Bundle b = intent.getExtras();

		Iterator<String> iterator = b.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = b.get(key).toString();
			Log.e(tag, "onMessage. " + key + " : " + value);
		}

		generateNotification(context, intent.getStringExtra("msg"));
	}

	/** 에러 발생시 */
	@Override
	protected void onError(Context context, String errorId) {
		Log.e(tag, "onError. errorId : " + errorId);
	}

	/** 단말에서 GCM 서비스 등록 했을 때 등록 id를 받는다 */
	@Override
	protected void onRegistered(Context context, String regId) {
		Log.e(tag, "onRegistered. regId : " + regId);

		// SharedPreference 에 RegId 등록
		SharedPreferences GCM_regId = getSharedPreferences("GCM_regId",
				MODE_PRIVATE);
		SharedPreferences.Editor edit_GCM_regId = GCM_regId.edit();

		edit_GCM_regId.putString("GCM_regId", regId);
		edit_GCM_regId.commit();
	}

	/** 단말에서 GCM 서비스 등록 해지를 하면 해지된 등록 id를 받는다 */
	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.e(tag, "onUnregistered. regId : " + regId);
	}

}
