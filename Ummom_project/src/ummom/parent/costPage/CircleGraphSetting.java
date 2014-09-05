package ummom.parent.costPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.graphics.Color;

import com.handstudio.android.hzgrapherlib.animation.GraphAnimation;
import com.handstudio.android.hzgrapherlib.vo.GraphNameBox;
import com.handstudio.android.hzgrapherlib.vo.circlegraph.CircleGraph;
import com.handstudio.android.hzgrapherlib.vo.circlegraph.CircleGraphVO;

/**
 * @author Administrator
 *	두번째 탭의 원형 그래프를 정의한 클래스
 */
public class CircleGraphSetting {

	int Acost = 1, Rcost = 1, Ccost = 1, Ecost = 1;

	public CircleGraphVO makeCircleGraphAllSetting() {
		// BASIC LAYOUT SETTING
		// padding
		// int paddingBottom = CircleGraphVO.DEFAULT_PADDING;

		init();

		int pad = 0;
		int paddingBottom = pad;
		int paddingTop = pad;
		int paddingLeft = pad;
		int paddingRight = pad;

		// graph margin
		int marginTop = CircleGraphVO.DEFAULT_MARGIN_TOP;
		int marginRight = CircleGraphVO.DEFAULT_MARGIN_RIGHT;

		// 원그래프의 반지름 설정
		int radius = 130;

		List<CircleGraph> arrGraph = new ArrayList<CircleGraph>();

		// #3366CC -> blue, #1096118 -> green, #DC3912 -> OrangeRed
		arrGraph.add(new CircleGraph("학원비", Color.parseColor("#3366CC"), Acost));
		arrGraph.add(new CircleGraph("준비물", Color.parseColor("#DC3912"), Rcost));
		arrGraph.add(new CircleGraph("용돈", Color.parseColor("#109618"), Ccost));
		arrGraph.add(new CircleGraph("기타", Color.parseColor("#FF6633"), Ecost));

		CircleGraphVO vo = new CircleGraphVO(paddingBottom, paddingTop,
				paddingLeft, paddingRight, marginTop, marginRight, radius,
				arrGraph);

		// 원 그래프의 외곽선
		vo.setLineColor(Color.WHITE);

		// 원 그래프안에 들어갈 글자 색깔
		vo.setTextColor(Color.WHITE);
		vo.setTextSize(20);

		// 원의 중심 설정 X ,Y
		vo.setCenterX(0);
		vo.setCenterY(0);

		// 애니메이션 설정
		vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, 200));

		// 그래프의 범례 설정
		GraphNameBox graphNameBox = new GraphNameBox();

		// 범례 레이아웃
		graphNameBox.setNameboxMarginTop(25);
		graphNameBox.setNameboxMarginRight(25);

		vo.setGraphNameBox(graphNameBox);

		return vo;
	}

	private void init() {
		StringCalc calc = new StringCalc();
		CostInfo_Thread cthread = new CostInfo_Thread();

		cthread.execute();
		try {
			cthread.get(1000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 학원비 총합 계산
		String sum = "0원";
		ArrayList<HashMap<String, String>> value = new ArrayList<HashMap<String, String>>();

		value = cthread.getAList();

		for (int i = 0; i < value.size(); i++)
			sum = calc.getStringSum(sum, value.get(i).get("expense"));

		sum = sum.substring(0, sum.length() - 1);
		Acost = Integer.parseInt(sum.replace(",", ""));

		// 준비물 비용 총합 계산
		sum = "0원";
		value = cthread.getRList();

		for (int i = 0; i < value.size(); i++)
			sum = calc.getStringSum(sum, value.get(i).get("expense"));

		sum = sum.substring(0, sum.length() - 1);
		Rcost = Integer.parseInt(sum.replace(",", ""));

		// 용돈 총합 계산
		sum = "0원";
		value = cthread.getCList();

		for (int i = 0; i < value.size(); i++)
			sum = calc.getStringSum(sum, value.get(i).get("expense"));

		sum = sum.substring(0, sum.length() - 1);
		Ccost = Integer.parseInt(sum.replace(",", ""));

		// 기타 비용 총합 계산
		sum = "0원";
		value = cthread.getEList();

		for (int i = 0; i < value.size(); i++)
			sum = calc.getStringSum(sum, value.get(i).get("expense"));

		sum = sum.substring(0, sum.length() - 1);
		Ecost = Integer.parseInt(sum.replace(",", ""));
		
	}

}
