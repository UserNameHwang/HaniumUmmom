package ummom.parent.costPage;

import java.util.ArrayList;
import java.util.List;

import com.handstudio.android.hzgrapherlib.vo.linegraph.LineGraph;
import com.handstudio.android.hzgrapherlib.vo.linegraph.LineGraphVO;

/**
 * @author Administrator
 *	두번째 탭의 선형 그래프를 정의한 클래스
 */
public class LineGraphSetting {

	public LineGraphVO makeLineGraphDefaultSetting() {

		String[] legendArr = { "1", "2", "3", "4", "5" };
		float[] graph1 = { 500, 100, 300, 200, 100 };
		float[] graph2 = { 000, 100, 200, 100, 200 };
		float[] graph3 = { 200, 500, 300, 400, 000 };

		List<LineGraph> arrGraph = new ArrayList<LineGraph>();
		arrGraph.add(new LineGraph("android", 0xaa66ff33, graph1));
		arrGraph.add(new LineGraph("ios", 0xaa00ffff, graph2));
		arrGraph.add(new LineGraph("tizen", 0xaaff0066, graph3));

		LineGraphVO vo = new LineGraphVO(legendArr, arrGraph);
		return vo;
	}

	/**
	 * make line graph using options
	 * 
	 * @return
	 */
	public LineGraphVO makeLineGraphAllSetting() {
		// 레이아웃 세팅
		// padding
	//	int paddingBottom = LineGraphVO.DEFAULT_PADDING;
		int pad = 70;
		int paddingBottom = pad;
		int paddingTop = pad;
		int paddingLeft = pad;
		int paddingRight = pad;

		// graph margin
	//	int marginTop = LineGraphVO.DEFAULT_MARGIN_TOP;
		int marginTop = 10;
		int marginRight = 10;

		// max value
		int maxValue = LineGraphVO.DEFAULT_MAX_VALUE;

		// Y축 증가치
		int increment = LineGraphVO.DEFAULT_INCREMENT;

		// 그래프에 담길 컨텐츠 정의
		String[] legendArr = { "1", "2", "3", "4", "5" };
		float[] graph1 = { 567, 123, 345, 234, 123 };

		List<LineGraph> arrGraph = new ArrayList<LineGraph>();

		arrGraph.add(new LineGraph("android", 0xaaff0066, graph1));

		LineGraphVO vo = new LineGraphVO(paddingBottom, paddingTop,
				paddingLeft, paddingRight, marginTop, marginRight, maxValue,
				increment, legendArr, arrGraph);

		// 애니메이션 설정
	//	vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION,	GraphAnimation.DEFAULT_DURATION));
		
		// 그래프 범례 표기 설정
	//	vo.setGraphNameBox(new GraphNameBox());
		
		// set draw graph region
		// vo.setDrawRegion(true);

		// use icon
		// arrGraph.add(new Graph(0xaa66ff33, graph1, R.drawable.icon1));
		// arrGraph.add(new Graph(0xaa00ffff, graph2, R.drawable.icon2));
		// arrGraph.add(new Graph(0xaaff0066, graph3, R.drawable.icon3));

		// LineGraphVO vo = new LineGraphVO(
		// paddingBottom, paddingTop, paddingLeft, paddingRight,
		// marginTop, marginRight, maxValue, increment, legendArr, arrGraph,
		// R.drawable.bg);
		return vo;
	}
	
	private class Monthly_Info extends Thread
	{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
}
