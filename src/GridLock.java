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
	    public static final int CAR_SIZE=2;
	    public static final int TRUCK_SIZE=3;

	
	    private Grid grid;
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
	        grid=new Grid();
	        SquareGroup.getChildren().addAll(grid.getListOfSquares());

            Sprite s= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE);
            grid.setSpriteOnGrid(s,1, 3);
            
            spriteGroup.getChildren().add(s); 
            
            Sprite horz= makeSprite(Sprite.Direction.HORIZONTAL,1,1,CAR_SIZE);
        
            spriteGroup.getChildren().add(horz);
            
            Sprite s3= makeSprite(Sprite.Direction.HORIZONTAL,2,2,TRUCK_SIZE);
            spriteGroup.getChildren().add(s3);
          
            if(grid.checkSetSpriteOnGrid(1,3)) {
            	Sprite s2= makeSprite(Sprite.Direction.VERTICAL,1,3,TRUCK_SIZE);
            	spriteGroup.getChildren().add(s2);
            }
           
           
	        return root;
	    }
	    /**
	     * Determines if moving the Sprite object @param sprite to the new x coordinate @param newX and y coordinate
	     * @param newY is valid. 
	     * @return true if the move is in the correct direction for the sprite and the new position is free 
	     * and on the grid. False otherwise
	     */
	    private boolean tryMove(Sprite sprite, int newX, int newY) {
	    	
	    	//Prevent the sprite going out of bounds
	   
	        if (newX<0 || newY <0 || newY>=6 || newX >=6) {
	        	return false;
	        }
	  //   	System.out.println("new grid squares id is " + grid.getSquareAtPosition(newX, newY).getSpriteID());

	        //make sure there are no other sprites in the move path
	     	if( !(grid.checkMoveToGrid(sprite, toGrid(sprite.getXcoord()), toGrid(sprite.getYcoord()),newX, newY))) {
	        	
	        	return false;
	        }
	        return true;
	    }

    	/**
    	 * Convert pixel/position on the main panel to a grid square index
    	 * @param pixel is the coordinate of an object on the primary stage
    	 * @return the corresponding grid square coordinate the pixel is in.
    	 */
	    private int toGrid(double pixel) {
	    	 return (int)(pixel + SQUARE_SIZE / 2) / SQUARE_SIZE; 
	     
	    }

	   
	   /**
	    * Create a new sprite object with event listeners for drag and drop.
	    * @param dir is the direction the sprite can move in.
	    * @param x is the x-coordinate of the first square containing the sprite.
	    * @param y is the y-coordinate of the first square containing the sprite.
	    * @param size is the length of the sprite (2 for cars, 3 for trucks)
	    * @return a new Sprite object with the above properties.
	    * 
	    * @Postcondition: A new sprite object is created and placed on the game board at position (x,y).
	    */
	    private Sprite makeSprite(Sprite.Direction dir, int x, int y,int size) {
	        	Sprite s = new Sprite(dir, x, y,size);
	        	 grid.setSpriteOnGrid(s,x, y);

	        	s.setOnMouseReleased(e -> {
		            int newX = toGrid(s.getLayoutX());
		            int newY = toGrid(s.getLayoutY());
		           // System.out.println("new x is " + newX + "new y is "+newY);
	
		            boolean result;
	
		            result = tryMove(s, newX, newY);
		            
	
		            int xCoord = toGrid(s.getXcoord());
		            int yCoord = toGrid(s.getYcoord());
	
		            if(result==false) {   	
		                    s.stopMove();
		            }else {
	                   grid.removeSpriteOnGrid(s, xCoord, yCoord); 
	                   s.move(newX, newY); 
	                   grid.setSpriteOnGrid(s,newX, newY);
	                  
		            }
	               
	        });

	        return s;
	    }

}