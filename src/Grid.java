/**
 * Grid.java written for COMP2511 18s1 Project - Group 3
 * 
 * 
 * @authors
 * 5162531 Diaz, Rebecca Avril: beccaD6 - <becca.diaz6@gmail.com>
 * 3461044 Kala, Shilpa: sk2552 - <simple.snowflake@gmail.com>
 * 3463869 Sun, Elliott Yongrui: umeb0shi - <elly.here@gmail.com>
 * 5157086 Galoyan, Mkrtich: PerlBug - <galoyanmko@gmail.com>
 * 5060239 Chen, Leo Jia Jian: leochen15 - <leochen1512@gmail.com>
 * 
 * A class representing the Game board grid
 * 
 */

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Grid {
	
	private Square grid[][] = new Square[GridLock.WIDTH][GridLock.HEIGHT];
	private ArrayList<Square> listOfSquares;
	private int moveCtr; //number of valid moves sprites have made on the grid.
	
	/**
	 * Constructor for a new Grid.
	 */
	public Grid() {
		this.moveCtr = 0; 
		listOfSquares = new ArrayList<Square>();
		//populating grid array with squares
		for(int y = 0; y < GridLock.HEIGHT; y++) {
	        	for(int x = 0; x < GridLock.WIDTH; x++) { //square coordinates go from (0,0) to (5,5)
	            	Square square;
	            	//Setting up a square which has index values x,y and the size of square.
					square = new Square(x, y, GridLock.SQUARE_SIZE, GridLock.SQUARE_SIZE); 
					grid[x][y] = square;
					listOfSquares.add(square);
	    					
	    		}
	    	}
        	
    }
	
	
	/**
	 * Getter method for the squares in the grid.
	 * @return listOfSquares
	 */
	public ArrayList<Square> getListOfSquares(){
		return this.listOfSquares;
	}
	
	/**
	 *  Getter method for the grid array itself.
	 * @return grid
	 */
	public Square[][] getGrid() {
		return this.grid;
	}
	
	/**
	 * Getter method for the square object at the index coordinates 
	 * (@param x, @param y)
	 * @return grid[x][y]
	 */
	public Square getSquareAtPosition(int x, int y) {
		return this.grid[x][y];
	}
	
	
	
	/**
	 * Reserves s.getSize() adjacent grid squares for the sprite @param s, starting from the grid
	 * square ( @param x, @param y).
	 * @precondition checkSpriteOnGrid(x,y)==true, s!=null
	 * @postcondition: all squares occupied by  the sprite object have s.getID() as their spriteID field
	 */
	public void setSpriteOnGrid(Sprite s, int x, int y) {
	
		int i;
		int id=s.getID();
		
		if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
			
			for( i=0; i<s.getSize(); i++) {
				//set square[x+i][y] to sprite id to signal that its occupied by sprite s
				grid[x+i][y].setSpriteID(id);
			}
	
			
		}else { 
			for(i=0; i<s.getSize(); i++) {

				grid[x][y+i].setSpriteID(id);
			}
		}
		
	}

	
	/**
	 * Determines if it is possible to move the starting square of the Sprite @param s 
	 *  from ( @param oldX, @param oldY) to ( @param x, @param y).
	 * 
	 * @return false if there is any other sprite on the path from the old (x,y) position to new (x,y) position or
	 * there is not enough space to move the sprite or the direction of movement is incorrect.
	 * Else true.
	 */
	public boolean checkMoveToGrid(Sprite s, int oldX, int oldY, int newX, int newY) {
		
		//check the new position is not out of bounds
		if (newX<0 || newY <0 || newY>=GridLock.HEIGHT || newX >=GridLock.WIDTH) {
        	return false;
        }
		int id=s.getID();
		 
		if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
			
			//make sure we move in the right direction and we don't go over the edge of the board
			if(oldY!=newY || newX>GridLock.WIDTH-s.getSize()) return false;
			//System.out.println("here,old X is " + oldX +"new X is " + newX);			
			int i=(oldX < newX )? oldX: newX;
			int j= (i==oldX)? newX: oldX;
			for(int x=i; x<=j; x++) {
				//if a square has a different id /occupied by different sprite then we can't move our sprite there				
				if(grid[x][oldY].getSpriteID()!=id && grid[x][oldY].getSpriteID()!=-1 ) return false;
				//make sure for the length of the sprite starting from grid[x][oldY] there are no collisions
				for(int k=0; k<s.getSize(); k++) {
					if(grid[x+k][oldY].getSpriteID()!=id && grid[x+k][oldY].getSpriteID()!=-1 ) return false;
				}
			}
			//If we are actually moving by one or more grid positions, increment moveCtr
			if(oldX!=newX) {
				moveCtr++;
			}
			
		}else { 
		
			//make sure we move in the right direction and we don't go over the edge of the board
			if(oldX!=newX || newY > GridLock.HEIGHT - s.getSize()) return false; 
			int i=(oldY < newY )? oldY: newY;
			int j= (i==oldY)? newY: oldY;
			for(int y=i; y<= j; y++) {
				
				if(grid[oldX][y].getSpriteID()!=id  && grid[oldX][y].getSpriteID()!=-1) return false;
				for(int k=0; k<s.getSize(); k++) {
					//make sure for the length of the sprite starting from grid[oldX][y] there are no collisions
					if(grid[oldX][y+k].getSpriteID()!=id && grid[oldX][y+k].getSpriteID()!=-1 ) return false;
				}
				
			}
			if(oldY!=newY) {
				moveCtr++;
			}
			
			
		}
		GridLock.counter.setCount(moveCtr);

		return true;
	}
	  

	
	/**
	 * Convert the pixel/position on the main panel to a grid square index
	 * @param pixel is the coordinate of an object on the primary stage
	 * @return the corresponding grid square coordinate the pixel is in.
	 */
	public double toGrid(double pixel) {
		 return (pixel + GridLock.SQUARE_SIZE / 2) / GridLock.SQUARE_SIZE; 
	 
	}

    /***
     * Remove a sprite from the grid by resetting the spriteID's of the squares it used to occupy.
     * @param s is the sprite to be removed.
     * @param x is the x coordinate of the first grid square containing s
     * @param y is the y coordinate of the first grid square containing s
     * @precondition: s!=null
     * @postcondition: All squares that previously had spriteID s.getID() now have spriteID ==-1 
     */
       
	public void removeSpriteOnGrid(Sprite s, int x, int y) {
		int i;
		int id=-1;
		if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
			
			for(i=0; i<s.getSize(); i++) {
				//set squares occupied by the length of the sprite,  starting at grid[x][y] to sprite id -1 to
				//signal they are now free
				grid[x+i][y].setSpriteID(id);
			}
			
		}else { 
			for(i=0; i<s.getSize(); i++) {

				grid[x][y+i].setSpriteID(id);
			}
			
		
		}
		
		
	}


	/**
	 * Checks if it is valid to set the sprite on the grid square with coordinates( @param x, @param y)
	 * @precondition: x,y are >=0 and valid indexes for the grid array
	 * @return true if no sprite is at those coordinates already, false else.
	 */
	public boolean checkSetSpriteOnGrid(int x, int y) {
		
		if(grid[x][y].hasSprite()) return false;
	
		return true;
	}
	
	
	/**
	 * return the X coordinate of the farthest the sprite @param s can go  (forwards or backwards)
	 *  before it touches an obstacle (a boundry or another sprite).
	 * @param oldX is the starting X position of the sprite before the drag event
	 * @param oldY is the starting Y position of the sprite before the drag event
	 * @param forwards: is the sprite moving to the right(forwards) or left/backwards?
	 * @precondition: s!=null
	 * @return return the horizontal grid square index that is the last square we can relocate the start of the sprite 
	 *  to before collision
	**/
	public int furthestMoveXdirection(Sprite s, int oldX, int oldY,boolean forwards) {
		
		int id=s.getID(); //id of the sprite that is moving
		int x;
		if(forwards) {
			//iterate from the sprites current position to the last possible X position it can take, looking for collisions.
			for(x=oldX; x<=GridLock.WIDTH-s.getSize(); x++) {
				//find where the front end of the sprite meets another sprite		
				if(grid[x][oldY].getSpriteID()!=-1 &&grid[x][oldY].getSpriteID()!=id) {
					//return the Position for the first leftmost square of the sprite
					return (x-s.getSize()) >=0? x-s.getSize(): 0; 
				}
				//check for the length of the sprite for a collision 
				for(int k=0; k<s.getSize(); k++) {
					if(grid[x+k][oldY].getSpriteID()!=id && grid[x+k][oldY].getSpriteID()!=-1 ) {
						return ((x+k)-s.getSize()) >=0? ((x+k)-s.getSize()): 0;
					}
					
				}
			}
			//if there are no obstacles on the sprites row moving forward, the sprite can go all the way to the righthand
			//boundary of the grid.
			return GridLock.WIDTH-s.getSize();
		}else {
			//going backwards
			for(x=oldX; x>0; x--) {
				//find where the front end of the sprite meets another sprite
				if(grid[x][oldY].getSpriteID()!=-1 &&grid[x][oldY].getSpriteID()!=id) {
					return (x+1); 
				}
				
				//check one square ahead
				if(grid[x-1][oldY].getSpriteID()!=id && grid[x-1][oldY].getSpriteID()!=-1 ) {
					return (x);
				}
				
			}
			
		
			//if there are no obstacles on the sprites row moving backwards, the sprite can go all the way to the lefthand
			//boundary of the grid (which is x position 0)
			return 0;
		}
		
	}
			


	/**
	 * Compute the farthest y coordinate that the sprite can move to before collision.
	 * @param s is the sprite to be moved.
	 * @param oldY is its current Y coordinate
	 * @param oldX is its current X coordinate
	 * @param upwards - is the sprite moving up or down?
	 * @precondition: s!=null
	 * @return the vertical grid square coordinate of the farthest position the sprite can relocate to without collision.
	 */
	public double furthestMoveYdirection(Sprite s, int oldX,int oldY, boolean upwards ) {
		int id=s.getID(); //id of our sprite
		int y;
		
		//moving up
		if(upwards) {
			//going upwards, y coordinates decrease to 0
			for(y=oldY; y>=0; y--) { 
				//find where the front end of the sprite meets another sprite		
				if(grid[oldX][y].getSpriteID()!=-1 && grid[oldX][y].getSpriteID()!=id) {
					//System.out.println("upward detected at y coord "+ y);
					return y+1; 
				}
				
				
			}
			
			return 0; //no collisions detected so the farthest y-coordinate position for the top of the sprite is y=0
			
			
		}else {
		
			//going downwards, y coordinates increase		
			for(y=oldY; y<= GridLock.WIDTH- s.getSize(); y++) { 
				//find where the tail end of the sprite meets another sprite
								
				if(grid[oldX][y].getSpriteID()!=-1 &&grid[oldX][y].getSpriteID()!=id) {
					//System.out.println("downard collision detected at y coord "+ y);
					return y-1; 
				}
				//check length of sprite after its first square
				//to prevent dragging part-way over another sprite.
			
				for(int k=0; k<s.getSize(); k++) { 
					if(grid[oldX][y+k].getSpriteID()!=id && grid[oldX][y+k].getSpriteID()!=-1 ) {
						return (y+k)-s.getSize() < 0? 0: (y+k)-s.getSize();
					}
				}
	
				
			}
			
			
			//maximum y  position for the top/first square of the sprite going downwards is when it's tail touches the 
			//bottom of the grid.
			return  GridLock.WIDTH- s.getSize(); 
			
		}

	}

	/**
	 * Resets the moveCtr to 0, for when a game is reset.
	 */
	public void resetMoveCtr() {
		
		 moveCtr=0;
	}
		

	/**
	 * Getter method for number of moves that Sprites have made on the grid.
	 * @return moveCtr.
	 */
	public int getMovectr() {
		
		return moveCtr;
	}
		
	
		

	
}