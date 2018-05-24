/**
 * State class to track moves
 * @author leochen
 *
 */
public class State {
	private Sprite prevSprite;
	private State prevState;
	
	public State (Sprite x, State s) {
		/*
		 * All are start point x and y coordinates
		 */
		this.prevSprite = x;
		prevState = s;
	}
	
	public Sprite getSprite() {
		return this.prevSprite;
	}
	
	public State getPrevState() {
		return this.prevState;
	}
}
