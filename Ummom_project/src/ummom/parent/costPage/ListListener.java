package ummom.parent.costPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import ummom.login.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Administrator 리스트 뷰의 변화를 감지하는 OnItemClickLitsener를 재정의한 클래스
 */
public class ListListener implements OnItemClickListener {

	private ListView mList;
	private Context c;
	private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	private DisplayMetrics dm;

	private int FAIL = 0;

	public ListListener(Context c, DisplayMetrics dm, ListView mList,
			ArrayList<HashMap<String, String>> list) {
		this.mList = mList;
		this.c = c;
		this.list = list;
		this.dm = dm;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		final View listItem = view;
		final int ItemPosition = position;
		int childCount = mList.getChildCount();
		
		AlertDialog.Builder alert = new AlertDialog.Builder(c);

		final int type;

		if (parent.getId() == R.id.Costlist1)
			type = 1;

		else if (parent.getId() == R.id.Costlist2)
			type = 2;

		else if (parent.getId() == R.id.Costlist3)
			type = 3;

		else if (parent.getId() == R.id.Costlist4)
			type = 4;

		else
			type = 0;

		// 추가하기
		if (position == childCount - 1) {

			alert.setTitle("추가하기");

			final DialogUI ui = new DialogUI(c, dm, 0, list.get(ItemPosition));

			LinearLayout layout = ui.getLayout();
			alert.setView(layout);

			alert.setPositiveButton("확인", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					String month = ui.getMonth();
					String descript = ui.getDescript();
					String expense = ui.getExpense();

					// 추가되는 부분 //
					HashMap<String, String> temp = new HashMap<String, String>();
					temp.put("descript", descript);
					temp.put("month", month);
					temp.put("expense", expense);

					SetCost_Thread thread = new SetCost_Thread(temp, type);

					thread.start();

					try {
						thread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (thread.getResult() == FAIL) {
						Toast.makeText(c, "등록에 실패하였습니다.", Toast.LENGTH_SHORT)
								.show();
						return;
					}

					temp.put("ID", thread.getCostID());

					HashMap<String, String> plus = list.get(list.size() - 1);
					list.set(list.size() - 1, temp);

					list.add(plus);

					// 추가되는 부분 //
					SimpleAdapter new_adapter = new SimpleAdapter(c, list,
							R.layout.list_row, new String[] { "descript",
									"month", "expense", "ID" }, new int[] {
									R.id.toptext, R.id.bottomtext,
									R.id.subtext, R.id.cost_ID });

					mList.setAdapter(new_adapter);

					setListViewHeightBasedOnChildren(mList);
				}
			});

			alert.setNeutralButton("취소", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});

