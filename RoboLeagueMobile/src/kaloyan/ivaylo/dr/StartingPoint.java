package kaloyan.ivaylo.dr;

import java.util.ArrayList;
import java.util.List;

import com.example.myfirstapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StartingPoint extends Activity {
	private static int REQUEST_ENABLE_BT = 42;
	private ListView list;
	private CustomArrayAdapter customAdapter;
	Context context = this;
	BluetoothAdapter bAdapter;
	List<BluetoothDevice> devices;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initBluetooth();
		fillListView();
		addNewRobot();
		getMassageForListItemClick();
	}

	private void initBluetooth() {

		devices = new ArrayList<BluetoothDevice>();
		bAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bAdapter == null) {
			Toast.makeText(context, "fail", Toast.LENGTH_LONG).show();
			return;
		}
		if (!bAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		else {
			Intent scanBluetooth = new Intent(getApplicationContext(),
					DeviceScanner.class);
			startActivity(scanBluetooth);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ENABLE_BT) {
			if (bAdapter.isEnabled()) {
				Intent scanBluetooth = new Intent(getApplicationContext(),
						DeviceScanner.class);
				startActivity(scanBluetooth);
			}
		}
	}

	private void fillListView() {
		customAdapter = new CustomArrayAdapter(Robots.getRobots(), this);
		list = (ListView) findViewById(R.id.listwithrobots);
		list.setAdapter(customAdapter);
	}

	private void addNewRobot() {

		final Button addRobot = (Button) findViewById(R.id.addRobot);
		addRobot.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Inflate and set the layout for the dialog
				// Pass null as the parent view because its going in the dialog
				// layout
				AlertDialog.Builder builder = new AlertDialog.Builder(
						StartingPoint.this);
				LayoutInflater inflater = StartingPoint.this
						.getLayoutInflater(); // Get the layout inflater
				final View view = inflater.inflate(R.layout.addrobotdialog,
						null);

				builder.setView(view);
				builder.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.cancel();
							}
						});
				builder.setPositiveButton(R.string.add,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								EditText roboName = (EditText) view
										.findViewById(R.id.roboname);
								if (roboName != null) {
									Robots.add(roboName.getText().toString());
									// for(BluetoothDevice dvc: devices) {
									// Toast.makeText(context,
									// devices.get(0).getName(),
									// Toast.LENGTH_LONG).show();
									// }
								}
							}
						});
				builder.create().show();

				customAdapter.notifyDataSetChanged();
			}
		});
	}

	private void getMassageForListItemClick() {

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				final View selectedView = arg1;
				TextView label = (TextView) selectedView.findViewById(R.id.row);
				String textViewName = (String) label.getText();

				Intent i = new Intent(getApplicationContext(),
						RobotScreen.class);
				i.putExtra("name", textViewName);
				startActivity(i);
			}
		});
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Exit")
				.setMessage("Are you sure you want to exit?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.exit(0);
							}
						}).setNegativeButton("No", null).show();
	}
}
