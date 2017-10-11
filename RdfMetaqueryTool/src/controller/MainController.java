package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.GraphNode;
import utils.ServiceClass;

public class MainController implements Initializable {
	
	@FXML private Label label;
	@FXML private Button addNodeButton,addArchButton,editNodeButton,editArchButton,deleteAllButton,deleteNodeButton, deleteArchButton, doButton;
	@FXML private ComboBox<String> cb;
	@FXML private SplitPane sp;
	@FXML private AnchorPane spAp1,spAp2;
	@FXML private Group root = new Group();
	@FXML private MenuItem exit, newGraph, addNodeMenuItem, addArchMenuItem,deleteAllMenuItem, aboutMenuItem;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	      doButton.setDisable(true);
	      setLabel();
	      setComboBox();
	      ServiceClass.root = this.root;
	      

	}
	
	public void addNode(){
		ServiceClass.setDialog("AddNodeDialog", "Add Node");
	}
	
	public void addArch(){
		ServiceClass.setDialog("AddArchDialog", "Add Arch");
	}
	
	public void editNode(){
		ServiceClass.setDialog("EditNodeDialog", "Edit Node");
	}
	
	public void editArch(){
		ServiceClass.setDialog("EditArchDialog", "Edit Arch");
	}
	
	public void deleteNode(){
		ServiceClass.setDialog("DeleteNodeDialog", "Delete Node");
	}
	
	public void deleteArch(){
		ServiceClass.setDialog("DeleteArchDialog", "Delete Arch");
	}
	
	public void deleteAll(){
		ServiceClass.root.getChildren().clear();
		for(int i = 0 ; i < ServiceClass.node.size();i++){
		ServiceClass.node.remove(i);
	}
		}
	
	private void setLabel(){
		label.setText("Then, select Add Node and Add Arch\nto create a metaquery \n");
	}
	
	private void setComboBox(){
		 //Settaggio iniziale combobox che conterrà il grafo RDF da interrogare
		ObservableList<String> lis = FXCollections.observableArrayList("Primo","Secondo");
		cb.setItems(lis);
		//cb.getSelectionModel().select(1);

	}
	
	@FXML 
	public void exitProgram(){
		System.exit(0);
	}

	
	
}
