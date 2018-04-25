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
        	for(int j = 0; j < 6; j++) {
        		Square square = new Square(j*100,i*100 , 100);
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
