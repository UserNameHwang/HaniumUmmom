package ummom.parent.managePage;

import java.util.ArrayList;

import ummom.login.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<ListData> {

	private Context mContext;
	private int mResource;

	private LayoutInflater mInflater;

	public CustomAdapter(Context context, int resource,
			ArrayList<ListData> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub

		this.mContext = context;
		this.mResource = resource;
		this.mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		

		if (convertView == null) {
			convertView = mInflater.inflate(mResource, null);
		}

		ListData data = getItem(position);
		
		if( data != null )
		{
			ImageView profile = (ImageView)convertView.findViewById(R.id.list_profile_image);
			TextView name, grade, id, school;
			
			name = (TextView)convertView.findViewById(R.id.list_name_text);
			grade = (TextView)convertView.findViewById(R.id.list_grade_text);
			id = (TextView)convertView.findViewById(R.id.list_id_text);
			school = (TextView)convertView.findViewById(R.id.list_school_text);
			
			id.setText(data.getData().get("ID"));
			grade.setText(data.getData().get("grade"));
			name.setText(data.getData().get("name"));
			school.setText(data.getData().get("school"));
			
			profile.setImageBitmap(data.getBitmap());
		}
		
		return convertView;
	}
}
