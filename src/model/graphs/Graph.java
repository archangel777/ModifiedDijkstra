package model.graphs;


import model.auxiliar_structures.DistanceVector;
import model.graph_entities.*;

public class Graph {
	
	private GraphStrategy strategy;
	
	public Graph() {
		strategy = new DinamicGraphStrategy();
	}

	public long getNumberOfNodes() {
		return strategy.getNumberOfNodes();
	}

	public Iterable<Edge> getEdges() {
		return strategy.getEdges();
	}

	public Iterable<Node> getNodes() {
		return strategy.getNodes();
	}

	public void addNode(Node node) {
		strategy.addNode(node);
	}

	public void addEdge(Edge edge) {
		strategy.addEdge(edge);
	}

	public long getRandomNodeIndex() {
		return strategy.getRandomNodeIndex();
	}

	public DistanceVector runDijkstra(long sourceIndex, long targetIndex) {
		return strategy.runDijkstra(sourceIndex, targetIndex);
	}

	public long getNodeIndexByCoord(Coordinate fromCoord) {
		return strategy.getNodeIndexByCoord(fromCoord);
	}

	public Node getNode(long sourceIndex) {
		return strategy.getNode(sourceIndex);
	}
	
	

}
