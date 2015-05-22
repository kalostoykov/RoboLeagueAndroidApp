package bg.roboleague.mobile;

import bg.roboleague.mobile.R;
import bg.roboleague.timer.RobotTimer;
import bg.roboleague.timer.TimerDataReceiver;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Calibration extends Activity implements TimerDataReceiver {
	private boolean closeDistanceCalibrationStatus = false;
	private boolean longDistanceCalibrationStatus = false;
	RobotTimer timer;
	TextView calibrationValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calibration);
		
		RobotTimer.addReceiver(this);
		calibrationValue = (TextView)findViewById(R.id.calibValue);
		Button closeDistanceButton = (Button)findViewById(R.id.closeDistance); 
		Button longDistanceButton = (Button)findViewById(R.id.longDistance);
		
		RobotTimer.enterCalibrationMode();
		
		closeDistanceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RobotTimer.setParameter(RobotTimer.THRESHOLD_NEAR, RobotTimer.getParameter(RobotTimer.SENSOR_READING));
				closeDistanceCalibrationStatus = true;
				isCalibrationReady();
				
				Toast.makeText(getApplicationContext(), "Close calibration status: set",
						Toast.LENGTH_LONG).show();
			}
		});
		
		longDistanceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RobotTimer.setParameter(RobotTimer.THRESHOLD_DISTANT, RobotTimer.getParameter(RobotTimer.SENSOR_READING));
				longDistanceCalibrationStatus = true;
				isCalibrationReady();
				
				Toast.makeText(getApplicationContext(), "Long calibration status: set",
						Toast.LENGTH_LONG).show();
			}
		});
		
	}

	private void isCalibrationReady() {
		if(closeDistanceCalibrationStatus && longDistanceCalibrationStatus) {
			RobotTimer.exitCalibrationMode();
			Toast.makeText(getApplicationContext(), "Full calibration status: set",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void receive(String parameter, int value) {
		if (parameter.equals(RobotTimer.SENSOR_READING)) {
			calibrationValue.setText(value);
		}
	}
}
