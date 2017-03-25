package model.graph_entities;

import java.util.ArrayList;

public class Shortcut extends Edge{
	
	private Edge edge1, edge2;

	public Shortcut(Long edgeId, Edge edge1, Edge edge2) {
		super(edgeId, edge1.getFromNodeIndex(), edge2.getToNodeIndex(), edge1.getCost() + edge2.getCost());
		this.edge1 = edge1;
		this.edge2 = edge2;
	}

	@Override
	public ArrayList<Edge> decontract() {
		ArrayList<Edge> arr = edge1.decontract();
		arr.addAll(edge2.decontract());
		return arr;
	}
}
