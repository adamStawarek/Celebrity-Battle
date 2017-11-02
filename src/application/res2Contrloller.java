package application;


import java.net.URL;
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
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class res2Contrloller {
	static boolean IsClicked=false;
	
	@FXML
	Button btnPlayAgain;
	@FXML
	Stage stage2;
	
	
	public void CloseWindow() {
		Platform.exit();
		System.out.println("Close window, pretty please");
	}
	
	public void OpenMenu() {
		IsClicked=false;
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
	                Parent root1 = (Parent) fxmlLoader.load();
	                stage2 = new Stage();
	                stage2.setScene(new Scene(root1));  
	                stage2.show();
	        } catch(Exception e) {
	           e.printStackTrace();
	   }
		Stage stage = (Stage) btnPlayAgain.getScene().getWindow();
	    stage.close();
		
		
	}
	public void PlayGameAgain() {
		IsClicked=true;		
		Stage s=new Stage();
		Main m=new Main();
		m.start(s);
		Stage stage = (Stage) btnPlayAgain.getScene().getWindow();
	    stage.close();
	}

	
	

	
	
}
