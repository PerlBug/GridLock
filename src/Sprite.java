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
/**
 * A  class to represent Sprites (cars and trucks).
 * A car has size 2, truck size 3.
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
    private static int classId=0; 
    private int id; //unique for every sprite created first sprite created has id 0
    private int size; //length of the block (width if horizontal movement, height if vertical)
    private Rectangle r;
    
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
        move(x, y); //sets up xCoord, yCoord
    	this.width=size*GridLock.SQUARE_SIZE; // 2 for cars, 3 for truck
    	this.height=GridLock.SQUARE_SIZE;
    	this.size=size;
       
        this.r = new Rectangle(width,height);
        if(dir==Direction.VERTICAL) {
          	 this.r = new Rectangle(height,width); //rotate sprite
        }
       // r.setStroke(Color.BLACK);
        r.setStrokeWidth(GridLock.SQUARE_SIZE * 0.03);
        r.setFill(Color.GREEN);
        r.setFill(new ImagePattern(image));
       // p.add(r);
        getChildren().addAll(r);

        
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        //STRATEGY PATTERN: ?? 
        setOnMouseDragged(e -> {
        	
        	if(direction==Direction.HORIZONTAL) {
        		
        			relocate(e.getSceneX() - mouseX + Xcoord,+ Ycoord);
        		
        	}else {
        		relocate( Xcoord, e.getSceneY() - mouseY + Ycoord);
        	}
        });
       
        
	}
    
 
/**
     * Getter method for the rectangle
     */
    public Rectangle getRectangle() {
    	return this.r;
    }
    
    
    public int getID() {
    	
    	return this.id;
    }
    
    /**
     * Updates the position of the Sprite object to the grid square ( @param x, @param y).
     * @postcondition: The Xcoord and Ycoord fields of this sprite object become x * GridLock.SQUARE_SIZE, y * GridLock.SQUARE_SIZE
     */
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
     * Getter method for the number of grid squares the sprite takes up.
     * @return size;
     */
    public int getSize() {
    	return size;
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
    /**
     * Getter method for the x coordinate of the last mouse click on the scene
     * @return
     */
    public double getMouseX() {
    	return mouseX;
    	
    }
    public double getMouseY() {
    	return mouseY;
    }

	
	
	

}
