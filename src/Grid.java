import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Grid {
	
	
	private Square grid[][] = new Square[GridLock.WIDTH][GridLock.HEIGHT];
	private ArrayList<Square> listOfSquares;
	
	public Grid() {
		listOfSquares = new ArrayList<Square>();
		//populating grid array with squares
		for(int y = 0; y < GridLock.HEIGHT; y++) {
        	for(int x = 0; x < GridLock.WIDTH; x++) { //square coordinates go from (0,0) to (5,5)

    	    
    	            	Square square;	
    					square = new Square(x, y, GridLock.SQUARE_SIZE, GridLock.SQUARE_SIZE); 
    					grid[x][y] = square;
    					listOfSquares.add(square);
    					
        		}
    	            
        	}
        	
        }
	
	
	
	public ArrayList<Square> getListOfSquares(){
		return listOfSquares;
	}
	
	public Square[][] getGrid() {
		return grid;
	}
	
	public Square getSquareAtPosition(int x, int y) {
		return grid[x][y];
	}
	
	/**
	 * @precondition checkSpriteOnGrid(x,y, )==true
	 * @param s
	 * @param x
	 * @param y
	 */
	public void setSpriteOnGrid(Sprite s, int x, int y) {
	
		grid[x][y].setSprite(s); //place actual sprite on first grid square
		//set sprite id of the other squares taken up by the sprite to signal they are also
		//occupied by the sprite
		int i;
		int id=s.getID();
		if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
			
			for(i=0; i<s.getSize(); i++) {
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
	 * if there is any other sprite on the path from old (x,y) position to new (x,y) position
	 * return false
	 * @param s
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean checkMoveToGrid(Sprite s, int oldX, int oldY, int newX, int newY) {
		
		
		int id=s.getID();
		 
		if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
			//make sure we move in the right direction and we don't go over the edge of the board
			if(oldY!=newY || newX>GridLock.WIDTH-s.getSize()) return false;
			
			int i=(oldX < newX )? oldX: newX;
			int j= (i==oldX)? newX: oldX;
			for(int x=i; x<=j; x++) {
				//if a square has a different id /occupied by different sprite we can't move our sprite
				//the full length of it.
				
				if(grid[x][oldY].getSpriteID()!=id && grid[x][oldY].getSpriteID()!=-1 ) return false;
			}
			
		}else { 
		
			//make sure we move in the right direction and we don't go over the edge of the board
			if(oldX!=newX || newY > GridLock.HEIGHT - s.getSize()) return false; 
			int i=(oldY < newY )? oldY: newY;
			int j= (i==oldY)? newY: oldY;
			for(int y=i; y<= j; y++) {
				
				if(grid[oldX][y].getSpriteID()!=id  && grid[oldX][y].getSpriteID()!=-1) return false;
				
			}
		}
		return true;
	}
	  
   
    /***
     * 
     * @param s
     * @param x
     * @param y
     */
       
	public void removeSpriteOnGrid(Sprite s, int x, int y) {
		if(s==null) {
			return;
		}
		// remove sprite from original square
		grid[x][y].setSprite(null);
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
	 * Is it valid to set the sprite on the grid square with coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean checkSetSpriteOnGrid(int x, int y) {
		
		if(grid[x][y].hasSprite()) return false;
	
		return true;
	}
	
	
	
}