import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Grid {
	private Square grid[][] = new Square[6][6];
	private ArrayList<Square> listOfSquares;
	
	public Grid() {
		listOfSquares = new ArrayList<Square>();
		//populating grid array with squares
		for(int i = 0; i < 6; i++) {
        	for(int j = 0; j < 6; j++) { //square coordinates go from (0,0) to (500,500)
        		Square square = new Square(i,j,i*GridLock.SQUARE_SIZE, j*GridLock.SQUARE_SIZE);
                square.setFill(Color.TRANSPARENT);
                square.setStroke(Color.BLACK);      
                grid[i][j] = square;
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