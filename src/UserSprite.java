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
public class UserSprite extends Sprite {
	
	
	
	public UserSprite(Direction dir, String url) {
		
		 super(dir, 0, 2, GridLock.CAR_SIZE, "file:sprites/nemo.png"); 
		
		 
	}


	
}
