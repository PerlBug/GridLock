import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
/**
 * Menu board. Consists of Menu Buttons. Can revert to Options Menu and Scoreboard here too.
 * Extends Parent node which can be easily fitted into Game scene
 * Holds file access variable which can be accessed globally. Variable holds location where hi scores are saved.
 * 
 * @author leochen
 *
 */
public class MenuBoard extends Parent{
	
	private VBox menu1;
	private AnchorPane menu2;
	public static String fileAccess = "Default Hi Scores";
	public static boolean flag = true;
	
	/**
	 * MenuBoard constructor: creates buttons and actions for these buttons.
	 * Initiliases scoreboard
	 * @param window: Links to primaryStage
	 * @param g: Links to original GridLock set up
	 */
	public MenuBoard (Stage window, GridLock g) {
	     
	     MenuButton play_button = new MenuButton("Play", "StoneButton.png");
	     MenuButton score_button = new MenuButton("Score", "StoneButton.png");
	     MenuButton option_button = new MenuButton("Options", "StoneButton.png");
	     MenuButton exit_button = new MenuButton("Exit", "StoneButton.png");
	     MenuButton instruction_button = new MenuButton("Instructions", "StoneButton.png");
	     
	     /*
	      * Set up Options Menu
	      */
	     menu2 = createOptionsMenu(window);
	     
	     /*
	      * Set up action calls for Main Menu buttons
	      * if Hi Scores disabled, you are unable to access Scoreboard.
	      */
	     
	     score_button.setOnMouseClicked(e -> {
	    	 	if(flag != false) {
	    	 		Scene scoreScene = new Scene(g.ScoreMenu(window), GridLock.CANVAS_HEIGHT, GridLock.CANVAS_WIDTH);
	    	 		window.setScene(scoreScene);
	    	 	}
	     });
	     //Play Button
	     play_button.setOnMouseClicked(e -> window.setScene(g.getGame(window)));
	     
	     //Options Button: When clicked, replaces current VBox with new AnchorPane.
	     option_button.setOnMouseClicked(e -> {
	    	 	getChildren().remove(0);
	    	 	getChildren().add(menu2);
	     });
	     
	     //Exits platform
	     exit_button.setOnMouseClicked(e -> {
	    	 	Platform.exit();
	    	 	System.exit(0);
	     });
	     
	     //Enters Instruction Manual
	     instruction_button.setOnMouseClicked(e -> window.setScene(g.getInstruction(window)));
	     
	     /*
	      * create the container of main menu buttons in a vertical box
	      */
	     
	     menu1 = new VBox(10);
	     menu1.setAlignment(Pos.TOP_CENTER);
	    	 menu1.setTranslateX((GridLock.CANVAS_WIDTH/2) - play_button.getWidthRel()/2);
	    	 menu1.setTranslateY((GridLock.CANVAS_HEIGHT/2) - 40*GridLock.widthScale);
	     menu1.getChildren().addAll(play_button, score_button, option_button, exit_button, instruction_button);
	     
	     
	     
	     getChildren().add(menu1); //add the title screen and button container to the stackpane
	     
	}
	
