/**
 * State.java written for COMP2511 18s1 Project - Group 3
 * 
 * @authors
 * 5162531 Diaz, Rebecca Avril: beccaD6 - <becca.diaz6@gmail.com>
 * 3461044 Kala, Shilpa: sk2552 - <simple.snowflake@gmail.com>
 * 3463869 Sun, Elliott Yongrui: umeb0shi - <elly.here@gmail.com>
 * 5157086 Galoyan, Mkrtich: PerlBug - <galoyanmko@gmail.com>
 * 5060239 Chen, Leo Jia Jian: leochen15 - <leochen1512@gmail.com>
 * 
 * State class to track moves of sprites
 * 
 */

public class State {
	private Sprite prevSprite;
	private State prevState;
	
	/**
	 * Constructor for state class
	 * @param x the sprite
	 * @param s the initial position of the sprite
	 */
	public State (Sprite x, State s) {
		/*
		 * All are start point x and y coordinates
		 */
		this.prevSprite = x;
		prevState = s;
	}
	
	/**
	 * returns the previous sprite
	 * @return this.prevSprite
	 */
	public Sprite getSprite() {
		return this.prevSprite;
	}
	
	/**
	 * returns the previous state(position) of the sprite
	 * @return this.prevState
	 */
	public State getPrevState() {
		return this.prevState;
	}
}
