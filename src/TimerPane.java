/**
 * @author Shilpa
 * A class to display the timer
 */
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class TimerPane extends StackPane {
	
	private Text t = new Text();
	private int seconds;
	private int minutes;
	
	public TimerPane (String imageurl) {
		Image imgPre = new Image(imageurl, 120, 120, false, false);
		ImageView img = new ImageView(imgPre);
		this.seconds = 0;
		this.minutes = 0;
		
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
}
