package ummom.child;

import ummom.child.youtube.YoutubePlayer;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import ummom.login.R;

/**
 * @class childpager
 * @desc 페이지별 정보 뿌려주는 클래스.  slidingtabbasic에 페이지 띄어주는곳에 갔다 쓰면 된다. 복잡해서 빼줌.
 *       현재 removepage 메서드 에러 있음.
 * @author Lemoness
 *
 */
public class childpager {

	private View mView_page1;
	private View mView_page2;
	private View mView_page3;
	private View mView_page4;
	private View mView_page5;
	int view1count = 0;

	private FragmentActivity mFA_Frag;

	public childpager(FragmentActivity frg){
		mFA_Frag = frg;
	}

	//페이지 1 표시 - 스케쥴, fragment사용
	public Object page1(ViewGroup container){
		if(mView_page1 == null) mView_page1 = mFA_Frag.getLayoutInflater().inflate(R.layout.layout_tab1, container, false);           

		TextView TV_nowTitle = (TextView) mView_page1.findViewById(R.id.tab1_nowtitle);
		TextView TV_nowContent = (TextView) mView_page1.findViewById(R.id.tab1_nowcontent);
		TextView TV_nextTitle = (TextView) mView_page1.findViewById(R.id.tab1_nexttitle);
		TextView TV_nextContent = (TextView) mView_page1.findViewById(R.id.tab1_nextcontent);
		
		TV_nowTitle.setText("지금할일");
		TV_nowContent.setText("코딩해 임마");
		TV_nextTitle.setText("다음에 할일");
		TV_nextContent.setText("또 코딩해 임마");

		container.addView(mView_page1);
		return mView_page1;
	}

	//페이지 2 표시 - 유튭
	public Object page2(ViewGroup container){
		if(mView_page2 == null) mView_page2 = mFA_Frag.getLayoutInflater().inflate(R.layout.layout_tab2, container, false);            
		container.addView(mView_page2);           

		new YoutubePlayer(mFA_Frag, 
				(EditText) mView_page2.findViewById(R.id.tab2_edt_searchquery), 
				(Button) mView_page2.findViewById(R.id.tab2_btn_search), 
				(ListView) mView_page2.findViewById(R.id.tab2_list));
		return mView_page2;
	}

	//페이지 3 표시 - 주소록
	public Object page3(ViewGroup container){
		if(mView_page3 == null) mView_page3 = mFA_Frag.getLayoutInflater().inflate(R.layout.layout_tab3,container, false);            
		container.addView(mView_page3);

		// Weather_forecast weather = new Weather_forecast(mFA_Frag);
		return mView_page3;
	}

	//페이지 4 표시 - 앨범, xml에서 fragment사용
	public Object page4(ViewGroup container){
		if(mView_page4 == null) mView_page4 = mFA_Frag.getLayoutInflater().inflate(R.layout.layout_tab4,container, false);            
		container.addView(mView_page4);           
/*
		new GalleryMaker(mFA_Frag,
				(Gallery) mView_page4.findViewById(R.id.tab4_gallery),
				(ImageView) mView_page4.findViewById(R.id.tab4_imgview),
				(TextView) mView_page4.findViewById(R.id.tab4_titletext),
				(TextView) mView_page4.findViewById(R.id.tab4_desctext));*/
		return mView_page4;
	}

	//페이지 5 표시 - 용돈기입장인데 안될거야 아마.
	public Object page5(ViewGroup container){
		if(mView_page5 == null) mView_page5 = mFA_Frag.getLayoutInflater().inflate(R.layout.layout_tab5,container, false);            
		container.addView(mView_page5);
		return mView_page5;
	}

	// 페이지 삭제시 호출 메서드. 에러있음
	public void removepage(int i){
		switch(i){
		case 1 :
			ViewGroup parent1 = (ViewGroup) mView_page1.getParent();
			if(parent1 != null){
				parent1.removeAllViews();
			}
			mView_page1.destroyDrawingCache();
			break;
		case 2 :
			ViewGroup parent2 = (ViewGroup) mView_page2.getParent();
			if(parent2 != null){
				parent2.removeAllViews();
			}
			mView_page2.destroyDrawingCache();
			break;
		case 3 :
			ViewGroup parent3 = (ViewGroup) mView_page3.getParent();
			if(parent3 != null){
				parent3.removeAllViews();
			}
			mView_page3.destroyDrawingCache();
			break;
		case 4 :
			ViewGroup parent4 = (ViewGroup) mView_page4.getParent();
			if(parent4 != null){
				parent4.removeAllViews();
			}
			mView_page4.destroyDrawingCache();
			break;
		case 5 :
			ViewGroup parent5 = (ViewGroup) mView_page5.getParent();
			if(parent5 != null){
				parent5.removeAllViews();
			}
			mView_page5.destroyDrawingCache();
			break;
		}
	}

}
