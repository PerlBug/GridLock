/**
 * State class to track moves
 * @author leochen
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
