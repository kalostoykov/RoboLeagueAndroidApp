package bg.roboleague.mobile;

import com.example.myfirstapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Calibration extends Activity {
	private boolean closeDistanceCalibrationStatus = false;
	private boolean longDistanceCalibrationStatus = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calibration);
		
		Button closeDistanceButton = (Button)findViewById(R.id.closeDistance); 
		Button longDistanceButton = (Button)findViewById(R.id.longDistance);
		
		
		
		closeDistanceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TO DO TO DO TO DO TO DO TO DO TO DO TO DO TO DO TO DO TO DO TO DO
				//call calibration for close distance
				closeDistanceCalibrationStatus = true; //result here
				isCalibrationReady();
			}

			
		});
		
		longDistanceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TO DO TO DO TO DO TO DO TO DO TO DO TO DO TO DO TO DO TO DO TO DO
				//call calibration for long distance
				longDistanceCalibrationStatus = true; //result here
				isCalibrationReady();
			}
		});
		
	}
	
	private void isCalibrationReady() {
		if(closeDistanceCalibrationStatus && longDistanceCalibrationStatus) {
			Intent i =  new Intent(this, StartingPoint.class);
			startActivity(i);
		}
	}
	
}
