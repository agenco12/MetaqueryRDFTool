package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DialogController;

public class EditNodeController implements DialogController, Initializable {

	Stage dialogStage;
	@FXML private Button okButton, cancelButton;
	@FXML private ComboBox nOneComboBox;
	@FXML private TextField newValueTextField;
	
	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
