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
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class settings extends Application implements Initializable{
	
	@FXML
    private RadioButton rbMulti,rbSingle,rbSmall,rbMedium,rbLarge,rbTime,rbKill ;
	
	@FXML
	private ComboBox<String> cmbTime,cmbKill,cmbScenario,cmbLevel;  
	
	
	@FXML
	private ImageView ScenarioImage,SettingsImage;
	
    Scene s;
	
	@FXML
	Button btnPlay,btnBack;

	@FXML 
	ImageView imgVolume;
	
	@FXML
	Slider sldVolume;
	
	int counter=0;
	
	@FXML
	public void Play() {
		
		SoundController sound=new SoundController("/sounds/confirm.wav", 3);
		sound.Play();
		
		Main m=new Main();
		Stage s= new Stage();
		
		m.isOnline=false;
		m.IsOnlyPlayer1=false;
		m.IsOnlyPlayer2=false;
		m.isServer=false;
		m.connection=false;
		m.volume=(int) sldVolume.getValue();
		
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
		
			if(cmbKill.getSelectionModel().getSelectedItem().toString()=="100") {
				m.MAXSCORE=1200;
			}
			else if(cmbKill.getSelectionModel().getSelectedItem().toString()=="300") {
				m.MAXSCORE=1200*3;
			}
			else
				m.MAXSCORE=1200*5;
		
		
		if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="USA-Elections") {
			m.setScenario1();
		}
		else if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="Krucjata Korwina") {
			m.setScenario2();
		}
		else if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="Windows vs Apple") {
			m.setScenario3();
		}
		else if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="Tesla vs Edison") {
			m.setScenario4();
		}
		
		if (cmbLevel.getSelectionModel().getSelectedItem().toString()=="Easy") {
			m.setHardModeOff();
			m._mode=mode.EASY;
			m.IsExtremeMode=false;
		}
		else if (cmbLevel.getSelectionModel().getSelectedItem().toString()=="Medium") {
			m.setHardModeOn();
			m._mode=mode.MEDIUM;
			m.IsExtremeMode=false;
		}
		else if (cmbLevel.getSelectionModel().getSelectedItem().toString()=="Hard") {
			m._mode=mode.HARD;
			m.setHardModeOff();
			m.IsExtremeMode=true;
		}
		m.start(s);
		Stage stage = (Stage) btnPlay.getScene().getWindow();	
		
	    stage.close();
	}
	
	public void ChangeScenarioImg() {
		if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="USA-Elections") {
			Image i = new Image(getClass().getResourceAsStream("/sounds/Scenario1.jpg"));
			ScenarioImage.setImage(i);
		}
		else if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="Krucjata Korwina") {			
			Image i = new Image(getClass().getResourceAsStream("/sounds/Scenario2.jpg"));
			ScenarioImage.setImage(i);
		}
		else if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="Windows vs Apple") {			
			Image i = new Image(getClass().getResourceAsStream("/sounds/Scenario3.jpg"));
			ScenarioImage.setImage(i);
		}
		else if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="Tesla vs Edison") {			
			Image i = new Image(getClass().getResourceAsStream("/sounds/Scenario4.jpg"));
			ScenarioImage.setImage(i);
		}
	}
	
	@FXML
	public void GoBack() {
		SoundController sound=new SoundController("/sounds/confirm.wav", 3);
		sound.Play();
		
		//aPlayer.Play();
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
	        stage.setResizable(false);
	        root1.setId("dark-scene");
	        s=new Scene(root1);
	        stage.setScene(s); 	    
	        s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
	        stage.show();
	       
			} catch(Exception e) {
				e.printStackTrace();
			}	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Image i = new Image(getClass().getResourceAsStream("/sounds/settings.png"));
		SettingsImage.setImage(i);
		Image i2 = new Image(getClass().getResourceAsStream("/sounds/Scenario3.jpg"));
		ScenarioImage.setImage(i2);
		Image i3 = new Image(getClass().getResourceAsStream("/sounds/soundOn.png"));
		Image i4 = new Image(getClass().getResourceAsStream("/sounds/soundOff.png"));
		imgVolume.setImage(i3);
		
		ToggleGroup group = new ToggleGroup();
		ToggleGroup group2 = new ToggleGroup();
		
		rbMulti.setToggleGroup(group);
		rbSingle.setToggleGroup(group);
		rbSingle.setSelected(true);
			
		rbSmall.setToggleGroup(group2);
		rbMedium.setToggleGroup(group2);
		rbLarge.setToggleGroup(group2);
		rbMedium.setSelected(true);
		
		
	    cmbKill.getItems().addAll("100", "300", "500");
	    cmbKill.getSelectionModel().select("100");
	    
	    cmbLevel.getItems().addAll("Easy", "Medium", "Hard");
	    cmbLevel.getSelectionModel().select("Medium");
	    
	    cmbScenario.getItems().addAll("Windows vs Apple","Tesla vs Edison");
	    cmbScenario.getSelectionModel().select("Windows vs Apple");
	    
	    cmbScenario.setOnKeyReleased(e->{
	    	
	    	if(e.getCode()==KeyCode.K) {
        		counter++;
        		if(counter==3)
        			cmbScenario.getItems().addAll("USA-Elections","Krucjata Korwina");
        		
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
	    sldVolume.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            			if(new_val.intValue()==0) {
            				imgVolume.setImage(i4);
            			}
            			else {
            				imgVolume.setImage(i3);
            			}
                }
            });
	    
	    
	}
}
