package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.GraphStorage;
import com.graphhopper.storage.NodeAccess;
import com.graphhopper.util.EdgeIterator;

import hugedataaccess.DataAccess;
import hugedataaccess.MMapDataAccess;
import model.graph_entities.Coordinate;
import model.graph_entities.Edge;
import model.graph_entities.Node;
import model.graphs.Graph;

public class HugeGraphLoader implements GraphLoaderStrategy{
	
	private String dir;
	
	public HugeGraphLoader(String dir) {
		this.dir = dir;
	}
	
	public Graph getYuriGraph() {
		Graph g = new Graph();
		g.addNode(new Node(1l));
		g.addNode(new Node(2l));
		g.addNode(new Node(3l));
		g.addNode(new Node(4l));
		g.addNode(new Node(5l));
		g.addNode(new Node(6l));
		g.addNode(new Node(7l));
		g.addEdge(new Edge(1l, 0, 1, 6));
		g.addEdge(new Edge(2l, 1, 2, 2));
		g.addEdge(new Edge(3l, 0, 5, 3));
		g.addEdge(new Edge(4l, 1, 3, 7));
		g.addEdge(new Edge(5l, 1, 6, 12));
		g.addEdge(new Edge(6l, 3, 4, 11));
		g.addEdge(new Edge(7l, 4, 6, 3));
		g.addEdge(new Edge(8l, 3, 5, 15));
		return g;
	}
	
	public Graph getSmallTestGraph() {
		Graph g = new Graph();
		g.addNode(new Node(1l));
		g.addNode(new Node(2l));
		g.addNode(new Node(3l));
		g.addNode(new Node(4l));
		g.addNode(new Node(5l));
		g.addNode(new Node(6l));
		g.addEdge(new Edge(1l, 0, 1, 1));
		g.addEdge(new Edge(2l, 0, 2, 10));
		g.addEdge(new Edge(3l, 1, 2, 2));
		g.addEdge(new Edge(4l, 2, 3, 3));
		g.addEdge(new Edge(5l, 2, 4, 10));
		g.addEdge(new Edge(6l, 3, 4, 2));
		g.addEdge(new Edge(7l, 3, 5, 20));
		g.addEdge(new Edge(8l, 4, 5, 1));
		return g;
	}
	
	public Graph getMediumTestGraph() {
		Graph g = new Graph();
		g.addNode(new Node(1l));
		g.addNode(new Node(2l));
		g.addNode(new Node(3l));
		g.addNode(new Node(4l));
		g.addNode(new Node(5l));
		g.addNode(new Node(6l));
		g.addNode(new Node(7l));
		g.addNode(new Node(8l));
		g.addNode(new Node(9l));
		g.addNode(new Node(10l));
		g.addNode(new Node(11l));
		g.addNode(new Node(12l));
		g.addEdge(new Edge(1l, 0, 1, 1306));
		g.addEdge(new Edge(2l, 0, 4, 2161));
		g.addEdge(new Edge(3l, 0, 6, 2661));
		g.addEdge(new Edge(4l, 1, 2, 629));
		g.addEdge(new Edge(5l, 1, 3, 919));
		g.addEdge(new Edge(6l, 2, 3, 435));
		g.addEdge(new Edge(7l, 3, 4, 1225));
		g.addEdge(new Edge(8l, 3, 5, 1983));
		g.addEdge(new Edge(9l, 4, 5, 1258));
		g.addEdge(new Edge(10l, 4, 6, 1483));
		g.addEdge(new Edge(11l, 5, 6, 1532));
		g.addEdge(new Edge(12l, 5, 8, 2113));
		g.addEdge(new Edge(13l, 5, 9, 2161));
		g.addEdge(new Edge(14l, 6, 7, 661));
		g.addEdge(new Edge(15l, 7, 8, 1145));
		g.addEdge(new Edge(16l, 7, 10, 1613));
		g.addEdge(new Edge(17l, 8, 9, 1709));
		g.addEdge(new Edge(18l, 8, 10, 725));
		g.addEdge(new Edge(19l, 8, 11, 383));
		g.addEdge(new Edge(20l, 9, 11, 2145));
		g.addEdge(new Edge(21l, 10, 11, 338));
		return g;
	}
	
