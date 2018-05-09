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
	
	private int moveCtr; //The number of grid squares traversed during the game. Not the same as number of drag 
	//and drops since a drag can be over multiple grid squares in some situations. 
	
	public UserCar(Direction dir, String url) {
		
		 super(dir, 0, 2, GridLock.CAR_SIZE, url); 
		 moveCtr=0; //At each game moveCtr is initialised to 0
		 
	}

	/**
	 * Check if the puzzle has been solved if the red car has reached
	 * the exit point.
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	boolean isSolved () {
		if (this.getXcoord() == 4 && this.getYcoord() ==2){
			return true;
		} else {
			return false;
		}
	}

	public int getMoveCtr() {
		return moveCtr;
		
	}
	
	
	/**
	 * Increment moveCtr.
	 * 
	 */
	public void incrementMoveCtr() {
		moveCtr++;
	}
	
	
}
