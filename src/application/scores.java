package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class scores extends Application{
	
	ListView<ScoreObiect> lstScores;
	
	@FXML
	public void GoBack() {
		meu m=new meu();		
		Stage s=new Stage();
		try {
			m.start(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage stage = (Stage) lstScores.getScene().getWindow();
	    stage.close();
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		try {
			
	        ObservableList<ScoreObiect> data = FXCollections.observableArrayList();
	        lstScores=new ListView<ScoreObiect>(data);		
	        SqlScores sql=new SqlScores();
	        List<ScoreObiect> scOb=sql.selectScores();
	        sql.closeConnection();
	        for(ScoreObiect s:scOb) {
	        	data.add(s);
	        	System.out.println(s.toString());
	        }	       
	        		lstScores.setItems(data);        
	        		lstScores.setLayoutX(16);
	        		lstScores.setLayoutY(50);
	        		lstScores.setPrefSize(570, 300); 
	        		
	        		
	        		AnchorPane root=new AnchorPane();
	        		root = (AnchorPane) FXMLLoader.load(getClass().getResource("scores.fxml"));
	               
	        		root.getChildren().add(lstScores);
	                stage = new Stage();
	                stage.setScene(new Scene(root));  
	                stage.show();
	        } catch(Exception e) {
	           e.printStackTrace();
	   }finally {
		   	
		    
	       // lstScores.setCellFactory(ComboBoxListCell.forListView(data));
	
	   }
		
	}

}
