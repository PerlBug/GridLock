/**
 * DragHandler.java written for COMP2511 18s1 Project - Group 3
 * GitHub IDs:
 * 
 * @authors
 * 5162531 Diaz, Rebecca Avril: beccaD6 - <becca.diaz6@gmail.com>
 * 3461044 Kala, Shilpa: sk2552 - <simple.snowflake@gmail.com>
 * 3463869 Sun, Elliott Yongrui: umeb0shi - <elly.here@gmail.com>
 * 5157086 Galoyan, Mkrtich: PerlBug - <galoyanmko@gmail.com>
 * 5060239 Chen, Leo Jia Jian: leochen15 - <leochen1512@gmail.com>
 * 
 * Handler class for when the user attempts to drag a sprite.
 * Prevents dragging motions that make the sprite temporarily go off screen or overlap another sprite then
 * bounce back to the original
 * valid position (where the drag request originated from).  
 */
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DragHandler implements EventHandler<MouseEvent>{
	
	private Grid grid; 
	private Sprite sprite;
	
	
	/**
	 * Create a new DragHandler object
	 * @param g is the grid for the current game
	 * @param s is the sprite being dragged.
	 */
	public DragHandler(Grid g, Sprite s) {
		this.grid=g;
		this.sprite=s;
	}

	
	/**
	 * Controls the range where a sprite can be dragged across the screen.
	 * In particular,the sprite cannot be dragged off the grid boundary, in the wrong direction,
	 *  or partially/fully over another sprite.
	 * 
	 */
	@Override
	public void handle(MouseEvent e) {
		//horizontal offset of the grid on the main canvass
		double leftOffset = (GridLock.CANVAS_WIDTH - (GridLock.WIDTH*GridLock.SQUARE_SIZE)-65*GridLock.widthScale*0.9)/2; 
		double newX= e.getSceneX()- leftOffset;
		double newY=e.getSceneY();
		
		//The coordinates of the sprite within the grid
		double Xcoord=sprite.getXcoord(); 
		double Ycoord=sprite.getYcoord();
		//The coordinates of where the mouse clicked before dragging the sprite
		double mouseX=sprite.getMouseX();
		double mouseY=sprite.getMouseY();

		sprite.setPrevX(Xcoord);
		sprite.setPrevY(Ycoord);
	
    	if(sprite.getDirection()==Sprite.Direction.HORIZONTAL) {
    		
    			double dist= newX-mouseX +Xcoord; //the new position we want to travel to with no restrictions
    			
    			//Maximum distance the sprite can move to the right
    			//(note that the coordinates of the sprite are for the first grid square
    			// on the left side of the sprite)
    			//so max right distance is when the first grid square is at x=2 or x=3 , and the back end of the
    			//sprite is at x=5
    			double maxRightDist=GridLock.WIDTH*GridLock.SQUARE_SIZE - GridLock.SQUARE_SIZE *sprite.getSize();
    		
    		
    			//determine if the sprite is moving forwards (to the right) or backwards
    			boolean forwards=false; 
    			
    			//sprite is moving forwards if we want to move to a grid square (toGrid(dist)) whose
    			//x coord is greater than where we currently are (toGrid(Xcoord))
    			if( (int) grid.toGrid(dist) >= (int)grid.toGrid(Xcoord)) {
    				forwards = true;
    			} 
    			
    			//calculate the farthest x distance/position we can travel before having to stop
    			//due to an obstacle
    			//note that the furthestX distance/position is different depending on if we are moving forward or back.
    			double furthestX= GridLock.SQUARE_SIZE*(grid.furthestMoveXdirection(sprite, (int)grid.toGrid(Xcoord),
    					(int)grid.toGrid(Ycoord), forwards));
    			
    			//if we are traveling forwards and want to move beyond the furthestX,
    			//this is not valid(cannot drag over an obstacle).
    			
    			if(forwards && (dist > furthestX)) {
    				sprite.relocate(furthestX, Ycoord);
    				
    			//if moving backwards and want to move beyond an obstacle, 
    				//also the drag is blocked
    			}else if(!forwards && (dist <= furthestX) ) {
    			
    				sprite.relocate(furthestX, Ycoord);
    			
    			//if the distance is out of the grid bounds to the right, drag is blocked at the boundary
    			}else if(dist >maxRightDist) {
    				sprite.relocate(maxRightDist, Ycoord); 
    				
    			//user cannot drag off screen to the left
    			}else if(dist <0){
    				sprite.relocate(0, Ycoord);
    			}else{
    				
    				sprite.relocate(dist, Ycoord);
    			}
        		
        	}else {
        		//distance we want to travel
        		double dist= e.getSceneY() - mouseY + Ycoord;
        		//maximum distance downwards  the sprite can move
        		double maxDownDist= GridLock.HEIGHT*GridLock.SQUARE_SIZE -GridLock.SQUARE_SIZE *sprite.getSize();
        		
        	
        		boolean upwards=true; // is sprite moving up or down?
        		//moving down if the y-coord/vertical position we want to travel to is greater than where we originated from.
        		// (y coords get bigger going downwards)
        		if( (int) grid.toGrid(dist) >= (int)grid.toGrid(Ycoord)) {
    			
    				upwards = false;
    			}
        
        		//find the farthest distance the sprite can travel upwards or downwards before collision
        		double furthestY= GridLock.SQUARE_SIZE*(grid.furthestMoveYdirection(sprite, (int)grid.toGrid(Xcoord),
    					(int)grid.toGrid(Ycoord), upwards));
        		
        		//if going upwards and the new y coord is less than the furthestY coord before an obstacle
        		//then only go up to the furthestY
        		if(upwards && dist <= furthestY ) {
        		
        			sprite.relocate(Xcoord, furthestY);
        		// if going downwards and the  dist is > than the furthestY, block the drag
        		}else if(!upwards && dist >= furthestY) {
        			
        			sprite.relocate(Xcoord, furthestY);
        		//prevent dragging sprite off bottom of the grid
        		}else if(dist > maxDownDist) {
        			
    				sprite.relocate(Xcoord,maxDownDist); 
    				
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
