import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.*;
/**
 * The target car which has to be moved to the outside
 * @author Shilpa
 *
 */
public class UserCar extends Sprite {
	
	
	
	public UserCar(Direction dir, String url) {
		
		 super(dir, 0, 2, GridLock.CAR_SIZE, "file:sprites/nemo.png"); 
		
		 
	}

	/**
	 * Check if the puzzle has been solved if the red car has reached
	 * the exit point.
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	private boolean isSolved () {
		if (this.getXcoord() == 4){
			return true;
		} else {
			return false;
		}
	}


	

	
}
