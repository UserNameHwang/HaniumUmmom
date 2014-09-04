package ummom.child.weather;

/**
 * @class WeatherInfo
 * @desc 날씨정보 저장 class
 * @author Lemoness
 *
 */
public class WeatherInfo{
	
	private String Weather_string;		//날씨 데이터
	private String Weather_icon;		//날씨 아이콘
	private String Weather_time;		//일자
	private int temp_max;				//최고온도
	private int temp_min;				//최저온도
	
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
