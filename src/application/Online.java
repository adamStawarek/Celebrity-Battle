package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Online extends Application implements Initializable{

	AudioPlayer aPlayer;
	@FXML
	Button btnPlay;
	@FXML
	TextField txtHost, txtPort;
	@FXML
	Text txtConnection;
	
	@FXML
	RadioButton rbYes,rbNo;

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

	@SuppressWarnings("static-access")
	@FXML 
	public void Play() {	
		boolean IsServer=false;
		aPlayer.Play();
		
		if (rbYes.isSelected()) {
			IsServer=true;
		}
		Main m=new Main();
		Stage s= new Stage();
		m.isOnline=true;
		m.isServer=IsServer;
		m.host=txtHost.getText();
		m.port=Integer.parseInt(txtPort.getText());
		m.IsMultiPlayer=true;
		m.start(s);
		
		Stage stage = (Stage) btnPlay.getScene().getWindow();	    
	    stage.close();
	}
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		aPlayer=new AudioPlayer(getClass().getResource("/sounds/confirm.wav"));
		ToggleGroup group = new ToggleGroup();
		rbNo.setToggleGroup(group);
		rbYes.setToggleGroup(group);
		rbYes.setSelected(true);
	}

	@FXML public void checkPort() {
		
		int port=Integer.parseInt(txtPort.getText());
		txtConnection.setText("Testing port " + port+"....");
		txtConnection.setFill(Paint.valueOf("black"));
	    Socket s = null;
	    try {
	        s = new Socket(txtHost.getText(), port);
	        s.setSoTimeout(3000);

	        // If the code makes it this far without an exception it means
	        // something is using the port and has responded.
	        txtConnection.setText("Port " + port + " is not available");
	        txtConnection.setFill(Paint.valueOf("red"));
	        return;
	    } catch (IOException e) {
	        txtConnection.setText("Port " + port + " is available");
	        txtConnection.setFill(Paint.valueOf("green"));
	        return;
	    } finally {
	        if( s != null){
	            try {
	                s.close();
	            } catch (IOException e) {
	                throw new RuntimeException("You should handle this error." , e);
	            }
	        }
	    }
		
	}

	

}
