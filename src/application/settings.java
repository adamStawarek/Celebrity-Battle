package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class settings extends Application implements Initializable{
	
	@FXML
    private RadioButton rbMulti,rbSingle,rbOnline,rbSmall,rbMedium,rbLarge,rbTime,rbKill ;
	
	@FXML
	private ComboBox<String> cmbTime,cmbKill,cmbScenario,cmbLevel;  

	
	@FXML
	Button btnPlay,btnBack;
	
	@FXML
	public void Play() {
		
		
		Main m=new Main();
		Stage s= new Stage();
		
		if (rbTime.isSelected())
			cmbKill.setDisable(true);
		else if (rbKill.isSelected())
			cmbTime.setDisable(true);
		
		if (!(rbSingle.isSelected()))
			cmbLevel.setDisable(true);
			
		
		if (rbSmall.isSelected())
			m.setSmall();
		else if (rbLarge.isSelected())
			m.setLarge();
		else if (rbMedium.isSelected())
			m.setMedium();
		
		if(rbSingle.isSelected())
			m.setMultiplayer(false);
		else
			m.setMultiplayer(true);
		
		if (rbKill.isSelected()) {
			if(cmbKill.getSelectionModel().getSelectedItem().toString()=="100") {
				m.MAXSCORE=1200;
			}
			else if(cmbKill.getSelectionModel().getSelectedItem().toString()=="300") {
				m.MAXSCORE=1200*3;
			}
			else
				m.MAXSCORE=1200*5;
		}
		
		
		if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="USA-Elections")
			m.setScenario1();
		else if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="Krucjata Korwina")
			m.setScenario2();
		
		if (cmbLevel.getSelectionModel().getSelectedItem().toString()=="Easy")
			m.setHardModeOff();
		else if (cmbLevel.getSelectionModel().getSelectedItem().toString()=="Medium")
			m.setHardModeOn();
		
		m.start(s);
		Stage stage = (Stage) btnPlay.getScene().getWindow();	    
	    stage.close();
	}
	
	@FXML
	public void GoBack() {
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
	
			

	@Override
	public void start(Stage stage) throws Exception {
		
		try {
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("set.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        stage = new Stage();
	        Scene s=new Scene(root1);
	        stage.setScene(s); 	    
	        s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        stage.show();
	       
			} catch(Exception e) {
				e.printStackTrace();
			}

		
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ToggleGroup group = new ToggleGroup();
		ToggleGroup group2 = new ToggleGroup();
		ToggleGroup group3 = new ToggleGroup();
		
		rbMulti.setToggleGroup(group);
		rbSingle.setToggleGroup(group);
		rbOnline.setToggleGroup(group);
		rbSingle.setSelected(true);
			
		rbSmall.setToggleGroup(group2);
		rbMedium.setToggleGroup(group2);
		rbLarge.setToggleGroup(group2);
		rbMedium.setSelected(true);
		
		rbTime.setToggleGroup(group3);
		rbKill.setToggleGroup(group3);
		rbKill.setSelected(true);
		
	    cmbTime.getItems().addAll("3min", "5min", "10min");
	    cmbTime.getSelectionModel().select("3min");
	    cmbTime.setDisable(true);
		
	    cmbKill.getItems().addAll("100", "300", "500");
	    cmbKill.getSelectionModel().select("100");
	    
	    cmbLevel.getItems().addAll("Easy", "Medium", "Hard");
	    cmbLevel.getSelectionModel().select("Medium");
	    
	    cmbScenario.getItems().addAll("USA-Elections", "Krucjata Korwina");
	    cmbScenario.getSelectionModel().select("USA-Elections");
	    
	    rbTime.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
	            if (isNowSelected) { 
	            	cmbKill.setDisable(true);
	            	cmbTime.setDisable(false);
	            } 
	        }
	    });
	    rbKill.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
	            if (isNowSelected) { 
	            	cmbTime.setDisable(true);
	            	cmbKill.setDisable(false);
	            } 
	        }
	    });
	    rbSingle.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
	            if (!(isNowSelected)) { 
	            	cmbLevel.setDisable(true);
	            } 
	            else
	            	cmbLevel.setDisable(false);
	        }
	    });
	    
	    
	    
	}
}
