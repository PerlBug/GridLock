/**
 * UserSprite.java written for COMP2511 18s1 Project - Group 3
 * 
 * @authors
 * 5162531 Diaz, Rebecca Avril: beccaD6 - <becca.diaz6@gmail.com>
 * 3461044 Kala, Shilpa: sk2552 - <simple.snowflake@gmail.com>
 * 3463869 Sun, Elliott Yongrui: umeb0shi - <elly.here@gmail.com>
 * 5157086 Galoyan, Mkrtich: PerlBug - <galoyanmko@gmail.com>
 * 5060239 Chen, Leo Jia Jian: leochen15 - <leochen1512@gmail.com>
 * 
 * The target sprite, nemo, which has to be moved to the outside
 * 
 */

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.*;

public class UserSprite extends Sprite {
	
	
	/**
	 * Constructor method always fixes the starting position of nemo sprite and the image associated with it
	 * @param dir
	 * @param url
	 */
	public UserSprite(Direction dir, String url) {
		
		 super(dir, 0, 2, GridLock.CAR_SIZE, "file:sprites/nemo.png"); 
		
		 
	}


	
}
