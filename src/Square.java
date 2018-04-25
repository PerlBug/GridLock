import javafx.scene.shape.Rectangle;

public class Square extends Rectangle{
	private double squareSize;
	private boolean occupied;
	
	public Square(double x, double y, double size) {
		super(x, y,size, size);
		occupied = false; 
		squareSize = size;
	}
	
	public double getSquareSize(){
		return squareSize;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	
}
