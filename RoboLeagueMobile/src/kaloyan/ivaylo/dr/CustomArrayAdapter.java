package kaloyan.ivaylo.dr;

import java.util.List;

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
		// TODO Auto-generated constructor stub
		list = names;
		context = cont;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
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
