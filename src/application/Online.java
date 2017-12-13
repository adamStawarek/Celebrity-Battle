package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Online extends Application implements Initializable{

	AudioPlayer aPlayer;
	@FXML
	Button btnPlay;

	@Override
	public void start(Stage stage) throws Exception {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Online.fxml"));
	                Parent root1 = (Parent) fxmlLoader.load();
	                stage = new Stage();
	                //root1.setId("dark-scene");
	                Scene s=new Scene(root1);                
	                s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	                stage.setScene(s);  
	                stage.show();
	        } catch(Exception e) {
	           e.printStackTrace();
	   }
	}

	@FXML public void GoBack() {
		
		aPlayer.Play();
		Stage stage = (Stage) btnPlay.getScene().getWindow();	    
	    stage.close();
	    meu m=new meu();
		Stage s=new Stage();
		try {
			m.start(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML public void Play() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		aPlayer=new AudioPlayer(getClass().getResource("/sounds/confirm.wav"));
		// TODO Auto-generated method stub
		
	}

}