	public Graph getOSMGraph(String filename) {
		Graph graph = new Graph();
		//double initialTime = System.currentTimeMillis();
		String osmFile = "./resources/" + filename;
		String hopperDir = "./test/osmimporter";
		GraphHopper gh = new GraphHopper()
				.forServer()
				.setOSMFile(osmFile)
				.setGraphHopperLocation(hopperDir)
				.setEncodingManager(new EncodingManager("car"))
				.importOrLoad();
		GraphStorage gs = gh.getGraph();
		EdgeIterator edgeIterator = gs.getAllEdges();
		
		HashSet<Long> set = new HashSet<>(); 
		HashMap<Long, Long> idToGraphIndex = new HashMap<>();
		
		while(edgeIterator.next()) {
			long externalFromNodeId = edgeIterator.getBaseNode();
			long externalToNodeId = edgeIterator.getAdjNode();
			long externalEdgeId = edgeIterator.getEdge();
			double distance = edgeIterator.getDistance(); 
			
			NodeAccess access = gs.getNodeAccess();
			
			double fromLat = access.getLatitude((int)externalFromNodeId);
			double fromLng = access.getLongitude((int)externalFromNodeId);	

			double toLat = access.getLatitude((int)externalToNodeId);
			double toLng = access.getLongitude((int)externalToNodeId);
			
			// Nodes
			Node node;
			if (!set.contains(externalFromNodeId)) {
				node = new Node(externalFromNodeId, new Coordinate(fromLat, fromLng));
				graph.addNode(node);
				set.add(externalFromNodeId);
				idToGraphIndex.put(externalFromNodeId, node.getGraphIndex());
			}
			if (!set.contains(externalToNodeId)) {
				node = new Node(externalToNodeId, new Coordinate(toLat, toLng));
				graph.addNode(node);
				set.add(externalToNodeId);
				idToGraphIndex.put(externalToNodeId, node.getGraphIndex());
			}
			// Edges
			int direction = getDirection(edgeIterator.getFlags());
			
			if(externalFromNodeId == externalToNodeId) continue;
			
			if (direction == 0 || direction == 1)
				graph.addEdge(new Edge(externalEdgeId, idToGraphIndex.get(externalFromNodeId), idToGraphIndex.get(externalToNodeId), distance));
			if (direction == 0 || direction == -1)
				graph.addEdge(new Edge(externalEdgeId, idToGraphIndex.get(externalToNodeId), idToGraphIndex.get(externalFromNodeId), distance));

		}
		return graph;
    }
	
	public void parseGraph(String fromFile) {
		this.parseGraph(fromFile, "./graph/");
	}
	
	public void parseGraph(String fromFile, String toDir) {
		String tmpDirName = "./tmp/";
		File tmpDir = new File(tmpDirName);
		if (!tmpDir.exists())
			tmpDir.mkdirs();
		File tmp1, tmp2;
		
		/********************
		 *  Hopper Loading  *
		 ********************/
		String osmFile = "./resources/" + fromFile;
		String hopperDir = "./test/osmimporter";
		GraphHopper gh = new GraphHopper()
				.forServer()
				.setOSMFile(osmFile)
				.setGraphHopperLocation(hopperDir)
				.setEncodingManager(new EncodingManager("car"))
				.importOrLoad();
		GraphStorage gs = gh.getGraph();
		EdgeIterator edgeIterator = gs.getAllEdges();
		
		tmp1 = new File(tmpDirName + "tmp1.txt");
		BufferedReader reader;
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(tmp1));
		
			while(edgeIterator.next()) {
				long externalEdgeId = edgeIterator.getEdge();
				long externalFromNodeId = edgeIterator.getBaseNode();
				long externalToNodeId = edgeIterator.getAdjNode();
				double distance = edgeIterator.getDistance();
				writer.write(externalEdgeId + " " + externalFromNodeId + " " + externalToNodeId + " " + distance + "\n");
			}
			
			/*****************
			 *    Parsing    *
			 *****************/
			
			int bufferLimit = 1000;
			
			while (tmp1.length() > 0) {
				
				tmp2 = new File(tmpDirName + "tmp2.txt");
				reader = new BufferedReader(new FileReader(tmp1));
				writer = new BufferedWriter(new FileWriter(tmp2));
				
				HashMap<Long, Long> idToGraphIndex = new HashMap<>();
				ArrayList<Long> nodes = new ArrayList<>();
				
				String line;
				while ((line = reader.readLine()) != null) {
					String[] splt = line.split(" ");
					long externalEdgeId = Long.parseLong(splt[0]);
					long externalFromNodeId = Long.parseLong(splt[1]);
					long externalToNodeId = Long.parseLong(splt[2]);
					double distance = Double.parseDouble(splt[3]);
				}
				
				tmp1.delete();
				tmp2.renameTo(new File("tmp1.txt"));
				tmp1 = new File(tmpDirName + "tmp1.txt");
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashSet<Long> set = new HashSet<>();
		HashMap<Long, Long> idToGraphIndex = new HashMap<>();
		
		while(edgeIterator.next()) {
			long externalEdgeId = edgeIterator.getEdge();
			long externalFromNodeId = edgeIterator.getBaseNode();
			long externalToNodeId = edgeIterator.getAdjNode();
			double distance = edgeIterator.getDistance(); 
			
			double fromLat = gs.getNodeAccess().getLatitude((int)externalFromNodeId);
			double fromLng = gs.getNodeAccess().getLongitude((int)externalFromNodeId);	

			double toLat = gs.getNodeAccess().getLatitude((int)externalToNodeId);
			double toLng = gs.getNodeAccess().getLongitude((int)externalToNodeId);
			// Nodes
			Node node;
			if (!set.contains(externalFromNodeId)) {
				node = new Node(externalFromNodeId, new Coordinate(fromLat, fromLng));
				//graph.addNode(node);
				set.add(externalFromNodeId);
				idToGraphIndex.put(externalFromNodeId, node.getGraphIndex());
			}
			if (!set.contains(externalToNodeId)) {
				node = new Node(externalToNodeId, new Coordinate(toLat, toLng));
				//graph.addNode(node);
				set.add(externalToNodeId);
				idToGraphIndex.put(externalToNodeId, node.getGraphIndex());
			}
			// Edges
			int direction = getDirection(edgeIterator.getFlags());

			if(externalFromNodeId == externalToNodeId) continue;
			
			/*if (direction == 0 || direction == 1)
				graph.addEdge(new Edge(externalEdgeId, idToGraphIndex.get(externalFromNodeId), idToGraphIndex.get(externalToNodeId), distance));
			if (direction == 0 || direction == -1)
				graph.addEdge(new Edge(externalEdgeId, idToGraphIndex.get(externalToNodeId), idToGraphIndex.get(externalFromNodeId), distance));
			 */
		}
    }
	
