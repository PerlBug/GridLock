import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Grid {
	private Square grid[][] = new Square[6][6];
	private ArrayList<Square> listOfSquares;
	
	public Grid() {
		listOfSquares = new ArrayList<Square>();
		//populating grid array with squares
		for(int y = 0; y < 6; y++) {
        	for(int x = 0; x < 6; x++) { //square coordinates go from (0,0) to (500,500)

    	    
    	            	Square square;	
    					square = new Square(x, y, GridLock.SQUARE_SIZE, GridLock.SQUARE_SIZE); 
    					grid[x][y] = square;
    					//SquareGroup.getChildren().add(Square);
    					
    					Sprite sprite = null;
    					square.setSprite(sprite);
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


	
	
	
}