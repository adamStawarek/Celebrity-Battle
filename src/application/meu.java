package application;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class meu {
	public void OpenScores() {
		System.out.println("SHow scores");
	}
	public void Close() {
		System.out.println("Close window");
		Platform.exit();
	}
	public void OpenSettings() {
		System.out.println("Open Settings");		
		try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));  
        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
