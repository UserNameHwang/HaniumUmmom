package ummom.teacher.firstTab;


public class ScheduleItem {
	
	String title;
	String description;
	
	public ScheduleItem(String title, String des){
		this.title = title;
		this.description = des;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
