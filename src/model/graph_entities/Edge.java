package model.graph_entities;
import java.util.ArrayList;

public class Edge extends GraphEntity {
	
	private Long id;
	private long fromNodeIndex, toNodeIndex;
	private double cost;
	
	public Edge(Long edgeId, long fromIndex, long toIndex, double cost) {
		this.id = edgeId;
		this.fromNodeIndex = fromIndex;
		this.toNodeIndex = toIndex;
		this.cost = cost;
	}
	
	public Long getId() {
		return id;
	}
	
	public double getCost() {
		return cost;
	}
	
	public long getFromNodeIndex() {
		return fromNodeIndex;
	}
	
	public long getToNodeIndex() {
		return toNodeIndex;
	}
	
	public ArrayList<Edge> decontract() {
		ArrayList<Edge> arr = new ArrayList<>();
		arr.add(this);
		return arr;
	}
}
