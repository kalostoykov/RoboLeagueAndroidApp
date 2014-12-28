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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myfirstapp.R;

public class DeviceScanner extends Activity {

	private BluetoothAdapter bluetooth;
	private List<String> deviceNames;
	
	private ArrayAdapter<String> deviceListAdapter;
	private ListView deviceList;

	private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device.getName() != null) {
					deviceNames.add(device.getName());
					deviceListAdapter.notifyDataSetChanged();
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				deviceListAdapter.notifyDataSetChanged();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devicemenu);
		
		deviceNames = new ArrayList<String>();
		bluetooth = BluetoothAdapter.getDefaultAdapter();
		
		deviceListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deviceNames);
		deviceList = (ListView) findViewById(R.id.devicelist);
		deviceList.setAdapter(deviceListAdapter);
		
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
