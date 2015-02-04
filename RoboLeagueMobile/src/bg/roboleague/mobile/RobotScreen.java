package bg.roboleague.mobile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import bg.roboleague.mobile.robots.Robot;
import bg.roboleague.mobile.robots.RobotTimer;
import bg.roboleague.mobile.robots.Robots;
import bg.roboleague.mobile.robots.TimerDataReceiver;

import com.example.myfirstapp.R;

public class RobotScreen extends Activity implements TimerDataReceiver {
	private TextView[] lapViews;
	int lapSelected = -1; // no lap selected
	Robot robot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.robotinfolayout);
		
		String robotName = this.getIntent().getStringExtra("name");
		
		lapViews = new TextView[3];

		TextView dispRobotName = (TextView)this.findViewById(R.id.inforobotname);
		TextView firstLap = (TextView)this.findViewById(R.id.firstlapresult);
		TextView secondLap = (TextView)this.findViewById(R.id.secondlapresult);
		TextView thirdLap = (TextView)this.findViewById(R.id.thirdlapresult);

		lapViews[0] = (TextView) this.findViewById(R.id.firstlapresult);
		lapViews[1] = (TextView) this.findViewById(R.id.secondlapresult);
		lapViews[2] = (TextView) this.findViewById(R.id.thirdlapresult);
		
		final Button startButton = (Button) this.findViewById(R.id.startButton);
		startButton.setText("Start");
		startButton.setTag(1);

		startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int status = (Integer) v.getTag();
				if (status == 1) {
					startButton.setText("Stop");
					v.setTag(0);
				} else {
					startButton.setText("Start");
					v.setTag(1);
				}
			}
		});

		dispRobotName.setText(robotName);
		robot = Robots.getByName(robotName);
		Toast.makeText(getApplicationContext(), robot.getName(), Toast.LENGTH_LONG).show();
		Random r = new Random();
		robot.setTime(0, r.nextInt(100000));
		robot.setTime(1, r.nextInt(100000));
		robot.setTime(2, r.nextInt(100000));
		
		int[] times = robot.getTimes();

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
					}
				}
			});
		}
		
		Robots.sortByTime();
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
			if (parameter.equals(RobotTimer.LAP_FINISHED)) {
				robot.setTime(lapSelected, value);
				lapViews[lapSelected].setText(formatTime(value));
			}

			else if (parameter.equals(RobotTimer.LAP_STARTED)) {
				Toast.makeText(this, "Robot " + robot.getName() + "has started!", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
