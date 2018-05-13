import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Menu Buttons for Main Page
 * @author leochen
 *
 */


public class MenuButton extends StackPane {
	
	private Text text;
	private ImageView btn;
	
	
	public MenuButton(String name, String img) {
		text = new Text(name);
		text.setFont(text.getFont().font(20));
		text.setFill(Color.WHITE);
		
		System.out.println("Runs here");
		Image btnImg = new Image(img, 150, 50, false, false);
		btn = new ImageView(btnImg);
		btn.setEffect(new Glow());
		
		setAlignment(Pos.CENTER);
		setRotate(-0.5);
		getChildren().addAll(btn, text);
		
		setOnMouseEntered(event -> {
			btn.setTranslateX(10);
			text.setTranslateX(10);
			text.setFill(Color.BLACK);
		});
		
		setOnMouseExited(event -> {
			btn.setTranslateX(0);
			text.setTranslateX(0);
			text.setFill(Color.WHITE);
		});
		
		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());
		
		setOnMousePressed(event -> {
			setEffect(drop);
		});
		setOnMouseReleased(event -> {
			setEffect(null);
		});
		
	}
}
