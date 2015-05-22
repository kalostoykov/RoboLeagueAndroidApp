package bg.roboleague.timer;

public interface TimerDataReceiver {
	public abstract void receive(String parameter, int value);
}