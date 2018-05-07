import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle{

    private Sprite sprite;
    private int spriteID; //Id of the sprite occupying the square.
    private int x;
    private int y;
    
    public Square( int x, int y, int height, int width) { 
        setWidth(width);
        setHeight(height);
        this.x=x;
        this.y=y;
        this.spriteID=-1; //default value

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
    	//System.out.println("setting sprite at " + x +" " + y +"to " + sprite);
        this.sprite = sprite;
        if(sprite!=null) {
        	spriteID=sprite.getID();
        } 
    }

	public void setSpriteID(int i) {
		// TODO Auto-generated method stub
		
	}

   

}
