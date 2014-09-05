package ummom.parent.managePage;

import java.util.HashMap;

import android.graphics.Bitmap;

public class ListData {

	private HashMap<String, String> data;
	private Bitmap bitmap;
	
	public ListData(HashMap<String, String> data, Bitmap bitmap)
	{
		super();
		this.data = data;
		this.bitmap = bitmap;
	}
	
	public void setData(HashMap<String, String> data)
	{
		this.data = data;
	}
	
	public HashMap<String, String> getData()
	{
		return this.data;
	}
	
	public void setBitmap(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}
	
	public Bitmap getBitmap()
	{
		return this.bitmap;
	}
}
