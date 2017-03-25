package model.graphs;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import model.auxiliar_structures.DistanceElement;
import model.auxiliar_structures.DistanceVector;
import model.graph_entities.Coordinate;
import model.graph_entities.Edge;
import model.graph_entities.Node;
import utils.BigList;

public class DinamicGraphStrategy implements GraphStrategy{
	private BigList<Node> nodes = new BigList<>();
	private BigList<Edge> edges = new BigList<>();
	private HashMap<Long, Long> idToIndex = new HashMap<>();
	private HashMap<Coordinate, Long> coordToIndex = new HashMap<>();
	
	public void addNode(Node node) {
		nodes.add(node);
		node.setGraphIndex(nodes.size() - 1);
		idToIndex.put(node.getId(), node.getGraphIndex());
		Coordinate c = node.getCoord();
		if (c != null) {
			coordToIndex.put(c, node.getGraphIndex());
		}
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
		edge.setGraphIndex(edges.size() - 1);
		nodes.get(edge.getFromNodeIndex()).addAdjacent(edge);
		nodes.get(edge.getToNodeIndex()).addParent(edge);
	}
	
	public long getNodeIndexById(Long nodeId) {
		return idToIndex.get(nodeId);
	}
	
	public long getNodeIndexByCoord(Coordinate c) {
		Node best = null;
		double min = Double.POSITIVE_INFINITY;
		for (Node n : nodes) {
			double d = n.getCoord().distanceTo(c);
			if (d < min) {
				best = n;
				min = d;
			}
		}
		
		return best.getGraphIndex();
	}
	
	public Node getNode(long index) {
		return nodes.get(index);
	}
	
	public Iterable<Node> getNodes() {
		return nodes;
	}
	
	public Iterable<Edge> getEdges() {
		return edges;
	}
	
	public long getNumberOfNodes() {
		return nodes.size();
	}
	
	public long getNumberOfEdges() {
		return edges.size();
	}
	
	public DistanceVector runDijkstra(long sourceIndex) {
		return runDijkstra(sourceIndex, -1);
	}
		
	public DistanceVector runDijkstra(long sourceIndex, long targetIndex) {
		DistanceVector vector = new DistanceVector(sourceIndex, this.getNumberOfNodes());
		//DistanceVector vector = new HugeDistanceVector(sourceIndex, this);
		Queue<DistanceElement> toVisit = new PriorityQueue<>();
		
		toVisit.add(vector.getElement(sourceIndex));
		while (!toVisit.isEmpty()) {
			DistanceElement min = toVisit.remove();
			
			if (min.isVisited()) continue;
			min.setVisited(true);
			
			if (targetIndex != -1 && targetIndex == min.getIndex()) {
				return vector;
			}
			
			expandElement(min, vector, toVisit);
		}
		
		return vector;
	}
	
	public void expandElement(DistanceElement min, DistanceVector vector, Queue<DistanceElement> toVisit) {
		long minIndex = min.getIndex();
		for (Edge e : nodes.get((int)minIndex).getAdjacents()) {
			DistanceElement neighbor = vector.getElement(e.getToNodeIndex());
			if (neighbor.isVisited()) continue;
			double newDistance = min.getDistance() + e.getCost();
			if (newDistance < neighbor.getDistance()) {
				neighbor.update(newDistance, minIndex);
				toVisit.add(neighbor);
			}
		}
	}
	
	public long getRandomNodeIndex() {
		return Math.abs(new Random().nextLong()%this.getNumberOfNodes());
	}

	@Override
	public DistanceVector runLegacyDijkstra(long sourceIndex, long targetIndex) {
		DistanceVector vector = new DistanceVector(sourceIndex, this.getNumberOfNodes());
		//DistanceVector vector = new HugeDistanceVector(sourceIndex, this);
		Queue<DistanceElement> toVisit = new PriorityQueue<>();
		
		toVisit.add(vector.getElement(sourceIndex));
		while (!toVisit.isEmpty()) {
			DistanceElement min = toVisit.remove();
			min.setVisited(true);
			if (targetIndex != -1 && targetIndex == min.getIndex()) {
				return vector;
			}
			
			long minIndex = min.getIndex();
			for (Edge e : nodes.get((int)minIndex).getAdjacents()) {
				DistanceElement neighbor = vector.getElement(e.getToNodeIndex());
				if (neighbor.isVisited()) continue;
				double newDistance = min.getDistance() + e.getCost();
				if (newDistance < neighbor.getDistance()) {
					toVisit.remove(neighbor);
					neighbor.update(newDistance, minIndex);
					toVisit.add(neighbor);
				}
			}
		}
		
		return vector;
	}
}
