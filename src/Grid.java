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
	
	
	public void setSpriteOnGrid(Sprite s, int x, int y) {
		/*if(grid[x][y].getSprite()!=null) { //if this square already has a sprite we ignore the request
			//and remove it from the grid displ
			//removeSpriteOnGrid(s,x,y);
			
			return; 
		}*/
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
		if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
			
			for(i=0; i<s.getWidthSquareSize(); i++) {
				//set square[x+i][y] to sprite id -1 to signal that its gone
				grid[x+i][y].setSpriteID(-1);
			}
			
		}else { //vertical
			for(i=0; i<s.getHeightSquareSize(); i++) {

				grid[x][y+i].setSpriteID(-1);
			}
			
		
		}
		
		
	}


	
	
	
}