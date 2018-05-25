/**
 * Square.java written for COMP2511 18s1 Project - Group 3
 * 
 * @authors
 * 5162531 Diaz, Rebecca Avril: beccaD6 - <becca.diaz6@gmail.com>
 * 3461044 Kala, Shilpa: sk2552 - <simple.snowflake@gmail.com>
 * 3463869 Sun, Elliott Yongrui: umeb0shi - <elly.here@gmail.com>
 * 5157086 Galoyan, Mkrtich: PerlBug - <galoyanmko@gmail.com>
 * 5060239 Chen, Leo Jia Jian: leochen15 - <leochen1512@gmail.com>
 * 
 * 
 * Class representing grid squares.
 * Each grid Square can contain part of a sprite object.
 * 
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    public Square( int x, int y, double height, double width) { 
        setWidth(width);
        setHeight(height);
        this.x=x;
        this.y=y;
        this.spriteID=-1; //default value for a square with no sprite 
        relocate(x * width, y * height);

        setFill(Color.TRANSPARENT);
        setStroke(Color.TRANSPARENT);
    }
    
    /**
     * Check if a square is occupied by a sprite. 
     * @return true if the spriteID for this square is not -1.
     */
    public boolean hasSprite() {
        return spriteID >=0 ;
    	
    }

    /**
     * Signal a square is occupied by a particular sprite.
     * @param i is the id of the sprite occupying the grid square.
     * @postCondition: this.spriteID=i;
     */
	public void setSpriteID(int i) {
		this.spriteID=i;
		
	}
	
	/**
	 * Getter method for the id of the sprite occupying the square
	 * @return spriteID
	 */
	public int getSpriteID() {
		return spriteID;
	}
   

}
