package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.GraphNode;
import utils.DialogController;
import utils.ServiceClass;

public class AddNodeController implements DialogController, Initializable {

	@FXML
	private TextField nTextField;
	@FXML
	private Button okButton, cancelButton;
	private Stage dialogStage;
	private double orgSceneX, orgSceneY;
	private double orgTranslateX, orgTranslateY;

	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handleOk() {
		if (ServiceClass.isInputValid(nTextField) & isValidValue(nTextField)) {
			createNode(nTextField.getText().toString());
			dialogStage.close();
		} else {
			ServiceClass.showErrorDialog("Make sure that you enter a valid value and that this is preceded by the \"?\" Character");
		}

	}

	private boolean isValidValue(TextField text) {
		for (int i = 0; i < ServiceClass.node.size(); i++) {
			if (text.getText().toString().equalsIgnoreCase(ServiceClass.node.get(i).getText())) {
				return false;
			}
		}
		return true;
	}

	private void createNode(String nodeName) {
		Random rnd = new Random();
		int j = 3;
		int n = 100 - j;
		int k = rnd.nextInt(n) + j;
		int h = rnd.nextInt(n) + j;
		GraphNode node = new GraphNode(nodeName, k, h, Color.BLACK);
		node.setOnMousePressed(circleOnMousePressedEventHandler);
		node.setOnMouseDragged(circleOnMouseDraggedEventHandler);
		node.setCursor(Cursor.HAND);

		ServiceClass.node.add(node);
		ServiceClass.root.getChildren().add(node);
	}

	EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			orgSceneX = t.getSceneX();
			orgSceneY = t.getSceneY();

			GraphNode node = (GraphNode) t.getSource();

			orgTranslateX = node.getTranslateX();
			orgTranslateY = node.getTranslateY();
		}
	};

	EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			double offsetX = t.getSceneX() - orgSceneX;
			double offsetY = t.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;

			GraphNode node = (GraphNode) t.getSource();

			node.setTranslateX(newTranslateX);
			node.setTranslateY(newTranslateY);

			updateLocations(node);
		}
	};

	public static void updateLocations(GraphNode node) {

		ArrayList<GraphNode> connectedNodes = node.getConnectedNodes();

		ArrayList<Line> edgesList = node.getEdges();

		for (int i = 0; i < connectedNodes.size(); i++) {

			GraphNode neighbor = connectedNodes.get(i);
			Line l = edgesList.get(i);

			l.setStartX(node.getCenterX());

			l.setStartY(node.getCenterY());

			l.setEndX(neighbor.getCenterX());

			l.setEndY(neighbor.getCenterY());
		}
	}

}
