package bg.roboleague.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import bg.roboleague.mobile.bluetooth.Bluetooth;
import bg.roboleague.mobile.bluetooth.BluetoothListener;

public class RobotTimer implements BluetoothListener {
	
	//state control commands
	public final static String ENABLE_MEASURING = "EMS";
	public final static String DISABLE_MEASURING = "DMS";
	public final static String BEGIN_CALIBRATION = "BCL";
	public final static String END_CALIBRATION = "ECL";

	//callibration commands
	public final static String THRESHOLD_NEAR = "NER";
	public final static String THRESHOLD_DISTANT = "DST";
	public final static String TOLERANCE = "TOL";
	public final static String AMOUNT = "TMS";
	public final static String DELAY = "DLY";
	public final static String MINIMAL_TIME = "MTI";

	//reporting back events/values
	public final static String ROBOT_STARTED = "RST";
	public final static String ROBOT_FINISHED = "RFN";
	public final static String SENSOR_READING = "SRD";
	
	private static HashMap<String, Integer> parameters = new HashMap<String, Integer>();
	private static List<TimerDataReceiver> receivers = new ArrayList<TimerDataReceiver>();
	private static boolean measuring = false;
	static {
		parameters.put(THRESHOLD_NEAR, 0);
		parameters.put(THRESHOLD_DISTANT, 0);
		parameters.put(TOLERANCE, 0);
		parameters.put(MINIMAL_TIME, 0);
		parameters.put(AMOUNT, 0);
		parameters.put(DELAY, 0);
		parameters.put(SENSOR_READING, 0);
		parameters.put(ROBOT_FINISHED, 0);
	}

	public static boolean isMeasuring() {
		return measuring;
	}

	public static void addReceiver(TimerDataReceiver receiver) {
		receivers.add(receiver);
	}

	public static void removeReceiver(TimerDataReceiver receiver) {
		receivers.remove(receiver);
	}

	private static void notifyReceivers(String parameter, int value) {
		for (TimerDataReceiver receiver : receivers) {
			receiver.receive(parameter, value);
		}
	}

	public static void enterCalibrationMode() {
		Bluetooth.send(BEGIN_CALIBRATION);
	}

	public static void exitCalibrationMode() {
		Bluetooth.send(END_CALIBRATION);
	}

	public static int getParameter(String parameter) {
		return parameters.get(parameter);
	}

	public static void setParameter(String parameter, int value) {
		if (parameters.containsKey(parameter)) {
			Bluetooth.send(parameter + value);
		}
	}

	public static void startMeasuring() {
		measuring = true;
		Bluetooth.send(ENABLE_MEASURING);
	}

	public static void stopMeasuring() {
		measuring = false;
		Bluetooth.send(DISABLE_MEASURING);
	}

	@Override
	public void receive(String command) {
		String parameter = command.substring(0, 3);
		int value = Integer.parseInt(command.substring(3));
		notifyReceivers(parameter, value);
		if (parameters.containsKey(parameter)) {
			parameters.put(parameter, value);
		} else {
			System.out.println("Error: invalid parameter " + parameter
					+ " received from timer!");
		}
	}
}