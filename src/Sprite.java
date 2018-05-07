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
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
/**
 * A  class to represent Sprites (cars and trucks)
 * @author 
 *
 */
public class Sprite extends StackPane {
	 
    private double mouseX, mouseY;
    private double Xcoord, Ycoord; //pixel not grid square values
	public enum Direction {
		HORIZONTAL,VERTICAL
	}
	private Direction direction; //horizontal or vertical movement direction
	private Image image;
	private String imageURL;   
    private double width;
    private double height;
    private static int classId=0; //unique for every sprite created first sprite created has id 0
    private int id;
   
    /**
     * Constructor for Sprite object.
     * @param imageURL is the icon for the Sprite
     * @param x is the x-coordinate of the Sprite on the grid
     * @param y is the y-coordinate of the Sprite on the grid
     * @param size is the number of grid squares the Sprite occupies
     * @param direction is the direction of movement of the Spirte.
     */
    public Sprite( Direction dir, int x, int y) {
    	
    	//image = new Image(imageURL, width, height, false, false); 
    	this.id=classId++;
    	System.out.println("sprite id is " + id);
    	this.direction=dir;
        move(x, y); //sets up xCoord, yCoord
    	this.width=2*GridLock.SQUARE_SIZE; // 2 for cars, 3 for truck
    	this.height=GridLock.SQUARE_SIZE;

       
        Rectangle r = new Rectangle(width,height);
        if(dir==Direction.VERTICAL) {
          	 r = new Rectangle(height,width); //rotate sprite
        }
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(GridLock.SQUARE_SIZE * 0.03);
        r.setFill(Color.GREEN);
      
        getChildren().addAll(r);

        
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + Xcoord, e.getSceneY() - mouseY + Ycoord);
        });
       
        
    }
    
    public int getID() {
    	
    	return this.id;
    }
    
    public void move(int x, int y) {
    	
    	
        Xcoord = x * GridLock.SQUARE_SIZE;
        Ycoord = y * GridLock.SQUARE_SIZE;
       /* System.out.println(Xcoord);
    	System.out.println(Ycoord);*/
        relocate(Xcoord, Ycoord);
    }

    /**
     * Aborts a move request by relocating the sprite back to its original position.
     */
    public void stopMove() {
        relocate(Xcoord, Ycoord);
    }
    
    /**
     * 
     * @return the factor by which the width of the sprite is . e.g. 3 * square size is a truck, 2* square size is
     * a car.
     */
    public int getWidthSquareSize() {
    	return (int)width/GridLock.SQUARE_SIZE;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getXcoord() {
        return Xcoord;
    }

    public double getYcoord() {
        return Ycoord;
    }

	public int getHeightSquareSize() {		
		return (int)width/GridLock.SQUARE_SIZE; //width->height when rotate vertical sprite
	}
	
	

}
