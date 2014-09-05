package ummom.parent.costPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import ummom.login.R;

import android.animation.LayoutTransition;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class CostDetail extends Activity {

	private SimpleAdapter aca_adapter1;
	private SimpleAdapter aca_adapter2;
	private SimpleAdapter aca_adapter3;
	private SimpleAdapter aca_adapter4;

	private ArrayList<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> list2 = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> list3 = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> list4 = new ArrayList<HashMap<String, String>>();

	private ListView mList1;
	private ListView mList2;
	private ListView mList3;
	private ListView mList4;

	private CostAcademy AList;
	private CostArrange RList;
	private CostCash CList;
	private CostEtc EList;

	int isOpened1;
	int isOpened2;
	int isOpened3;
	int isOpened4;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_detail);

		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6495ed")));

		// pixel 단위를 DP 단위로 바꾸기 위한 DisplayMetrics
		DisplayMetrics dm;
		
		StringCalc calc = new StringCalc();
		
		// 서버로 부터 지출 정보를 가져오기 위한 쓰레드
		CostInfo_Thread cost_thread = new CostInfo_Thread();
		
		cost_thread.execute();

		try {
			cost_thread.get(1000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LayoutTransition lt = new LayoutTransition();
		lt.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
		lt.disableTransitionType(LayoutTransition.DISAPPEARING);

		LinearLayout costLayout = (LinearLayout) findViewById(R.id.CostLayout);
		ViewFlipper flipper1 = (ViewFlipper) findViewById(R.id.CostFlipper1);
		ViewFlipper flipper2 = (ViewFlipper) findViewById(R.id.CostFlipper2);
		ViewFlipper flipper3 = (ViewFlipper) findViewById(R.id.CostFlipper3);
		ViewFlipper flipper4 = (ViewFlipper) findViewById(R.id.CostFlipper4);

		costLayout.setLayoutTransition(lt);

		flipper1.setLayoutTransition(lt);
		flipper2.setLayoutTransition(lt);
		flipper3.setLayoutTransition(lt);
		flipper4.setLayoutTransition(lt);

		dm = getResources().getDisplayMetrics();

		AList = new CostAcademy();
		RList = new CostArrange();
		CList = new CostCash();
		EList = new CostEtc();

		// AList 초기화
		String Asum;
		Context c = this;
		TextView Atext1 = (TextView) findViewById(R.id.Atext1);
		mList1 = (ListView) findViewById(R.id.Costlist1);

		list1 = cost_thread.getAList();
		
		HashMap<String, String> addList = new HashMap<String, String>();
		addList.put("descript", "추가하기");
		addList.put("month", "차차");
		addList.put("expense", "구현중");
		addList.put("ID", "");
		list1.add(addList);
		
		Log.d("test", list1.toString());
		
		aca_adapter1 = new SimpleAdapter(c, list1, R.layout.list_row,
				new String[] { "descript", "month", "expense", "ID" }, new int[] {
						R.id.toptext, R.id.bottomtext, R.id.subtext, R.id.cost_ID });
		
		AList.setList(list1);

		ListListener listener1 = new ListListener(c, dm, mList1,
				AList.getList());

		mList1.setOnItemClickListener(listener1);

		Asum = "0원";
		for (int i = 0; i < AList.getList().size() - 1; i++)
			Asum = calc.getStringSum(Asum, AList.getList().get(i).get("expense"));

		Atext1.setText(Asum);

		// RList 초기화
		String Rsum;
		TextView Atext2 = (TextView) findViewById(R.id.Atext2);
		mList2 = (ListView) findViewById(R.id.Costlist2);

		list2 = cost_thread.getRList();
		
		list2.add(addList);

		aca_adapter2 = new SimpleAdapter(c, list2, R.layout.list_row,
				new String[] { "descript", "month", "expense", "ID" }, new int[] {
						R.id.toptext, R.id.bottomtext, R.id.subtext, R.id.cost_ID });
		
		RList.setList(list2);

		ListListener listener2 = new ListListener(c, dm, mList2,
				RList.getList());

		mList2.setOnItemClickListener(listener2);

		Rsum = "0원";
		for (int i = 0; i < RList.getList().size() - 1; i++)
			Rsum = calc.getStringSum(Rsum, RList.getList().get(i).get("expense"));

		Atext2.setText(Rsum);

		// CList 초기화
		String Csum;
		TextView Atext3 = (TextView) findViewById(R.id.Atext3);
		mList3 = (ListView) findViewById(R.id.Costlist3);

		list3 = cost_thread.getCList();
		
		list3.add(addList);

		CList.setList(list3);
		
		aca_adapter3 = new SimpleAdapter(c, list3, R.layout.list_row,
				new String[] { "descript", "month", "expense", "ID" }, new int[] {
						R.id.toptext, R.id.bottomtext, R.id.subtext, R.id.cost_ID });

		ListListener listener3 = new ListListener(c, dm, mList3,
				CList.getList());

		mList3.setOnItemClickListener(listener3);

		Csum = "0원";
		for (int i = 0; i < CList.getList().size() - 1; i++)
			Csum = calc.getStringSum(Csum, CList.getList().get(i).get("expense"));

		Atext3.setText(Csum);

		// EList 초기화
		String Esum;

		TextView Atext4 = (TextView) findViewById(R.id.Atext4);
		mList4 = (ListView) findViewById(R.id.Costlist4);

		list4 = cost_thread.getEList();

		addList = new HashMap<String, String>();
		addList.put("descript", "추가하기");
		addList.put("month", "차차");
		addList.put("expense", "구현중");
		list4.add(addList);

		aca_adapter4 = new SimpleAdapter(c, list4, R.layout.list_row,
				new String[] { "descript", "month", "expense", "ID" }, new int[] {
						R.id.toptext, R.id.bottomtext, R.id.subtext, R.id.cost_ID });

		EList.setList(list4);

		ListListener listener4 = new ListListener(c, dm, mList4,
				EList.getList());

		mList4.setOnItemClickListener(listener4);

		Esum = "0원";
		for (int i = 0; i < EList.getList().size() - 1; i++)
			Esum = calc.getStringSum(Esum, EList.getList().get(i).get("expense"));

		Atext4.setText(Esum);
		
		mList4.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
			
			@Override
			public void onChildViewRemoved(View parent, View child) {
				// TODO Auto-generated method stub
				Log.d("removed", "1");
			}
			
			@Override
			public void onChildViewAdded(View parent, View child) {
				// TODO Auto-generated method stub
				Log.d("added", "0");
			}
		});
		
		// ListItem 초기화 끝

		// 지출 내역 총합
		TextView CostSum = (TextView) findViewById(R.id.costSum);
		String AllCostSum = "0원";

		AllCostSum = calc.getStringSum(calc.getStringSum(Asum, Rsum),
				calc.getStringSum(Csum, Esum));

		CostSum.setText(AllCostSum);
	}

	// 코드라인 줄이기 ////////////////////////////////////
	// 학원비 리스트뷰
	public void onACost(View v) {

		LinearLayout ll;
		ViewFlipper flipper;
		TextView Atext1 = (TextView) findViewById(R.id.Atext1);

		final ImageButton imgbtn1;
		final ImageButton imgbtn2;

		String sum;

		Animation anim_rotate = AnimationUtils.loadAnimation(v.getContext(),
				R.anim.imgbtn_rotate);
		
		StringCalc calc = new StringCalc();

		flipper = (ViewFlipper) findViewById(R.id.CostFlipper1);
		ll = (LinearLayout) findViewById(R.id.Cost1);

		imgbtn1 = (ImageButton) findViewById(R.id.CostimgBtn1);
		imgbtn2 = (ImageButton) findViewById(R.id.CostimgBtn2);

		LayoutTransition lt = new LayoutTransition();
		lt.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
		lt.disableTransitionType(LayoutTransition.DISAPPEARING);

		ll.setLayoutTransition(lt);

		String pre_sum = Atext1.getText().toString();

		// Expand 확장 시킬 때
		if (isOpened1 == 0) {
			anim_rotate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					imgbtn1.setVisibility(View.INVISIBLE);
					imgbtn2.setVisibility(View.VISIBLE);
				}
			});
			imgbtn1.startAnimation(anim_rotate);

			RelativeLayout rl = (RelativeLayout) findViewById(R.id.CostTab1);

			rl.setBackgroundColor(Color.parseColor("#91a7ff"));

			flipper.showNext();
			mList1.setAdapter(aca_adapter1);

			setListViewHeightBasedOnChildren(mList1);
			isOpened1 = 1;
		}

		// Collapse 닫을 때
		else {
			anim_rotate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					imgbtn2.setVisibility(View.INVISIBLE);
					imgbtn1.setVisibility(View.VISIBLE);
				}
			});

			imgbtn2.startAnimation(anim_rotate);

			RelativeLayout rl = (RelativeLayout) findViewById(R.id.CostTab1);

			rl.setBackgroundColor(Color.parseColor("#afbfff"));

			sum = "0원";
			for (int i = 0; i < AList.getList().size() - 1; i++)
				sum = calc.getStringSum(sum, AList.getList().get(i).get("expense"));

			Atext1.setText(sum);

			flipper.showPrevious();
			mList1.setAdapter(null);

			setListViewHeightBasedOnChildren(mList1);
			isOpened1 = 0;
		}
		
		// 리스트가 열리고 닫힐 때 지출 총합을 계산
		TextView CostSum = (TextView) findViewById(R.id.costSum);
		String Allsum = CostSum.getText().toString();
		sum = Atext1.getText().toString();

		CostSum.setText(calc.getStringSum(sum, calc.getStringSub(Allsum, pre_sum)));
	}

	// 준비물 리스트뷰
	public void onRCost(View v) {

		LinearLayout ll;
		ViewFlipper flipper;
		TextView Atext2 = (TextView) findViewById(R.id.Atext2);

		final ImageButton imgbtn1;
		final ImageButton imgbtn2;

		String sum;

		Animation anim_rotate = AnimationUtils.loadAnimation(v.getContext(),
				R.anim.imgbtn_rotate);
		
		StringCalc calc = new StringCalc();

		flipper = (ViewFlipper) findViewById(R.id.CostFlipper2);
		ll = (LinearLayout) findViewById(R.id.Cost2);

		imgbtn1 = (ImageButton) findViewById(R.id.CostimgBtn3);
		imgbtn2 = (ImageButton) findViewById(R.id.CostimgBtn4);

		LayoutTransition lt = new LayoutTransition();
		lt.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
		lt.disableTransitionType(LayoutTransition.DISAPPEARING);

		ll.setLayoutTransition(lt);

		String pre_sum = Atext2.getText().toString();

		// Expand 확장 시킬 때
		if (isOpened2 == 0) {
			anim_rotate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					imgbtn1.setVisibility(View.INVISIBLE);
					imgbtn2.setVisibility(View.VISIBLE);
				}
			});
			imgbtn1.startAnimation(anim_rotate);

			RelativeLayout rl = (RelativeLayout) findViewById(R.id.CostTab2);

			rl.setBackgroundColor(Color.parseColor("#91a7ff"));
			
			flipper.showNext();
			mList2.setAdapter(aca_adapter2);

			setListViewHeightBasedOnChildren(mList2);
			isOpened2 = 1;
		}

		// Collapse 닫을 때
		else {
			anim_rotate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					imgbtn2.setVisibility(View.INVISIBLE);
					imgbtn1.setVisibility(View.VISIBLE);
				}
			});

			imgbtn2.startAnimation(anim_rotate);

			RelativeLayout rl = (RelativeLayout) findViewById(R.id.CostTab2);

			rl.setBackgroundColor(Color.parseColor("#afbfff"));

			sum = "0원";
			for (int i = 0; i < RList.getList().size() - 1; i++)
				sum = calc.getStringSum(sum, RList.getList().get(i).get("expense"));

			Atext2.setText(sum);

			flipper.showPrevious();
			mList2.setAdapter(null);

			setListViewHeightBasedOnChildren(mList2);
			isOpened2 = 0;
		}
		
		// 리스트가 열리고 닫힐 때 지출 총합을 계산
		TextView CostSum = (TextView) findViewById(R.id.costSum);
		String Allsum = CostSum.getText().toString();
		sum = Atext2.getText().toString();

		CostSum.setText(calc.getStringSum(sum, calc.getStringSub(Allsum, pre_sum)));
	}

	// 용돈 리스트뷰
	public void onCCost(View v) {

		LinearLayout ll;
		ViewFlipper flipper;
		TextView Atext3 = (TextView) findViewById(R.id.Atext3);

		final ImageButton imgbtn1;
		final ImageButton imgbtn2;

		String sum;

		Animation anim_rotate = AnimationUtils.loadAnimation(v.getContext(),
				R.anim.imgbtn_rotate);
		
		StringCalc calc = new StringCalc();

		flipper = (ViewFlipper) findViewById(R.id.CostFlipper3);
		ll = (LinearLayout) findViewById(R.id.Cost3);

		imgbtn1 = (ImageButton) findViewById(R.id.CostimgBtn5);
		imgbtn2 = (ImageButton) findViewById(R.id.CostimgBtn6);

		LayoutTransition lt = new LayoutTransition();
		lt.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
		lt.disableTransitionType(LayoutTransition.DISAPPEARING);

		ll.setLayoutTransition(lt);

		String pre_sum = Atext3.getText().toString();

		// Expand 확장 시킬 때
		if (isOpened3 == 0) {
			anim_rotate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					imgbtn1.setVisibility(View.INVISIBLE);
					imgbtn2.setVisibility(View.VISIBLE);
				}
			});
			imgbtn1.startAnimation(anim_rotate);
			
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.CostTab3);

			rl.setBackgroundColor(Color.parseColor("#91a7ff"));

			flipper.showNext();
			mList3.setAdapter(aca_adapter3);

			setListViewHeightBasedOnChildren(mList3);
			isOpened3 = 1;
		}

		// Collapse 닫을 때
		else {
			anim_rotate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					imgbtn2.setVisibility(View.INVISIBLE);
					imgbtn1.setVisibility(View.VISIBLE);
				}
			});

			imgbtn2.startAnimation(anim_rotate);
			
			RelativeLayout rl = (RelativeLayout)findViewById(R.id.CostTab3);
			
			rl.setBackgroundColor(Color.parseColor("#afbfff"));

			sum = "0원";
			for (int i = 0; i < CList.getList().size() - 1; i++)
				sum = calc.getStringSum(sum, CList.getList().get(i).get("expense"));

			Atext3.setText(sum);

			flipper.showPrevious();
			mList3.setAdapter(null);

			setListViewHeightBasedOnChildren(mList3);
			isOpened3 = 0;
		}
		
		// 리스트가 열리고 닫힐 때 지출 총합을 계산
		TextView CostSum = (TextView) findViewById(R.id.costSum);
		String Allsum = CostSum.getText().toString();
		sum = Atext3.getText().toString();

		CostSum.setText(calc.getStringSum(sum, calc.getStringSub(Allsum, pre_sum)));

	}

	// 기타 리스트뷰
	public void onECost(View v) {

		LinearLayout ll;
		ViewFlipper flipper;
		TextView Atext4 = (TextView) findViewById(R.id.Atext4);

		final ImageButton imgbtn1;
		final ImageButton imgbtn2;

		String sum;

		Animation anim_rotate = AnimationUtils.loadAnimation(v.getContext(),
				R.anim.imgbtn_rotate);
		
		StringCalc calc = new StringCalc();

		flipper = (ViewFlipper) findViewById(R.id.CostFlipper4);
		ll = (LinearLayout) findViewById(R.id.Cost4);

		imgbtn1 = (ImageButton) findViewById(R.id.CostimgBtn7);
		imgbtn2 = (ImageButton) findViewById(R.id.CostimgBtn8);

		LayoutTransition lt = new LayoutTransition();
		lt.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
		lt.disableTransitionType(LayoutTransition.DISAPPEARING);

		ll.setLayoutTransition(lt);

		String pre_sum = Atext4.getText().toString();

		// Expand 확장 시킬 때
		if (isOpened4 == 0) {
			anim_rotate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					imgbtn1.setVisibility(View.INVISIBLE);
					imgbtn2.setVisibility(View.VISIBLE);
				}
			});
			imgbtn1.startAnimation(anim_rotate);
			
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.CostTab4);

			rl.setBackgroundColor(Color.parseColor("#91a7ff"));

			flipper.showNext();
			mList4.setAdapter(aca_adapter4);

			setListViewHeightBasedOnChildren(mList4);
			isOpened4 = 1;
		}

		// Collapse 닫을 때
		else {
			anim_rotate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					imgbtn2.setVisibility(View.INVISIBLE);
					imgbtn1.setVisibility(View.VISIBLE);
				}
			});

			imgbtn2.startAnimation(anim_rotate);
			
			RelativeLayout rl = (RelativeLayout)findViewById(R.id.CostTab4);
			
			rl.setBackgroundColor(Color.parseColor("#afbfff"));
			
			sum = "0원";
			for (int i = 0; i < EList.getList().size() - 1; i++)
				sum = calc.getStringSum(sum, EList.getList().get(i).get("expense"));

			Atext4.setText(sum);

			flipper.showPrevious();
			mList4.setAdapter(null);

			setListViewHeightBasedOnChildren(mList4);
			isOpened4 = 0;
		}

		// 리스트가 열리고 닫힐 때 지출 총합을 계산
		TextView CostSum = (TextView) findViewById(R.id.costSum);
		String Allsum = CostSum.getText().toString();
		sum = Atext4.getText().toString();

		CostSum.setText(calc.getStringSum(sum, calc.getStringSub(Allsum, pre_sum)));

	}	

	/**
	 * @param listView
	 * 	스크롤뷰에 의하여 리스트뷰의 크기가 고정되지 않기 때문에
	 * 	아래의 메서드를 이용하여 리스트뷰를 리사이징한다.
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

	// 화면 전환 애니메이션 설정
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		overridePendingTransition(R.anim.page_donmove, R.anim.page_disappear);

		finish();
	}
	
}
