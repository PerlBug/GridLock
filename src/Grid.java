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
			
			for(i=0; i<s.getWidthSquareSize(); i++) {
				//set square[x+i][y] to sprite id to signal that its occupied
				grid[x+i][y].setSpriteID(id);
			}
			
		}else { //vertical
			for(i=0; i<s.getHeightSquareSize(); i++) {

				grid[x][y+i].setSpriteID(id);
			}
		}
		
	}

	//check if we can move a sprite s to starting grid square (x,y,) and have no
	//collisions for the length of the sprite
	//remember if vertical block at (0,0) start pos is (0,0) end pos is (0,2)
	public boolean checkMoveToGrid(Sprite s, int x, int y) {
		
		//if the starting positions mean we go off the grid , its impossible regardless of collisions
		if( x>GridLock.WIDTH-s.getWidthSquareSize() || y > GridLock.HEIGHT - s.getHeightSquareSize()){
			return false;
		}
		int i;
		int id=s.getID();
		if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
			
			for(i=0; i<s.getWidthSquareSize(); i++) {
				//if the square has a different id /occupied by different sprite we cant move our sprite
				//the full length.
				
				if(grid[x+i][y].getSpriteID()!=id && grid[x+i][y].getSpriteID()!=-1 ) return false;
			}
			
		}else { //vertical
			for(i=0; i<s.getHeightSquareSize(); i++) {
				if(grid[x][y+i].getSpriteID()!=id  && grid[x][y+i].getSpriteID()!=-1) return false;
				
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
			
			for(i=0; i<s.getWidthSquareSize(); i++) {
				//set square[x+i][y] to sprite id -1 to signal that its gone
				grid[x+i][y].setSpriteID(id);
			}
			
		}else { //vertical
			for(i=0; i<s.getHeightSquareSize(); i++) {

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