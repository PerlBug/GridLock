/**
 * TimerPane.java written for COMP2511 18s1 Project - Group 3
 * 
 * @authors
 * 5162531 Diaz, Rebecca Avril: beccaD6 - <becca.diaz6@gmail.com>
 * 3461044 Kala, Shilpa: sk2552 - <simple.snowflake@gmail.com>
 * 3463869 Sun, Elliott Yongrui: umeb0shi - <elly.here@gmail.com>
 * 5157086 Galoyan, Mkrtich: PerlBug - <galoyanmko@gmail.com>
 * 5060239 Chen, Leo Jia Jian: leochen15 - <leochen1512@gmail.com>
 * 
 * This is a class to display the timer
 * 
 */
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class TimerPane extends StackPane implements Runnable {
	
	private Text t = new Text();
	private int seconds;
	private int minutes;
	private Timer timer;
	private boolean keep_running;
	public static final double HEIGHT = GridLock.CANVAS_WIDTH/5;
	public static final double WIDTH = GridLock.CANVAS_WIDTH/5;

	/**
	 * Constructor method for the TimerPane class
	 * @precondition imageurl != null and is is a valid image
	 * @param imageurl
	 */
	public TimerPane (String imageurl) {
		
		Image imgPre = new Image(imageurl, WIDTH, HEIGHT, false, false);
		ImageView img = new ImageView(imgPre);
		this.seconds = 0;
		this.minutes = 0;
		keep_running = true;
		
		timer = new Timer();
		
		getChildren().addAll(img, t);
		
		setAlignment(Pos.CENTER);
	}

	/**
	 * Method which prints the time elapsed from the start of the game
	 * @precondition seconds >= 0 and minutes >=0;
	 * @param seconds
	 * @param minutes
	 */
	public void printLabel(int seconds, int minutes) {
		this.seconds = seconds;
		this.minutes = minutes;
		if (minutes < 10) {
			if(seconds < 10) {
				this.t.setText("Time: " + "0" + Integer.toString(minutes) + ":0" + Integer.toString(seconds));		
			}
			else {
				this.t.setText("Time: " + "0" + Integer.toString(minutes) + ":" + Integer.toString(seconds));	
			}
			
		}
		
		
	}
	
	/**
	 * This method sets the flag which 'terminates' the thread, stopping the clock
	 * 
	 * @param flag
	 */
	public void set_keep_timing(boolean flag) {
		this.keep_running = flag; //true = thread keeps running; false = thread stops
	}
	
	/**
	 * This method sets the current display time on the clock
	 * @precondition sec >= 0
	 * @precondition min >= 0
	 * @param sec
	 * @param min
	 */
	public void setTime(int sec, int min) {
		this.seconds = sec;
		this.minutes = min;
	}
	
	/**
	 * Method which runs a thread which keeps displaying the current time in minutes and seconds
	 * on the TimerPane in the GUI
	 */
	@Override
	public void run() {
		
		
		while (keep_running) {
			
			double finishedTime = timer.getTimeFromStart();
			this.seconds = timer.getSeconds(finishedTime);
			this.minutes = timer.getMinutes(finishedTime);
			if (minutes < 10) {
				if(seconds < 10) {
					this.t.setText("Time: " + "0" + Integer.toString(minutes) + ":0" + Integer.toString(seconds));		
				}
				else {
					this.t.setText("Time: " + "0" + Integer.toString(minutes) + ":" + Integer.toString(seconds));	
				}
				
			}
		}
		
		
		
		
	}
}
