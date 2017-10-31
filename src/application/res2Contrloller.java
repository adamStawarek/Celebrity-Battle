package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Timer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class res2Contrloller  {
	static boolean IsClicked=false;
	Stage stage2;
	public void CloseWindow() {
		Platform.exit();
		System.out.println("Close window, pretty please");
	}
	
	public void OpenMenu() {
		
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
	                Parent root1 = (Parent) fxmlLoader.load();
	                stage2 = new Stage();
	                stage2.setScene(new Scene(root1));  
	                stage2.show();
	        } catch(Exception e) {
	           e.printStackTrace();
	   }
		
		
	}
	public void PlayGameAgain() {
		IsClicked=true;		
		Stage s=new Stage();
		Main m=new Main();
		m.start(s);
	}
	
	public static void main(String[] args) {
        
			Application.launch(Main.class, args);
    }
}
