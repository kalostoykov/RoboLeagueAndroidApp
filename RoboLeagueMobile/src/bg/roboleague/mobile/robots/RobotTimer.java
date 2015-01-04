package bg.roboleague.mobile.robots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bg.roboleague.mobile.bluetooth.Bluetooth;
import bg.roboleague.mobile.bluetooth.BluetoothListener;

public class RobotTimer implements BluetoothListener {

	public final static String THRESHOLD_NEAR = "NER";
	public final static String THRESHOLD_DISTANT = "DST";
	public final static String TOLERANCE = "TOL";
	public final static String TIME_MINIMUM = "TMN";

	public final static String TIMES_MEASURE = "TMS";
	public final static String DELAY_MEASURE = "DLY";

	public final static String BEGIN_MEASURING = "BMS";
	public final static String STOP_MEASURING = "SMS";
	public final static String CALIBRATE = "CAL";
	public final static String STANDBY = "SBY";

	public final static String MEASURED_VALUE = "RAW";
	public final static String LAP_STARTED = "SLP";
	public final static String LAP_FINISHED = "FLP";

	private static HashMap<String, Integer> parameters = new HashMap<String, Integer>();

	private static List<TimerDataReceiver> receivers = new ArrayList<TimerDataReceiver>();

	private static boolean measuring = false;

	static {
		parameters.put(THRESHOLD_NEAR, 0);
		parameters.put(THRESHOLD_DISTANT, 0);
		parameters.put(TOLERANCE, 0);
		parameters.put(TIME_MINIMUM, 0);
		parameters.put(TIMES_MEASURE, 0);
		parameters.put(DELAY_MEASURE, 0);
		parameters.put(MEASURED_VALUE, 0);
		parameters.put(LAP_FINISHED, 0);
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

	public static void notifyReceivers(String parameter, int value) {
		for (TimerDataReceiver receiver : receivers) {
			receiver.receive(parameter, value);
		}
	}

	public static void enterCalibrationMode() {
		Bluetooth.send(CALIBRATE);
	}

	public static void exitCalibrationMode() {
		Bluetooth.send(STANDBY);
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
		Bluetooth.send(BEGIN_MEASURING);
	}

	public static void stopMeasuring() {
		measuring = false;
		Bluetooth.send(STOP_MEASURING);
	}

	@Override
	public void receive(String command) {
		String parameter = command.substring(0, 3);
		int value = Integer.parseInt(command.substring(3));
		notifyReceivers(parameter, value);

		if (parameters.containsKey(parameter)) {
			parameters.put(parameter, value);

		} else {
			System.out.println("Error: invalid parameter " + parameter + " received from timer!");
		}
	}

	private static void set(String parameter, int value) {

		parameters.put(parameter, value);
	}
}
