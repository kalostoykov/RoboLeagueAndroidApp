package bg.roboleague.mobile;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import bg.roboleague.mobile.robots.Robot;
import bg.roboleague.mobile.robots.RobotTimer;
import bg.roboleague.mobile.robots.Robots;
import bg.roboleague.mobile.robots.TimerDataReceiver;

import bg.roboleague.mobile.R;

public class RobotScreen extends Activity implements TimerDataReceiver {
	private TextView[] lapViews;
	int lapSelected = -1; // no lap selected
	Robot robot;
	String robotName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.robotinfolayout);

		robotName = this.getIntent().getStringExtra("name");

		lapViews = new TextView[3];

		TextView dispRobotName = (TextView) this.findViewById(R.id.inforobotname);
		TextView firstLap = (TextView) this.findViewById(R.id.firstlapresult);
		TextView secondLap = (TextView) this.findViewById(R.id.secondlapresult);
		TextView thirdLap = (TextView) this.findViewById(R.id.thirdlapresult);

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
					RobotTimer.startMeasuring();
				} else {
					startButton.setText("Start");
					v.setTag(1);
					RobotTimer.stopMeasuring();
					// get robot time after finish the lap
					int poluchenoVreme = 123;
					FileIO.addRobotTimeToFile(robotName, lapSelected, poluchenoVreme);
				}
			}
		});

		dispRobotName.setText(robotName);
		robot = Robots.getByName(robotName);
		Toast.makeText(getApplicationContext(), robot.getName(),
				Toast.LENGTH_LONG).show();
		Random r = new Random();
		
//		robot.setTime(0, r.nextInt(100000));
//		robot.setTime(1, r.nextInt(100000));
//		robot.setTime(2, r.nextInt(100000));
		
//		writeToExternalStorageFile("myfile2.csv", "Robot1");//create an empty line for Robot1 in the file
//		writeToExternalStorageFile("myfile2.csv", "Robot3");
//		writeToExternalStorageFile("myfile2.csv", "Robot2");
//		
//		appendToExternalStorageFile("myfile2.csv", "Robot2", 1, 11);// add the first time (equal to 11) to robot with name Robot2
//		appendToExternalStorageFile("myfile2.csv", "Robot2", 2, 22);
//		appendToExternalStorageFile("myfile2.csv", "Robot2", 3, 33);
//		appendToExternalStorageFile("myfile2.csv", "Robot1", 1, 1);
//		appendToExternalStorageFile("myfile2.csv", "Robot1", 2, 2);
//		appendToExternalStorageFile("myfile2.csv", "Robot1", 3, 3);
//		appendToExternalStorageFile("myfile2.csv", "Robot3", 1, 111);
//		appendToExternalStorageFile("myfile2.csv", "Robot3", 2, 222);
//		appendToExternalStorageFile("myfile2.csv", "Robot3", 3, 333);
//		
//		appendToExternalStorageFile("myfile2.csv", "Robot1", 2, 9999);
//		
//		
//		readFromExternalStorageFile("myfile2.csv");
		// fill the table
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
	
//	public void writeToExternalStorageFile(String fileName, String robotName) { // add new robot
//			//String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "myfile.csv";
//			String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;
//			CSVWriter writer;
//			try {
//				writer = new CSVWriter(new FileWriter(csv, true), '*');
//				
//				List<String[]> data = new ArrayList<String[]>();
//				data.add(new String[] {robotName, "-1", "-1", "-1"});
////				data.add(new String[] {"Robot2", "234234", "54684", "5456456"});
////				data.add(new String[] {"Robot3", "68464", "6+849864", "65468"});
////				
//				writer.writeAll(data);
//				writer.close();
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	}
//	private void appendToExternalStorageFile(String fileName, String robotName, int entry, int newTime) {
//		CSVReader reader;
//		String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;
//		try {
//			reader = new CSVReader(new FileReader(csv), '*');
//			List<String[]> myEntries = reader.readAll();
//			
//			for(String[] line : myEntries){
//				if(line[0].equals(robotName)){
//					line[entry] = Integer.toString(newTime);
//				}
//			}
//
//			CSVWriter writer;
//			try {
//				writer = new CSVWriter(new FileWriter(csv, false), '*');
//				writer.writeAll(myEntries);
//				writer.close();
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			for(String[] line : myEntries){
//				Log.i("el", line[0]+" "+line[1]+" "+line[2]+" "+line[3]  );
//			}
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	private void readFromExternalStorageFile(String fileName) {
//		CSVReader reader;
//		String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;
//		try {
//			reader = new CSVReader(new FileReader(csv), '*');
//			List<String[]> myEntries = reader.readAll();
//			for(String[] line : myEntries){
//				Log.i("el", line[0]+" "+line[1]+" "+line[2]+" "+line[3]  );
//			}
//			//Log.i("items", myEntries.get(1));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
////		CSVReader reader;
////		String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;
////		try {
////			reader = new CSVReader(new FileReader(csv));
////			String [] nextLine;
////		    while ((nextLine = reader.readNext()) != null) {
////		        // nextLine[] is an array of values from the line
////		    	List<String> data = new ArrayList<String>();
////		    	data.add(nextLine[0]);
////		        Log.i("Items", nextLine[0]);
////		    }
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	    
//	    
//	}
	
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
