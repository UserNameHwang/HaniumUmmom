package ummom.teacher.thridTab;

public class StudentsItem {
	
	private String name;
	private String birth;
	private String phone;
	private int img;
	private int icon;
	
	public StudentsItem(int img, String name,String birth, String phone, int icon){
		this.name = name;
		this.birth = birth;
		this.phone = phone;
		this.img = img;
		this.icon = icon;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	
	

}
