package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.GraphNode;
import utils.DialogController;
import utils.ServiceClass;

public class DeleteArchController implements DialogController, Initializable {
	
	Stage dialogStage;
	@FXML private Button okButton, cancelButton;
	@FXML private ComboBox propertyComboBox;
	private ObservableList<String> propertyList = FXCollections.observableArrayList();
	private ArrayList<GraphNode> node = ServiceClass.node;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// The problem is here : 
		// the lOne and lTwo are empty without any info
		// You load them inside the ComboBoxes which will have nothing
		// and then you assk in this line ---> 
		
		loadPropertyComboBox(propertyComboBox, propertyList);
		
		// To get the selected item which of course is null
		
		// After that you ask for the node with label = null 
		// and inside the method fromStringToGraphNode() you are getting an error 
		
		
		
		
	}

	private void loadPropertyComboBox(ComboBox c, ObservableList<String> l) {
		for (int i = 0; i < node.size(); i++) {
			GraphNode currentNode = node.get(i);
			for(int j = 0 ; j < currentNode.getEdgesLabelList().size(); j++){
				l.add(currentNode.getEdgesLabelList().get(j).getText().toString());
			}
		}
		c.setItems(l);
	}
	
	
	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

	}

}
