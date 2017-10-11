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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.GraphNode;
import utils.DialogController;
import utils.ServiceClass;

public class EditNodeController implements DialogController, Initializable {

	Stage dialogStage;
	@FXML
	private Button okButton, cancelButton;
	@FXML
	private ComboBox nOneComboBox;
	@FXML
	private TextField newValueTextField;
	private ObservableList<String> lOne = FXCollections.observableArrayList();
	private ArrayList<GraphNode> node = ServiceClass.node;

	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadNComboBox(nOneComboBox, lOne);
		newValueTextField.setPromptText("?x");

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
		if (isInputValid(nOneComboBox,newValueTextField)) {

			String g = (String) nOneComboBox.getSelectionModel().getSelectedItem();
			
			for(int i = 0 ; i < node.size() ; i++){
				if(node.get(i).getText().equalsIgnoreCase(g)){
					GraphNode n = node.get(i);
					n.setText(newValueTextField.getText().toString());
					break;
				}
			}

			dialogStage.close();
		} else {
			ServiceClass.showErrorDialog(
					"Make sure that you enter a valid value and that this is preceded by the \"?\" Character");
		}

	}

	private boolean isInputValid(ComboBox c, TextField t) {
		if (c.getSelectionModel().getSelectedItem() == null || t.getText().toString() == null
				|| t.getText().toString().length() > 2 || !t.getText().toString().contains("?")) {
			return false;
		} else {
			return true;
		}
	}
	
	/*private void printNode(){
		for(int i = 0 ; i < node.size() ; i++){
			System.out.println("----------------");
			System.out.println(node.get(i).getText());
			System.out.println("----------------");
		}
	}*/
}
