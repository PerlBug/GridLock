/**
 * Timer.java written for COMP2511 18s1 Project - Group 3
 * 
 * @authors
 * 5162531 Diaz, Rebecca Avril: beccaD6 - <becca.diaz6@gmail.com>
 * 3461044 Kala, Shilpa: sk2552 - <simple.snowflake@gmail.com>
 * 3463869 Sun, Elliott Yongrui: umeb0shi - <elly.here@gmail.com>
 * 5157086 Galoyan, Mkrtich: PerlBug - <galoyanmko@gmail.com>
 * 5060239 Chen, Leo Jia Jian: leochen15 - <leochen1512@gmail.com>
 * 
 * This is a Timer class which keeps track of how long the game has gone for.
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
