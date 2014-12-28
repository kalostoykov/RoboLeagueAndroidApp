package kaloyan.ivaylo.dr;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfirstapp.R;

public class BluetoothDeviceAdapter extends BaseAdapter {

	List<BluetoothDevice> devices;
	Context context;
	
	public BluetoothDeviceAdapter(List<BluetoothDevice> list, Context context) {
		devices = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		devices.size();
		return 0;
	}

	@Override
	public Object getItem(int item) {
		return devices.get(item);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.textviewmain, parent, false);	
		}
		TextView name = (TextView) row.findViewById(R.id.row);
		
		name.setText(devices.get(position).getName());

		return row;
	}

}
