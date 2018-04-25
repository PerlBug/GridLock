
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GridLock extends Application {
	private Grid grid = new Grid();

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600, Color.GREY);
        
         //adding arraylist of grid to the window
        root.getChildren().addAll(grid.getListOfSquares());
 
        primaryStage.setTitle("GridLock");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}