/**
 * MenuButton.java written for COMP2511 18s1 Project - Group 3
 * GitHub IDs:
 * 
 * @authors
 * 5162531 Diaz, Rebecca Avril: beccaD6 - <becca.diaz6@gmail.com>
 * 3461044 Kala, Shilpa: sk2552 - <simple.snowflake@gmail.com>
 * 3463869 Sun, Elliott Yongrui: umeb0shi - <elly.here@gmail.com>
 * 5157086 Galoyan, Mkrtich: PerlBug - <galoyanmko@gmail.com>
 * 5060239 Chen, Leo Jia Jian: leochen15 - <leochen1512@gmail.com>
 * 
 *  Main Buttons for Main Page
 *  
 */

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MenuButton extends StackPane {
	
	private Text text;
	private ImageView btn;
	
	private double heightRel; //height and width of the menu button relative to the canvas size
	private double widthRel;
	
	
	public double getHeightRel() {
		return heightRel;
	}


	public double getWidthRel() {
		return widthRel;
	}


	public MenuButton(String name, String img) {
		text = new Text(name);
		text.setFont(text.getFont().font(20));
		text.setFill(Color.WHITE);
		heightRel = GridLock.CANVAS_HEIGHT/16;
		widthRel = GridLock.CANVAS_WIDTH/3;
		System.out.println("Runs here");
		Image btnImg = new Image(img, widthRel, heightRel, false, false);
		btn = new ImageView(btnImg);
		btn.setEffect(new Glow());
		
		setAlignment(Pos.CENTER);
		getChildren().addAll(btn, text);
		
		setOnMouseEntered(event -> {
			btn.setTranslateX(10);
			text.setTranslateX(10);
			text.setFill(Color.BLACK);
		});
		
		setOnMouseExited(event -> {
			btn.setTranslateX(0);
			text.setTranslateX(0);
			text.setFill(Color.WHITE);
		});
		
		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());
		
		setOnMousePressed(event -> {
			setEffect(drop);
		});
		setOnMouseReleased(event -> {
			setEffect(null);
		});
		
	}
	
	public MenuButton(String name, String img, double x, double y) {
		text = new Text(name);
		text.setFont(text.getFont().font(20));
		text.setFill(Color.WHITE);
		heightRel = y;
		widthRel = x;
		System.out.println("Runs here");
		Image btnImg = new Image(img, widthRel, heightRel, false, false);
		btn = new ImageView(btnImg);
		btn.setEffect(new Glow());
		
		setAlignment(Pos.CENTER);
		getChildren().addAll(btn, text);
		
		setOnMouseEntered(event -> {
			btn.setScaleX(1.1);
			btn.setScaleY(1.1);
			btn.setTranslateX(10);
			text.setTranslateX(10);
			text.setFill(Color.BLACK);
		});
		
		setOnMouseExited(event -> {
			btn.setTranslateX(0);
			text.setTranslateX(0);
			text.setFill(Color.WHITE);
		});
		
		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());
		
		setOnMousePressed(event -> {
			setEffect(drop);
		});
		setOnMouseReleased(event -> {
			setEffect(null);
		});
		
	}
	
	public void removeTranslate(double x) {
		this.btn.setTranslateX(x);
		this.text.setTranslateX(x);
		setOnMouseEntered(e -> text.setFill(Color.BLACK));
		setOnMouseExited(e -> text.setFill(Color.WHITE));
	}
	
	public void setSize(double x, double y) {
		
	}
}
