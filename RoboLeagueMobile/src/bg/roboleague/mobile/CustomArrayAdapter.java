package bg.roboleague.mobile;

import java.util.List;

import bg.roboleague.mobile.robots.Robot;

import com.example.myfirstapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomArrayAdapter extends BaseAdapter {
	
	List<Robot> list;
	Context context;
	LayoutInflater myInflater;
	
	public CustomArrayAdapter(List<Robot> names, Context cont) {
		list = names;
		context = cont;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.textviewmain, parent, false);	
		}
		TextView name = (TextView) row.findViewById(R.id.row);
		
		name.setText(list.get(position).getName());

		return row;
	}

}
