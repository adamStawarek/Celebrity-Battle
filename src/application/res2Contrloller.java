package application;


import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class res2Contrloller extends Application implements Initializable{
	
	static boolean IsClicked=false;
	static boolean IsTrumpWin=false;
	static String res;
	AudioPlayer aPlayer;
	
	@FXML
	Button btnPlayAgain;
	@FXML
	Stage stage2;
	@FXML
	ImageView imgEnd;
	@FXML
	Text txtResult;
	
	
		
	public void CloseWindow() {
		aPlayer.Play();
		Platform.exit();
		System.out.println("Close window, pretty please");
	}
	
	public void OpenMenu() {
		aPlayer.Play();
		IsClicked=false;
		meu m=new meu();
		Stage s=new Stage();
		try {
			m.start(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage stage = (Stage) btnPlayAgain.getScene().getWindow();
	    stage.close();
		
		
	}
	public void PlayGameAgain() {
		aPlayer.Play();
		IsClicked=true;		
		Stage s=new Stage();
		Main m=new Main();
		m.start(s);
		Stage stage = (Stage) btnPlayAgain.getScene().getWindow();
	    stage.close();
	}
		
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		try {
			
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("res2.fxml"));
	                Parent root1 = (Parent) fxmlLoader.load();	               
	                stage = new Stage();	
	                stage.setResizable(false);
	                root1.setId("dark-scene");
	                Scene s=new Scene(root1);
	                s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	                stage.setScene(s);                 			                
	                stage.show();
	        } catch(Exception e) {
	           e.printStackTrace();
	   }
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		aPlayer=new AudioPlayer(getClass().getResource("/sounds/confirm.wav"));
		Image i;
		if (IsTrumpWin) {
			 i = new Image(getClass().getResourceAsStream(res+"/Player1Win.png"));
			 txtResult.setText("Player1 wins!");
			 imgEnd.setTranslateX(50);
		}
		else {
			 i = new Image(getClass().getResourceAsStream(res+"/Player2Win.png"));
			 txtResult.setText("Player2 wins!");
		}
        imgEnd.setImage(i);
	}	
	
}
