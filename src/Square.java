import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle{

    private Sprite sprite;
    private int spriteID; //Id of the sprite occupying the square.
    
    public Square( int x, int y, int height, int width) { 
        setWidth(width);
        setHeight(height);

        relocate(x * width, y * height);

        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }

    public boolean hasSprite() {
        return sprite != null;
    	//return spriteID < 0;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

   

}
