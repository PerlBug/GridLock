import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class GridLock extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GridLock");
        
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
}