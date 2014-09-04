package ummom.child.schedule;

public class ScheduleDataset {
	
	private String mS_time;
	private String mS_title;
	private String mS_desc;
	
	public ScheduleDataset(String time, String title, String desc){
		mS_time = time;
		mS_title = title;
		mS_desc = desc;
	}
	
	public ScheduleDataset(){
		mS_time = "";
		mS_title = "";
		mS_desc = "";
	}
	
	public String getTime(){
		return mS_time;
	}
	public String getTitle(){
		return mS_title;
	}
	public String getDesc(){
		return mS_desc;
	}
	
	public void setTime(String time){
		mS_time = time;
	}
	public void setTitle(String title){
		mS_title = title;
	}
	public void setDesc(String desc){
		mS_desc = desc;
	}
}
