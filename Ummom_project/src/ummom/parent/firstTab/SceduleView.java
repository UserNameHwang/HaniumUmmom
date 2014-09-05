package ummom.parent.firstTab;

import ummom.login.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SceduleView extends LinearLayout{
	
	private TextView title;
	private TextView description;
	private ImageView edit;
	private ImageView delete;
	
	public SceduleView(Context context, SceduleItem item) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.homework_listitem, this ,true);
		
		title = (TextView) findViewById(R.id.title);
		title.setText(item.getTitle());
		
		description = (TextView) findViewById(R.id.des);
		description.setText(item.getDescription());
		
		edit = (ImageView) findViewById(R.id.edit_icon);
		delete = (ImageView) findViewById(R.id.delete_icon);
		
	}
	
	public void setText(int index, String data){
		if(index == 0){
			title.setText(data);
		}else if(index == 1){
			description.setText(data);
		}
	}

}