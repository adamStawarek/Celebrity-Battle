package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class scores extends Application{
	
	@FXML
	ListView<String> lstScores;
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scores.fxml"));
	                Parent root1 = (Parent) fxmlLoader.load();
	                stage = new Stage();
	                stage.setScene(new Scene(root1));  
	                stage.show();
	        } catch(Exception e) {
	           e.printStackTrace();
	   }
	}

}
