package ummom.teacher.mainsliding;

import ummom.teacher.view.SlidingTabLayout;
import ummom.login.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SlidingTabsBasicFragment extends Fragment {

	private SlidingTabLayout mSlidingTabLayout;
	private View studentsList;
	private View first_tab;
	private int check = 0;
	private int check2 = 0;

	private ViewPager mViewPager;
	private TabsAdapter mTabsAdapter = new TabsAdapter();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_teacher, container, false);
	}

	@SuppressLint("NewApi")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		mViewPager = (ViewPager) view.findViewById(R.id.viewpager_teacher);
		
		Log.d("NULL vs", mViewPager +"?"+ mTabsAdapter +"!" );
		mViewPager.setAdapter(mTabsAdapter);
		mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs_teacher);
		mSlidingTabLayout.setViewPager(mViewPager);

		ActionBar bar = getActivity().getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6495ed")));
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayUseLogoEnabled(false);

	}

	// PageAdapter
	class TabsAdapter extends PagerAdapter {

		// 몇개의 페이지를 보여줄 것인지 정할 수 있음.
		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public boolean isViewFromObject(View view, Object o) {
			return o == view;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub

			return POSITION_UNCHANGED;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if (position == 0) {
				return "일정";
			} else if (position == 1) {
				return "사진첩";
			} else if (position == 2) {
				return "학생관리";
			} else if (position == 3) {
				return "생활정보";
			} else {
				return "Item " + (position + 1);
			}

		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view;
			if (position == 0) {
				if (check2 == 0) {
					first_tab = getActivity().getLayoutInflater().inflate(
							R.layout.teacher_firsttab, container, false);

					container.addView(first_tab);
					check2++;
					return first_tab;
				} else {
					container.addView(first_tab);
					return first_tab;
				}
			}

			else if (position == 2) {
				if (check == 0) {
					
					  studentsList =  getActivity().getLayoutInflater().inflate(R.layout.teacher_thirdtab,container, false);
					  container.addView(studentsList); 
					  check++; 
					  return studentsList;					 

				} else {
					container.addView(studentsList);
					return studentsList;
				}
			}

			else {

				view = getActivity().getLayoutInflater().inflate(
						R.layout.pager_item, container, false);
				TextView title = (TextView) view.findViewById(R.id.item_title);
				title.setText(String.valueOf(position + 1));
				container.addView(view);
				return view;

			}

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@SuppressLint("NewApi")
		@Override
		public void finishUpdate(ViewGroup container) {
			// TODO Auto-generated method stub
			super.finishUpdate(container);

			if (mSlidingTabLayout.getCurrentPage() == 0) {
				getActivity().getActionBar().setTitle("일정관리");
			} else if (mSlidingTabLayout.getCurrentPage() == 1) {
				getActivity().getActionBar().setTitle("사진관리");
			} else if (mSlidingTabLayout.getCurrentPage() == 2) {
				getActivity().getActionBar().setTitle("학생관리");
			} else if (mSlidingTabLayout.getCurrentPage() == 3) {
				getActivity().getActionBar().setTitle("생활정보");
			}

		}
	}

}
