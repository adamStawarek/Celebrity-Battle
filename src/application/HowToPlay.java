package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HowToPlay extends Application implements Initializable{
	
	@FXML
	ImageView img1,img2;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Image i = new Image(getClass().getResourceAsStream("/sounds/QuestionMark.png"));
		img1.setImage(i);
		img2.setImage(i);
	}
	
	@FXML
	public void GoBack() {
		SoundController sound=new SoundController("/sounds/confirm.wav", 3);
		sound.Play();
		
		//aPlayer.Play();
		Stage stage = (Stage) img1.getScene().getWindow();	    
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
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HowToPlay.fxml"));
	                Parent root1 = (Parent) fxmlLoader.load();	               
	                primaryStage = new Stage();	
	                primaryStage.setResizable(false);
	                root1.setId("dark-scene");
	                Scene s=new Scene(root1);
	                s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	                primaryStage.setScene(s);                 			                
	                primaryStage.show();
	        } catch(Exception e) {
	           e.printStackTrace();
	   }
	}

}
