package application;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class scores extends Application{
	
	TableView<ScoreObiect> tblScores1,tblScores2,tblScores3;
	
	@FXML
	public void GoBack(ActionEvent event) {
		meu m=new meu();		
		Stage s=new Stage();
		try {
			m.start(s);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		try {
			
	        tblScores1=addTable("jdbc:sqlite:scores1.sqlite", 70);
	        tblScores2=addTable("jdbc:sqlite:scores2.sqlite", 250);
	        tblScores3=addTable("jdbc:sqlite:scores3.sqlite", 430);
	
	        		
	        		AnchorPane root=new AnchorPane();
	        		root = (AnchorPane) FXMLLoader.load(getClass().getResource("scores.fxml"));
	               
	        		root.getChildren().add(tblScores1);
	        		root.getChildren().add(tblScores2);
	        		root.getChildren().add(tblScores3);
	                stage = new Stage();
	                stage.setScene(new Scene(root));  
	                stage.show();
	        }catch(Exception e) {
	           e.printStackTrace();
	   }
		
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	TableView<ScoreObiect> addTable(String s, int y) {
		
			ObservableList<ScoreObiect> data1 = FXCollections.observableArrayList();
			TableView<ScoreObiect> tblScores;
			tblScores=new TableView<>(data1);
			SqlScores sql1=new SqlScores(s);
			
			List<ScoreObiect> scOb1=sql1.selectScores();
	        Collections.sort(scOb1);
	        sql1.closeConnection();
	        for(ScoreObiect Sc:scOb1) {
	        	data1.add(Sc);
	        }
	        
			TableColumn IndexCol = new TableColumn("");
	        IndexCol.setMinWidth(50);
	       
	        TableColumn NameCol = new TableColumn("Name");
	        NameCol.setMinWidth(200);
	        NameCol.setCellValueFactory(
	                new PropertyValueFactory<>("name"));
	        
	        TableColumn ScoreCol = new TableColumn("Score");
	        ScoreCol.setMinWidth(100);
	        ScoreCol.setCellValueFactory(
	                new PropertyValueFactory<>("score"));
	        
	        TableColumn DateCol = new TableColumn("Date");
	        DateCol.setMinWidth(200);
	        DateCol.setCellValueFactory(
	                new PropertyValueFactory<>("date"));
	                
	        tblScores.setItems(data1);
	        tblScores.getColumns().addAll(IndexCol, NameCol, ScoreCol, DateCol);
	        tblScores.setLayoutY(y);
	        tblScores.setLayoutX(15);
	        tblScores.setPrefSize(570, 120);
	        return tblScores;
		
	}

}
