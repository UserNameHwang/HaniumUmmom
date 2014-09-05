package ummom.parent.costPage;

/**
 * @author Administrator
 *	'...원' 과 같은 방식의 문자열을
 *	더하고 빼기위해 정의한 클래스
 */
public class StringCalc {

	public String getStringSum(String str1, String str2) {

		// 각각의 문자열에서 "원"빼기
		String value1 = str1.substring(0, str1.length() - 1);
		String value2 = str2.substring(0, str2.length() - 1);

		int intvalue1 = Integer.parseInt(value1.replace(",", ""));
		int intvalue2 = Integer.parseInt(value2.replace(",", ""));

		int sum = intvalue1 + intvalue2;

		String stringSum = "" + sum;

		String rtString = "";
		int size = stringSum.length();
		int times = (size - 1) / 3;
		int start = 0, end = size % 3;

		if (end == 0)
			end = 3;
		// ex) 2000 -> 2,000
		for (int i = 0; i < times; i++) {
			rtString += stringSum.substring(start, end) + ",";
			start = end;
			end += 3;
		}

		rtString += stringSum.substring(start, end);

		return rtString + "원";
	}

	public String getStringSub(String str1, String str2) {

		// 각각의 문자열에서 "원"빼기
		String value1 = str1.substring(0, str1.length() - 1);
		String value2 = str2.substring(0, str2.length() - 1);

		int intvalue1 = Integer.parseInt(value1.replace(",", ""));
		int intvalue2 = Integer.parseInt(value2.replace(",", ""));

		int sub = intvalue1 - intvalue2;

		String stringSum = "" + sub;

		String rtString = "";
		int size = stringSum.length();
		int times = (size - 1) / 3;
		int start = 0, end = size % 3;

		if (end == 0)
			end = 3;
		// ex) 2000 -> 2,000
		for (int i = 0; i < times; i++) {
			rtString += stringSum.substring(start, end) + ",";
			start = end;
			end += 3;
		}

		rtString += stringSum.substring(start, end);

		return rtString + "원";
	}
}
