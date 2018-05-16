import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuBoard extends Parent{
	
	private VBox menu1, menu2;
	
	
	public MenuBoard (Stage window, GridLock g) {

		//final Image titleScreen = new Image( "Title Page.png", CANVAS_WIDTH, CANVAS_HEIGHT, false, false); //title screen image
//	     final Image playButton = new Image("Play.png", 150, 100, false, false); //the play button image
//	     final Image scoreButton = new Image("Score.png", 150, 100, false, false); //the score button image
	     
	     
	     //final ImageView menuScreen_node = new ImageView();
	     //menuScreen_node.setImage(titleScreen); //set the image of the title screen
	     //menuScreen_node.setPreserveRatio(true);
	  
//	     final Button play_button  = new Button();
//	     final ImageView play_button_node = new ImageView(); 
//	      
//	     final Button score_button = new Button();
//	     final ImageView score_button_node = new ImageView(); 
	     
	     MenuButton play_button = new MenuButton(" ", "Play.png");
	     MenuButton score_button = new MenuButton(" ", "Score.png");
	     //MenuButton option_button = new MenuButton(" ", "Options.png");
	     
//	     play_button_node.setImage(playButton); //set the image of the play button
//	     score_button_node.setImage(scoreButton); //set the image of the score button
//	      
//	     play_button.setGraphic(play_button_node);
//	     play_button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))); //this is to make the button background transparent
//	     play_button.setScaleShape(true);
//	     
//	     score_button.setGraphic(score_button_node);
//	     score_button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
//	     play_button.setMaxWidth(Double.MAX_VALUE); //Ensures that both buttons are of the same size
//	     score_button.setMaxWidth(Double.MAX_VALUE);
//	     option_button.setMaxWidth(Double.MAX_VALUE);
	     
	     /*
	      * Set up Options Menu
	      */
	     menu2 = createOptionsMenu();
	     
	     /*
	      * Set up action calls for Main Menu buttons
	      */
	     play_button.setOnMouseClicked(e -> window.setScene(g.getGame()));
	     score_button.setOnMouseClicked(e -> window.setScene(g.getGame()));
	     
	     /* OPTION BUTTON REMOVED TEMPORARILY
	     option_button.setOnMouseClicked(e -> {
	    	 	getChildren().remove(0);
	    	 	getChildren().add(menu2);
	     });
	     */
	     
	     /*
	      * create the container of main menu buttons in a vertical box
	      */
	     
	     menu1 = new VBox(10);
	     menu1.setAlignment(Pos.TOP_CENTER);
	    	 menu1.setTranslateX(250);
	    	 menu1.setTranslateY(350);
	     menu1.getChildren().addAll(play_button, score_button);
	     
	     
	     
	     getChildren().add(menu1); //add the title screen and button container to the stackpane
	     
	}
	
		private VBox createOptionsMenu() {
			
			VBox root = new VBox(10);
			ScrollBar s1 = new ScrollBar();
			Button b1 = new Button("Back");
			b1.setOnMouseClicked(e -> {
				getChildren().remove(0);
				getChildren().add(menu1);
			});
			root.setTranslateX(200);
			root.setTranslateY(300);
			root.getChildren().addAll(s1, b1);
			
			return root;
		}
		
}