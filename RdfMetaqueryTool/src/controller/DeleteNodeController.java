package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.GraphNode;
import utils.DialogController;
import utils.ServiceClass;

public class DeleteNodeController implements DialogController, Initializable {

	Stage dialogStage;
	@FXML private Button okButton, cancelButton;
	@FXML private ComboBox nOneComboBox;
	private ObservableList<String> lOne = FXCollections.observableArrayList();
	private ArrayList<GraphNode> node = ServiceClass.node;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadNComboBox(nOneComboBox,lOne);
		
	}
	
	private void loadNComboBox(ComboBox c, ObservableList<String> l){
		for (int i = 0; i < node.size(); i++){
			 l.add(node.get(i).getText());
			
		}
		c.setItems(l);
	}

	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
    private void handleCancel() {
        dialogStage.close();
    }
	
	@FXML
    private void handleOk() {
		String nodeString = (String) nOneComboBox.getSelectionModel().getSelectedItem();
		if( nodeString != null){
			ObservableList<Node> nodeToDelete = FXCollections.observableArrayList();
			GraphNode n = ServiceClass.fromStringToGraphNode(nodeString);
			//remove this node from arrayList
			node.remove(n);
			//delete all connection from this node to other node
			breakConnectionWithOtherNode(n);
			//delete all edges from this node to other node
			for(int i = 0 ; i < n.getEdges().size();i++){
				nodeToDelete.add(n.getEdges().get(i));
			}
			//delete all label of edges
			for(int i = 0 ; i < n.getEdgesLabelList().size() ; i++){
				nodeToDelete.add(n.getEdgesLabelList().get(i));
			}
			nodeToDelete.add(n);
			n.getEdges().clear();
			n.getConnectedNodes().clear();
			//remove from the group the node and the edges 
			ServiceClass.root.getChildren().removeAll(nodeToDelete);
			
	        dialogStage.close();

		}else{
			ServiceClass.showErrorDialog("Make sure you have selected the value of the node to be deleted");
		}
    }
	
	
	private void breakConnectionWithOtherNode(GraphNode n){
		for(int i = 0 ; i < node.size() ; i++){
			GraphNode currentNode = node.get(i);
			for(int j = 0 ; j < currentNode.getConnectedNodes().size() ; j++){
				if(currentNode.getConnectedNodes().get(j).equals(n)){
					currentNode.breakConnectionWith(n);
					break;
				}
			}
		}
	}

	

}