	private int getDirection(long flags) {
		long direction = (flags & 3);

		if(direction ==  1) return 1;   // One direction: From --> To 
		if(direction ==  2) return -1;  // One direction: To --> From
		if(direction == 3) return 0;   // Bidirectional: To <--> From
		
		return 9999;
	}
	
	public Graph importOrLoad(String osmFile) {
		File f = new File(dir + "nodes");
		if (!f.exists()) this.saveGraph(this.getOSMGraph(osmFile));
		return this.loadGraph();
	}
	
	public void saveGraph(Graph g) {
		String edgeFile = dir + "edges";
		String nodeFile = dir + "nodes";
		//String adjFile = dir + "adjs";
		
		DataAccess nodeAccess = new MMapDataAccess(nodeFile, 20*g.getNumberOfNodes(), MMapDataAccess.DEFAULT_SEGMENT_SIZE);
		//DataAccess adjAccess = new MMapDataAccess(edgeFile, 16*g.getNumberOfNodes(), 16);
		DataAccess edgeAccess = new MMapDataAccess(edgeFile, 32*g.getNumberOfEdges(), MMapDataAccess.DEFAULT_SEGMENT_SIZE);
		
		long offset = 0;
		for (Edge e : g.getEdges()) {
			edgeAccess.setLong(offset, e.getId());
			edgeAccess.setLong(offset + 8, e.getFromNodeIndex());
			edgeAccess.setLong(offset + 16, e.getToNodeIndex());
			edgeAccess.setDouble(offset + 24, e.getCost());
			offset += 32;
		}
		offset = 0;
		long adjOffset = 0;
		for (Node n : g.getNodes()) {
			nodeAccess.setLong(offset, n.getId());
			System.out.println(nodeAccess.getLong(offset));
			nodeAccess.setLong(offset + 8, adjOffset);
			nodeAccess.setInt(offset + 16, n.getNumberOfAdjacents());
//				for (Edge adj : n.getAdjacents()) {
//					adjWriter.write(adj.getGraphIndex() + "\n");
//				}
			adjOffset += n.getNumberOfAdjacents();
			offset += 32;
		}
	}
	
	public Graph loadGraph() {
		Graph g = new Graph(dir);
		String edgeFile = dir + "edges";
		String nodeFile = dir + "nodes";
		//String adjFile = dir + "adjs.txt";
		
		DataAccess nodeAccess = new MMapDataAccess(nodeFile, 32);
		//DataAccess adjAccess = new MMapDataAccess(edgeFile, 16*g.getNumberOfNodes(), 16);
		DataAccess edgeAccess = new MMapDataAccess(edgeFile, 32);
		
		System.out.println(edgeAccess.getCapacity() / 32);
		
		for (int off = 0; off < nodeAccess.getCapacity(); off += 32) {
			System.out.println(nodeAccess.getLong(0));
			g.addNode(new Node(nodeAccess.getLong(off)));
		}
			
		for (int off = 0; off < edgeAccess.getCapacity(); off += 32) {
			long id = nodeAccess.getLong(off);
			long fromIndex = nodeAccess.getLong(off + 8);
			long toIndex = nodeAccess.getLong(off + 16);
			double cost = nodeAccess.getDouble(off + 24);
			g.addEdge(new Edge(id, fromIndex, toIndex, cost));
		}
		
		return g;
	}

	@Override
	public Graph generateRandomGraph(long nNodes, float density) {
		// TODO Auto-generated method stub
		return null;
	}
}
