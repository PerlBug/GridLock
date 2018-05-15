import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Move Counter Object
 * @author leochen
 *
 */
public class Counter extends StackPane {
	private Text countShow = new Text();
	private int count;
	
	public Counter(String url) {
		Image imgPre = new Image(url, 120, 120, false, false);
		ImageView img = new ImageView(imgPre);
		this.count = 0;
		
		countShow.setText("Moves: \n" + Integer.toString(getCount()));
		
		getChildren().addAll(img, countShow);
		
		setAlignment(Pos.CENTER);
		
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
		this.countShow.setText("Moves: " + Integer.toString(getCount()));
	}
}
