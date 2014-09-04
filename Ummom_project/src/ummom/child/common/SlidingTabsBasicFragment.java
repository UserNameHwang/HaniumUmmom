package ummom.child.common;

import ummom.child.childpager;
import ummom.child.view.SlidingTabLayout;
import ummom.login.R;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SlidingTabsBasicFragment extends Fragment {

	private SlidingTabLayout mSlidingTabLayout;
	private ViewPager mViewPager;
	private childpager pager;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		return inflater.inflate(R.layout.fragment_child, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager_child);        

		mViewPager.setAdapter(new SamplePagerAdapter());

		mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs_child);
		mSlidingTabLayout.setViewPager(mViewPager);

		ActionBar actionbar = getActivity().getActionBar();
		actionbar.setTitle("UMchild");
		actionbar.setBackgroundDrawable(new ColorDrawable(new Color().parseColor("#6495ed")));
		pager = new childpager(getActivity());

	}

	// PageAdapter
	class SamplePagerAdapter extends PagerAdapter {

		// UMchild 페이지 갯수 설정
		@Override
		public int getCount() {
			return 5;
		}


		@Override
		public boolean isViewFromObject(View view, Object o) {
			return o == view;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}


		// TabTitle 설정부분
		// 아이콘으로 대체하므로 필요없다.
		@Override
		public CharSequence getPageTitle(int position) {
			
			return "";
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			switch(position){
			case 0 :
				return pager.page1(container);
			case 1 :
				return pager.page2(container);
			case 2 :
				return pager.page3(container);
			case 3 :
				return pager.page4(container);
			case 4 :
				return pager.page5(container);
			default :
				   
				return null;
			}
		}


		//	?占쎈㈃ ?占쎈뜲?占쏀듃 ?占쏀썑??Action Bar????占쏙옙??占�占쏙옙
		@Override
		public void finishUpdate(ViewGroup container) {
			// TODO Auto-generated method stub
			super.finishUpdate(container);

			ActionBar AB_title = getActivity().getActionBar();
			AB_title.setDisplayUseLogoEnabled(true);
			AB_title.setIcon(R.drawable.ic_launcher);

			switch(mSlidingTabLayout.getCursoredpage()){
			case 0 :
				AB_title.setSubtitle("일정 확인");
				break;
			case 1 :
				AB_title.setSubtitle("Youtube 보기");
				break;
			case 2 :
				AB_title.setSubtitle("주소록");
				break;
			case 3 :
				AB_title.setSubtitle("앨범 보기");
				break;
			case 4 :
				AB_title.setSubtitle("용돈기입장");
				break;
			default : 
				AB_title.setSubtitle("error");
				break;
			}
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
			//pager.removepage(position);
		}
	}
}
