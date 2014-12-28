package kaloyan.ivaylo.dr;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BluetoothConnection {
	private BluetoothAdapter adapter;
	private BluetoothDevice device;
	private BluetoothSocket socket;
	
	public BluetoothConnection(String deviceAddress) {
		adapter = BluetoothAdapter.getDefaultAdapter();
	}
}
