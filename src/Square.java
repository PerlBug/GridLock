import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle{

    private Sprite sprite;
    
    public Square( int x, int y, int height, int width) { 
        setWidth(width);
        setHeight(height);

        relocate(x * width, y * height);

        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }

    public boolean hasSprite() {
        return sprite != null;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

   

}
