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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.GraphNode;
import model.Record;
import utils.DialogController;
import utils.ServiceClass;

public class EditArchController implements DialogController, Initializable {

	Stage dialogStage;
	@FXML
	private Button okButton, cancelButton;
	@FXML
	private ComboBox nOneComboBox, nTwoComboBox;
	@FXML
	private TextField propertyTextField;
	private ObservableList<String> lOne = FXCollections.observableArrayList();
	private ObservableList<String> lTwo = FXCollections.observableArrayList();
	ArrayList<GraphNode> node = ServiceClass.node;

	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadNComboBox(nOneComboBox, lOne);
		loadNComboBox(nTwoComboBox, lTwo);
	}

	@FXML
	private void handleOk() {
		if (isInputValid(nOneComboBox, nTwoComboBox, propertyTextField)) {

			String n1 = (String) nOneComboBox.getSelectionModel().getSelectedItem();
			String n2 = (String) nTwoComboBox.getSelectionModel().getSelectedItem();
			String property = propertyTextField.getText().toString();

			GraphNode nOne = ServiceClass
					.fromStringToGraphNode((String) nOneComboBox.getSelectionModel().getSelectedItem());
			GraphNode nTwo = ServiceClass
					.fromStringToGraphNode((String) nTwoComboBox.getSelectionModel().getSelectedItem());

			print(nOne,nTwo);
			Record r = searchRecord(nOne,nTwo);
			Label oldLabel = r.getEdgeLabel();
			r.setEdgeLabel(new Label(property));
			Label newlabel = r.getEdgeLabel();
			
			editArchName(nOne,nTwo,property);
			
			//cambio la lettera nell'array etichetta del nodo 1
			for(int i = 0; i < nOne.getEdgesLabelList().size() ; i++){	
				if(nOne.getEdgesLabelList().get(i).equals(oldLabel)){
					nOne.getEdgesLabelList().get(i).setText(property);
				}
			}
			//cambio la lettera nell'array etichetta del nodo 2
			for(int i = 0; i < nTwo.getEdgesLabelList().size() ; i++){	
				if(nTwo.getEdgesLabelList().get(i).equals(oldLabel)){
					nTwo.getEdgesLabelList().get(i).setText(property);
				}
			}
			
			newlabel.setText(property);

			print(nOne,nTwo);
			
			System.out.println("EDIT ARCH");

			System.out.println(ServiceClass.updateMetaquery());

			System.out.println("--------");

			MainController.metaqueryLabel.setText(ServiceClass.updateMetaquery());


			dialogStage.close();
		} else {
			ServiceClass.showErrorDialog(
					"Make sure you have entered a valid value and \ncorrectly selected the values in the two nodes");
		}

	}

	private boolean isInputValid(ComboBox c, ComboBox c2, TextField t) {
		if (c.getSelectionModel().getSelectedItem() == null || c2.getSelectionModel().getSelectedItem() == null
				|| c.getSelectionModel().getSelectedIndex() == c2.getSelectionModel().getSelectedIndex()
				|| t.getText().toString() == null || t.getText().toString().length() > 2) {
			return false;
		} else {
			return true;
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	private void loadNComboBox(ComboBox c, ObservableList<String> l) {
		for (int i = 0; i < ServiceClass.node.size(); i++) {
			l.add(ServiceClass.node.get(i).getText());

		}
		c.setItems(l);
	}
	
	private void editArchName(GraphNode n1, GraphNode n2,String value){
		Record r = searchRecord(n1,n2);
		r.getEdgeLabel().setText(value);
	}
	
	private Record searchRecord(GraphNode n1, GraphNode n2){
		for (int i = 0; i < ServiceClass.record.size(); i++) {
			if ((ServiceClass.record.get(i).getN1().equals(n1) && ServiceClass.record.get(i).getN2().equals(n2))
					|| (ServiceClass.record.get(i).getN1().equals(n2)
							&& ServiceClass.record.get(i).getN2().equals(n1))) {
					return ServiceClass.record.get(i);
			}
		}
		return null;
	}
	
	private void print(GraphNode n1, GraphNode n2){
		System.out.println("Edit Arch");
		System.out.println("NODO 1");
		System.out.println(n1.getText());
		for(int i = 0 ; i < n1.getEdgesLabelList().size() ; i++){
			System.out.println(n1.getEdgesLabelList().get(i).toString());
		}
		System.out.println("NODO 2");
		System.out.println(n2.getText());
		for(int i = 0 ; i < n2.getEdges().size() ; i++){
			System.out.println(n2.getEdgesLabelList().get(i).toString());
		}
		System.out.println("---------");

	}

}
