package ummom.teacher.firstTab;

import ummom.login.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScheduleView extends LinearLayout{
	
	private TextView title;
	private TextView description;
	private ImageView edit;
	private ImageView delete;
	
	public ScheduleView(Context context, ScheduleItem item) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.homework_listitem, this ,true);
		
		title = (TextView) findViewById(R.id.title);
		title.setText(item.getTitle());
		
		description = (TextView) findViewById(R.id.des);
		description.setText(item.getDescription());
		
		edit = (ImageView) findViewById(R.id.img_icon_edit);
		delete = (ImageView) findViewById(R.id.img_icon_delete);
		
	}
	
	public void setText(int index, String data){
		if(index == 0){
			title.setText(data);
		}else if(index == 1){
			description.setText(data);
		}
	}

}
