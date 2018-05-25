/**
 * Counter.java written for COMP2511 18s1 Project - Group 3
 * GitHub IDs:
 * 
 * @authors
 * 5162531 Diaz, Rebecca Avril: beccaD6 - <becca.diaz6@gmail.com>
 * 3461044 Kala, Shilpa: sk2552 - <simple.snowflake@gmail.com>
 * 3463869 Sun, Elliott Yongrui: umeb0shi - <elly.here@gmail.com>
 * 5157086 Galoyan, Mkrtich: PerlBug - <galoyanmko@gmail.com>
 * 5060239 Chen, Leo Jia Jian: leochen15 - <leochen1512@gmail.com>
 * 
 * This is a class for a move counter object
 */

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Counter extends StackPane {
	private Text countShow = new Text();
	private int count;
	private double size = GridLock.CANVAS_WIDTH/5;
	public Counter(String url) {
		Image imgPre = new Image(url, size, size, false, false);
		ImageView img = new ImageView(imgPre);
		this.count = 0;
		
		countShow.setText("Moves: \n" + Integer.toString(getCount()));
		
		getChildren().addAll(img, countShow);
		
		setAlignment(Pos.CENTER);
		
	}
	/**
	 * method to get count
	 * @return
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * method to set count and uses toString to display the number of Moves counter records
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
		this.countShow.setText("Moves: " + Integer.toString(getCount()));
	}
}
