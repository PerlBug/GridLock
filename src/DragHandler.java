
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
/**
 *  Handler class for when the user attempts to drag a sprite.
 *  Prevents dragging motions that make the sprite temporarily go off screen then bounce back to the original
 *  valid position the drag request originated from. The sprite cannot be dragged off screen now.
 *  
 * 
 * @author becca
 *
 */
public class DragHandler implements EventHandler<MouseEvent>{
	
	private Grid grid; //current grid
	private Sprite sprite;
	public DragHandler(Grid g, Sprite s) {
		this.grid=g;
		this.sprite=s;
	}

	@Override
	public void handle(MouseEvent e) {
		double newX= e.getSceneX();
		double newY=e.getSceneY();
		double Xcoord=sprite.getXcoord();
		double Ycoord=sprite.getYcoord();
		double mouseX=sprite.getMouseX();
		double mouseY=sprite.getMouseY();
		

    	if(sprite.getDirection()==Sprite.Direction.HORIZONTAL) {
    			double dist= e.getSceneX()-mouseX +Xcoord;
    			
    		//	System.out.println(dist);
    			
    			// if the user tries to drag the sprite off screen to the right
    			//they are blocked at the grid boundary
    			//100 is the width/height of each grid square
    			if(dist >GridLock.WIDTH-100 *sprite.getSize()) {
    				sprite.relocate(600-100 *sprite.getSize(), Ycoord); 
    				
    			//user cannot drag off screen to the left
    			}else if(dist <0){
    				sprite.relocate(0, Ycoord);
    			}
    			else{
    			//	double furthestDist = furthestPossibleDist(dist);
    				//find furthest possible poss they can drag to before next obstacle and go there!
    				sprite.relocate(dist, Ycoord);
    			}
        		
        	}else {
        		double dist= e.getSceneY() - mouseY + Ycoord;
        		//prevent dragging sprite off bottom of the grid
        		if(dist >GridLock.HEIGHT -100 *sprite.getSize()) {
    				sprite.relocate(Xcoord,600-100 *sprite.getSize()); //or 300 for trucks..
    				
    			//prevent dragging sprite off top of the grid
    			}else if(dist <0){
    				sprite.relocate(Xcoord, 0);
    			}
    			else{
    			
    				sprite.relocate( Xcoord, dist);
    			}
 	
        	}
     }
		

}