package bg.roboleague.mobile.bluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class Bluetooth extends Thread {
	private final static UUID RN42_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static BluetoothAdapter adapter = BluetoothAdapter
			.getDefaultAdapter();
	private static BluetoothDevice device = null;
	private static BluetoothSocket socket = null;
	private static BufferedReader input = null;
	private static PrintWriter output = null;
	private static boolean connected = false;
	private static BluetoothListener listener;

	public static BluetoothListener getListener() {
		return listener;
	}

	public static void setListener(BluetoothListener listener) {
		Bluetooth.listener = listener;
	}

	public static void connect(String deviceAddress) {
		device = adapter.getRemoteDevice(deviceAddress);
		try {
			socket = device.createRfcommSocketToServiceRecord(RN42_UUID);
			socket.connect();
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			Log.w("BLT", "Can't connect to Bluetooth module!");
			e.printStackTrace();
		}
		connected = true;
		(new Bluetooth()).start();
	}

	public void run() {
		while (connected) {
			String received = receive();
			if (received != null) {
				listener.receive(received);
			}
		}
	}

	public static void sendLine(String message) {
		send(message + "\n");
	}

	public static void send(String message) {
		output.write(message);
		output.flush();
	}

	public static String receive() {
		String received = null;
		if (connected) {
			try {
				received = input.readLine().toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return received;
	}

	public static void disconnect() {
		try {
			if (input != null)
				input.close();
			if (output != null)
				output.close();
			if (socket != null)
				socket.close();
			connected = false;
		} catch (IOException e) {
			Log.w("BLT", "Can't disconnect from Bluetooth module!");
			e.printStackTrace();
		}
	}

	public static boolean isConnected() {
		return connected;
	}
}