			alert.show();

		}

		else {

			AlertDialog.Builder selector = new AlertDialog.Builder(c);

			String[] items = { "편집", "삭제" };

			selector.setItems(items, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					if (which == 1) {
						TextView id_text = (TextView)listItem.findViewById(R.id.cost_ID);
												
						DelCost_Thread thread = new DelCost_Thread(id_text.getText().toString());
						
						thread.start();
						
						try {
							thread.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if (thread.getResult() == FAIL) {
							Toast.makeText(c, "삭제에 실패하였습니다.", Toast.LENGTH_SHORT)
									.show();
							return;
						}
						
						list.remove(ItemPosition);

						SimpleAdapter new_adapter = new SimpleAdapter(c, list,
								R.layout.list_row, new String[] { "descript",
										"month", "expense", "ID" }, new int[] {
										R.id.toptext, R.id.bottomtext,
										R.id.subtext, R.id.cost_ID });

						mList.setAdapter(new_adapter);

						setListViewHeightBasedOnChildren(mList);
					}

					else {
						AlertDialog.Builder alert = new AlertDialog.Builder(c);

						// 편집하기
						alert.setTitle("편집하기");

						final DialogUI ui = new DialogUI(c, dm, 1, list
								.get(ItemPosition));

						LinearLayout layout = ui.getLayout();
						alert.setView(layout);

						alert.setPositiveButton("확인", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								String month = ui.getMonth();
								String descript = ui.getDescript();
								String expense = ui.getExpense();

								// 편집되는 부분 시작 //
								TextView id_text = (TextView)listItem.findViewById(R.id.cost_ID);
								
								HashMap<String, String> temp = new HashMap<String, String>();
								temp.put("descript", descript);
								temp.put("month", month);
								temp.put("expense", expense);
								temp.put("cost_id", id_text.getText().toString());
								
								UpdateCost_Thread thread =
										new UpdateCost_Thread(temp, type);

								thread.start();
								
								try {
									thread.join();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								if (thread.getResult() == FAIL) {
									Toast.makeText(c, "변경에 실패하였습니다.", Toast.LENGTH_SHORT)
											.show();
									return;
								}
								
								list.set(ItemPosition, temp);
								// 편집되는 부분 종료 //

								SimpleAdapter new_adapter = new SimpleAdapter(
										c, list, R.layout.list_row,
										new String[] { "descript", "month",
												"expense", "ID" }, new int[] {
												R.id.toptext, R.id.bottomtext,
												R.id.subtext, R.id.cost_ID });

								mList.setAdapter(new_adapter);

								setListViewHeightBasedOnChildren(mList);
							}
						});

						alert.setNeutralButton("취소", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});

						alert.show();
					}
				}
			});

			selector.show();

		}

	}

	/**
	 * @param listView
	 *            스크롤 뷰에 의한 리스트뷰의 크기가 변형되어 아래의 매서드를 통해 리스트뷰의 크기를 재지정한다.
	 */
	private static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = 0;
			listView.setLayoutParams(params);
			listView.requestLayout();

			return;
		}

		int totalHeight = 0;
		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.AT_MOST);
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	public class SetCost_Thread extends Thread {
		private HashMap<String, String> value;
		private int type;

		private int SUCCESS = 400;
		private int result;
		private String cost_id;

		public SetCost_Thread(HashMap<String, String> value, int type) {
			// TODO Auto-generated constructor stub
			this.value = value;
			this.type = type;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			HttpClient client = new DefaultHttpClient();
			String path = "http://14.63.212.236/index.php/cost/setCost";

			HttpPost post = new HttpPost(path);
			HttpConnectionParams
					.setConnectionTimeout(client.getParams(), 30000);

			String id = "pest";

			String expense = value.get("expense");
			String date = value.get("month");
			String des = value.get("descript");

			String str_type = "" + type;

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("reg_date", date));
			params.add(new BasicNameValuePair("expense", expense));
			params.add(new BasicNameValuePair("cost_des", des));
			params.add(new BasicNameValuePair("cost_type", str_type));

			try {
				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
						HTTP.UTF_8);
				post.setEntity(ent);

				HttpResponse httpResponse = client.execute(post);
				HttpEntity resEn = httpResponse.getEntity();

				String parse = EntityUtils.toString(resEn);
				Log.d("JSON", "" + parse);

				JSONObject object = new JSONObject(parse);

				if (Integer.parseInt(object.getString("result").toString()) == SUCCESS) {
					result = 1;
					cost_id = object.getString("cost_id");
				} else
					result = 0;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public int getResult() {
			return result;
		}

		public String getCostID() {
			return cost_id;
		}
	}

	public class DelCost_Thread extends Thread {
		
		private int SUCCESS = 400;

		private String cost_id;
		private int result;
		
		public DelCost_Thread(String cost_id) {
			// TODO Auto-generated constructor stub
			
			this.cost_id = cost_id;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub

			HttpClient client = new DefaultHttpClient();
			String path = "http://14.63.212.236/index.php/cost/deleteCost";

			HttpPost post = new HttpPost(path);
			HttpConnectionParams
					.setConnectionTimeout(client.getParams(), 30000);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("cost_id", cost_id));
			
			try {
				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
						HTTP.UTF_8);
				post.setEntity(ent);

				HttpResponse httpResponse = client.execute(post);
				HttpEntity resEn = httpResponse.getEntity();

				String parse = EntityUtils.toString(resEn);
				Log.d("JSON", "" + parse);

				JSONObject object = new JSONObject(parse);

				if (Integer.parseInt(object.getString("result").toString()) == SUCCESS) {
					result = 1;
				} else
					result = 0;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public int getResult()
		{
			return result;
		}

	}
	
	public class UpdateCost_Thread extends Thread
	{
		private HashMap<String, String> value;
		private int type;

		private int SUCCESS = 400;
		private int result;
		
		public UpdateCost_Thread(HashMap<String, String> value, int type) {
			// TODO Auto-generated constructor stub
			this.value = value;
			this.type = type;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			HttpClient client = new DefaultHttpClient();
			String path = "http://14.63.212.236/index.php/cost/updateCost";

			HttpPost post = new HttpPost(path);
			HttpConnectionParams
					.setConnectionTimeout(client.getParams(), 30000);

			String id = "pest";
			
			String expense = value.get("expense");
			String date = value.get("month");
			String des = value.get("descript");

			String cost_id = value.get("cost_id");
			String str_type = "" + type;

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("reg_date", date));
			params.add(new BasicNameValuePair("expense", expense));
			params.add(new BasicNameValuePair("cost_des", des));
			params.add(new BasicNameValuePair("cost_type", str_type));
			params.add(new BasicNameValuePair("cost_id", cost_id));

			try {
				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
						HTTP.UTF_8);
				post.setEntity(ent);

				HttpResponse httpResponse = client.execute(post);
				HttpEntity resEn = httpResponse.getEntity();

				String parse = EntityUtils.toString(resEn);
				Log.d("JSON", "" + parse);

				JSONObject object = new JSONObject(parse);

				if (Integer.parseInt(object.getString("result").toString()) == SUCCESS) {
					result = 1;
				} else
					result = 0;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public int getResult() {
			return result;
		}
	}

}
