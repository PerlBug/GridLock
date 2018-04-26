import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
	private Image image;
	private String imageURL;
    private double xPos;
    private double yPos;    
    private double width;
    private double height;
    
    public Sprite(String imageURL, double x, double y, int size) {
    	this.imageURL = imageURL;
    	xPos = x;
    	yPos = y;
    	width = size*100;
    	height = 100;
    	image = new Image(imageURL, width, height, false, false);    	
    }
    
    public void setSize(int size) {
    	width = size*100;
    	height = 100;
    	image = new Image(imageURL, width, height, false, false); 
    	
    }
    
    
    
    public void setPosition(double x, double y) {
    	xPos = x;
    	yPos = y;
    }
    
    public void setImage(String imageURL, double w, double h) {
    	image = new Image(imageURL, w, h, false, false);
    }
    
    public void rotateSprite() {
    	double temp = height; 
    	height = width;
    	width = temp;
    	
    	image = new Image(imageURL, width, height, false, false);
    }
    
    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, xPos, yPos );
    }
 
    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(xPos,yPos,width,height);
    }
 
    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects(this.getBoundary());
    }
    
    
}
