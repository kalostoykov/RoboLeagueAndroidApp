package kaloyan.ivaylo.dr;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myfirstapp.R;

public class DeviceScanner extends Activity {

	private BluetoothAdapter bluetooth;
	private List<BluetoothDevice> devices;
	
	private BluetoothDeviceAdapter deviceListAdapter;
	private ListView deviceList;
	
	private ArrayAdapter<String> btArrayAdapter;

	private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device.getName() != null) {
					devices.add(device);
					btArrayAdapter.add(device.getName() + "\n" + device.getAddress());
					Log.w("BLT", device.getName());
					btArrayAdapter.notifyDataSetChanged();
//					deviceListAdapter.notifyDataSetChanged();
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//				deviceListAdapter.notifyDataSetChanged();
				btArrayAdapter.notifyDataSetChanged();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devicemenu);
		
		devices = new ArrayList<BluetoothDevice>();
		bluetooth = BluetoothAdapter.getDefaultAdapter();
		
		deviceListAdapter = new BluetoothDeviceAdapter(devices, this);
		deviceList = (ListView) findViewById(R.id.devicelist);
		deviceList.setAdapter(deviceListAdapter);
		
		deviceList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				Log.w("BLT", "Selected item " + devices.get(position));
			}
		});
		
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		
		registerReceiver(broadcastReceiver, filter);
		discoverDevices();
	}

	public void discoverDevices() {
		if (bluetooth.isDiscovering()) {
			bluetooth.cancelDiscovery();
		}
		bluetooth.startDiscovery();
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}


}
