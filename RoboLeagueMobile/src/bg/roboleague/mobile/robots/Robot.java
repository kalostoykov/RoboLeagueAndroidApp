package bg.roboleague.mobile.robots;

public class Robot implements Comparable<Robot>{
	private String name;
	private int[] times;
	private int bestTime;

	public Robot(String name) {
		this.name = name;
		this.times = new int[] { 0, 0, 0 };
		this.bestTime = 0;
	}
	
	public int getBestTime() {
		return bestTime;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTime(int attempt, int time) {
		this.times[attempt] = time;
		bestTime = Math.min(times[0], Math.min(times[1], times[2]));
	}

	public void setAllTimes(int time1, int time2, int time3) {
		this.times = new int[] { time1, time2, time3 };
		bestTime = Math.min(times[0], Math.min(times[1], times[2]));
	}
	
	public int getTime(int attempt) {
		if((attempt > 2) || (attempt < 0)) {
			return -1;
		} else {
			return times[attempt];
		}
	}
	
	public int[] getTimes() {
		return times;
	}

	@Override
	public int compareTo(Robot another) {
		return this.bestTime - another.getBestTime();
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
