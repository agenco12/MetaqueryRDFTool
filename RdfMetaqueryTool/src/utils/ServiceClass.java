package utils;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.GraphNode;

public class ServiceClass {
	
	public static ArrayList<GraphNode> node = new ArrayList<GraphNode>();
	public static Group root;
	public static String metaquery;
	
	
	public static void setDialog(String dialog,String title){
		try {
		    // Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/" + dialog + ".fxml"));
		    AnchorPane page = (AnchorPane) loader.load();
		    Stage dialogStage = new Stage();
		    dialogStage.setTitle(title);
		    dialogStage.initModality(Modality.WINDOW_MODAL);
		    dialogStage.initOwner(Main.getPs());
		    Scene scene = new Scene(page);
		    dialogStage.setScene(scene);
		    

	     loader.<DialogController>getController().setDialogStage(dialogStage);

		    // Show the dialog and wait until the user closes it
		    dialogStage.showAndWait();


		  } catch (IOException e) {
		    // Exception gets thrown if the fxml file could not be loaded
		    e.printStackTrace();
		  }
		
	}

	public static boolean isInputValid(TextField t){
		 String text = t.getText();
		 if(text.equalsIgnoreCase("") || !text.contains("?") || text.length() > 2 || text == null){
			 return false;
		 }
		 else{
		 return true;
	 }
		 }
	 
	 public static void showErrorDialog(String error){
		 Alert alert = new Alert(AlertType.ERROR);
		 alert.setTitle("Error");
		 alert.setHeaderText(null);
		 alert.setContentText(error);

		 alert.showAndWait();
	 }

	 public static GraphNode fromStringToGraphNode(String n){
			
			for(int i = 0 ; i < ServiceClass.node.size();i++){
				if(n.equalsIgnoreCase(ServiceClass.node.get(i).getText())){
					return ServiceClass.node.get(i);
				}
			}
			return null;
		}


}
