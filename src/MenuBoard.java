import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
/**
 * Menu board. Consists of Menu Buttons. Can revert to Options Menu and Scoreboard here too.
 * 
 * @author leochen
 *
 */
public class MenuBoard extends Parent{
	
	private VBox menu1, menu2;
	
	
	public MenuBoard (Stage window, GridLock g) {
	     
	     MenuButton play_button = new MenuButton("Play", "StoneButton.png");
	     MenuButton score_button = new MenuButton("Score", "StoneButton.png");
	     MenuButton option_button = new MenuButton("Options", "StoneButton.png");
	     MenuButton exit_button = new MenuButton("Exit", "StoneButton.png");
	     MenuButton instruction_button = new MenuButton("Instructions", "StoneButton.png");
	     /*
	      * Set up Options Menu
	      */
	     menu2 = createOptionsMenu();
	     
	     /*
	      * Set up action calls for Main Menu buttons
	      */
	     play_button.setOnMouseClicked(e -> window.setScene(g.getGame(window)));
	     score_button.setOnMouseClicked(e -> window.setScene(g.getGame(window)));
	     option_button.setOnMouseClicked(e -> {
	    	 	getChildren().remove(0);
	    	 	getChildren().add(menu2);
	     });
	     exit_button.setOnMouseClicked(e -> {
	    	 	Platform.exit();
	    	 	System.exit(0);
	     });
	     instruction_button.setOnMouseClicked(e -> window.setScene(g.getInstruction(window)));
	     
	     /*
	      * create the container of main menu buttons in a vertical box
	      */
	     
	     menu1 = new VBox(10);
	     menu1.setAlignment(Pos.TOP_CENTER);
	    	 menu1.setTranslateX((GridLock.CANVAS_WIDTH/2) - play_button.getWidthRel()/2);
	    	 menu1.setTranslateY((GridLock.CANVAS_HEIGHT/2) - 40);
	     menu1.getChildren().addAll(play_button, score_button, option_button, exit_button, instruction_button);
	     
	     
	     
	     getChildren().add(menu1); //add the title screen and button container to the stackpane
	     
	}
	
		private VBox createOptionsMenu() {
			
			VBox root = new VBox(10);
			
			
			ChoiceBox<String> diffMenu = new ChoiceBox<String>();
			
			diffMenu.getItems().addAll("Easy", "Medium", "Hard");
			diffMenu.setValue("Medium");
			
			ScrollBar s1 = new ScrollBar();
			Button confirm = new Button("OK");
			confirm.setOnMouseClicked(e -> {
				GridLock.setDifficulty(diffMenu.getValue());
				getChildren().remove(0);
				getChildren().add(menu1);
			
			});
			Button b1 = new Button("Back");
			b1.setOnMouseClicked(e -> {
				getChildren().remove(0);
				getChildren().add(menu1);
			});
			root.setTranslateX(200);
			root.setTranslateY(300);
			root.getChildren().addAll(s1,diffMenu,  confirm, b1);
			
			return root;
		}
		
}