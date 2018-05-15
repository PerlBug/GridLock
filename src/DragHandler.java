
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
/**
 *  Handler class for when the user attempts to drag a sprite.
 *  Prevents dragging motions that make the sprite temporarily go off screen then bounce back to the original
 *  valid position (where the drag request originated from). The sprite cannot be dragged off screen now.
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
		//have to add the new offset...
		double leftOffset = (GridLock.CANVAS_WIDTH - (GridLock.WIDTH*GridLock.SQUARE_SIZE))/2;
		double newX= e.getSceneX()- leftOffset;
		double newY=e.getSceneY()- GridLock.CANVAS_HEIGHT*150/800+leftOffset;
		System.out.println("scene x is  " +(e.getSceneX()-20));
		double Xcoord=sprite.getXcoord();
		double Ycoord=sprite.getYcoord();
		//double backXcoord=sprite.getXcoord()+sprite.getSize();
		double mouseX=sprite.getMouseX();
		double mouseY=sprite.getMouseY();

		

    	if(sprite.getDirection()==Sprite.Direction.HORIZONTAL) {
    			double dist= newX-mouseX +Xcoord;
    			
    			//maximum distance the sprite can relocate moving to the right
    			double maxRightDist=GridLock.WIDTH*GridLock.SQUARE_SIZE-GridLock.SQUARE_SIZE *sprite.getSize();
    		
    			
    			// if the user tries to drag the sprite off screen to the right
    			//they are blocked at the grid boundary
    			//100 is the width/height of each grid square
    			//sprite.relocate(furthestX, Ycoord);
    			
    			boolean forwards=false; //figure out if sprite is moving forwards (->) or backwards (<-)
    			if( (int) grid.toGrid(dist) >= (int)grid.toGrid(Xcoord)) {
    				System.out.println("the sprite is moving forwards (dist >=xcoord) , "
    						+ "dist is "+(int) grid.toGrid(dist) + "xcoord is "+(int)grid.toGrid(Xcoord));
    				//System.out.println("THE new X is " +(grid.toGrid(newX)) +"old x is " +grid.toGrid(Xcoord));
    				forwards = true;
    			} //ISSUE is if drag slightly less than start click they think backwards!
    			System.out.println("the sprite is moving backwards (dist <xcoord) , "
						+ "dist is "+(int) grid.toGrid(dist) + "xcoord is "+(int)grid.toGrid(Xcoord) );
    			
    			double furthestX= GridLock.SQUARE_SIZE*(grid.furthestMoveXdirection(sprite, (int)grid.toGrid(Xcoord),
    					(int)grid.toGrid(Ycoord), forwards));
    			//above newX is dist now , new X is random and not very useful anymore idk why
    			
    			
    			if(forwards && (dist > furthestX)) {
    				System.out.println("in if 1, furthest x is "+furthestX +"dist is "+ dist);
    				sprite.relocate(furthestX, Ycoord);
    			}else if(!forwards && ((int)dist < (int)furthestX)) {
    				System.out.println("in if 2(backwards dist <furthestx)"
    						+ ", furthest x is "+furthestX +"dist is "+ dist);
    				sprite.relocate(furthestX, Ycoord);
    			}else if(dist >maxRightDist) {
    				System.out.println("in if 3 (dist > max right dist), furthest x is "+furthestX +"dist is "+ dist);
    				sprite.relocate(maxRightDist, Ycoord); 
    				
    			//user cannot drag off screen to the left
    			}else if(dist <0){
    				System.out.println("in if 4 (dist <0), furthest x is "+furthestX +"dist is "+ dist);
    				sprite.relocate(0, Ycoord);
    			}else{
    				System.out.println("in if 5, furthest x is "+furthestX +"dist is "+ dist);
    			//	double furthestDist = furthestPossibleDist(dist);
    				//find furthest possible poss they can drag to before next obstacle and go there!
    				sprite.relocate(dist, Ycoord);
    			}
        		
        	}else {
        		double dist= e.getSceneY() - mouseY + Ycoord;
        		//maximum distance downwards the sprite can move
        		double maxDownDist= GridLock.HEIGHT*GridLock.SQUARE_SIZE -GridLock.SQUARE_SIZE *sprite.getSize();
        		
        	
        		boolean upwards=true; // is sprite moving up or down?
        		if( (int) grid.toGrid(dist) >= (int)grid.toGrid(Ycoord)) {
    				//y coords get bigger going downwards
    				upwards = false;
    			}
        		System.out.println("MOVING UPWARDS= "+upwards);
        		
        		
        		//find furthest dist sprite can travel upwards or downwards without collision
        		double furthestY= GridLock.SQUARE_SIZE*(grid.furthestMoveYdirection(sprite, (int)grid.toGrid(Xcoord),
    					(int)grid.toGrid(Ycoord), upwards));
        		
        		//if going upwards and the new y coord is less than the max upwards y coord
        		//then only go up to the max upwards y coord 
        		if(upwards && dist < furthestY ) {
        		
        			sprite.relocate(Xcoord, furthestY);
        		// if going downwards and the  dist is > than the max we can go, go to max
        		}else if(!upwards && dist > furthestY) {
        			System.out.println("going backwards dist > furthesty dist is "+dist 
        					+ "fruthest y is "+ furthestY);
        			sprite.relocate(Xcoord, furthestY);
        		//prevent dragging sprite off bottom of the grid
        		}else if(dist >maxDownDist) {
        			
    				sprite.relocate(Xcoord,maxDownDist); 
    				
    			//prevent dragging sprite off top of the grid
    			}else if(dist <0){
    				sprite.relocate(Xcoord, 0);
    			}
    			else{
    			System.out.println("here last else");
    			System.out.println(" dist is "+dist 
    					+ "fruthest y is "+ furthestY);
    				sprite.relocate( Xcoord, dist);
    			}
 	
        	}
     }
		

}