import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Scoreboard extends AnchorPane {
	
	TableView<Score> EasyTab = new TableView<Score>();
	TableView<Score> MedTab = new TableView<Score>();
	TableView<Score> HardTab = new TableView<Score>();
	ObservableList<Score> dataE = FXCollections.observableArrayList();
	ObservableList<Score> dataM = FXCollections.observableArrayList();
	ObservableList<Score> dataH = FXCollections.observableArrayList();
	private double tableSize;
	
	@SuppressWarnings("unchecked")
	public Scoreboard(Stage window, GridLock g) {
		
		TabPane scoreboard = new TabPane();
		scoreboard.setPrefSize(GridLock.CANVAS_WIDTH/2, GridLock.CANVAS_WIDTH/2);
		tableSize = GridLock.CANVAS_WIDTH/2;
		AnchorPane.setTopAnchor(scoreboard,  200.0);
		AnchorPane.setBottomAnchor(scoreboard, 350.0);
		AnchorPane.setLeftAnchor(scoreboard, 100.0);
		AnchorPane.setRightAnchor(scoreboard, 100.0);
		
		Tab easy = new Tab();
		Tab medium = new Tab();
		Tab hard = new Tab();
		
		easy.setText("Easy");
		medium.setText("Medium");
		hard.setText("Hard");
		
		TableColumn<Score, String> nameE = new TableColumn<Score, String> ("Name");
		TableColumn nameM = new TableColumn ("Name");
		nameM.setMinWidth(tableSize/3);
		nameM.setMaxWidth(tableSize/3);
		TableColumn<Score, String> nameH = new TableColumn<Score, String> ("Name");
		
		nameE.setCellValueFactory(
				new PropertyValueFactory<Score, String>("name"));
		nameM.setCellValueFactory(
				new PropertyValueFactory<Score, String>("name"));
		nameH.setCellValueFactory(
				new PropertyValueFactory<Score, String>("name"));
		
		
		TableColumn<Score, Integer>MovesE = new TableColumn<Score, Integer>("Moves");
		TableColumn MovesM = new TableColumn ("Moves");
		MovesM.setMinWidth(tableSize/3);
		MovesM.setMaxWidth(tableSize/3);
		TableColumn<Score, Integer>MovesH = new TableColumn<Score, Integer>("Moves");
		
		MovesE.setCellValueFactory(
				new PropertyValueFactory<Score, Integer>("moves"));
		MovesM.setCellValueFactory(
				new PropertyValueFactory<Score, Integer>("moves"));
		MovesH.setCellValueFactory(
				new PropertyValueFactory<Score, Integer>("moves"));
		
		TableColumn<Score, String>StringE = new TableColumn<Score, String>("Time");
		TableColumn StringM = new TableColumn ("Time");
		StringM.setMinWidth(tableSize/3);
		StringM.setMaxWidth(tableSize/3);
		TableColumn<Score, String>StringH = new TableColumn<Score, String>("Time");
		
		StringE.setCellValueFactory(
				new PropertyValueFactory<Score, String>("time"));
		StringM.setCellValueFactory(
				new PropertyValueFactory<Score, String>("time"));
		StringH.setCellValueFactory(
				new PropertyValueFactory<Score, String>("time"));
		
		AnchorPane easyPane = new AnchorPane();
		AnchorPane medPane = new AnchorPane();
		AnchorPane hardPane = new AnchorPane();
		
		easyPane.getChildren().add(EasyTab);
		medPane.getChildren().add(MedTab);
		hardPane.getChildren().add(HardTab);
		
		easyPane.setPrefHeight(tableSize);
		medPane.setPrefWidth(tableSize);
		hardPane.setPrefWidth(tableSize);
		
		
		easy.setContent(easyPane);
		medium.setContent(medPane);
		hard.setContent(hardPane);
		
		scoreboard.getTabs().addAll(easy, medium, hard);
		
		MenuButton back = new MenuButton("Back", "StoneButton.png");
		back.setScaleX(0.5);
		back.setScaleY(0.5);
		back.setLayoutX(GridLock.CANVAS_WIDTH * 0.67);
		back.setLayoutY(GridLock.CANVAS_HEIGHT * 0.8);
		
		back.setOnMouseClicked(e -> window.setScene(new Scene(g.startMenu(window), GridLock.CANVAS_HEIGHT, GridLock.CANVAS_WIDTH)));
		
		if(MenuBoard.flag == true) {
			populateScores();
			EasyTab.setItems(dataE);
			MedTab.setItems(dataM);
			HardTab.setItems(dataH);
		}
		
		EasyTab.setPrefSize(tableSize, tableSize);
		MedTab.setPrefSize(tableSize, tableSize);
		HardTab.setPrefSize(tableSize, tableSize);
		
		
		
		EasyTab.getColumns().addAll(nameE, MovesE, StringE);
		MedTab.getColumns().addAll(nameM, MovesM, StringM);
		HardTab.getColumns().addAll(nameH, MovesH, StringH);
		
		
		getChildren().addAll(scoreboard, back);
	}
	
	public void populateScores() {
		dataE.clear();
		dataM.clear();
		dataH.clear();
		
		File fi = new File(MenuBoard.fileAccess);
		if(fi.exists()) {
		Scanner sc = null;
				
			try {
				sc = new Scanner(new File(MenuBoard.fileAccess));
				
				while(sc.hasNextLine()) {
					String s1 = sc.nextLine();
					String s2[] = s1.split(" ");
					if(s2[0].equals("Easy")) {
						System.out.println(s2[0] + s2[1] + s2[2]);
						dataE.add(new Score(s2[1], Integer.parseInt(s2[2]), s2[3]));
					} else if (s2[0].equals("Medium")) {
						System.out.println(s2[0] + s2[1] + s2[2]);
						dataM.add(new Score(s2[1], Integer.parseInt(s2[2]), s2[3]));
					} else if (s2[0].equals("Hard")) {
						System.out.println(s2[0] + s2[1] + s2[2]);
						dataH.add(new Score(s2[1], Integer.parseInt(s2[2]), s2[3]));
					}
				}	
				
			} catch(FileNotFoundException f) {
				f.printStackTrace();
			}
			
		        		
			
		        		
		}
	}
	
	public static class Score {
		private final SimpleStringProperty name;
		private final SimpleIntegerProperty moves;
		private final SimpleStringProperty time;
		
		private Score (String s, int m, String t) {
			this.name = new SimpleStringProperty(s);
			this.moves = new SimpleIntegerProperty(m);
			this.time = new SimpleStringProperty(t);
			
		}
		public String getName() {
            return name.get();
        }
 
        public void setName(String fName) {
            name.set(fName);
        }
 
        public int getMoves() {
            return moves.get();
        }
 
        public void setMoves(int fName) {
            moves.set(fName);
        }
 
        public String getTime() {
            return time.get();
        }
 
        public void setTime(String fName) {
            time.set(fName);
        }
	}
	
	public void refresh() {
		
		populateScores();
		
		
	}
	
	
	
}
