//for sprint review
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Main class for the GridLock application
 * 
 *
 */
public class GridLock extends Application {
		
	 	static Rectangle2D primaryStagebounds = Screen.getPrimary().getVisualBounds();
	 	public static final double CANVAS_HEIGHT = primaryStagebounds.getHeight()*0.8;
	 	public static final double CANVAS_WIDTH = CANVAS_HEIGHT * 650/800; //Sets the proportion of the game to satisfy all screen sizes
	    public static final double SQUARE_SIZE = (double)CANVAS_HEIGHT/8; //Each square should be equivalent 100/800px height
	    public static final int WIDTH = 6;
	    public static final int HEIGHT = 6;
	    public static final int CAR_SIZE=2;
	    public static final int TRUCK_SIZE=3;
	    private int moveCtr; //number of successful drags and drops during the duration of the game
	    private Grid grid;
	    private MenuBoard gameMenu;
	    private Group squareGroup = new Group(); //Used within Create Game Board
	    private Group spriteGroup = new Group(); // Used within create game board
	    
	    /*
	     * Declares Difficulty: Starts at Medium Difficulty.
	     */
	    private static String Difficulty = "Medium";






	    public static Counter counter;
	    public static TimerPane clock; 
	    
	    
	    Scene scene1, scene, scene2;
	    Timer t;
	    
	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) {
	    	t = new Timer();	
	    	moveCtr=0;
	        scene = new Scene(createGameBoard(primaryStage), CANVAS_HEIGHT, CANVAS_WIDTH);
	        scene1 = new Scene(startMenu(primaryStage), CANVAS_HEIGHT, CANVAS_WIDTH);
	        //scene2 = new Scene(exitScreen(primaryStage), CANVAS_HEIGHT, CANVAS_WIDTH);
	        
	        primaryStage.setTitle("Gridlock");
	        primaryStage.setScene(scene1);
	        
	        /*
	         * Sets the starting position of the window to being the top left
	         */
	        	       
	        primaryStage.setX(primaryStagebounds.getMinX());
	        primaryStage.setY(primaryStagebounds.getMinY());
	        
	        
	        /*
	         * Sizes the Canvas to be correct height and creates max height so cannot be expanded
	         * beyond
	         */
	        double left_offset = (CANVAS_WIDTH - (WIDTH*SQUARE_SIZE))/2;
	        primaryStage.setHeight(CANVAS_HEIGHT+left_offset);
	        primaryStage.setWidth(CANVAS_WIDTH);
	        primaryStage.setMaxHeight(CANVAS_HEIGHT+left_offset);
	        primaryStage.setMaxWidth(CANVAS_WIDTH);
	        final boolean resizable = primaryStage.isResizable();
	        primaryStage.setResizable(!resizable);
	        primaryStage.setResizable(resizable);
	        
	        
	        primaryStage.show();
	        
	    }

	    /**
	     * Returns the pane for the start menu. Attaches the image to the game Menu (VBox)
	     * @param window primaryStage
	     * @return
	     */
	    public Pane startMenu(Stage window) {

	    		Pane root = new Pane();
	        
	        final Image titleScreen = new Image( "Title Page.png", CANVAS_WIDTH, CANVAS_HEIGHT, false, false);
	        final ImageView menuScreen_node = new ImageView();
		    menuScreen_node.setImage(titleScreen); //set the image of the title screen
		    menuScreen_node.setPreserveRatio(true);
	        
		    gameMenu = new MenuBoard(window, this);
		    
		    root.getChildren().addAll(menuScreen_node, gameMenu);
		     
		     return root;
		}
	    /**
	     * Returns the GameBoard Scene
	     * @return
	     */
	    public Scene getGame() {
	    		return this.scene;
	    }
	    
	    private GridPane exitScreen(Stage window, int seconds, int minutes, int c) {

	    	 final Image titleScreen = new Image( "file:src/exitscreen.png", CANVAS_WIDTH, CANVAS_HEIGHT, false, false); //title screen image
		     final Image replayButton = new Image("file:src/replay.png", 150, 100, false, false); //the play button image
		     final Image homeButton = new Image("file:src/home-button-round-blue.png", 150, 100, false, false); //the score button image		    


	     final ImageView flashScreen_node = new ImageView();
		     flashScreen_node.setImage(titleScreen); //set the image of the title screen
		     flashScreen_node.setPreserveRatio(true);
		     
		     clock = new TimerPane("CounterImg.png");
			 clock.setTranslateX(40);
			 clock.setTranslateY(75);
			 clock.printLabel(seconds, minutes);
			 Counter counter2 = new Counter("CounterImg.png");
			 counter2.setTranslateX(-80);
			 counter2.setTranslateY(75);
			 counter2.setCount(c);
			 
		     final Button play_button  = new Button();
		     final ImageView play_button_node = new ImageView(); 
		      
		     final Button score_button = new Button();
		     final ImageView score_button_node = new ImageView(); 
		     
		     play_button_node.setImage(replayButton); //set the image of the play button
		     score_button_node.setImage(homeButton); //set the image of the score button
		     
		     play_button.setGraphic(play_button_node);
		     play_button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))); //this is to make the button background transparent
		     play_button.setScaleShape(true);
		     
		     score_button.setGraphic(score_button_node);
		     score_button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		     play_button.setMaxWidth(Double.MAX_VALUE); //Ensures that both buttons are of the same size
		     score_button.setMaxWidth(Double.MAX_VALUE);
		     
		     
		     play_button.setOnAction(e -> window.setScene(scene));
		     score_button.setOnAction(e -> window.setScene(scene1));
		     /*
		      * create the container of those buttons in a horizontal box
		      */
		     final HBox buttonContainer = new HBox(1);
		     buttonContainer.setAlignment(Pos.TOP_CENTER);
		     Insets buttonContainerPadding = new Insets(400, 1, 1, 1); //Distance from the top center down
		     buttonContainer.setPadding(buttonContainerPadding);
		     buttonContainer.getChildren().addAll(play_button,score_button);
		
		     GridPane root = new GridPane();
		      
		     root.getChildren().addAll(flashScreen_node, clock, counter2, buttonContainer); //add the title screen and button container to the stackpane
		     
		     return root;
		}
	    
	    private Parent createGameBoard(Stage window) {
	    	//ALL RESET LOGIC IS HERE
	    	spriteGroup.getChildren().clear();
	    	t.resetTimer();
	    	System.out.println("Difficulty is: " + Difficulty);
	        Pane root = new Pane();
	        final Image gameScreen = new Image( "GameCanvas.png", CANVAS_WIDTH, CANVAS_HEIGHT, false, false); //title screen image
	        root.setPrefSize(WIDTH * SQUARE_SIZE +CANVAS_WIDTH, HEIGHT * SQUARE_SIZE + CANVAS_HEIGHT);
	        
	        final ImageView gameScreen_node = new ImageView();
		    gameScreen_node.setImage(gameScreen); //set the image of the title screen
		    gameScreen_node.setPreserveRatio(true);
		    
		    counter = new Counter("CounterImg.png");
		    counter.setTranslateX(20);
		    counter.setTranslateY(20);
		    
		    /*
		    clock = new TimerPane("CounterImg.png");
			clock.setTranslateX(80);
			clock.setTranslateY(20);
			double finishedTime = t.getTimeFromStart();
			int seconds = t.getSeconds(finishedTime);
			int minutes = t.getMinutes(finishedTime);
			clock.printLabel(seconds, minutes);
	        */
	        grid=new Grid();
	        squareGroup.getChildren().addAll(grid.getListOfSquares());
	        
	        double left_offset = (CANVAS_WIDTH - (WIDTH*SQUARE_SIZE))/2;
	        //Insets gridContainerPadding = new Insets(CANVAS_HEIGHT*150/800,left_offset, left_offset,1);
	        squareGroup.setLayoutX(left_offset);
	        squareGroup.setLayoutY(CANVAS_HEIGHT*150/800+left_offset);
	        
	        //layout of sprites must match that of the grid always
	        spriteGroup.setLayoutX(left_offset);
	        spriteGroup.setLayoutY(CANVAS_HEIGHT*150/800+left_offset);
	        
	        
	        //NOTE: vertical sprites cannot start past (X,3) if a truck or (X,4) if a car
	        //And horizontal sprites cannot start past (3,Y) if truck or (4,Y) if a car
	        //Also no collision detection when creating a sprite, only when moving sprites about
	        //so make sure you create valid /no overlap starting positions for the sprites.
            
            //generation of different boards
            //change value of i to get different board difficulties
	        //creating 4 different boards
	    	int i = 4;
            switch (i) {
            	case 0: Sprite v10= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
            		spriteGroup.getChildren().add(v10); 
            		Sprite h10= makeSprite(Sprite.Direction.HORIZONTAL,1,1,CAR_SIZE,"file:sprites/dory.png");
            		spriteGroup.getChildren().add(h10);
            		Sprite h20= makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/whale.png");
            		spriteGroup.getChildren().add(h20);
            		Sprite v20= makeSprite(Sprite.Direction.VERTICAL,4,0,TRUCK_SIZE, "file:sprites/gurgle.png");
            		spriteGroup.getChildren().add(v20);
            		UserCar redCar0 = makeUserCar(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:src/nemo.png", window);
            		spriteGroup.getChildren().add((Sprite)redCar0);
            		break;
            	case 1: Sprite v11= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
            		spriteGroup.getChildren().add(v11);
            		Sprite v01= makeSprite(Sprite.Direction.VERTICAL,2,3,CAR_SIZE, "file:sprites/gurgle.png");
            		spriteGroup.getChildren().add(v01); 
    			Sprite h11= makeSprite(Sprite.Direction.HORIZONTAL,0,1,CAR_SIZE,"file:sprites/dory.png");
    			spriteGroup.getChildren().add(h11);
	    		Sprite h21= makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/whale.png");
	    		spriteGroup.getChildren().add(h21);
	    		Sprite v21= makeSprite(Sprite.Direction.VERTICAL,3,0,TRUCK_SIZE, "file:sprites/gurgle.png");
	   		spriteGroup.getChildren().add(v21);
	   		Sprite v31= makeSprite(Sprite.Direction.VERTICAL,4,0,TRUCK_SIZE, "file:sprites/gurgle.png");
	   		spriteGroup.getChildren().add(v31);
	    		Sprite v41= makeSprite(Sprite.Direction.VERTICAL,5,0,TRUCK_SIZE, "file:sprites/gurgle.png");
	    		spriteGroup.getChildren().add(v41);
	    		UserCar redCar1 = makeUserCar(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:src/nemo.png", window);
	    		spriteGroup.getChildren().add((Sprite)redCar1);
	    		break;
    		case 2: Sprite v12= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
    			spriteGroup.getChildren().add(v12); 
	    		Sprite h12= makeSprite(Sprite.Direction.HORIZONTAL,1,1,CAR_SIZE,"file:sprites/dory.png");
	    		spriteGroup.getChildren().add(h12);
	    		Sprite h22= makeSprite(Sprite.Direction.HORIZONTAL,3,4,CAR_SIZE, "file:sprites/dory.png");
	    		spriteGroup.getChildren().add(h22);
	    		Sprite h42= makeSprite(Sprite.Direction.HORIZONTAL,4,0,CAR_SIZE, "file:sprites/dory.png");
	    		spriteGroup.getChildren().add(h42);
	    		Sprite h32= makeSprite(Sprite.Direction.HORIZONTAL,0,5,CAR_SIZE, "file:sprites/dory.png");
	    		spriteGroup.getChildren().add(h32);
		    	Sprite v22= makeSprite(Sprite.Direction.VERTICAL,4,1,CAR_SIZE, "file:sprites/gurgle.png");
		    	spriteGroup.getChildren().add(v22);
		    	Sprite v32= makeSprite(Sprite.Direction.VERTICAL,2,2,CAR_SIZE, "file:sprites/gurgle.png");
		    	spriteGroup.getChildren().add(v32);
		    	Sprite v42= makeSprite(Sprite.Direction.VERTICAL,5,1,CAR_SIZE, "file:sprites/gurgle.png");
		    	spriteGroup.getChildren().add(v42);
		    	Sprite v52= makeSprite(Sprite.Direction.VERTICAL,3,2,CAR_SIZE, "file:sprites/gurgle.png");
		    	spriteGroup.getChildren().add(v52);
		    	UserCar redCar2 = makeUserCar(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:src/nemo.png", window);
		    	spriteGroup.getChildren().add((Sprite)redCar2);
		    	break; 
    		case 3: Sprite v13= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
    			spriteGroup.getChildren().add(v13); 
			Sprite h13= makeSprite(Sprite.Direction.HORIZONTAL,1,1,CAR_SIZE,"file:sprites/dory.png");
			spriteGroup.getChildren().add(h13);
			Sprite h23= makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/whale.png");
			spriteGroup.getChildren().add(h23);
		    	Sprite v23= makeSprite(Sprite.Direction.VERTICAL,4,0,TRUCK_SIZE, "file:sprites/gurgle.png");
		    	spriteGroup.getChildren().add(v23);
		    	Sprite v33= makeSprite(Sprite.Direction.VERTICAL,5,0,TRUCK_SIZE, "file:sprites/gurgle.png");
		    	spriteGroup.getChildren().add(v33);
		    	Sprite v43= makeSprite(Sprite.Direction.VERTICAL,2,2,CAR_SIZE, "file:sprites/gurgle.png");
    			spriteGroup.getChildren().add(v43); 
    			Sprite v53= makeSprite(Sprite.Direction.VERTICAL,2,4,CAR_SIZE, "file:sprites/gurgle.png");
    			spriteGroup.getChildren().add(v53); 
		    	UserCar redCar3 = makeUserCar(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:src/nemo.png", window);
		    	spriteGroup.getChildren().add((Sprite)redCar3);
		    	break; 
    		case 4: Sprite v14= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
    			spriteGroup.getChildren().add(v14); 
    			Sprite v34= makeSprite(Sprite.Direction.VERTICAL,2,3,CAR_SIZE, "file:sprites/gurgle.png");
    			spriteGroup.getChildren().add(v34); 
			Sprite h14= makeSprite(Sprite.Direction.HORIZONTAL,2,1,CAR_SIZE,"file:sprites/dory.png");
			spriteGroup.getChildren().add(h14);
			Sprite h44= makeSprite(Sprite.Direction.HORIZONTAL,2,0,CAR_SIZE,"file:sprites/dory.png");
			spriteGroup.getChildren().add(h44);
			Sprite h34= makeSprite(Sprite.Direction.HORIZONTAL,3,4,TRUCK_SIZE,"file:sprites/whale.png");
			spriteGroup.getChildren().add(h34);
			Sprite h24= makeSprite(Sprite.Direction.HORIZONTAL,3,5,TRUCK_SIZE, "file:sprites/whale.png");
			spriteGroup.getChildren().add(h24);
		    	Sprite v24= makeSprite(Sprite.Direction.VERTICAL,4,0,TRUCK_SIZE, "file:sprites/gurgle.png");
		    	spriteGroup.getChildren().add(v24);
		    	Sprite v44= makeSprite(Sprite.Direction.VERTICAL,5,0,CAR_SIZE, "file:sprites/gurgle.png");
		    	spriteGroup.getChildren().add(v44);
		    	Sprite v54= makeSprite(Sprite.Direction.VERTICAL,5,2,CAR_SIZE, "file:sprites/gurgle.png");
		    	spriteGroup.getChildren().add(v54);
		    	UserCar redCar4 = makeUserCar(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:src/nemo.png", window);
		    	spriteGroup.getChildren().add((Sprite)redCar4);
		    	break; 
            }
            
            
            
            if(grid.checkSetSpriteOnGrid(1,3)) {
            	Sprite s2= makeSprite(Sprite.Direction.VERTICAL,1,3,TRUCK_SIZE,"file:sprites/whale.png");
            	spriteGroup.getChildren().add(s2);
            }
            
            root.getChildren().addAll(gameScreen_node, counter, squareGroup, spriteGroup);
            root.setStyle("-fx-border-color: black");
            
            
	        return root;
	    }
	

    	/**
    	 * Convert pixel/position on the main panel to a grid square index
    	 * @param pixel is the coordinate of an object on the primary stage
    	 * @return the corresponding grid square coordinate the pixel is in.
    	 */
	    private double toGrid(double pixel) {
	    	 return (double)(pixel + SQUARE_SIZE / 2) / SQUARE_SIZE; 
	     
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
	        	
	        	s.setOnMouseDragged(new DragHandler(grid,s));
	        	s.setOnMouseReleased(e -> {
		            int newX = (int) toGrid(s.getLayoutX());
		            int newY = (int) toGrid(s.getLayoutY());
		            System.out.println("new x is " + newX + "new y is "+newY);

		            int xCoord = (int) toGrid(s.getXcoord());
		            int yCoord = (int) toGrid(s.getYcoord());
		            boolean result;
	
		            result = grid.checkMoveToGrid(s,xCoord,yCoord, newX, newY);
		            if(result==false) {   	
		                    s.stopMove();
		            }else {
	                   grid.removeSpriteOnGrid(s, xCoord, yCoord); 
	                   s.move(newX, newY); 
	                   grid.setSpriteOnGrid(s,newX, newY);
	                   moveCtr++;
	                   System.out.println("move ctr is " + moveCtr);
	                  
		            }
	               
	        });
	        	
	        	/* s.setOnMouseDragged(e -> {
	             	if(grid.checkMoveToGrid(  s,toGrid(s.getXcoord()), toGrid(s.getYcoord()),toGrid(e.getSceneX()),
	             			toGrid(e.getSceneY()) )  ){ //if there is a grid boundary or another obstacle in the way
	     	    			//we do not want the user to be able to even try to drag past it, the request should be blocked.
	     	        	if(s.getDirection()==Sprite.Direction.HORIZONTAL) {
	     	        		
	     	        			s.relocate(e.getSceneX() - mouseX + s.getXcoord(),+ s.getYcoord());
	     	        		
	     	        	}else {
	     	        		s.relocate( s.getXcoord(), e.getSceneY() - mouseY + s.getYcoord());
	     	        	}
	             	
	             	}
	             });*/

	        return s;
	    }
	    
	    private UserCar makeUserCar(Sprite.Direction dir, int size, String url, Stage window) {
        	 UserCar s = new UserCar(dir, url);
        	 grid.setSpriteOnGrid(s,0,2);
        	 s.setOnMouseDragged(new DragHandler(grid,s));
        	s.setOnMouseReleased(e -> {
	            int newX = (int) toGrid(s.getLayoutX());
	            int newY = (int) toGrid(s.getLayoutY());
	           // System.out.println("new x is " + newX + "new y is "+newY);

	            int xCoord = (int) toGrid(s.getXcoord());
	            int yCoord = (int) toGrid(s.getYcoord());
	            boolean result;

	            result = grid.checkMoveToGrid(s,xCoord,yCoord, newX, newY);

	            if(result==false) {   	
	                    s.stopMove();
	            }else {
                   grid.removeSpriteOnGrid(s, xCoord, yCoord); 
                   s.move(newX, newY); 
                   grid.setSpriteOnGrid(s,newX, newY);
                   moveCtr++;
                   if (newX == 4) {
						 //Get time taken to complete game and print 
						double finishedTime = t.getTimeFromStart();
						int seconds = t.getSeconds(finishedTime);
						int minutes = t.getMinutes(finishedTime);
						System.out.println("Time taken " + minutes + " Minutes and " + seconds + " Seconds");
						System.out.println("Moves taken " + moveCtr);

						//Reset the game screen for the next round
						scene = new Scene(createGameBoard(window), CANVAS_HEIGHT, CANVAS_WIDTH);
						scene2 = new Scene(exitScreen(window, seconds, minutes, moveCtr), CANVAS_HEIGHT, CANVAS_WIDTH);
						window.setScene(scene2); //Goes to exit screen.
						moveCtr=0; //reset ctr for next game
                   } else {
                	   
                		System.out.println("move ctr is " + moveCtr);
                   }
	            }
	            
        });

        return s;
    }
	    
	    public static void setDifficulty(String d) {
	    		Difficulty = d;
	    }
}
