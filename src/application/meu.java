package application;

import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.midi.SysexMessage;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class meu extends Application implements Initializable{
	
	@FXML
	Button btnSettings,btnClose,btnScores;
	
	AudioPlayer aPlayer;
	
	public void OpenScores() {
		aPlayer.Play();
		System.out.println("SHow scores");
		scores s=new scores();
		Stage stg=new Stage();
		try {
			s.start(stg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage stage = (Stage) btnSettings.getScene().getWindow();	    
	    stage.close();
	}
	public void Close() {
		aPlayer.Play();
		System.out.println("Close window");
		Platform.exit();
	}
	public void OpenSettings() {
		aPlayer.Play();
		System.out.println("Open Settings");		
		settings sets=new settings();
		try {
			sets.start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Stage stage = (Stage) btnSettings.getScene().getWindow();	    
	    stage.close();
	}
	
	 //The main is now launched in res2Controlle
   	public static void main(String[] args) {
   			launch(args);
   	}
	
	@Override
	public void start(Stage stage) throws Exception {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
	                Parent root1 = (Parent) fxmlLoader.load();
	                stage = new Stage();
	                root1.setId("dark-scene");
	                Scene s=new Scene(root1);
	                
	                s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	                stage.setScene(s);  
	                stage.show();
	        } catch(Exception e) {
	           e.printStackTrace();
	   }
		
	}
	public void animation1(Button b,int milisec,int delay,int X) {
		  
		  TranslateTransition translateTransition = new TranslateTransition(); 
	      //Setting the duration and delay of the transition  
	      translateTransition.setDuration(Duration.millis(milisec)); 
	      translateTransition.setDelay(Duration.millis(delay));
	      //Setting the node for the transition 
	      translateTransition.setNode(b); 
	      //Setting the value of the transition along the x axis. 
	      translateTransition.setByX(X); 
	      //Setting the cycle count for the transition 
	      translateTransition.setCycleCount(1); 
	      //Setting auto reverse value to false 
	      translateTransition.setAutoReverse(false);       
	      //Playing the animation 
	      translateTransition.play();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		aPlayer=new AudioPlayer(getClass().getResource("/sounds/confirm.wav"));
		animation1(btnSettings,500,200,450);
		animation1(btnScores,500,400,-450);
		animation1(btnClose,500,600,450);
		btnSettings.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		           //btnSettings.setStyle("-fx-background-color: red;");
		          }
		});
	      
	}
}
