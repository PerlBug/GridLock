import com.sun.javafx.scene.traversal.Direction;
import javafx.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Stack;
/**
 * A  class to represent Sprites (we chose images of fish to represent our sprites instead of cars)
 *  A  small fish takes up 2 grid squares,a large fish takes up 3.
 *  @author becca
 * 
 */
public class Sprite extends StackPane {
    private double mouseX, mouseY;  //where the user clicked on the sprite
    private double Xcoord, Ycoord; //this is a pixel value (not a grid square coordinate)
	public enum Direction {
		HORIZONTAL,VERTICAL
	}
	private Direction direction; //horizontal or vertical movement direction
	private Image image;
	private String imageURL;   
    private double width;
    private double height;
    private static int classId=0; 
    private int id; 	//unique for every sprite created. The first sprite created has id 0
    private int size; 	//length of the block (width if horizontal movement, height if vertical)
    private Rectangle r;
    
    private int initialGX;
    private int initialGY;
    private double prevXC;
    private double prevYC;
    
    
    Stack StackX = new Stack();
    Stack StackY = new Stack();
    
    /**
     * Constructor for Sprite object.
     * @param imageURL is the icon for the Sprite
     * @param x is the x-coordinate of the first square containing the sprite on the grid
     * @param y is the y-coordinate of the first square containing the Sprite on the grid
     * @param size is the number of grid squares the Sprite occupies
     * @param direction is the direction of movement of the Sprite.
     */
    public Sprite( Direction dir, int x, int y, int size, String imageURL) {
    	//StackPane p= new StackPane();
    	
    	Image image = new Image(imageURL, width, height, false, false); 
    	this.id=classId++;
    	System.out.println("sprite id is " + id);
    	this.direction=dir;
    	this.initialGX = x;
    	this.initialGY = y;
    	//Initialise previous values.
    	this.Xcoord = x * GridLock.SQUARE_SIZE;
        this.Ycoord = y * GridLock.SQUARE_SIZE;
        move(x, y); //sets up xCoord, yCoord, and prevXC, prevYC
    	this.width=size*GridLock.SQUARE_SIZE; 
    	this.height=GridLock.SQUARE_SIZE;
    	this.size=size;
        this.r = new Rectangle(width,height);
        if(dir==Direction.VERTICAL) {
          	 this.r = new Rectangle(height,width); //rotate sprite
        }
       
        r.setStrokeWidth(GridLock.SQUARE_SIZE * 0.03);
        r.setFill(Color.GREEN);
        r.setFill(new ImagePattern(image));
        getChildren().addAll(r);

        //record where the sprite was clicked by the mouse. This information is used later by the DragHandler class.
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        
	}
    
 
    /**
     * Getter method for the rectangle bounding the sprite
     * @return r
     */
    public Rectangle getRectangle() {
    		return this.r;
    }
    
    /**
     * Getter method for the unique identifier of the sprite
     * @return id
     */
    public int getID() {
    	
    	return this.id;
    }
    
    
    /**
     * 
     * Updates the position of the Sprite object to the grid square ( @param x, @param y).
     * @postcondition: The Xcoord and Ycoord fields of this sprite object become x * GridLock.SQUARE_SIZE, y * GridLock.SQUARE_SIZE
     */
    public void move(int x, int y) {
    	
	    //creating stack
    	
    	//Pertains to undo button
		this.prevXC = Xcoord;
		this.prevYC = Ycoord;
    		
		//place the sprite at pixel coordinates corresponding to the provided x,y, grid square coordinates
        Xcoord = x * GridLock.SQUARE_SIZE;
        Ycoord = y * GridLock.SQUARE_SIZE;
        
        StackX.push(Xcoord);
        StackY.push(Ycoord);
        relocate(Xcoord, Ycoord);
    }

    /**
     * Aborts a move request by relocating the sprite back to its original position.
     */
    public void stopMove() {
        relocate(Xcoord, Ycoord);
    }
    
    /**
     * Resets sprite to initial position
     * @author leochen
     */
    public void resetSprite() {
    		double xcord = this.initialGX * GridLock.SQUARE_SIZE;
    		double ycord = this.initialGY * GridLock.SQUARE_SIZE;
    		relocate(xcord, ycord);
    }
    
    /**
     * Gets the position of the sprite as a point
     * @return p
     */
    public Point getPosition(Sprite v) {
    	Point p = null;
    	p.setLocation(Xcoord, Ycoord);
    	return p;
    }
    
    /**
     * Getter method for the number of grid squares the sprite takes up.
     * @return size;
     */
    public int getSize() {
    		return this.size;
    }

    /**
     * Getter method for the direction the sprite travels in
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }
    /**
     * Getter method for the (pixel)x coordinate of the sprite on the grid.
     * @return Xcoord
     */
    public double getXcoord() {
        return this.Xcoord;
    }
    /**
     * Getter method for the (pixel)y coordinate of the sprite on the grid.
     * @return Ycoord
     */
    public double getYcoord() {
        return this.Ycoord;
    }
    /**
     * Getter method for the x coordinate of the last mouse click on the sprite
     * @return mouseX
     */
    public double getMouseX() {
    	return mouseX;
    	
    }
    /**
     * Getter method for the y coordinate of the last mouse click on the sprite
     * @return mouseY
     */
    public double getMouseY() {
    	return mouseY;
    }
    
    
  /**
   * set the previous X coordinate of the sprite to @param X
   *  * @postcondition:  this.prevX = X;
   */
    public void setPrevX(double X) {
    		this.prevXC = X;
    }
    /**
     * set the previous X coordinate of the sprite to @param Y
     * @postcondition:  this.prevYC = Y;
     */
    public void setPrevY(double Y) {
    		this.prevYC = Y;
    }
    
    /**
     * Get the previous X coordinate of the sprite
     * @return prevXC
     */

    public double getPrevX() {
    		return this.prevXC;
    }
    
    /**
     * Get the previous X coordinate of the sprite
     * @return prevXY
     */

    public double getPrevY() {
    		return this.prevYC;
    }
	

}
