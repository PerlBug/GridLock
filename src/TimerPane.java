/**
 * @author Shilpa
 * A class to display the timer
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

	
	public TimerPane (String imageurl) {
		
		Image imgPre = new Image(imageurl, WIDTH, HEIGHT, false, false);
		ImageView img = new ImageView(imgPre);
		this.seconds = 0;
		this.minutes = 0;
		keep_running = true;
		
		timer = new Timer();
		//t.setText("Time taken " + minutes + " Minutes and " + seconds + " Seconds");
		
		getChildren().addAll(img, t);
		
		setAlignment(Pos.CENTER);
	}

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

	public void set_keep_timing(boolean flag) {
		this.keep_running = flag;
	}
	public void setTime(int sec, int min) {
		this.seconds = sec;
		this.minutes = min;
	}
	@Override
	public void run() {
		
		
		while (keep_running) {
			//System.out.println("hello i'm here");
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
