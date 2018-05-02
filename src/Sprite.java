import com.sun.javafx.scene.traversal.Direction;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * A  class to represent Sprites (cars and trucks)
 * @author team name?
 *
 */
public class Sprite {
	
	public enum Direction {
		HORIZONTAL,
		VERTICAL
	}
	private Direction direction; //horizontal or vertical movement direction
	private Image image;
	private String imageURL;
    private double xPos;
    private double yPos;    
    private double width;
    private double height;
    
    /**
     * Constructor for Sprite object.
     * @param imageURL is the icon for the Sprite
     * @param x is the x-coordinate of the Sprite on the grid
     * @param y is the y-coordinate of the Sprite on the grid
     * @param size is the number of grid squares the Sprite occupies
     * @param direction is the direction of movement of the Spirte.
     */
    public Sprite(String imageURL, double x, double y, int size, Direction dir) {
    	this.imageURL = imageURL;
    	xPos = x;
    	yPos = y;
    	width = size*100;
    	height = 100;
    	direction=dir;
    	image = new Image(imageURL, width, height, false, false);    	
    }
    
    /**
     * 
     * @param size
     */
    public void setSize(int size) {
    	width = size*100;
    	height = 100;
    	image = new Image(imageURL, width, height, false, false); 
    	
    }
    
    
    
    public void setPosition(double x, double y) {
    	xPos = x;
    	yPos = y;
    }
    
    public void setImage(String imageURL, double w, double h) {
    	image = new Image(imageURL, w, h, false, false);
    }
    
    public void rotateSprite() {
    	double temp = height; 
    	height = width;
    	width = temp;
    	
    	image = new Image(imageURL, width, height, false, false);
    }
   
    
    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, xPos, yPos );
    }
 
    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(xPos,yPos,width,height);
    }
 
    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects(this.getBoundary());
    }
    
    
}
