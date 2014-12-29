package bg.roboleague.mobile.robots;

public class Robot {
	private String name;
	private int[] times;

	public Robot(String name) {
		this.name = name;
		this.times = new int[3];
		
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTime(int attempt, int time) {
		this.times[attempt] = time;
	}

	public int[] getTimes() {
		return times;
	}
}
