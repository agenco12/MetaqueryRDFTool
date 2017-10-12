package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.GraphNode;
import utils.DialogController;
import utils.ServiceClass;

public class AddArchController implements DialogController, Initializable {

	Stage dialogStage;

	@FXML
	private TextField propertyTextField;
	@FXML
	private Button okButton, cancelButton;
	@FXML
	private ComboBox nOneComboBox, nTwoComboBox;
	private ObservableList<String> lOne = FXCollections.observableArrayList();
	private ObservableList<String> lTwo = FXCollections.observableArrayList();

	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadNComboBox(nOneComboBox, lOne);
		loadNComboBox(nTwoComboBox, lTwo);
		

		nOneComboBox.setPromptText("N1");
		nTwoComboBox.setPromptText("N2");
		propertyTextField.setPromptText("Property");
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

	private void connectNodes(GraphNode node1, GraphNode node2, String edgeText) {

		Line edgeLine = new Line(node1.getCenterX(), node1.getCenterY(), node2.getCenterX(), node2.getCenterY());
		Label edgeLabel = new Label(edgeText);

		node1.addNeighbor(node2);
		node2.addNeighbor(node1);

		node1.addEdge(edgeLine, edgeLabel);
		node2.addEdge(edgeLine, edgeLabel);

		ServiceClass.root.getChildren().addAll(edgeLine, edgeLabel);

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

	@FXML
	private void handleOk() {
		if (comboBoxValid()
				& !propertyTextField.getText().equalsIgnoreCase("") & propertyTextField != null & nOneComboBox
						.getSelectionModel().getSelectedIndex() != nTwoComboBox.getSelectionModel().getSelectedIndex()
				& propertyTextField.getText().length() == 1) {

			GraphNode nOne = ServiceClass
					.fromStringToGraphNode((String) nOneComboBox.getSelectionModel().getSelectedItem());
			GraphNode nTwo = ServiceClass
					.fromStringToGraphNode((String) nTwoComboBox.getSelectionModel().getSelectedItem());
			String property = propertyTextField.getText().toString();

			if (checkAreConnected(nOne, nTwo)) {
				connectNodes(nOne, nTwo, property);

				ServiceClass.metaquery += property + "(" + nOne.getText() + "," + nTwo.getText() + ") ";
				System.out.println(ServiceClass.metaquery);
				print(nOne,nTwo);
				dialogStage.close();
			} else {
				ServiceClass.showErrorDialog("The two nodes are already connected");
			}

		} else {
			ServiceClass.showErrorDialog(
					"Make sure you have selected two nodes with \ndifferent values, verify that the value associated with \nthe property is valid, that is, consists of only one letter");
		}
	}
	
	private void print(GraphNode n1, GraphNode n2){
		System.out.println("NODO 1");
		System.out.println(n1.getCenterX() + "<--x - y--->" + n1.getCenterY());
		for(int i = 0 ; i < n1.getEdges().size() ; i++){
			System.out.println(n1.getEdges().get(i).toString());
		}
		System.out.println("NODO 2");
		System.out.println(n2.getCenterX() + "<--x - y--->" + n2.getCenterY());
		for(int i = 0 ; i < n2.getEdges().size() ; i++){
			System.out.println(n2.getEdges().get(i).toString());
		}

	}

	private boolean checkAreConnected(GraphNode n1, GraphNode n2) {
		for (int i = 0; i < n1.getConnectedNodes().size(); i++) {
			if (n1.getConnectedNodes().get(i).equals(n2)) {
				return false;
			}
		}
		return true;

	}

}
