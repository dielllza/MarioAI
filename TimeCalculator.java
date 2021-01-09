public class TimeCalculator {
	public long durationInSeconds, startTime;
	public TimeCalculator(long durationInSeconds) {
		this.durationInSeconds = durationInSeconds ;
	}
	public void startTimer() {
		startTime = System.currentTimeMillis();
	}
	public long getCurrentTimer() {
		long elapsedTime = System.currentTimeMillis() - startTime;
		long elapsedSeconds = elapsedTime / 1000;
		return elapsedSeconds;
	}
	public boolean isOver() {
		if(this.getCurrentTimer() >= durationInSeconds)
			return true;
		return false;
	}
	
}
