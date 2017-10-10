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
	@FXML private ComboBox nOneComboBox, nTwoComboBox, propertyComboBox;
	private ObservableList<String> lOne = FXCollections.observableArrayList();
	private ObservableList<String> lTwo = FXCollections.observableArrayList();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// The problem is here : 
		// the lOne and lTwo are empty without any info
		// You load them inside the ComboBoxes which will have nothing
		// and then you assk in this line ---> 
		
		loadNComboBox(nOneComboBox,lOne);
		
		// To get the selected item which of course is null
		String nodeString = (String) nOneComboBox.getSelectionModel().getSelectedItem();
		
		// After that you ask for the node with label = null 
		// and inside the method fromStringToGraphNode() you are getting an error 
		GraphNode n = ServiceClass.fromStringToGraphNode(nodeString);
		ArrayList<GraphNode> g = n.getConnectedNodes();
		for(int i = 0 ; i < g.size();i++){
			lTwo.add(g.get(i).getText());
		}
		loadNComboBox(nTwoComboBox,lTwo);
		
		
		
	}

	private void loadNComboBox(ComboBox c, ObservableList<String> l) {
		for (int i = 0; i < ServiceClass.node.size(); i++) {
			l.add(ServiceClass.node.get(i).getText());

		}
		c.setItems(l);
	}
	
	
	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

	}

}
