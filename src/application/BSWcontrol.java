package application;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BSWcontrol extends Application implements Initializable{
	@FXML 
	Text scoreText;
	@FXML
	TextField gloryName;
	
	static int ScorePosition=0;
	static int score=0;
	static int MaxScore=0;
	AudioPlayer aPlayer;
	
	@FXML
	public void submit() {
		aPlayer.Play();
		
		if (MaxScore==1200) {
			SqlScores scr=new SqlScores("jdbc:sqlite:scores1.sqlite");
			
			String name=gloryName.getText();
			scr.insertScore(name, score);
			scr.closeConnection();
			
			Stage stage = (Stage) gloryName.getScene().getWindow();	    
		    stage.close();
		}
		else if (MaxScore==1200*3) {
			SqlScores scr=new SqlScores("jdbc:sqlite:scores2.sqlite");
	
			String name=gloryName.getText();
			scr.insertScore(name, score);
			scr.closeConnection();
			
			Stage stage = (Stage) gloryName.getScene().getWindow();	    
		    stage.close();
		}
		else if (MaxScore==1200*5) {
			SqlScores scr=new SqlScores("jdbc:sqlite:scores3.sqlite");
		
			String name=gloryName.getText();
			scr.insertScore(name, score);
			scr.closeConnection();
			
			Stage stage = (Stage) gloryName.getScene().getWindow();	    
		    stage.close();
		}
	
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
	
		try {				
	        		AnchorPane root=new AnchorPane();
	        		root = (AnchorPane) FXMLLoader.load(getClass().getResource("BestScoreWindow.fxml"));
	                stage = new Stage();
	                root.setId("dark-scene");
	                Scene s=new Scene(root);
	                s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	                stage.setScene(s);  
	                stage.show();
	        }catch(Exception e) {
	           e.printStackTrace();
	   }
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		aPlayer=new AudioPlayer(getClass().getResource("/sounds/confirm.wav"));
		scoreText.setText("Your score is "+(ScorePosition+1));
	}

}