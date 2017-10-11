package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.glass.ui.Size;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.GraphNode;
import utils.DialogController;
import utils.ServiceClass;

public class DeleteNodeController implements DialogController, Initializable {

	Stage dialogStage;
	@FXML
	private Button okButton, cancelButton;
	@FXML
	private ComboBox nOneComboBox;
	private ObservableList<String> lOne = FXCollections.observableArrayList();
	private ArrayList<GraphNode> node = ServiceClass.node;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadNComboBox(nOneComboBox, lOne);

	}

	private void loadNComboBox(ComboBox c, ObservableList<String> l) {
		for (int i = 0; i < node.size(); i++) {
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
		if (nodeString != null) {
			ObservableList<Node> nodeToDelete = FXCollections.observableArrayList();
			GraphNode n = ServiceClass.fromStringToGraphNode(nodeString);
			// remove this node from arrayList
			node.remove(n);
			// delete all connection from this node to other node
			 breakConnectionWithOtherNode(n);
			 deleteEdges(n);
			 deleteEdgesLabel(n);
			// delete all edges from this node to other node
			for (int i = 0; i < n.getEdges().size(); i++) {
				nodeToDelete.add(n.getEdges().get(i));
			}
			// delete all label of edges
			for (int i = 0; i < n.getEdgesLabelList().size(); i++) {
				nodeToDelete.add(n.getEdgesLabelList().get(i));
			}
			nodeToDelete.add(n);
			printConnectedNode();
			printLabel();
			printLine();
			// remove from the group the node and the edges
			ServiceClass.root.getChildren().removeAll(nodeToDelete);

			dialogStage.close();

		} else {
			ServiceClass.showErrorDialog("Make sure you have selected the value of the node to be deleted");

		}
	}

	private void deleteEdges(GraphNode n) {
		ArrayList<Line> edgesToDelete = n.getEdges();
		for (int i = 0; i < node.size(); i++) {
			GraphNode currentNode = node.get(i);
			for (int j = 0; j < edgesToDelete.size(); j++) {
				Line currentLine = edgesToDelete.get(j);
				for (int k = 0; k < currentNode.getEdges().size(); k++) {
					if (currentLine.equals(currentNode.getEdges().get(k))) {
						currentNode.getEdges().remove(k);
						break;
					}
				}
			}
		}
	}
	
	private void deleteEdgesLabel(GraphNode n){
		ArrayList<Label> edgesLabelToDelete = n.getEdgesLabelList();
		for (int i = 0; i < node.size(); i++) {
			GraphNode currentNode = node.get(i);
			for (int j = 0; j < edgesLabelToDelete.size(); j++) {
				Label currentLabel = edgesLabelToDelete.get(j);
				for (int k = 0; k < currentNode.getEdgesLabelList().size(); k++) {
					if (currentLabel.equals(currentNode.getEdgesLabelList().get(k))) {
						currentNode.getEdgesLabelList().remove(k);
						break;
					}
				}
			}
		}
	}

	private void printConnectedNode() {
		for (int i = 0; i < node.size(); i++) {
			GraphNode currentNode = node.get(i);
			System.out.println("--------------------------------------------");
			System.out.println(currentNode.getText());
			System.out.println("--------------------------------------------");
			for (int j = 0; j < currentNode.getConnectedNodes().size(); j++) {
				System.out.println(currentNode.getConnectedNodes().get(j).getText());

			}
		}
	}

	private void printLabel() {
		for (int i = 0; i < node.size(); i++) {
			GraphNode currentNode = node.get(i);
			System.out.println("--------------------------------------------");
			System.out.println(currentNode.getText());
			System.out.println("--------------------------------------------");
			for (int j = 0; j < currentNode.getEdgesLabelList().size(); j++) {
				System.out.println(currentNode.getEdgesLabelList().get(j));

			}
		}
	}

	private void printLine() {
		for (int i = 0; i < node.size(); i++) {
			GraphNode currentNode = node.get(i);
			System.out.println("--------------------------------------------");
			System.out.println(currentNode.getText());
			System.out.println("--------------------------------------------");
			for (int j = 0; j < currentNode.getEdges().size(); j++) {
				System.out.println(currentNode.getEdges().get(j).toString());

			}
		}
	}

	private void breakConnectionWithOtherNode(GraphNode n) {
		for (int i = 0; i < node.size(); i++) {
			GraphNode currentNode = node.get(i);
			for (int j = 0; j < currentNode.getConnectedNodes().size(); j++) {
				if (currentNode.getConnectedNodes().get(j).equals(n)) {
					currentNode.breakConnectionWith(n);
					break;
				}
			}
		}
	}

}
