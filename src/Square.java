import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * Class representing grid squares.
 * Each grid Square can contain part of a sprite object.
 * 
 *
 */
public class Square extends Rectangle{

    private int spriteID; //Id of the sprite occupying the square.
    private int x;
    private int y;
    
    /**
     * Create a grid square.
     * @param x is the x coordinate of the square
     * @param y is the y coordinate of the square
     * @param height is the height of the square
     * @param width is the width of the square
     */
    public Square( int x, int y, int height, int width) { 
        setWidth(width);
        setHeight(height);
        this.x=x;
        this.y=y;
        this.spriteID=-1; //default value
        relocate(x * width, y * height);

        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }
    /**
     * Check if a square is occupied by a sprite. 
     * @return true if the spriteID for this square is not -1.
     */
    public boolean hasSprite() {
        return spriteID >=0 ;
    	
    }


	public void setSpriteID(int i) {
		this.spriteID=i;
		
	}

	public int getSpriteID() {
		return spriteID;
	}
   

}
