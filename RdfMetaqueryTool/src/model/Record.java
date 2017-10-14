package model;

import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class Record {
	
	GraphNode n1;
	GraphNode n2;
	Line edge;
	Label edgeLabel;
	
	public Record(GraphNode n1, GraphNode n2, Line edge, Label edgeLabel) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.edge = edge;
		this.edgeLabel = edgeLabel;
	}

	public GraphNode getN1() {
		return n1;
	}

	public void setN1(GraphNode n1) {
		this.n1 = n1;
	}

	public GraphNode getN2() {
		return n2;
	}

	public void setN2(GraphNode n2) {
		this.n2 = n2;
	}

	public Line getEdge() {
		return edge;
	}

	public void setEdge(Line edge) {
		this.edge = edge;
	}

	public Label getEdgeLabel() {
		return edgeLabel;
	}

	public void setEdgeLabel(Label edgeLabel) {
		this.edgeLabel = edgeLabel;
	}
	
	
	
	

}
