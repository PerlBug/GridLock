/**
 * GridLock.java written for COMP2511 18s1 Project - Group 3
 * 
 * @authors and GitHub IDs
 * 5162531 Diaz, Rebecca Avril: beccaD6 - <becca.diaz6@gmail.com>
 * 3461044 Kala, Shilpa: sk2552 - <simple.snowflake@gmail.com>
 * 3463869 Sun, Elliott Yongrui: umeb0shi - <elly.here@gmail.com>
 * 5157086 Galoyan, Mkrtich: PerlBug - <galoyanmko@gmail.com>
 * 5060239 Chen, Leo Jia Jian: leochen15 - <leochen1512@gmail.com>
 * 
 * This is a class for the GridLock system. It contains the main[] method
 * 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main class for the GridLock application
 * Holds creation of Gameboard, Exit Screen, Instructions and MenuBoard
 * Creation of Sprites
 * Manages all items within game
 *
 */
public class GridLock extends Application {
		
	 	static Rectangle2D primaryStagebounds = Screen.getPrimary().getVisualBounds();
	 	public static final double CANVAS_HEIGHT = primaryStagebounds.getHeight()*0.8;
	 	public static final double CANVAS_WIDTH = CANVAS_HEIGHT * 650/800; //Sets the proportion of the game to satisfy all screen sizes
	 	public static final double heightScale = CANVAS_HEIGHT/800; // scale of Y due to screen dimension
	 	public static final double widthScale = CANVAS_WIDTH/650; //scale of X due to screen dimension
	    public static final double SQUARE_SIZE = (double)CANVAS_HEIGHT/8*0.9; //Each square should be equivalent 100/800px height
	    public static final int WIDTH = 6;
	    public static final int HEIGHT = 6;
	    public static final int CAR_SIZE=2;
	    public static final int TRUCK_SIZE=3;
	    
	    
	    private Grid grid;
	    private MenuBoard gameMenu;
	    private Scoreboard scoreMenu;
	    private Group squareGroup = new Group(); //Used within Create Game Board
	    private Group spriteGroup = new Group(); // Used within create game board
	    private Stack<Sprite> StackUndo = new Stack<Sprite>();
	    
	    public static List<State> stateList= new ArrayList<State>();
	    public static State prevState;
	    
	    class member{
		    	private Sprite v;
		    	private UserSprite nemo;
	    }
	    
	    /*
	     * Declares Difficulty: Starts at Medium Difficulty.
	     */
	    private static String Difficulty = "Medium";

	    public static Runnable liveClock;

