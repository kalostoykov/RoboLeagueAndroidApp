package bg.roboleague.mobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class FileIO {
	private static String fileName = "results.csv";
	
	public static void addRobotToFile(String robotName) {

		String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(csv, true));
			List<String[]> data = new ArrayList<String[]>();
			data.add(new String[] { robotName, "0", "0", "0" });
			writer.writeAll(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addRobotTimeToFile(String robotName, int entry, int newTime) {
		CSVReader reader;
		String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;
		try {
			reader = new CSVReader(new FileReader(csv));
			List<String[]> myEntries = reader.readAll();

			for (String[] line : myEntries) {
				if (line[0].equals(robotName)) {
					line[entry] = Integer.toString(newTime);
				}
			}

			CSVWriter writer;
			try {
				writer = new CSVWriter(new FileWriter(csv, false));
				writer.writeAll(myEntries);
				writer.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readRobotFile() {
		CSVReader reader;
		String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;
		try {
			reader = new CSVReader(new FileReader(csv));
			List<String[]> myEntries = reader.readAll();
			for (String[] line : myEntries) {
				Log.i("el", line[0] + " " + line[1] + " " + line[2] + " "+ line[3]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
