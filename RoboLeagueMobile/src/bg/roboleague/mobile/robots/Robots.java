package bg.roboleague.mobile.robots;

import java.util.ArrayList;
import java.util.List;

public class Robots {
	static final List<Robot> robots = new ArrayList<Robot>();

	public static List<String> getNames() {
		List<String> names = new ArrayList<String>();
		for (Robot robot : robots) {
			names.add(robot.getName());
		}
		return names;
	}

	public static List<Robot> getRobots() {
		return robots;
	}

	public static void add(String name) {
		robots.add(new Robot(name));
	}
	
	public static Robot getByName(String name) {
		for(Robot robot: robots) {
			if(robot.getName().equals(name)) 
				return robot; 
		}
		return null;
	}
}
