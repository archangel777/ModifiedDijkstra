package main;

import java.util.List;

import model.*;

public interface Graph {

	long getNumberOfNodes();

	List<Edge> getEdges();

	List<Node> getNodes();

	void addNode(Node node);

	void addEdge(Edge edge);

	long getRandomNodeIndex();

	DistanceVector runDijkstra(long sourceIndex, long targetIndex);

	long getNodeIndexByCoord(Coordinate fromCoord);

	Node getNode(long sourceIndex);
	
	

}
