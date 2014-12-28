package kaloyan.ivaylo.dr;

import java.util.ArrayList;
import java.util.List;

import com.example.myfirstapp.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Main extends Activity {
	private static int REQUEST_ENABLE_BT = 42;
	Context context = this;
	BluetoothAdapter bAdapter;
	List<BluetoothDevice> devices;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devicemenu);
		initBluetooth();
	}
	
	private void initBluetooth() {
		// TODO Auto-generated method stub
		devices = new ArrayList<BluetoothDevice>();
		bAdapter = BluetoothAdapter.getDefaultAdapter();
		if(bAdapter == null){
			Toast.makeText(context, "No bluetooth adapter", Toast.LENGTH_LONG).show();
			return;
		}
		if(!bAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
	}
}
