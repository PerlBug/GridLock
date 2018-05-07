import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.*;
/**
 * The target car which has to be moved to the outside
 * @author Shilpa
 *
 */
public class UserCar extends Sprite {

	public UserCar(Direction dir) {
		
		super(dir, 1, 2, 2,"file:sprites/playercar.png"); 
        Rectangle r = super.getRectangle();
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(GridLock.SQUARE_SIZE * 0.03);
        r.setFill(Color.RED);
		
		
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
