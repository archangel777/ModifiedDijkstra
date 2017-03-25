package model.graphs;

import model.auxiliar_structures.DistanceVector;
import model.graph_entities.Coordinate;
import model.graph_entities.Edge;
import model.graph_entities.Node;

public interface GraphStrategy {
	
	long getNumberOfNodes();
	
	long getNumberOfEdges();

	Iterable<Edge> getEdges();

	Iterable<Node> getNodes();

	void addNode(Node node);

	void addEdge(Edge edge);

	long getRandomNodeIndex();

	DistanceVector runDijkstra(long sourceIndex, long targetIndex);

	long getNodeIndexByCoord(Coordinate fromCoord);

	Node getNode(long sourceIndex);

	DistanceVector runLegacyDijkstra(long sourceIndex, long targetIndex);
	
}
