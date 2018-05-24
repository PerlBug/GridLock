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
	 * Method which returns the time elapsed since the timer was initiated.
	 * @return
	 */
	public double getTimeFromStart() {
		return presentTime = (System.currentTimeMillis() - startingTime)/1000;
	}
	
	/**
	 * Method which calculates and returns the time elapsed in seconds
	 * @precondition time>=0
	 * @param time
	 * @return
	 */
	public int getSeconds(double time) {
		return (int)time%60;
	}
	
	/**
	 * Method which calculates and returns the time elapsed in minutes
	 * @precondition time >= 0
	 * @param time
	 * @return
	 */
	public int getMinutes(double time) {
		return (int)(time/60);
	}
	
	/**
	 * Method which restarts the timer from the current time
	 * @postcondition the timer is reset
	 */
	public void resetTimer() {
		startingTime = System.currentTimeMillis();
	}
	
	
}
