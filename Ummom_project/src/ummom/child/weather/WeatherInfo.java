package ummom.child.weather;

/**
 * @class WeatherInfo
 * @desc  날씨정보 저장용 class
 * @author Lemoness
 *
 */
public class WeatherInfo{
	
	private String Weather_string;		//날씨정보 - 문자열
	private String Weather_icon;		//날씨정보 - 아이콘 이름
	private String Weather_time;		//날씨정보 - 시간
	private int temp_max;				//날씨정보 - 최고온도
	private int temp_min;				//날씨정보 - 최저온도
	
	public WeatherInfo(){
	}
	
	public void setInfo(String string, String icon, String time, int max, int min){
		Weather_string = string;
		Weather_icon = icon;
		Weather_time = time;
		temp_max = max;
		temp_min = min;
	}
	
	public void setString(String string){
		Weather_string = string;
	}
	public void setIcon(String icon){
		Weather_icon = icon;
	}
	public void setTime(String time){
		Weather_time = time;
	}
	public void setTemp_max(int max){
		temp_max = max;
	}
	public void setTemp_min(int min){
		temp_min = min;
	}
	
	public String getString(){
		return Weather_string;
	}
	public String getIcon(){
		return Weather_icon;
	}
	public String getTime(){
		return Weather_time;
	}
	public int getTemp_max(){
		return temp_max;
	}
	public int getTemp_min(){
		return temp_min;
	}
	
}
