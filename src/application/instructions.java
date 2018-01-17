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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class instructions extends Application implements Initializable{
	
	public static int scenario;
	public static int playerNumb;
	
	@FXML
	ImageView imgPlayer,imgShoot,imgKeys;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Image i = null,i2 = null,i3 = null;
		if(playerNumb==2) {
			i = new Image(getClass().getResourceAsStream("/sounds/keys.png"));
			i2 = new Image(getClass().getResourceAsStream("/sounds/N.png"));
			if(scenario==1)
				i3 = new Image(getClass().getResourceAsStream("/resources/Player2.gif"));
			else
				i3 = new Image(getClass().getResourceAsStream("/resources"+scenario+"/Player2.gif"));
		}else if(playerNumb==1){
			i = new Image(getClass().getResourceAsStream("/sounds/keys2.png"));
			i2 = new Image(getClass().getResourceAsStream("/sounds/R.png"));
			if(scenario==1)
				i3 = new Image(getClass().getResourceAsStream("/resources/Player1.gif"));
			else
				i3 = new Image(getClass().getResourceAsStream("/resources"+scenario+"/Player1.gif"));
		}
		System.out.println(playerNumb);
		imgKeys.setImage(i);
		imgShoot.setImage(i2);
		imgPlayer.setImage(i3);
		
	}
	@FXML
	public void Play() {
		Stage stage = (Stage) imgPlayer.getScene().getWindow();	    
	    stage.close();
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Stage sstage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Instructions.fxml"));
        Parent roott = (Parent) fxmlLoader.load();
        roott.setId("dark-scene");
        Scene s=new Scene(roott);
        s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        sstage.setScene(s);
        sstage.setResizable(false);
        sstage.setTitle("modal window");
        
        sstage.initModality(Modality.WINDOW_MODAL);
        sstage.initOwner(primaryStage.getScene().getWindow());
        sstage.showAndWait();
	}

}
