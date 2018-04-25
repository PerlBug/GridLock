import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 



public class GridLock extends Application {
	private ArrayList<Square> grid = new ArrayList<Square>();

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600, Color.WHITE);
        
        
        //populating grid array with squares
        for(int i = 0; i < 6; i++) {
        	for(int j = 0; j < 6; j++) {
        		Square square = new Square(j*100,i*100 , 100);
                square.setFill(Color.TRANSPARENT);
                square.setStroke(Color.BLACK);
                grid.add(square);
        	}
        	
        }
        
        
      
         //adding arraylist of grid to the window
        root.getChildren().addAll(grid);
 
        primaryStage.setTitle("GridLock");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}