	/**
	 * Creates the options menu. User Stories: Being able to choose difficulty, being able to turn on and off hi scores
	 * @param window
	 * @return
	 */
		private AnchorPane createOptionsMenu(Stage window) {
			
			AnchorPane root = new AnchorPane();
			
			Label difficultyLbl = new Label("Difficulty: ");
			difficultyLbl.setFont(new Font(20));
			
			ChoiceBox<String> diffMenu = new ChoiceBox<String>();
			
			diffMenu.getItems().addAll("Easy", "Medium", "Hard");
			diffMenu.setValue("Medium");
			
			final HBox diffB = new HBox(20*GridLock.widthScale);
			diffB.setTranslateX(-30*GridLock.widthScale);
			final HBox scoreB = new HBox(10*GridLock.widthScale);
			scoreB.setTranslateX(-30*GridLock.widthScale);
			final HBox confirmB = new HBox(20*GridLock.widthScale);
			final HBox HSclearB = new HBox(30*GridLock.widthScale);
			HSclearB.setTranslateX(-30*GridLock.widthScale);
			
			diffB.getChildren().addAll(difficultyLbl, diffMenu);
			
			MenuButton confirm = new MenuButton("OK", "StoneButton.png");
			confirm.setScaleX(0.5);
			confirm.setScaleY(0.7);
			confirm.removeTranslate(0);
			confirm.setOnMouseClicked(e -> {
				GridLock.setDifficulty(diffMenu.getValue());
				getChildren().remove(0);
				getChildren().add(menu1);
			
			});
			
			MenuButton b1 = new MenuButton("Back", "StoneButton.png");
			b1.setScaleX(0.5);
			b1.setScaleY(0.7);
			b1.removeTranslate(0);
			b1.setOnMouseClicked(e -> {
				getChildren().remove(0);
				getChildren().add(menu1);
			});
			
			confirmB.setTranslateX(-100*GridLock.widthScale);
			confirmB.getChildren().addAll(confirm, b1);
			
			Label clearHS = new Label("Clear Hi Scores?  ");
			clearHS.setFont(new Font(20));
			Button clearHiScores = new Button("Remove");
			
			HSclearB.getChildren().addAll(clearHS, clearHiScores);
			
			//Set a prompt which tells the user that current Hi scores will be deleted.
			Label enableLbl = new Label("Enable Hi Scores:  ");
			enableLbl.setFont(new Font(20));
			
			Image thumbUp = new Image("thumbUp.png", 30*GridLock.widthScale, 30*GridLock.heightScale, false, false);
			Image thumbDown = new Image("thumbDown.png", 30*GridLock.widthScale, 30*GridLock.heightScale, false, false);
			
			ImageView HSthumbUp = new ImageView(thumbUp);
			ImageView HSthumbDown = new ImageView(thumbDown);
	
			Tooltip t = new Tooltip("Current File Destination: " + fileAccess + "\n" + "To set new Location: Click Here");
			
			DropShadow drop = new DropShadow(30*GridLock.widthScale, Color.YELLOW);
			drop.setInput(new Glow());
			
			if(flag == true) {
				HSthumbDown.setEffect(null);
				HSthumbUp.setEffect(drop);
				Tooltip.install(HSthumbUp, t);
			} else if ( flag == false) {
				HSthumbDown.setEffect(drop);
				HSthumbUp.setEffect(null);
				Tooltip.uninstall(HSthumbUp, t);
			}
			
			HSthumbUp.setOnMouseEntered(e -> {
				HSthumbUp.setScaleY(1.2);
				HSthumbUp.setScaleX(1.2);
				});
			HSthumbUp.setOnMouseExited(e -> {
				HSthumbUp.setScaleX(1/1.2);
				HSthumbUp.setScaleY(1/1.2);
			});
			HSthumbDown.setOnMouseEntered(e -> {
				HSthumbDown.setScaleY(1.2);
				HSthumbDown.setScaleX(1.2);
				});
			HSthumbDown.setOnMouseExited(e -> {
				HSthumbDown.setScaleX(1/1.2);
				HSthumbDown.setScaleY(1/1.2);
			});
			
			
			//Edit location to save Hi Score File. Choose directory which you want this saved into and a file will
			//be created within that folder
			DirectoryChooser fileChoose = new DirectoryChooser();
			HSthumbUp.setOnMouseClicked(e -> {
			File file = fileChoose.showDialog(window);
				if(file != null) {
					setFileAccess(file.getPath()+"/HiScores");
					System.out.println(file.getPath()+"/HiScores");
				}
			});
			
			HSthumbDown.setOnMouseClicked(e -> {
				setFlag(false);
				HSthumbDown.setEffect(new Glow());
				HSthumbUp.setEffect(null);
				Tooltip.uninstall(HSthumbUp, t);
			});
			
			scoreB.getChildren().addAll(enableLbl, HSthumbUp, HSthumbDown);
			
			clearHiScores.setOnMouseClicked(e -> {
				try {
					clearHiScores();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					System.out.println("File doesn't exist: no Hi Scores saved");
					e1.printStackTrace();
				}
			});
			
			//These numbers work in windows
			root.setTranslateX(125);
			root.setTranslateY(250);
			AnchorPane.setTopAnchor(diffB, 0.0*GridLock.heightScale);
			AnchorPane.setTopAnchor(scoreB, 50.0*GridLock.heightScale);
			AnchorPane.setTopAnchor(HSclearB, 100.0*GridLock.heightScale);
			AnchorPane.setTopAnchor(confirmB, 250.0*GridLock.heightScale);
			root.getChildren().addAll(diffB,  HSclearB, scoreB , confirmB);
			
			return root;
		}
			
	private static void setFileAccess(String e) {
		fileAccess = e;
	}
	
	private static void setFlag(boolean t) {
		flag = t;
	}
	
	private void clearHiScores() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileAccess);
		pw.close();
		
	}
		
}