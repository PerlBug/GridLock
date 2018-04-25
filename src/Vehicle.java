import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Vehicle extends Canvas {
	
	private int size;
	private GraphicsContext gc = this.getGraphicsContext2D();
	private String imagePath;
	
	public Vehicle(int size, String imagePath) {
		super();
		this.size = size;
		this.setImagePath(imagePath);
	}
	
	public int getSize() {
		return size;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public GraphicsContext getGc() {
		return gc;
	}
}
