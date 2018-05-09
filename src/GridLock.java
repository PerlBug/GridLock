import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main class for the GridLock application
 * 
 *
 */
public class GridLock extends Application {
	
	    public static final int SQUARE_SIZE = 100;
	    public static final int WIDTH = 6;
	    public static final int HEIGHT = 6;
	    public static final int CAR_SIZE=2;
	    public static final int TRUCK_SIZE=3;
	    public int moveCounter=0;
	
	    private Grid grid;
	    private Group SquareGroup = new Group();
	    private Group spriteGroup = new Group();
	    
	    Scene scene1, scene;
	    
	    
	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) {
	    	moveCounter=0; //initialize move counter
	        scene = new Scene(createGameBoard());
	        scene1 = new Scene(startMenu(primaryStage));
	        primaryStage.setTitle("Gridlock");
	        primaryStage.setScene(scene1);
	        primaryStage.show();
	    }

	    
	    public GridPane startMenu(Stage window) {

		     final Image titleScreen = new Image( "Title Page.png" ); //title screen image
		     final Image playButton = new Image("Play.png", 150, 100, false, false); //the play button image
		     final Image scoreButton = new Image("Score.png", 150, 100, false, false); //the score button image
		     
		     final ImageView flashScreen_node = new ImageView();
		     flashScreen_node.setImage(titleScreen); //set the image of the title screen
		     flashScreen_node.setPreserveRatio(true);
		  
		     final Button play_button  = new Button();
		     final ImageView play_button_node = new ImageView(); 
		      
		     final Button score_button = new Button();
		     final ImageView score_button_node = new ImageView(); 
		     
		     play_button_node.setImage(playButton); //set the image of the play button
		     score_button_node.setImage(scoreButton); //set the image of the score button
		      
		     play_button.setGraphic(play_button_node);
		     play_button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))); //this is to make the button background transparent
		     play_button.setScaleShape(true);
		     
		     score_button.setGraphic(score_button_node);
		     score_button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		     play_button.setMaxWidth(Double.MAX_VALUE); //Ensures that both buttons are of the same size
		     score_button.setMaxWidth(Double.MAX_VALUE);
		     
		     play_button.setOnAction(e -> window.setScene(scene));
		     score_button.setOnAction(e -> window.setScene(scene));
		     /*
		      * create the container of those buttons in a horizontal box
		      */
		     final HBox buttonContainer = new HBox(1);
		     buttonContainer.setAlignment(Pos.TOP_CENTER);
		     Insets buttonContainerPadding = new Insets(400, 1, 1, 1); //Distance from the top center down
		     buttonContainer.setPadding(buttonContainerPadding);
		     buttonContainer.getChildren().addAll(play_button,score_button);
		
		     GridPane root = new GridPane();
		      
		     root.getChildren().addAll(flashScreen_node, buttonContainer); //add the title screen and button container to the stackpane
		     
		     return root;
		}
	    private Parent createGameBoard() {
	        Pane root = new Pane();
	        root.setPrefSize(WIDTH * SQUARE_SIZE, HEIGHT * SQUARE_SIZE);
	        root.getChildren().addAll(SquareGroup, spriteGroup);
	        grid=new Grid();
	        SquareGroup.getChildren().addAll(grid.getListOfSquares());

	        //NOTE: vertical sprites cannot start past (X,3) if a truck or (X,4) if a car
	        //And horizontal sprites cannot start past (3,Y) if truck or (4,Y) if a car
	        //Also no collision detection when creating a sprite, only when moving sprites about
	        //so make sure you create valid /no overlap starting positions for the sprites.
            Sprite s= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/playercar.png");
 
            
            spriteGroup.getChildren().add(s); 
            
            Sprite horz= makeSprite(Sprite.Direction.HORIZONTAL,1,1,CAR_SIZE,"file:sprites/playercar.png");
        
            spriteGroup.getChildren().add(horz);
            
            Sprite s3= makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/playercar.png");

            spriteGroup.getChildren().add(s3);
           // Sprite fakeUser=makeSprite(Sprite.Direction.HORIZONTAL,1,2,CAR_SIZE, "file:sprites/playercar.png");
            //spriteGroup.getChildren().add(fakeUser);
            UserCar redCar = makeUserCar(Sprite.Direction.HORIZONTAL,1,2,CAR_SIZE, "file:sprites/redcar.png");
            spriteGroup.getChildren().add((Sprite)redCar);
            
            if(grid.checkSetSpriteOnGrid(1,3)) {
            	Sprite s2= makeSprite(Sprite.Direction.VERTICAL,1,3,TRUCK_SIZE,"file:sprites/playercar.png");
            	spriteGroup.getChildren().add(s2);
            }
           
           
	        return root;
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
	    private Sprite makeSprite(Sprite.Direction dir, int x, int y,int size, String imageURL) {
	        	Sprite s = new Sprite(dir, x, y,size, imageURL);
	        	 grid.setSpriteOnGrid(s,x, y);

	        	s.setOnMouseReleased(e -> {
		            int newX = toGrid(s.getLayoutX());
		            int newY = toGrid(s.getLayoutY());
		           // System.out.println("new x is " + newX + "new y is "+newY);

		            int xCoord = toGrid(s.getXcoord());
		            int yCoord = toGrid(s.getYcoord());
		            boolean result;
	
		            result = grid.checkMoveToGrid(s,xCoord,yCoord, newX, newY);
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
	    
	    private UserCar makeUserCar(Sprite.Direction dir, int x, int y,int size, String url) {
        	 UserCar s = new UserCar(dir, x, y, url);
        	 grid.setSpriteOnGrid(s,x, y);

        	s.setOnMouseReleased(e -> {
	            int newX = toGrid(s.getLayoutX());
	            int newY = toGrid(s.getLayoutY());
	           // System.out.println("new x is " + newX + "new y is "+newY);

	            int xCoord = toGrid(s.getXcoord());
	            int yCoord = toGrid(s.getYcoord());
	            boolean result;

	            result = grid.checkMoveToGrid(s,xCoord,yCoord, newX, newY);
	            


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