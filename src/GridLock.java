
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("GridLock");
    	primaryStage.setScene(scene);
    	Canvas canvas = new Canvas(600,600);
    	GraphicsContext gc = canvas.getGraphicsContext2D();
    	root.getChildren().add(canvas);
    	root.getChildren().addAll(grid.getListOfSquares());
    	
    	Sprite block = new Sprite("file:sprites/playercar.png",300, 300, 3, Sprite.Direction.HORIZONTAL );
    	block.setSize(2);
    	block.setPosition(400, 400); 
       	block.render(gc);
    	primaryStage.show();
    	
    	
    }
}