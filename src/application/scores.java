package application;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

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
	                Scene s=new Scene(root);
	                s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	                stage.setScene(s);  
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
	        
			
	        TableColumn numberCol = new TableColumn("#");
	        numberCol.setMinWidth(20);
	        numberCol.setCellValueFactory(new Callback<CellDataFeatures<ScoreObiect, ScoreObiect>, ObservableValue<ScoreObiect>>() {
	            @Override public ObservableValue<ScoreObiect> call(CellDataFeatures<ScoreObiect, ScoreObiect> p) {
	                return new ReadOnlyObjectWrapper(p.getValue());
	            }
	        });

	        numberCol.setCellFactory(new Callback<TableColumn<ScoreObiect, ScoreObiect>, TableCell<ScoreObiect, ScoreObiect>>() {
	            @Override public TableCell<ScoreObiect, ScoreObiect> call(TableColumn<ScoreObiect, ScoreObiect> param) {
	                return new TableCell<ScoreObiect, ScoreObiect>() {
	                    @Override protected void updateItem(ScoreObiect item, boolean empty) {
	                        super.updateItem(item, empty);

	                        if (this.getTableRow() != null && item != null) {
	                        	int index=this.getTableRow().getIndex()+1;
	                            setText(index+"");
	                        } else {
	                            setText("");
	                        }
	                    }
	                };
	            }
	        });
	        numberCol.setStyle("-fx-alignment: CENTER;");
	        numberCol.setSortable(false);
	       
	        TableColumn NameCol = new TableColumn("Name");
	        NameCol.setMinWidth(220);
	        NameCol.setCellValueFactory(
	                new PropertyValueFactory<>("name"));
	        NameCol.setStyle("-fx-alignment: CENTER;");
	        
	        TableColumn ScoreCol = new TableColumn("Time");
	        ScoreCol.setMinWidth(100);
	        ScoreCol.setCellValueFactory(
	                new PropertyValueFactory<>("score"));
	        ScoreCol.setStyle("-fx-alignment: CENTER;");
	        
	        TableColumn DateCol = new TableColumn("Date");
	        DateCol.setMinWidth(220);
	        DateCol.setCellValueFactory(
	                new PropertyValueFactory<>("prettyDate"));
	        DateCol.setStyle("-fx-alignment: CENTER;");
	                
	        tblScores.setItems(data1);
	        tblScores.getColumns().addAll(numberCol, NameCol, ScoreCol, DateCol);
	        tblScores.setLayoutY(y);
	        tblScores.setLayoutX(15);
	        tblScores.setPrefSize(570, 120);
	        return tblScores;
		
	}

}
