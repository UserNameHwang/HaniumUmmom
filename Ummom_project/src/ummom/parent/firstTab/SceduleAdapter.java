package ummom.parent.firstTab;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SceduleAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<SceduleItem> mlist = new ArrayList<SceduleItem>();
	
	public SceduleAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}
	
	public void addItem(SceduleItem item){
		mlist.add(item);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		SceduleView view = null;
		if(convertView == null){
			view = new SceduleView(mContext,mlist.get(position));
		}else{
			view = (SceduleView) convertView;
			view.setText(0, mlist.get(position).getTitle() );
			view.setText(1, mlist.get(position).getDescription() );
		}		
		return view;
	}
	
	

}
