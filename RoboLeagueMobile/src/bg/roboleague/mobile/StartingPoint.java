package bg.roboleague.mobile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bg.roboleague.mobile.robots.Robots;
import bg.roboleague.mobile.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
		createExternalStorageFile();
		fillListView();
		enterCalibrationActivity();
		addNewRobot();
		sortByTimeButton();
		startActivityForListItemClick();
	}

	private void initBluetooth() {

		devices = new ArrayList<BluetoothDevice>();
		bAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bAdapter == null) {
			Toast.makeText(context, "fail", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (bAdapter.isEnabled()) {
			Intent scanBluetooth = new Intent(getApplicationContext(),
					DeviceScanner.class);
			startActivity(scanBluetooth);
		}
		else {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
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
	
	private void enterCalibrationActivity() {
		final Button enterCalibrationActivityButton = (Button) findViewById(R.id.entercalibrationactivity);
		
		enterCalibrationActivityButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Calibration.class);
				startActivity(i);
			}
		});
	}
	
	private void addNewRobot() {

		final Button addRobot = (Button) findViewById(R.id.addRobot);
		addRobot.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						StartingPoint.this);
				LayoutInflater inflater = StartingPoint.this.getLayoutInflater();
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

								EditText editTextRoboName = (EditText) view
										.findViewById(R.id.roboname);
								String roboName = editTextRoboName.getText().toString();
								if (roboName.matches("")) {
									Toast.makeText(context, "Enter valid name!", Toast.LENGTH_LONG).show();
								} else {
									Robots.add(roboName);
									FileIO.addRobotToFile(roboName);
								}
							}
						});
				builder.create().show();

				customAdapter.notifyDataSetChanged();
			}
		});
	}
	
	private void sortByTimeButton() {
		Button sortButton = (Button)findViewById(R.id.sortrobots);
		sortButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Robots.sortByTime();
				list.invalidateViews();
			}
		});
	}
	
	private void startActivityForListItemClick() {

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
	
	private void createExternalStorageFile() {
		try {
			FileWriter myFileWriter = new FileWriter(android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
					+ File.separator + "results.csv", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
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