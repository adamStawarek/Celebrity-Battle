package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Online extends Application implements Initializable{

	@FXML
	Button btnPlay;
	@FXML
	TextField txtHost, txtPort;
	@FXML
	Text txtConnection,txtHOSTS;
	
	@FXML
	RadioButton rbYes,rbNo;
	@FXML
	ListView<String> lstIPs;
	
	
	public static ObservableList<String> items = FXCollections.observableArrayList();
	@FXML ComboBox<String> cmbScenario;
	@FXML ImageView imgScenario;

	@Override
	public void start(Stage stage) throws Exception {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Online.fxml"));
	                Parent root1 = (Parent) fxmlLoader.load();
	                stage = new Stage();
	                //root1.setId("dark-scene");
	                stage.setResizable(false);
	                Scene s=new Scene(root1);                
	                s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	                stage.setScene(s);  
	                stage.show();
	        } catch(Exception e) {
	           e.printStackTrace();
	   }
	}

	@FXML public void GoBack() {
		
		SoundController sound=new SoundController("/sounds/confirm.wav", 3);
		sound.Play();
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
		SoundController sound=new SoundController("/sounds/confirm.wav", 3);
		sound.Play();
		
		if (rbYes.isSelected()) {
			IsServer=true;
		}
		Main m=new Main();
		Stage s= new Stage();
		
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
		m.isOnline=true;
		m.isServer=IsServer;
		m.IsHardMode=false;
		m.IsExtremeMode=false;
		m.MAXSCORE=1200*3;
		m.host=txtHost.getText();
		m.port=Integer.parseInt(txtPort.getText());
		m.IsMultiPlayer=true;
		m.start(s);
		
		Stage stage = (Stage) btnPlay.getScene().getWindow();	    
	    stage.close();
	}
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//aPlayer=new AudioPlayer(getClass().getResource("/sounds/confirm.wav"));
		ToggleGroup group = new ToggleGroup();
		rbNo.setToggleGroup(group);
		rbYes.setToggleGroup(group);
		rbYes.setSelected(true);
		cmbScenario.getItems().addAll("Windows vs Apple","Tesla vs Edison");
		cmbScenario.getSelectionModel().select("Windows vs Apple");
		Image i2 = new Image(getClass().getResourceAsStream("/sounds/Scenario3.jpg"));
		imgScenario.setImage(i2);
		lstIPs.setItems(items);
		lstIPs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> ov,
              String old_val, String new_val) {
            txtHost.setText(new_val);
          }
		});
		
		getNetworkIPs();
		try {
			txtHost.setText(InetAddress.getLocalHost().getHostAddress().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public static void getNetworkIPs() {
	    final byte[] ip;
	    try {
	        ip = InetAddress.getLocalHost().getAddress();
	    } catch (Exception e) {
	        return;     // exit method, otherwise "ip might not have been initialized"
	    }

	    for(int i=1;i<=254;i++) {
	        final int j = i;  // i as non-final variable cannot be referenced from inner class
	        new Thread(new Runnable() {   // new thread for parallel execution
	            public void run() {
	                try {
	                    ip[3] = (byte)j;
	                    InetAddress address = InetAddress.getByAddress(ip);
	                    String output = address.toString().substring(1);
	                    if (address.isReachable(5000)) {
	                    	items.add(output);
	                        System.out.println(output + " is on the network");
	                    } else {
	                        System.out.println("Not Reachable: "+output);
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }).start();     // dont forget to start the thread
	    }
	}
	
	public void ChangeScenarioImg() {
		if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="USA-Elections") {
			Image i = new Image(getClass().getResourceAsStream("/sounds/Scenario1.jpg"));
			imgScenario.setImage(i);
		}
		else if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="Krucjata Korwina") {			
			Image i = new Image(getClass().getResourceAsStream("/sounds/Scenario2.jpg"));
			imgScenario.setImage(i);
		}
		else if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="Windows vs Apple") {			
			Image i = new Image(getClass().getResourceAsStream("/sounds/Scenario3.jpg"));
			imgScenario.setImage(i);
		}
		else if (cmbScenario.getSelectionModel().getSelectedItem().toString()=="Tesla vs Edison") {			
			Image i = new Image(getClass().getResourceAsStream("/sounds/Scenario4.jpg"));
			imgScenario.setImage(i);
		}
	}

	@FXML public void checkPort() throws UnknownHostException, SocketException {
		
		int port=Integer.parseInt(txtPort.getText());
		txtConnection.setText("Host:  '"+txtHost.getText()+"' Testing port " + port+"....");
		txtConnection.setFill(Paint.valueOf("black"));
		//txtConnection.setFont(Font.font(18));
		txtConnection.setFont(Font.font(STYLESHEET_CASPIAN,FontWeight.BOLD, 15));

		    try{
		        InetSocketAddress sa = new InetSocketAddress(InetAddress.getLocalHost(), port);
		        Socket ss = new Socket();
		        ss.connect(sa, 1);            // change from 1 to 500 (for example)
		        ss.close();
		        txtConnection.setText("Host: '"+txtHost.getText()+"', Port: '" + port + "' is not available");
		        txtConnection.setFill(Paint.valueOf("red"));
		        
		    }catch(Exception e) {
		    	txtConnection.setText("Host: '"+txtHost.getText()+"', Port: '" + port + "' is available");
		    	txtConnection.setFill(Paint.valueOf("green"));
		    }
		   
		
	}

	

}
