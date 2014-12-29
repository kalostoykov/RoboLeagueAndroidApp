package bg.roboleague.mobile;

import bg.roboleague.mobile.robots.Robot;
import bg.roboleague.mobile.robots.Robots;

import com.example.myfirstapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class RobotScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.robotinfolayout);
		
		String robotName = this.getIntent().getStringExtra("name");
		
		TextView dispRobotName = (TextView)this.findViewById(R.id.inforobotname);
		TextView firstLap = (TextView)this.findViewById(R.id.firstlapresult);
		TextView secondLap = (TextView)this.findViewById(R.id.secondlapresult);
		TextView thirdLap = (TextView)this.findViewById(R.id.thirdlapresult);
		
		dispRobotName.setText(robotName);
		Robot robot = Robots.getByName(robotName);
		Toast.makeText(getApplicationContext(), robot.getName(),Toast.LENGTH_LONG).show();
		
		robot.setTime(0, 1234);
		robot.setTime(1, 9);
		robot.setTime(2, 23);
		int[] times = robot.getTimes();
		
		firstLap.setText(Integer.toString(times[0]));
		secondLap.setText(Integer.toString(times[1]));
		thirdLap.setText(Integer.toString(times[2]));
	}
}
