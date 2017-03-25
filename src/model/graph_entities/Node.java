package model.graph_entities;

import java.util.ArrayList;
import java.util.List;

public class Node extends GraphEntity {
	
	private Long id;
	private Coordinate coord;
	private List<Edge> adjacents = new ArrayList<>();
	private List<Edge> parents = new ArrayList<>();
	
	public Node(Long id) {
		this.id = id;
	}
	
	public Node(Long id, Coordinate c) {
		this(id);
		this.coord = c;
	}
	
	public void addAdjacent(Edge e) {
		adjacents.add(e);
	}
	
	public void addParent(Edge e) {
		parents.add(e);
	}
	
	public Long getId() {
		return id;
	}
	
	public Coordinate getCoord() {
		return coord;
	}
	
	public List<Edge> getAdjacents() {
		return adjacents;
	}
	
	public int getNumberOfAdjacents() {
		return adjacents.size();
	}
	
	public List<Edge> getParents() {
		return parents;
	}
	
	public int getNumberOfParents() {
		return parents.size();
	}
	
	public double getCostToNode(int otherIndex) {
		for (Edge e: adjacents) {
			if (otherIndex == e.getToNodeIndex())
				return e.getCost();
		}
		return -1;
	}
	
	public double getCostToParent(int parentIndex) {
		for (Edge e: parents) {
			if (parentIndex == e.getFromNodeIndex())
				return e.getCost();
		}
		return -1;
	}
	
}
