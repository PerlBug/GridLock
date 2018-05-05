import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
/**
 * Class that creates and displays an alert box which congratulates the user for solving the game and asks
 * if they want to play again.
 * 
 *
 */

public class AlertBox {
	
	static boolean answer;
	//example use :
	/*boolean res;
	 * goalSquare.setOnAction(e->{
		 res= AlertBox.display();
	});
	
	* if res==true .... generate and display new puzzle
	* ...else exit the program
	*/
	
	/**
	 * Creates a new alert box and collects user input to a question.
	 * @return the users answer( true or false)
	 */
	public boolean display() {
		Stage window=new Stage();
		//make user click out of this window before continuing
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("");
		window.setMinWidth(250);
		
		Label l=new Label();
		l.setText("Congratulations you solved the puzzle!");
		
		Label l2=new Label();
		l2.setText("Solve a new Puzzle?");
		
		
		
		
		Button yesButton= new Button("Yes");
		Button noButton= new Button ("No");
		yesButton.setOnAction(e->{
			
			answer=true;
			window.close(); //main program sees true and 
							//can then generate a new puzzle
			
		});
		noButton.setOnAction(e->{
			
			answer=false;
			window.close(); //main program receives false and can then shut down the program
			
		});
		
		VBox layout= new VBox(30);
		layout.getChildren().addAll(l,l2,yesButton,noButton);
		layout.setAlignment(Pos.CENTER);	
		Scene scene= new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
		
	}

}
