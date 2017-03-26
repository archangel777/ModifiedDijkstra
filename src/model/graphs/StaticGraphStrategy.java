package model.graphs;

import java.io.File;

import hugedataaccess.DataAccess;
import model.auxiliar_structures.DistanceVector;
import model.graph_entities.Coordinate;
import model.graph_entities.Edge;
import model.graph_entities.Node;

public class StaticGraphStrategy implements GraphStrategy{
	
	File nodesFile, adjFile, edgesFile;
	DataAccess nodesAccess, adjAccess, edgesAccess;
	
	public StaticGraphStrategy(String path) {
		nodesFile = new File(path + "nodes");
		adjFile = new File(path + "adjs");
		edgesFile = new File(path + "edges");
		//nodesAccess = new MMapDataAccess(path + "nodes");
		//adjAccess = new MMapDataAccess(path + "adjs");
		//edgesAccess = new MMapDataAccess(path + "edges");
	}

	@Override
	public long getNumberOfNodes() {
		return nodesFile.length() / 32;
	}
	
	@Override
	public long getNumberOfEdges() {
		return edgesFile.length() / 32;
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

	@Override
	public DistanceVector runLegacyDijkstra(long sourceIndex, long targetIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
