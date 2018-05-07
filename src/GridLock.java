import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GridLock extends Application {
	
	    public static final int SQUARE_SIZE = 100;
	    public static final int WIDTH = 6;
	    public static final int HEIGHT = 6;

	    private Square[][] grid = new Square[WIDTH][HEIGHT];

	    private Group SquareGroup = new Group();
	    private Group spriteGroup = new Group();
	    
	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) {
	        Scene scene = new Scene(createGameBoard());
	        primaryStage.setTitle("Gridlock");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	    private Parent createGameBoard() {
	        Pane root = new Pane();
	        root.setPrefSize(WIDTH * SQUARE_SIZE, HEIGHT * SQUARE_SIZE);
	        root.getChildren().addAll(SquareGroup, spriteGroup);

	        for (int y = 0; y < HEIGHT; y++) {
	            for (int x = 0; x < WIDTH; x++) {
	            	Square Square;	
					Square = new Square(x, y, SQUARE_SIZE, SQUARE_SIZE); 
					grid[x][y] = Square;
					SquareGroup.getChildren().add(Square);
					
					Sprite sprite = null;
					Square.setSprite(sprite);
	                }
	            }

            Sprite s= makeSprite(Sprite.Direction.VERTICAL,1,1);
            //this is where we need a method for a grid object to set multiple squares
            // to one  sprite...
            

            spriteGroup.getChildren().add(s);

	        return root;
	    }
	    /**
	     * Calculates if moving the Sprite object @param sprite to the new x coordinate @param newX and y coordinate
	     * @param newY is valid. 
	     * @return true if the move is in the correct direction for the sprite and the new position is free 
	     * and on the grid. False otherwise
	     */
	    private boolean tryMove(Sprite sprite, int newX, int newY) {
	    	
	    	//Prevent the sprite going out of bounds, or moving into an already occupied square
	        if (grid[newX][newY].hasSprite() || newX <0 || newX>WIDTH-sprite.getWidthSquareSize() || newY<0 || newY >HEIGHT-sprite.getHeightSquareSize()) {
	            return false;
	        }if(sprite.getDirection()==Sprite.Direction.HORIZONTAL) {
	        	if(toGrid(sprite.getYcoord())!=newY) {
	        		System.out.println("new " + newY + "old " +toGrid(sprite.getYcoord()));
	        		return false;
	        	}
	        }else{
	        	if(toGrid(sprite.getXcoord())!=newX) {
	        	
	        		return false;
	        	}
	        }
	        return true;
	    }

    	/**
    	 * Convert pixel/position on the main panel to a grid square index
    	 * @param pixel is the coordinate of an object on the primary stage
    	 * @return
    	 */
	    private int toGrid(double pixel) {
	    	 return (int)(pixel + SQUARE_SIZE / 2) / SQUARE_SIZE; 
	     
	    }

	   
	    /**
	     * Create a sprite object and add event listeners for drag and drop
	     * @param dir
	     * @param x
	     * @param y
	     * @return
	     */
	    private Sprite makeSprite(Sprite.Direction dir, int x, int y) {
	        	Sprite s = new Sprite(dir, x, y);

	        	s.setOnMouseReleased(e -> {
		            int newX = toGrid(s.getLayoutX());
		            int newY = toGrid(s.getLayoutY());
	
		            boolean result;
	
		            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
		            	
		                result = false;
		            } else {
		                result = tryMove(s, newX, newY);
		            }
	
		            int xCoord = toGrid(s.getXcoord());
		            int yCoord = toGrid(s.getYcoord());
	
		            if(result==false) {   	
		                    s.stopMove();
		            }else {
	                    s.move(newX, newY); //issue is newX new Y are the wrong coords
	                    grid[xCoord][yCoord].setSprite(null);
	                    grid[newX][newY].setSprite(s);
		            }
	               
	        });

	        return s;
	    }

	   
		

}