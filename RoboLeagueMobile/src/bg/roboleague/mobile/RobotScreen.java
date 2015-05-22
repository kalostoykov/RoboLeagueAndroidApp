package bg.roboleague.mobile;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import bg.roboleague.mobile.robots.Robot;
import bg.roboleague.mobile.robots.Robots;
import bg.roboleague.mobile.R;
import bg.roboleague.timer.RobotTimer;
import bg.roboleague.timer.TimerDataReceiver;

public class RobotScreen extends Activity implements TimerDataReceiver {
	private TextView[] lapViews;
	int lapSelected = -1; // no lap selected
	Robot robot;
	String robotName;
	int[] times;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.robotinfolayout);
		
		RobotTimer.addReceiver(this);
		robotName = this.getIntent().getStringExtra("name");
		
		lapViews = new TextView[3];

		TextView dispRobotName = (TextView) this.findViewById(R.id.inforobotname);

		lapViews[0] = (TextView) this.findViewById(R.id.firstlapresult);
		lapViews[1] = (TextView) this.findViewById(R.id.secondlapresult);
		lapViews[2] = (TextView) this.findViewById(R.id.thirdlapresult);

		final Button startButton = (Button) this.findViewById(R.id.startButton);
		startButton.setText("Start");
		startButton.setTag(1);
		
		startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int status = (Integer) v.getTag();
				if (status == 1) {
					if(lapSelected >= 0) {
						startButton.setText("Stop");
						RobotTimer.startMeasuring();
						v.setTag(0);
					} else {
						Toast.makeText(getApplicationContext(), "Select lap first!",
								Toast.LENGTH_LONG).show();
					}
				} else {
					if(RobotTimer.getParameter(RobotTimer.ROBOT_FINISHED) == 1) {
						v.setTag(1);
					} else {
						v.setTag(1);
						startButton.setText("Start");
						RobotTimer.stopMeasuring();
					}
					
				}
			}
		});
		
		resetLapTime();

		dispRobotName.setText(robotName);
		robot = Robots.getByName(robotName);
		Toast.makeText(getApplicationContext(), robot.getName(),
				Toast.LENGTH_LONG).show();
		
		times = robot.getTimes();
		
		for (int i = 0; i < lapViews.length; ++i) {
			
			lapViews[i].setText(formatTime(times[i]));
			
			final int lap = i;
			lapViews[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!RobotTimer.isMeasuring()) {
						lapSelected = lap;
						unsetHighlight();
						lapViews[lapSelected].setTextColor(Color.GREEN);
						Log.i("LAPNUMBER", Integer.toString(lapSelected));
					}
				}
			});
		}
		
		Robots.sortByTime();
	}
	
	private void resetLapTime() {
		Button resetTimeButton = (Button) this.findViewById(R.id.resetTimeButton);
		
		resetTimeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				robot.setTime(lapSelected, 0);
				lapViews[lapSelected].setText(formatTime(0));
				FileIO.addRobotTimeToFile(robotName, lapSelected + 1, 0);
			}
		});
	}

	private String formatTime(int millis) {
		return String.format("%02d:%02d:%03d", millis / 60000,
				(millis / 1000) % 60, millis % 1000);
	}
	
	private void unsetHighlight() {
		for (int i = 0; i < lapViews.length; ++i) {
			lapViews[i].setTextColor(Color.WHITE);
		}
	}

	@Override
	public void receive(String parameter, int value) {
		if (lapSelected != -1) {
			if (parameter.equals(RobotTimer.ROBOT_FINISHED)) {
				
				robot.setTime(lapSelected, value);
				lapViews[lapSelected].setText(formatTime(value));
				FileIO.addRobotTimeToFile(robotName, lapSelected + 1, value);
			}
			
			else if (parameter.equals(RobotTimer.ROBOT_STARTED)) {
				Toast.makeText(this, "Robot " + robot.getName() + "has started!", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
