import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.paint.Color;
/**
 * A class representing the Game Board Grid.
 *
 *
 */
public class Grid {
	//WIDTH is the size of the grid required 6
	//Height is the size of the grid required 6
	
	private Square grid[][] = new Square[GridLock.WIDTH][GridLock.HEIGHT];
	private ArrayList<Square> listOfSquares;
	
	public Grid() {
		listOfSquares = new ArrayList<Square>();
		//populating grid array with squares
		for(int y = 0; y < GridLock.HEIGHT; y++) {
	        	for(int x = 0; x < GridLock.WIDTH; x++) { //square coordinates go from (0,0) to (5,5)
	            	Square square;
	            		//Setting up a square which has index values x,y and size of square.
					square = new Square(x, y, GridLock.SQUARE_SIZE, GridLock.SQUARE_SIZE); 
					grid[x][y] = square;
					listOfSquares.add(square);
	    					
	    		}
    	    	}
        	
    }
	
	
	
	public ArrayList<Square> getListOfSquares(){
		return this.listOfSquares;
	}
	
	public Square[][] getGrid() {
		return this.grid;
	}
	
	public Square getSquareAtPosition(int x, int y) {
		return this.grid[x][y];
	}
	
	/**
	 * Reserves s.getSize() adjacent grid squares for the sprite @param s, starting from the grid
	 * square ( @param x, @param y).
	 * @precondition checkSpriteOnGrid(x,y)==true
	 * @postcondition: all squares underneath the sprite object have s.getID() as their spriteID field
	 */
	public void setSpriteOnGrid(Sprite s, int x, int y) {
	
		
		//set sprite id of squares taken up by the sprite to signal they are 
		//occupied by the sprite
		int i;
		int id=s.getID();
		if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
			//System.out.println("size of user car is " s.getSize());
			
			
			for( i=0; i<s.getSize(); i++) {
				//set square[x+i][y] to sprite id to signal that its occupied
				grid[x+i][y].setSpriteID(id);
			}
	
			
		}else { //vertical
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
		
		 if (newX<0 || newY <0 || newY>=GridLock.HEIGHT || newX >=GridLock.WIDTH) {
	        	return false;
	        }
		int id=s.getID();
		 
		if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
			
			//make sure we move in the right direction and we don't go over the edge of the board
			if(oldY!=newY || newX>GridLock.WIDTH-s.getSize()) return false;
			System.out.println("here,old X is " + oldX +"new X is " + newX);			
			int i=(oldX < newX )? oldX: newX;
			int j= (i==oldX)? newX: oldX;
			for(int x=i; x<=j; x++) {
				//if a square has a different id /occupied by different sprite we can't move our sprite
				//the full length of it.
				if(grid[x][oldY].getSpriteID()!=id && grid[x][oldY].getSpriteID()!=-1 ) return false;
				//make sure for the length of the car no collisions
				for(int k=0; k<s.getSize(); k++) {
					if(grid[x+k][oldY].getSpriteID()!=id && grid[x+k][oldY].getSpriteID()!=-1 ) return false;
				}
			}
			
		}else { 
		
			//make sure we move in the right direction and we don't go over the edge of the board
			if(oldX!=newX || newY > GridLock.HEIGHT - s.getSize()) return false; 
			int i=(oldY < newY )? oldY: newY;
			int j= (i==oldY)? newY: oldY;
			for(int y=i; y<= j; y++) {
				
				if(grid[oldX][y].getSpriteID()!=id  && grid[oldX][y].getSpriteID()!=-1) return false;
				for(int k=0; k<s.getSize(); k++) { //- or +? test
					if(grid[oldX][y+k].getSpriteID()!=id && grid[oldX][y+k].getSpriteID()!=-1 ) return false;
				}
				
			}
		}
		return true;
	}
	  

	
	/**
    	 * Convert pixel/position on the main panel to a grid square index
    	 * @param pixel is the coordinate of an object on the primary stage
    	 * @return the corresponding grid square coordinate the pixel is in.
    	 */
	    public double toGrid(double pixel) {
	    	 return (pixel + GridLock.SQUARE_SIZE / 2) / GridLock.SQUARE_SIZE; 
	     
	    }

    /***
     * Remove a sprite from the grid by resetting the spriteID's of the squares it used to cover.
     * @param s is the sprite to be removed.
     * @param x is the x coordinate of the first grid square containing s
     * @param y is the y coordinate of the first grid square containing s
     * @postcondition: All squares that previously had spriteID s.getID() now have spriteID ==-1 
     */
       
	public void removeSpriteOnGrid(Sprite s, int x, int y) {
		if(s==null) {
			return;
		}
	
		int i;
		int id=-1;
		if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
			
			for(i=0; i<s.getSize(); i++) {
				//set square[x+i][y] to sprite id -1 to signal that its gone
				grid[x+i][y].setSpriteID(id);
			}
			
		}else { //vertical
			for(i=0; i<s.getSize(); i++) {

				grid[x][y+i].setSpriteID(id);
			}
			
		
		}
		
		
	}


	/**
	 * Is it valid to set the sprite on the grid square with coordinates( @param x, @param y)
	 * @return
	 */
	public boolean checkSetSpriteOnGrid(int x, int y) {
		
		if(grid[x][y].hasSprite()) return false;
	
		return true;
	}
	
	
	/**
	 * return x coord of furthest i can go towards newX before i bump into another object.
	 * @param s
	 * @param oldX
	 * @param oldY
	 * @param newX
	 * @param newY
	 * @return
	 */
	
	//return the square square index thats the last place we can relocate to before 
	//bumping into something
	//different depending on if moving backwards or forwards
	
	public int furthestMoveXdirection(Sprite s, int oldX, int oldY,boolean forwards) {
		
		/*if(newX > GridLock.WIDTH-s.getSize()) {
			return GridLock.WIDTH-s.getSize();
		}
		if(newX <0) {
			return 0;
		}*/
		int id=s.getID();
		//System.out.println("here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		int x;
		//TWO CASES
		//moving forwards new x> old x, assume already handle out of bounds cases?
		if(forwards) {
			for(x=oldX; x<=GridLock.WIDTH-s.getSize(); x++) {
				//find where the front end of the sprite meets another sprite
				
				
				if(grid[x][oldY].getSpriteID()!=-1 &&grid[x][oldY].getSpriteID()!=id) {
					System.out.println("forward:collision detected at x coord "+ x);
					return (x-s.getSize()) >=0? x-s.getSize(): 0; 
				}
				for(int k=0; k<s.getSize(); k++) {
					if(grid[x+k][oldY].getSpriteID()!=id && grid[x+k][oldY].getSpriteID()!=-1 ) {
						System.out.println("forwad k loop: collision detected at x coord "+ (x+k));
						return ((x+k)-s.getSize()) >=0? ((x+k)-s.getSize()): 0;
					}
					
				}
			}
			
			return GridLock.WIDTH-s.getSize();
		}else {
		
		//moving backwards newx < old x
			//we are comparing the tail for collisions, but tail is really the first square of the sprite
			for(x=oldX; x>0; x--) {
				//find where the front end of the sprite meets another sprite
				if(grid[x][oldY].getSpriteID()!=-1 &&grid[x][oldY].getSpriteID()!=id) {
					System.out.println("collision detected at x coord "+ x);
					return (x+1); 
				}
				
				//check one ahead
				if(grid[x-1][oldY].getSpriteID()!=id && grid[x-1][oldY].getSpriteID()!=-1 ) {
					System.out.println("backward k loop: collision detected at x coord "+ (x-1));
					return (x);
				}
				
			}
			
		
			System.out.println("backwards, no collision detected returning 0");
			return 0;
		}
		
	}
			
			
	
		


	/**
	 * Compute the y coordinate that the sprite can move to before collision.
	 * @param sprite
	 * @param oldY
	 * @param upwards --- is the sprite moving up or down?
	 * @return
	 */
	public double furthestMoveYdirection(Sprite s, int oldX,int oldY, boolean upwards ) {
		int id=s.getID(); //id of our sprite
		int y;
		
		//moving up
		if(upwards) {
			for(y=oldY; y>=0; y--) { //going upwards, y coordinates decrease to 0
				//find where the front end of the sprite meets another sprite
				
				
				if(grid[oldX][y].getSpriteID()!=-1 && grid[oldX][y].getSpriteID()!=id) {
					System.out.println("upward detected at y coord "+ y);
					return y+1; 
				}
				
				
			}
			
			return 0; //no collisions so the furthest position for the top of the sprite is y=0
			
			
		}else {
		
			//moving downwards
			
			for(y=oldY; y<= GridLock.WIDTH- s.getSize(); y++) { //going downwards, y coordinates increase
				//find where the tail end of the sprite meets another sprite
				
				
				if(grid[oldX][y].getSpriteID()!=-1 &&grid[oldX][y].getSpriteID()!=id) {
					System.out.println("downard collision detected at y coord "+ y);
					return y-1; 
				}
				//check length of sprite after its first square.
				//prevent dragging part-way over another sprite.
			
				for(int k=0; k<s.getSize(); k++) { 
					if(grid[oldX][y+k].getSpriteID()!=id && grid[oldX][y+k].getSpriteID()!=-1 ) {
						return (y+k)-s.getSize() < 0? 0: (y+k)-s.getSize();
					}
				}
				
				
				
				
			}
			
			return  GridLock.WIDTH- s.getSize(); //maximum y  position for the top of the sprite 
			
		}

	}
		
	
		

	
}