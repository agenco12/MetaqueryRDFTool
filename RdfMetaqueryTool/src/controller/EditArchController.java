package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import utils.DialogController;

public class EditArchController implements DialogController, Initializable {

	Stage dialogStage;
	@FXML private Button okButton, cancelButton;
	@FXML private ComboBox nOneComboBox, nTwoComboBox, propertyComboBox;

	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
