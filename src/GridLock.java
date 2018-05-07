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

            Sprite s= makeSprite(Sprite.Direction.VERTICAL,1,3);
            grid.setSpriteOnGrid(s,1, 3);
            
            spriteGroup.getChildren().add(s); 
            
            Sprite horz= makeSprite(Sprite.Direction.HORIZONTAL,1,1);
            grid.setSpriteOnGrid(horz,1, 1);
            spriteGroup.getChildren().add(horz);
            
            Sprite s3= makeSprite(Sprite.Direction.HORIZONTAL,2,2);
            grid.setSpriteOnGrid(s3,2, 2);
            spriteGroup.getChildren().add(s3);
          
            if(grid.checkSetSpriteOnGrid(1,3)) {
            	Sprite s2= makeSprite(Sprite.Direction.VERTICAL,1,3);
            	grid.setSpriteOnGrid(s2,1, 3); 
            	spriteGroup.getChildren().add(s2);
            }
           
           
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
	   
	        if (newX<0 || newY <0 || newY>=6 || newX >=6) {
	        	return false;
	        }
	     	System.out.println("new grid squares id is " + grid.getSquareAtPosition(newX, newY).getSpriteID());
	     	
	     	/*if(grid.getSquareAtPosition(newX, newY).getSprite()!=null && sprite.checkShapeIntersection(sprite.getRect(), 
	     			grid.getSquareAtPosition(newX, newY).getSprite().getRect())) {
	     		return false;
	     	}*/
	     	
	     	//TODO: need to check the whole length of the object's id!!!!
	     	if( !(grid.checkMoveToGrid(sprite, toGrid(sprite.getXcoord()), toGrid(sprite.getYcoord()),newX, newY))) {
	        	System.out.println("here, occupeid");
	        	return false;
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
	                   // grid.getSquareAtPosition(xCoord,yCoord).setSprite(null);
	                   s.move(newX, newY); 
	                   grid.setSpriteOnGrid(s,newX, newY);
	                  //  grid.getSquareAtPosition(newX,newY).setSprite(s);
		            }
	               
	        });

	        return s;
	    }

	   
		

}