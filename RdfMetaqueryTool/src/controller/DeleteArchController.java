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
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.GraphNode;
import model.Record;
import utils.DialogController;
import utils.ServiceClass;

public class DeleteArchController implements DialogController, Initializable {

	Stage dialogStage;
	@FXML
	private Button okButton, cancelButton;
	@FXML
	private ComboBox nOneComboBox, nTwoComboBox;
	private ArrayList<GraphNode> node = ServiceClass.node;
	private ObservableList<String> lOne = FXCollections.observableArrayList();
	private ObservableList<String> lTwo = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// The problem is here :
		// the lOne and lTwo are empty without any info
		// You load them inside the ComboBoxes which will have nothing
		// and then you assk in this line --->

		loadNComboBox(nOneComboBox, lOne);
		loadNComboBox(nTwoComboBox, lTwo);
		// To get the selected item which of course is null

		// After that you ask for the node with label = null
		// and inside the method fromStringToGraphNode() you are getting an
		// error

	}

	private void loadNComboBox(ComboBox c, ObservableList<String> l) {
		for (int i = 0; i < ServiceClass.node.size(); i++) {
			l.add(ServiceClass.node.get(i).getText());

		}
		c.setItems(l);
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handleOk() {
		if (comboBoxValid() & nOneComboBox.getSelectionModel().getSelectedIndex() != nTwoComboBox.getSelectionModel()
				.getSelectedIndex()) {

			GraphNode nOne = ServiceClass
					.fromStringToGraphNode((String) nOneComboBox.getSelectionModel().getSelectedItem());
			GraphNode nTwo = ServiceClass
					.fromStringToGraphNode((String) nTwoComboBox.getSelectionModel().getSelectedItem());

//			Line edge = findEdgeToDelete(nOne, nTwo);
		//	ArrayList<Label> n1 = nOne.getEdgesLabelList();
		//	ArrayList<Label> n2 = nTwo.getEdgesLabelList();
	//		ServiceClass.root.getChildren().remove(edge);
			Line edge = edgeToDelete(nOne,nTwo).getEdge();
			Label label = edgeToDelete(nOne,nTwo).getEdgeLabel();
			
			for(int i = 0 ; i < node.size() ; i++){
				GraphNode currentNode = node.get(i);
				for(int j = 0 ; j < currentNode.getEdges().size() ; j++){
					if(currentNode.getEdges().get(j).equals(edge)){
						currentNode.getEdges().remove(j);
						break;
					}
				}
			}
			
			deleteEdge(nOne,edge);
			deleteEdge(nTwo,edge);
			deleteEdgeLabel(nOne,label);
			deleteEdgeLabel(nTwo,label);
			deleteRecord(nOne,nTwo);
			//deleteEdgeFromNode(nOne,edge);
			//deleteEdgeFromNode(nTwo,edge);
			deleteNodeFromConnectedList(nOne,nTwo);
			//deleteEdgeLabelFromNode(nOne,label);
			//deleteEdgeLabelFromNode(nTwo,label);
			
			ServiceClass.root.getChildren().removeAll(edge,label);

			print(nOne, nTwo);
			System.out.println("DELETE ARCH");

			System.out.println(ServiceClass.updateMetaquery());

			System.out.println("--------");			
			/*
			 * ServiceClass.metaquery += property + "(" + nOne.getText() + "," +
			 * nTwo.getText() + ") ";
			 * System.out.println(ServiceClass.metaquery);
			 */
			MainController.metaqueryLabel.setText(ServiceClass.updateMetaquery());

			dialogStage.close();

		} else {
			ServiceClass.showErrorDialog(
					"Make sure you have selected two nodes with \ndifferent values, verify that the value associated with \nthe property is valid, that is, consists of only one letter");
		}
	}
	
	private void deleteRecord(GraphNode n1, GraphNode n2){
		Record r = edgeToDelete(n1,n2);
		for(int i = 0 ; i < ServiceClass.record.size() ; i++){
			if(ServiceClass.record.get(i).equals(r)){
				ServiceClass.record.remove(ServiceClass.record.get(i));
			}
		}
	}
	
	private void deleteNodeFromConnectedList(GraphNode n1, GraphNode n2){
		for(int i = 0 ; i < n1.getConnectedNodes().size() ; i++){
			if(n1.getConnectedNodes().get(i).equals(n2)){
				n1.getConnectedNodes().remove(i);
				break;
			}
		}
		
		for(int i = 0 ; i < n2.getConnectedNodes().size() ; i++){
			if(n2.getConnectedNodes().get(i).equals(n1)){
				n2.getConnectedNodes().remove(i);
				break;
			}
		}
	}
	
	private void deleteEdgeFromNode(GraphNode n, Line l){
			for(int j = 0 ; j < n.getEdges().size() ; j++){
				if(n.getEdges().get(j).equals(l)){
					n.getEdges().remove(j);
					break;
				}
			}	
	}
	
	private void deleteEdgeLabelFromNode(GraphNode n, Label l){
		for(int j = 0 ; j < n.getEdgesLabelList().size() ; j++){
			if(n.getEdgesLabelList().get(j).equals(l)){
				n.getEdgesLabelList().remove(j);
				break;
			}
		}	
}

	private Record edgeToDelete(GraphNode n1,GraphNode n2){
		for(int i = 0 ; i < ServiceClass.record.size() ; i++){
			if((ServiceClass.record.get(i).getN1().equals(n1) && ServiceClass.record.get(i).getN2().equals(n2)) || (ServiceClass.record.get(i).getN1().equals(n2) && ServiceClass.record.get(i).getN2().equals(n1))){
				return ServiceClass.record.get(i);
			}
		}
		return null;
	}
	
	private void deleteEdge(GraphNode n, Line edge){
		for(int i = 0; i < n.getEdges().size() ; i++){
			if(n.getEdges().get(i).equals(edge)){
				n.getEdges().remove(edge);
			}
		}
	}
	
	private void deleteEdgeLabel(GraphNode n,Label l){
		for(int i = 0; i < n.getEdgesLabelList().size() ; i++){
			if(n.getEdgesLabelList().get(i).equals(l)){
				n.getEdgesLabelList().remove(l);
			}
		}
	}

	// metodo per cancellare l'arco da entrambi i nodi lo cerco dicendo che se
	// l'arco inizia dal centro del primo nodo e finisce al centro del secondo o
	// viceversa allora è l'arco che cerco.
	private Line findEdgeToDelete(GraphNode n1, GraphNode n2) {
		for (int i = 0; i < n1.getEdges().size(); i++) {
			Line currentEdge = n1.getEdges().get(i);
			if ((currentEdge.getStartX() == n1.getCenterX() && currentEdge.getStartY() == n1.getCenterY()
					&& currentEdge.getEndX() == n2.getCenterX() && currentEdge.getEndY() == n2.getCenterY())
					|| (currentEdge.getStartX() == n2.getCenterX() && currentEdge.getStartY() == n2.getCenterY()
							&& currentEdge.getEndX() == n1.getCenterX() && currentEdge.getEndY() == n1.getCenterY())) {
				n1.getEdges().remove(currentEdge);
				n2.getEdges().remove(currentEdge);
				return currentEdge;
			}
		}
		return null;
	}

	private boolean comboBoxValid() {
		if (nOneComboBox.getSelectionModel().getSelectedItem() == null
				& nTwoComboBox.getSelectionModel().getSelectedItem() == null & nOneComboBox.getSelectionModel()
						.getSelectedIndex() != nTwoComboBox.getSelectionModel().getSelectedIndex()) {
			return false;
		} else {
			return true;
		}
	}

	private void print(GraphNode n1, GraphNode n2) {
		System.out.println("NODO 1");
		for (int i = 0; i < n1.getEdges().size(); i++) {
			System.out.println(n1.getEdges().get(i).toString());
			Label l = n1.getEdgesLabelList().get(i);
			System.out.println(n1.getEdgesLabelList().get(i).toString());
		}

		System.out.println("NODO 2");
		for (int i = 0; i < n2.getEdges().size(); i++) {
			System.out.println(n2.getEdges().get(i).toString());
		}

	}

	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

	}

}
