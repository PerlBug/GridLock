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
	
	
	public UserCar(Direction dir, int x, int y, String url) {
		
		 super(dir, x, y, GridLock.CAR_SIZE, url); 
		 
	}

	/**
	 * Check if the puzzle has been solved if the red car has reached
	 * the exit point.
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	boolean isSolved (int xPos, int yPos) {
		if (xPos == 4 && yPos == 2) {
			return true;
		} else {
			return false;
		}
	}
}
