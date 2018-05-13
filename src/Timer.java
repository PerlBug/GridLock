/**
 * A Timer class which keeps track of how long the game has gone for.
 * @author Shilpa
 *
 */
public class Timer {
	
	private double startingTime;
	private double presentTime;
	public Timer() {
		startingTime = System.currentTimeMillis();
	}
	
	/**
	 * Method which gets the time elapsed since the timer was initiated.
	 * @return
	 */
	public double getTimeFromStart() {
		return presentTime = (System.currentTimeMillis() - startingTime)/1000;
	}
	
	/**
	 * Method which calculates and returns the time elapsed in seconds
	 * @param time
	 * @return
	 */
	public int getSeconds(double time) {
		return (int)time%60;
	}
	
	/**
	 * Method which calculates and returns the time elapsed in minutes
	 * @param time
	 * @return
	 */
	public int getMinutes(double time) {
		return (int)(time/60);
	}
	
	public void resetTimer() {
		startingTime = System.currentTimeMillis();
	}
	
	
}