	    public static Counter counter;
	    public static TimerPane clock; 
	    
	    
	    Scene scene1, scene, scene2, instructionScene;
	    private Timer t;
		private boolean replay=false;
		private static int curr_i=1; //current i value for the preset game
	    
	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) {
	    	t = new Timer();	
	    	
	        //scene = new Scene(createGameBoard(primaryStage), CANVAS_HEIGHT, CANVAS_WIDTH);

	        scene1 = new Scene(startMenu(primaryStage), CANVAS_HEIGHT, CANVAS_WIDTH);
	        //scene2 = new Scene(exitScreen(primaryStage), CANVAS_HEIGHT, CANVAS_WIDTH);
	        instructionScene = new Scene(instructions(primaryStage), CANVAS_HEIGHT, CANVAS_WIDTH);
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
	        double left_offset = (CANVAS_WIDTH - (WIDTH*SQUARE_SIZE)-(65*widthScale*0.9))/2;
	        primaryStage.setHeight(CANVAS_HEIGHT+left_offset);
	        primaryStage.setWidth(CANVAS_WIDTH);
	        primaryStage.setMaxHeight(CANVAS_HEIGHT+left_offset);
	        primaryStage.setMaxWidth(CANVAS_WIDTH);
	        primaryStage.resizableProperty().setValue(Boolean.FALSE);

	        
	        //makes sure closing window shuts down application
	        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent t) {
	                Platform.exit();
	                System.exit(0);
	            }
	        });
	        
	        primaryStage.show();
	        
	    }

	    /**
	     * Returns the pane for the start menu. Attaches the image to the game Menu (VBox)
	     * @param window primaryStage
	     * @return
	     */
	    public Pane startMenu(Stage window) {

	    		Pane root = new Pane();
	        
	        final Image titleScreen = new Image( "Title Page2.png", CANVAS_WIDTH, CANVAS_HEIGHT, false, false);
	        final ImageView menuScreen_node = new ImageView();
		    menuScreen_node.setImage(titleScreen); //set the image of the title screen
		    menuScreen_node.setPreserveRatio(true);
	        
		    gameMenu = new MenuBoard(window, this);
		    
		    root.getChildren().addAll(menuScreen_node, gameMenu);
		     
		     return root;
		}
	    /**
	     * Returns the GameBoard Scene by making a new one
	     * @return
	     */
	    public Scene getGame(Stage window) {
	    		//creates new game scene and returns
	    	    scene = new Scene(createGameBoard(window), CANVAS_HEIGHT, CANVAS_WIDTH);
	    		return this.scene;
	    }
	    
	    /**
	     * Returns the Instructions scene
	     * @param window
	     * @return
	     */
	    public Scene getInstruction(Stage window) {
	    	return this.instructionScene;
	    }
	    
	    /**
	     * Creates a scene with the instructions for the game
	     * @precondition window != null
	     * @postcondition A scene is created containing instructions for the user to play the game
	     * @param window
	     * @return
	     */
	    private GridPane instructions(Stage window) {
	    	final Image background = new Image( "file:src/instructions_scene.png", CANVAS_WIDTH, CANVAS_HEIGHT, false, false);
	    	
	    	final ImageView flashScreen_node = new ImageView();
		    flashScreen_node.setImage(background); //set the image of the title screen
		    flashScreen_node.setPreserveRatio(true);
		    
		    final Image menuImage = new Image("file:src/home-button-round-blue.png", 100*widthScale, 100*heightScale, false, false);
		    final Button menuButton  = new Button();
		    final ImageView menuButtonNode = new ImageView(); 
		    menuButtonNode.setImage(menuImage);
		    
		    
		    menuButton.setGraphic(menuButtonNode);
		    menuButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))); //this is to make the button background transparent
		    menuButton.setScaleShape(true);
		    menuButton.setMaxWidth(Double.MAX_VALUE);
			
			menuButton.setOnAction(e -> window.setScene(scene1)); //Go back to the main menu when clicked
		    final HBox buttonContainer = new HBox(1);
		    buttonContainer.setAlignment(Pos.TOP_RIGHT);
		    Insets buttonContainerPadding = new Insets(600*heightScale, 1, 20*heightScale, 20*widthScale); //Distance from the top center down
		    buttonContainer.setPadding(buttonContainerPadding);
		    buttonContainer.getChildren().addAll(menuButton);
		    
	    	GridPane root = new GridPane(); 
		    root.getChildren().addAll(flashScreen_node, buttonContainer);
		    return root;
	    }
	    
	    
	    /**
	     * Method which creates the exit scene and displays number of moves and how long game took
	     * @param window
	     * @param seconds
	     * @param minutes
	     * @param c
	     * 
	     * @precondition window != null
	     * @precondition seconds >= 0; minutes >= 0; c >=0
	     * @postcondition a new exit scene is created
	     * 
	     * @return
	     */
	    private AnchorPane exitScreen(Stage window, int seconds, int minutes, int c) {

	    	 final Image titleScreen = new Image( "file:src/exitScreen2.png", CANVAS_WIDTH, CANVAS_HEIGHT, false, false); //title screen image

		     final ImageView flashScreen_node = new ImageView();
		     flashScreen_node.setImage(titleScreen); //set the image of the title screen
		     flashScreen_node.setPreserveRatio(true);
		     
		     clock = new TimerPane("file:src/goldseal.png");
			 clock.setTranslateX(40);
			 clock.setTranslateY(60);
			 clock.printLabel(seconds, minutes);
			 Counter counter2 = new Counter("file:src/goldseal.png");
			 counter2.setTranslateX(-30);
			 counter2.setTranslateY(60);
			 counter2.setCount(c);
			 
			 MenuButton play_button = new MenuButton("Play Again", "file:src/StoneButton.png"); //not really replay since can get a different puzzle every time you press it..
			 MenuButton score_button = new MenuButton("Return Home", "file:src/StoneButton.png");
			 play_button.setScaleX(0.8);
			 play_button.setScaleY(0.9);
			 score_button.setScaleX(0.8);
			 score_button.setScaleY(0.9);
			 
			 play_button.removeTranslate(0);
			 score_button.removeTranslate(0);
			 
			 TextField nameEnter = new TextField();
			 nameEnter.setPromptText("Enter your name to record your score!");
			 nameEnter.setStyle(""+ "-fx-font-size: 10px;");
			 nameEnter.setFocusTraversable(false); 
			 nameEnter.setMinWidth(1.18*CANVAS_WIDTH/2*widthScale); //made wider to fit the message 
			 nameEnter.setMinHeight(30*heightScale);
			 nameEnter.setOpacity(80);

			 
			 MenuButton submit = new MenuButton("", "file:src/arrow.png", 50*widthScale, 40*heightScale);
		     
		     play_button.setOnMouseClicked(e -> window.setScene(new Scene(createGameBoard(window), CANVAS_HEIGHT, CANVAS_WIDTH))); //replay
		     
		     score_button.setOnMouseClicked(e -> window.setScene(scene1));
		     
			 submit.setOnMouseClicked(e -> {
			    	 	if(!nameEnter.getText().trim().isEmpty()) {
					    	 FileWriter writeScore = null;
							 BufferedWriter bw = null;
							 File file = new File(MenuBoard.fileAccess);
							 try {
							     if(!file.exists()) {
							    	 	file.createNewFile();
							     }
								 writeScore = new FileWriter(file.getAbsoluteFile(), true);
							     bw = new BufferedWriter(writeScore);
							     if(seconds < 10) {
							    	 if (minutes < 10) {
							    		 bw.write(Difficulty + " " + counter2.getCount() + " 0" + minutes + ":0" + seconds + " " + nameEnter.getText() +"\n");
							    	 }
							    	 else {
							    		 bw.write(Difficulty + " " + counter2.getCount() + " " + minutes + ":0" + seconds + " " + nameEnter.getText() +"\n");
							    	 }
							    	 
							     }
							     else {
							    	 
							    	 bw.write(Difficulty + " " + counter2.getCount() + " " + minutes + ":" + seconds + " " + nameEnter.getText() +"\n");
							     }
							     
							     
							     System.out.println("Saved Name: " + nameEnter.getText());
							 } catch (IOException er){
								 er.printStackTrace();
							 }finally {
								 try {
									 if(bw != null)
										 bw.close();
									 if(writeScore != null)
										 writeScore.close();
								 } catch(IOException ex) {
									 ex.printStackTrace();
								 }
							 }
				    	 	}
					
					Stage popUp = new Stage();
                    	popUp.initModality(Modality.WINDOW_MODAL);
					final Button exitB = new Button("Close Dialog");
					exitB.setOnMouseClicked(event -> popUp.close());
					
					popUp.setX(200);
					popUp.setY(200);
					Text displayS = new Text();
					if(nameEnter.getText().trim().isEmpty()) {
						displayS.setText("Please input name");
					} else {
						displayS = new Text("Saved into Hi Score. Thank you!");
					}
					nameEnter.clear();
					VBox v1= new VBox(10);
					v1.getChildren().addAll(displayS, exitB);
					popUp.setScene(new Scene(v1));
					popUp.show();
			     });
		     /*
		      * create the container of those buttons in a horizontal box
		      */
		     final HBox buttonContainer = new HBox(5);
		     buttonContainer.setAlignment(Pos.TOP_CENTER);
		     System.out.println("Layout Y is " + buttonContainer.getLayoutY());
		     Insets buttonContainerPadding = new Insets(600*heightScale, 120*widthScale, 10*heightScale, 120*widthScale); //Distance from the top center down
		     buttonContainer.setMaxSize(buttonContainer.getWidth(), play_button.getHeight());
		     buttonContainer.setPadding(buttonContainerPadding);
		     buttonContainer.getChildren().addAll(play_button,score_button);
		     
		     final HBox textEnter = new HBox(10);
		     Insets textEnterPadding = new Insets(530*heightScale, 150*widthScale , 10*heightScale ,150*widthScale);
		     textEnter.setMaxSize(buttonContainer.getWidth(), play_button.getHeight());
		     textEnter.setPadding(textEnterPadding);
		     textEnter.getChildren().addAll(nameEnter, submit);
		     
		     final HBox StatDisplay = new HBox(GridLock.CANVAS_WIDTH/4);
		     StatDisplay.setAlignment(Pos.TOP_CENTER);
		     Insets StatContainerPadding = new Insets(300*heightScale,140*widthScale,300*heightScale,140*widthScale);
		     StatDisplay.setMaxHeight(clock.getHeight());
		     StatDisplay.setPadding(StatContainerPadding);
		     StatDisplay.getChildren().addAll(clock, counter2);
		     AnchorPane root = new AnchorPane();
		      
		     root.getChildren().addAll(flashScreen_node, StatDisplay, buttonContainer, textEnter); //add the title screen and button container to the stackpane
		     
		     return root;
		}
	    
	    
	    /**
	     * Method for creating the gameboard
	     * @precondition window != null
	     * @postcondition a new game scene is created with a different random preset game 
	     * @param window 
	     * @return
	     */
	    private Parent createGameBoard(Stage window) {
	    	//ALL RESET LOGIC IS HERE
	    	spriteGroup.getChildren().clear();
	    	t.resetTimer();
	    	System.out.println("Difficulty is: " + Difficulty);
	        Pane root = new Pane();
	        final Image gameScreen = new Image( "GameCanvas2.png", CANVAS_WIDTH, CANVAS_HEIGHT, false, false); //title screen image
	        root.setPrefSize(WIDTH * SQUARE_SIZE +CANVAS_WIDTH, HEIGHT * SQUARE_SIZE + CANVAS_HEIGHT);
	        
	        final ImageView gameScreen_node = new ImageView();
		    gameScreen_node.setImage(gameScreen); //set the image of the title screen
		    gameScreen_node.setPreserveRatio(true);
		    
		    counter = new Counter("file:src/bluebubble.png");
		    counter.setTranslateX(0);
		    counter.setTranslateY(0);
		    
		    		    
		    liveClock = new TimerPane("file:src/bluebubble.png");
		    Thread t1 = new Thread(liveClock);
		    t1.start();
		    
		    ((TimerPane) liveClock).setTranslateX(CANVAS_WIDTH-TimerPane.WIDTH);
		    ((TimerPane) liveClock).setTranslateY(0);
		    
	        grid=new Grid();
	        squareGroup.getChildren().addAll(grid.getListOfSquares());
	        
	        double left_offset = (CANVAS_WIDTH - (WIDTH*SQUARE_SIZE))/2;
	        //Insets gridContainerPadding = new Insets(CANVAS_HEIGHT*150/800,left_offset, left_offset,1);
	        squareGroup.setLayoutX(left_offset);
	        squareGroup.setLayoutY(CANVAS_HEIGHT*150/800+left_offset);
	        
	        //layout of sprites must match that of the grid always
	        spriteGroup.setLayoutX(left_offset);
	        spriteGroup.setLayoutY(CANVAS_HEIGHT*150/800+left_offset);
	        
	        //Pertains to Undo Button
	        MenuButton undo = new MenuButton("Undo", "StoneButton.png");
	        undo.setScaleX(0.8);
	        undo.setScaleY(0.9);
	        undo.removeTranslate(0);
	        undo.setOnMouseClicked(e -> {
	        	if(prevState!=null) {
	        		Sprite s1 = prevState.getSprite();
	        		grid.removeSpriteOnGrid(s1, (int) toGrid(s1.getXcoord()), (int)toGrid(s1.getYcoord()));
        			int newX=(int)toGrid( s1.getPrevX());
        			int newY=(int) toGrid(s1.getPrevY());
        			
        			s1.move(newX,  newY);
        			grid.setSpriteOnGrid(s1,  (int) toGrid(s1.getXcoord()), (int) toGrid(s1.getYcoord()));
        			prevState = prevState.getPrevState();
	        	}
//	        	if(!StackUndo.empty()) {
//		        	Sprite s1= StackUndo.pop();
//			        	if(s1!=null) {
//			        			grid.removeSpriteOnGrid(s1, (int) toGrid(s1.getXcoord()), (int)toGrid(s1.getYcoord()));
//			        			int newX=(int)toGrid( s1.getPrevX());
//			        			int newY=(int) toGrid(s1.getPrevY());
//			        				
//				        		s1.move(newX, newY);
//			        			grid.setSpriteOnGrid(s1, (int) toGrid(s1.getXcoord()), (int)toGrid(s1.getYcoord()));
//				        		//prevState = prevState.getPrevState();
//			        	}
//	        	}
	        });
	        
	        //
	        
	        final MenuButton reset = new MenuButton("Reset", "StoneButton.png");
	        reset.setScaleX(0.8);
	        reset.setScaleY(0.9);
	        reset.removeTranslate(0);
	     //   reset.setOnMouseClicked(e ->); //replay
	        reset.setOnMouseClicked(e -> resetGameBoard(window));
	        final MenuButton menuButton = new MenuButton("Back to Menu", "StoneButton.png");
	        menuButton.setScaleX(0.8);
	        menuButton.setScaleY(0.9);
	        menuButton.removeTranslate(0);
		    menuButton.setOnMouseClicked(e -> window.setScene(scene1)); //Go back to the main menu when clicked
		    final HBox buttonContainer = new HBox(5);
		    buttonContainer.setAlignment(Pos.CENTER);
		    Insets buttonContainerPadding = new Insets(120*heightScale, 1, 1, CANVAS_WIDTH/2-75); //Distance from the top center down
		    buttonContainer.setPadding(buttonContainerPadding);
		    buttonContainer.setTranslateX(-245*widthScale);
		    buttonContainer.getChildren().addAll(undo, menuButton, reset);
	        
	        //NOTE: vertical sprites cannot start past (X,3) if a truck or (X,4) if a car
	        //And horizontal sprites cannot start past (3,Y) if truck or (4,Y) if a car
	        //Also no collision detection when creating a sprite, only when moving sprites about
	        //so make sure you create valid /no overlap starting positions for the sprites.
            
            //generation of different boards
            //change value of i to get different board difficulties
	        //creating 4 different boards
		    Random rand = new Random();
		    StackUndo.clear();
		    int i=0;
		    Sprite e0 = null, e1 = null, e2 = null, e3 = null, e4 = null, e5 = null, e6 = null, e7 = null, e8 = null, e9 = null, e10 = null, e11 = null, redCar = null;
		    if (Difficulty == "Easy") {
		    	if(replay) {
		    		
		    		i=curr_i;
		    	}else {
		    		i = rand.nextInt(4) + 0;
		    	}
		    	System.out.println(i);
	            switch (i) {
	            	case 0: e1 = makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e1);
		            		e2 = makeSprite(Sprite.Direction.HORIZONTAL,1,1,CAR_SIZE,"file:sprites/dory.png");
		            		spriteGroup.getChildren().add(e2);
		            		e3 = makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/whale.png");
		            		spriteGroup.getChildren().add(e3);
		            		e4= makeSprite(Sprite.Direction.VERTICAL,4,0,TRUCK_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e4);
		            		redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
		            		spriteGroup.getChildren().add((Sprite)redCar);
		            		break;
	            	case 1: e1 = makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e1);
		            		e2= makeSprite(Sprite.Direction.VERTICAL,2,3,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e2); 
		            		e3 = makeSprite(Sprite.Direction.HORIZONTAL,0,1,CAR_SIZE,"file:sprites/dory.png");
		            		spriteGroup.getChildren().add(e3);
		            		e4= makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/whale.png");
		            		spriteGroup.getChildren().add(e4);
		            		e5= makeSprite(Sprite.Direction.VERTICAL,3,0,TRUCK_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e5);
		            		e6= makeSprite(Sprite.Direction.VERTICAL,4,0,TRUCK_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e6);
		            		e7= makeSprite(Sprite.Direction.VERTICAL,5,0,TRUCK_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e7);
				    		redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
				    		spriteGroup.getChildren().add((Sprite)redCar);
				    		break;
		    		case 2: e1= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
			    			spriteGroup.getChildren().add(e1); 
				    		e2= makeSprite(Sprite.Direction.HORIZONTAL,1,1,CAR_SIZE,"file:sprites/dory.png");
				    		spriteGroup.getChildren().add(e2);
				    		e3= makeSprite(Sprite.Direction.HORIZONTAL,4,5,CAR_SIZE, "file:sprites/dory.png");
				    		spriteGroup.getChildren().add(e3);
				    		e4= makeSprite(Sprite.Direction.HORIZONTAL,4,0,CAR_SIZE, "file:sprites/dory.png");
				    		spriteGroup.getChildren().add(e4);
				    		e5= makeSprite(Sprite.Direction.HORIZONTAL,0,5,CAR_SIZE, "file:sprites/dory.png");
				    		spriteGroup.getChildren().add(e5);
					    	e6= makeSprite(Sprite.Direction.VERTICAL,4,1,TRUCK_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e6);
					    	e7= makeSprite(Sprite.Direction.VERTICAL,2,2,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e7);
					    	e8= makeSprite(Sprite.Direction.VERTICAL,5,1,TRUCK_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e8);
					    	e9= makeSprite(Sprite.Direction.VERTICAL,3,2,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e9);
					    	redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
					    	spriteGroup.getChildren().add((Sprite)redCar);
					    	break; 
		    		case 3: e1= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
			    			spriteGroup.getChildren().add(e1); 
			    			e2= makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/whale.png");
			    			spriteGroup.getChildren().add(e2);
			    			e3 = makeSprite(Sprite.Direction.VERTICAL,4,0,TRUCK_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e3);
					    	e4= makeSprite(Sprite.Direction.VERTICAL,5,0,TRUCK_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e4);
					    	e5= makeSprite(Sprite.Direction.VERTICAL,2,2,CAR_SIZE, "file:sprites/gurgle.png");
			    			spriteGroup.getChildren().add(e5); 
			    			e6= makeSprite(Sprite.Direction.VERTICAL,2,4,CAR_SIZE, "file:sprites/gurgle.png");
			    			spriteGroup.getChildren().add(e6); 
					    redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
					    	spriteGroup.getChildren().add((Sprite)redCar);
					    	break; 
		    		case 4: e1= makeSprite(Sprite.Direction.VERTICAL,2,3,CAR_SIZE, "file:sprites/gurgle.png");
			    			spriteGroup.getChildren().add(e1); 
							e2= makeSprite(Sprite.Direction.HORIZONTAL,3,3,CAR_SIZE,"file:sprites/dory.png");
							spriteGroup.getChildren().add(e2);
							e3 = makeSprite(Sprite.Direction.HORIZONTAL,3,4,TRUCK_SIZE,"file:sprites/whale.png");
							spriteGroup.getChildren().add(e3);
							e4= makeSprite(Sprite.Direction.HORIZONTAL,3,5,TRUCK_SIZE, "file:sprites/whale.png");
							spriteGroup.getChildren().add(e4);
					    	e5= makeSprite(Sprite.Direction.VERTICAL,4,0,TRUCK_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e5);
					    	e6= makeSprite(Sprite.Direction.VERTICAL,5,0,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e6);
					    	e7= makeSprite(Sprite.Direction.VERTICAL,5,2,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e7);
					    	redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
					    	spriteGroup.getChildren().add((Sprite)redCar);
					    	break; 
	            }
		    } else if (Difficulty == "Medium") {
		    	if(replay) {
		    		 i=curr_i;
		    	}else {
		    		 i = rand.nextInt(3) + 0;
		    	}
		    		
		    		
	            switch (i) {
	            	case 0: e1= makeSprite(Sprite.Direction.VERTICAL,2,3,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e1);
		            		e2= makeSprite(Sprite.Direction.VERTICAL,0,0,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e2);
		            		e3= makeSprite(Sprite.Direction.VERTICAL,1,0,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e3);
		            		e4= makeSprite(Sprite.Direction.HORIZONTAL,2,1,CAR_SIZE,"file:sprites/dory.png");
		            		spriteGroup.getChildren().add(e4);
		            		e5= makeSprite(Sprite.Direction.HORIZONTAL,2,0,CAR_SIZE,"file:sprites/dory.png");
		            		spriteGroup.getChildren().add(e5);
		            		e6= makeSprite(Sprite.Direction.HORIZONTAL,4,5,CAR_SIZE,"file:sprites/dory.png");
		            		spriteGroup.getChildren().add(e6);
		            		e7= makeSprite(Sprite.Direction.HORIZONTAL,2,5,CAR_SIZE,"file:sprites/dory.png");
		            		spriteGroup.getChildren().add(e7);
		            		e8= makeSprite(Sprite.Direction.HORIZONTAL,4,0,CAR_SIZE,"file:sprites/dory.png");
		            		spriteGroup.getChildren().add(e8);
		            		e9= makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/whale.png");
		            		spriteGroup.getChildren().add(e9);
		            		e10= makeSprite(Sprite.Direction.VERTICAL,4,1,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e10);
		            		redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
		            		spriteGroup.getChildren().add((Sprite)redCar);
		            		break;
	            	case 1: e1= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e1);
		            		e2= makeSprite(Sprite.Direction.VERTICAL,2,3,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e2); 
		            		e3= makeSprite(Sprite.Direction.HORIZONTAL,0,1,CAR_SIZE,"file:sprites/dory.png");
		            		spriteGroup.getChildren().add(e3);
		            		e4= makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/whale.png");
		            		spriteGroup.getChildren().add(e4);
		            		e5= makeSprite(Sprite.Direction.VERTICAL,3,1,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e5);
		            		e6= makeSprite(Sprite.Direction.VERTICAL,4,1,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e6);
		            		e7= makeSprite(Sprite.Direction.VERTICAL,5,1,CAR_SIZE, "file:sprites/gurgle.png");
		            		spriteGroup.getChildren().add(e7);
		            		e8= makeSprite(Sprite.Direction.HORIZONTAL,0,5,CAR_SIZE, "file:sprites/dory.png");
		            		spriteGroup.getChildren().add(e8);
		            		e9= makeSprite(Sprite.Direction.HORIZONTAL,03,5,CAR_SIZE, "file:sprites/dory.png");
		            		spriteGroup.getChildren().add(e9);
		            		e0= makeSprite(Sprite.Direction.HORIZONTAL,3,0,TRUCK_SIZE, "file:sprites/whale.png");
		            		spriteGroup.getChildren().add(e0);
				    		redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
				    		spriteGroup.getChildren().add((Sprite)redCar);
				    		break;
		    		case 2: e0= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
			    			spriteGroup.getChildren().add(e0); 
				    		e1= makeSprite(Sprite.Direction.HORIZONTAL,1,1,CAR_SIZE,"file:sprites/dory.png");
				    		spriteGroup.getChildren().add(e1);
				    		e2= makeSprite(Sprite.Direction.HORIZONTAL,3,4,CAR_SIZE, "file:sprites/dory.png");
				    		spriteGroup.getChildren().add(e2);
				    		e3= makeSprite(Sprite.Direction.HORIZONTAL,4,3,CAR_SIZE, "file:sprites/dory.png");
				    		spriteGroup.getChildren().add(e3);
				    		e4= makeSprite(Sprite.Direction.HORIZONTAL,4,0,CAR_SIZE, "file:sprites/dory.png");
				    		spriteGroup.getChildren().add(e4);
				    		e5= makeSprite(Sprite.Direction.HORIZONTAL,0,5,CAR_SIZE, "file:sprites/dory.png");
				    		spriteGroup.getChildren().add(e5);
					    	e6= makeSprite(Sprite.Direction.VERTICAL,4,1,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e6);
					    	e7= makeSprite(Sprite.Direction.VERTICAL,2,2,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e7);
					    	e8= makeSprite(Sprite.Direction.VERTICAL,5,1,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e8);
					    	e9= makeSprite(Sprite.Direction.VERTICAL,3,2,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e9);
					    	e10= makeSprite(Sprite.Direction.VERTICAL,0,0,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e10);
					    redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
					    	spriteGroup.getChildren().add((Sprite)redCar);
					    	break; 
		    		case 3: e1= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
			    			spriteGroup.getChildren().add(e1); 
			    			e2= makeSprite(Sprite.Direction.HORIZONTAL,1,1,CAR_SIZE,"file:sprites/dory.png");
			    			spriteGroup.getChildren().add(e2);
			    			e3= makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/whale.png");
			    			spriteGroup.getChildren().add(e3);
					    	e4= makeSprite(Sprite.Direction.VERTICAL,4,1,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e4);
					    	e5= makeSprite(Sprite.Direction.VERTICAL,5,1,CAR_SIZE, "file:sprites/gurgle.png");
					    	spriteGroup.getChildren().add(e5);
					    	e6= makeSprite(Sprite.Direction.VERTICAL,2,2,CAR_SIZE, "file:sprites/gurgle.png");
			    			spriteGroup.getChildren().add(e6); 
			    			e7= makeSprite(Sprite.Direction.VERTICAL,2,4,CAR_SIZE, "file:sprites/gurgle.png");
			    			spriteGroup.getChildren().add(e7); 
			    			e8= makeSprite(Sprite.Direction.HORIZONTAL,0,5,CAR_SIZE, "file:sprites/Dory.png");
			    			spriteGroup.getChildren().add(e8);
			    			e9= makeSprite(Sprite.Direction.HORIZONTAL,3,0,TRUCK_SIZE, "file:sprites/whale.png");
			    			spriteGroup.getChildren().add(e9);
					    	redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
					    	spriteGroup.getChildren().add((Sprite)redCar);
					    	break; 
	            	} 
		    	} else if (Difficulty == "Hard") {
		    		
	            	
	            	if(replay) {
			    		
			    		 i=curr_i;
			    	}else {
			    		 i = rand.nextInt(3) + 0;
			    	}
	            	switch (i) {
	            		case 0: e1= makeSprite(Sprite.Direction.VERTICAL,1,3,CAR_SIZE, "file:sprites/gurgle.png");
				    			spriteGroup.getChildren().add(e1); 
				    			e2= makeSprite(Sprite.Direction.VERTICAL,2,3,CAR_SIZE, "file:sprites/gurgle.png");
				    			spriteGroup.getChildren().add(e2); 
				    			e3= makeSprite(Sprite.Direction.HORIZONTAL,2,1,CAR_SIZE,"file:sprites/dory.png");
				    			spriteGroup.getChildren().add(e3);
								e4= makeSprite(Sprite.Direction.HORIZONTAL,2,0,CAR_SIZE,"file:sprites/dory.png");
								spriteGroup.getChildren().add(e4);
								e5= makeSprite(Sprite.Direction.HORIZONTAL,3,4,TRUCK_SIZE,"file:sprites/whale.png");
								spriteGroup.getChildren().add(e5);
								e6= makeSprite(Sprite.Direction.HORIZONTAL,3,5,TRUCK_SIZE, "file:sprites/whale.png");
								spriteGroup.getChildren().add(e6);
						    	e7= makeSprite(Sprite.Direction.VERTICAL,4,0,TRUCK_SIZE, "file:sprites/gurgle.png");
						    	spriteGroup.getChildren().add(e7);
						    	e8= makeSprite(Sprite.Direction.VERTICAL,5,0,CAR_SIZE, "file:sprites/gurgle.png");
						    	spriteGroup.getChildren().add(e8);
						    	e9= makeSprite(Sprite.Direction.VERTICAL,5,2,CAR_SIZE, "file:sprites/gurgle.png");
						    	spriteGroup.getChildren().add(e9);
						    	redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
						    	spriteGroup.getChildren().add((Sprite)redCar);
						    	break; 
	            		case 1: e1= makeSprite(Sprite.Direction.VERTICAL,2,3,CAR_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e1);
			            		e2= makeSprite(Sprite.Direction.VERTICAL,0,0,CAR_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e2);
			            		e3= makeSprite(Sprite.Direction.VERTICAL,1,0,CAR_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e3);
			            		e4= makeSprite(Sprite.Direction.HORIZONTAL,2,1,CAR_SIZE,"file:sprites/dory.png");
			            		spriteGroup.getChildren().add(e4);
			            		e5= makeSprite(Sprite.Direction.HORIZONTAL,2,0,CAR_SIZE,"file:sprites/dory.png");
			            		spriteGroup.getChildren().add(e5);
			            		e6= makeSprite(Sprite.Direction.HORIZONTAL,4,5,CAR_SIZE,"file:sprites/dory.png");
			            		spriteGroup.getChildren().add(e6);
			            		e7= makeSprite(Sprite.Direction.HORIZONTAL,2,5,CAR_SIZE,"file:sprites/dory.png");
			            		spriteGroup.getChildren().add(e7);
			            		e8= makeSprite(Sprite.Direction.HORIZONTAL,4,0,CAR_SIZE,"file:sprites/dory.png");
			            		spriteGroup.getChildren().add(e8);
			            		e9= makeSprite(Sprite.Direction.HORIZONTAL,0,4,CAR_SIZE,"file:sprites/dory.png");
			            		spriteGroup.getChildren().add(e9);
			            		e10= makeSprite(Sprite.Direction.HORIZONTAL,3,3,TRUCK_SIZE, "file:sprites/whale.png");
			            		spriteGroup.getChildren().add(e10);
			            		e11= makeSprite(Sprite.Direction.VERTICAL,4,1,CAR_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e11);
			            		redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
			            		spriteGroup.getChildren().add((Sprite)redCar);
			            		break;
	            		case 2: e1= makeSprite(Sprite.Direction.VERTICAL,2,0,TRUCK_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e1);
			            		e2= makeSprite(Sprite.Direction.VERTICAL,3,0,TRUCK_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e2);
			            		e3= makeSprite(Sprite.Direction.VERTICAL,4,0,CAR_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e3);
			            		e4= makeSprite(Sprite.Direction.HORIZONTAL,0,0,CAR_SIZE,"file:sprites/dory.png");
			            		spriteGroup.getChildren().add(e4);
			            		e5= makeSprite(Sprite.Direction.VERTICAL,4,2,CAR_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e5);
			            		e6= makeSprite(Sprite.Direction.HORIZONTAL,4,4,CAR_SIZE,"file:sprites/dory.png");
			            		spriteGroup.getChildren().add(e6);
			            		e7= makeSprite(Sprite.Direction.VERTICAL,1,4,CAR_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e7);
			            		redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
			            		spriteGroup.getChildren().add((Sprite)redCar);
			            		break;
	            		case 3: e1= makeSprite(Sprite.Direction.VERTICAL,0,0,CAR_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e1);
			            		e2= makeSprite(Sprite.Direction.VERTICAL,1,4,CAR_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e2);
			            		e3= makeSprite(Sprite.Direction.VERTICAL,3,0,CAR_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e3);
			            		e4= makeSprite(Sprite.Direction.VERTICAL,3,2,CAR_SIZE,"file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e4);
			            		e5= makeSprite(Sprite.Direction.HORIZONTAL,2,4,CAR_SIZE,"file:sprites/dory.png");
			            		spriteGroup.getChildren().add(e5);
			            		e6= makeSprite(Sprite.Direction.HORIZONTAL,2,5,CAR_SIZE,"file:sprites/dory.png");
			            		spriteGroup.getChildren().add(e6);
			            		e7= makeSprite(Sprite.Direction.HORIZONTAL,0,3,TRUCK_SIZE, "file:sprites/whale.png");
			            		spriteGroup.getChildren().add(e7);
			            		e8= makeSprite(Sprite.Direction.VERTICAL,5,0,TRUCK_SIZE, "file:sprites/gurgle.png");
			            		spriteGroup.getChildren().add(e8);
			            		redCar = makeUserSprite(Sprite.Direction.HORIZONTAL,CAR_SIZE, "file:sprites/nemo.png", window);
			            		spriteGroup.getChildren().add((Sprite)redCar);
			            		break;
	            	}
		    	}
		
            root.getChildren().addAll(gameScreen_node, counter, ((TimerPane) liveClock), buttonContainer, squareGroup, spriteGroup);
            root.setStyle("-fx-border-color: black");
            curr_i=i; //update
            replay=false;//reset replay if it changed.
            
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
	    *  @precondition dir != null
	    * @precondition size = 2 or 3 ONLY
	    * @precondition url != null and is a valid string to an image
	    * @precondition window != null
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
		            System.out.println("new x is " + newX + " new y is "+newY);

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
	                   StackUndo.add(s);
	                 //Pertains to undo button
	           		   State currState = new State(s, GridLock.prevState);
	           		   stateList.add(currState);
	           		   prevState = currState;
	          
		            }
		           

	               
	        });
	        	
	        	

	        return s;
	    }
	    
	    
	    /**
	     * A method to create the target sprite, nemo 
	     * @postcondition the target object, nemo is created and placed on the gameboard
	     * @postcondition Monitors the position of nemo and detects the end of the game
	     * @precondition dir != null
	     * @precondition size = 2 or 3 ONLY
	     * @precondition url != null and is a valid string to an image
	     * @precondition window != null
	     * 
	     * @param dir the orientation of the sprite (horizontal/vertical)
	     * @param size determines the size of the sprite, could be 2 spaces or 3 spaces large
	     * @param url location of the image file
	     * @param window used to change scene to exit scene when game is won
	     * @return
	     */
	    private UserSprite makeUserSprite(Sprite.Direction dir, int size, String url, Stage window) {
        	 UserSprite s = new UserSprite(dir, url);
        	 grid.setSpriteOnGrid(s,0,2);
        	 s.setOnMouseDragged(new DragHandler(grid,s));
        	 s.setOnMouseReleased(e -> {
	            int newX = (int) toGrid(s.getLayoutX());
	            int newY = (int) toGrid(s.getLayoutY());
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
                  
                   //Nemo has reached the goal -> go to the exit screen
                   if (newX == 4) {
                	   //Get time taken to complete game and print 
						double finishedTime = t.getTimeFromStart();
						int seconds = t.getSeconds(finishedTime);
						int minutes = t.getMinutes(finishedTime);
						System.out.println("Time taken " + minutes + " Minutes and " + seconds + " Seconds");

						System.out.println("Moves taken " + grid.getMovectr());
						int oldCount=grid.getMovectr(); //get the last move count to display
						//at end of game.
						scene2 = new Scene(exitScreen(window, seconds, minutes, oldCount), CANVAS_HEIGHT, CANVAS_WIDTH);
			
						grid.resetMoveCtr(); //set to 0 again

						((TimerPane) liveClock).set_keep_timing(false);
						
						window.setScene(scene2); //Goes to exit screen.
						
						
                   } else {
                	   
                		System.out.println("move ctr is " + grid.getMovectr());
                   }
                 
                   StackUndo.add(s);
                 //Pertains to undo button
           		   State currState = new State(s, GridLock.prevState);
           		   stateList.add(currState);
           		   prevState = currState;
	            }
	        
	            
        });

        return s;
    }
	    /**
	     * setter for difficulty
	     * @param d Difficulty string
	     */
	    public static void setDifficulty(String d) {
	    		Difficulty = d;
	    }
	    
	    /**
	     * Creating scoreboard menu
	     * @param window scene for the scoreboard
	     * @return root
	     */
	    public AnchorPane ScoreMenu(Stage window) {

	    		AnchorPane root = new AnchorPane();
		        
		        final Image titleScreen = new Image( "HiScoreMenu2.png", CANVAS_WIDTH, CANVAS_HEIGHT, false, false);
		        final ImageView scoreScreen_node = new ImageView();
			    scoreScreen_node.setImage(titleScreen); //set the image of the title screen
			    scoreScreen_node.setPreserveRatio(true);
		        
			    scoreMenu = new Scoreboard(window, this);
			    
			    	scoreMenu.populateScores();
			    
			    root.getChildren().addAll(scoreScreen_node, scoreMenu);
		     
		     return root;
		}
	    
	    
	/**
	 * Resets the gameboard to original position
	 * @author leochen    
	 */
	    public void resetGameBoard(Stage window) {
	    		replay=true; //signal want to use the curr_i as the level and not a new random level.
	    	 	window.setScene(new Scene(createGameBoard(window), CANVAS_HEIGHT, CANVAS_WIDTH));
	    }
}
