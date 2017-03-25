package model.graphs;

import model.auxiliar_structures.DistanceVector;
import model.graph_entities.Coordinate;
import model.graph_entities.Edge;
import model.graph_entities.Node;

public class StaticGraphStrategy implements GraphStrategy{

	@Override
	public long getNumberOfNodes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<Edge> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Node> getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNode(Node node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEdge(Edge edge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getRandomNodeIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DistanceVector runDijkstra(long sourceIndex, long targetIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getNodeIndexByCoord(Coordinate fromCoord) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Node getNode(long sourceIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
