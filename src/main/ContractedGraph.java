package main;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import model.DistanceElement;
import model.Edge;
import model.Node;
import model.Shortcut;
import model.SpecialNode;
import utils.BigArray;

public class ContractedGraph {
	
	private BigArray<SpecialNode> nodes;
	private List<Edge> edges = new ArrayList<>();
	
	public ContractedGraph(Graph g) {
		nodes = new BigArray<>(g.getNumberOfNodes());
		for (Node n : g.getNodes())
			addNode(new SpecialNode(n.getId()));

		for (Edge e : g.getEdges()) {
			addEdge(new Edge(e.getId(), e.getFromNodeIndex(), e.getToNodeIndex(), e.getCost()));
		}
		
		enumerateNodes();
		
		beginContracting();
	}
	
	public void addNode(SpecialNode node) {
		node.setGraphIndex(nodes.size());
		nodes.set(node.getGraphIndex(), node);
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
		edge.setGraphIndex(edges.size() - 1);
		nodes.get((int)edge.getFromNodeIndex()).addAdjacent(edge);
		nodes.get((int)edge.getToNodeIndex()).addParent(edge);
	}
	
	public void enumerateNodes() {
		int k = 0;
		for (SpecialNode n : nodes) {
			calculateImportance(n);	
			System.out.println(k++);
		}
	}
	
	public void beginContracting() {
		System.out.println("Contracting");
		PriorityQueue<SpecialNode> queue = new PriorityQueue<>();
		for (SpecialNode n : nodes)
			queue.add(n);
		while (!queue.isEmpty()) {
			SpecialNode min = queue.poll();
			calculateImportance(min);
			if (!queue.isEmpty()) {
				while (min.getImportance().getValue() > queue.peek().getImportance().getValue()) {
					queue.add(min);
					min = queue.poll();
					calculateImportance(min);
				}
			}
			System.out.println("Contracting node " + min.getGraphIndex());
			contractNode(min);
		}
		System.out.println("Contracted!");
	}
	
	public List<Boolean> existsShorterPathThan(Edge e1, List<Edge> adjacents) {
		List<Long> adjToFind = new ArrayList<>();
		List<Boolean> foundPaths = new ArrayList<>(adjacents.size());
		
		for (Edge e : adjacents) {
			adjToFind.add(e.getToNodeIndex());
			foundPaths.add(false);
		}
		
		HashMap<Long, DistanceElement> map = new HashMap<>();
		
		DistanceElement ignoredElement = new DistanceElement(e1.getToNodeIndex());
		ignoredElement.setVisited(true);
		map.put(ignoredElement.getIndex(), ignoredElement);
		
		DistanceElement first = new DistanceElement(e1.getFromNodeIndex());
		first.changeDistance(0);
		map.put(first.getIndex(), first);
		
		PriorityQueue<DistanceElement> queue = new PriorityQueue<>();
		queue.add(first);
		
		while (!queue.isEmpty() && !adjToFind.isEmpty()) {
			DistanceElement min = queue.remove();
		
			if (min.isVisited()) continue;
			min.setVisited(true);
			int toRemoveIndex = adjToFind.indexOf(min.getIndex());
			if (toRemoveIndex != -1) {
				foundPaths.set(toRemoveIndex, true);
				adjToFind.remove(toRemoveIndex);
			}
			if (adjToFind.isEmpty()) break;
			
			for (Edge e : nodes.get((int)min.getIndex()).getAdjacents()) {
				double newDistance = min.getDistance() + e.getCost();
				if (!map.containsKey(e.getToNodeIndex())) {
					map.put(e.getToNodeIndex(), new DistanceElement(e.getToNodeIndex()));
				}
				DistanceElement neighbor = map.get(e.getToNodeIndex());
				
				if (neighbor.isVisited()) continue;
				
				if (neighbor.getDistance() > newDistance) {
					neighbor.changeDistance(newDistance);
					queue.add(neighbor);
				}
			}
		}
		return foundPaths;
	}
	
	public void calculateImportance(SpecialNode node) {
		List<Edge> needingShortcut = new ArrayList<>();
		int ed = 0;
		int cn = 0;
		for (Edge e1 : node.getParents()) {
			if (nodes.get((int)e1.getFromNodeIndex()).isContracted()) {
				cn++;
				continue;
			}
			List<Edge> nonContractedAdj = new ArrayList<>();
			for (Edge e : node.getAdjacents())
				if (!nodes.get(e.getToNodeIndex()).isContracted())
					nonContractedAdj.add(e);
			System.out.println("Adjacents: " + nonContractedAdj.size());
			List<Boolean> existingPaths = existsShorterPathThan(e1, nonContractedAdj);
			for (int i = 0; i < nonContractedAdj.size(); i++) {
				Edge e2 = nonContractedAdj.get(i);
				if (nodes.get(e2.getToNodeIndex()).isContracted()) continue;
				if (!existingPaths.get(i)) {
					ed++;
					if (!needingShortcut.contains(e1)) needingShortcut.add(e1);
					if (!needingShortcut.contains(e2)) needingShortcut.add(e2);
				}
			}
		}
		for (Edge e2 : node.getAdjacents())
			if (nodes.get(e2.getToNodeIndex()).isContracted()) cn++;
		ed -= node.getParents().size() + node.getAdjacents().size();
		int sc = needingShortcut.size();
		Importance nodeImportance = node.getImportance();
		nodeImportance.setContractedNeighbors(cn);
		nodeImportance.setEdgeDifference(ed);
		nodeImportance.setShortcutCover(sc);
	}

	public void contractNode(SpecialNode node) {
		List<Edge> edgesToAdd = new ArrayList<>();
		for (Edge e : node.getAdjacents()) {
			if (nodes.get(e.getToNodeIndex()).isContracted()) continue;
			nodes.get(e.getToNodeIndex()).getImportance().updateNodeLevel(node.getImportance().getNodeLevel() + 1);
		}
		for (Edge e1 : node.getParents()) {
			if (!nodes.get(e1.getFromNodeIndex()).isContracted()) {
				nodes.get(e1.getFromNodeIndex()).getImportance().updateNodeLevel(node.getImportance().getNodeLevel() + 1);
				List<Edge> nonContractedAdj = new ArrayList<>();
				for (Edge e : node.getAdjacents())
					if (!nodes.get(e.getToNodeIndex()).isContracted())
						nonContractedAdj.add(e);
				List<Boolean> existingPaths = existsShorterPathThan(e1, nonContractedAdj);
				for (int i = 0; i < nonContractedAdj.size(); i++) {
					Edge e2 = nonContractedAdj.get(i);
					if (!nodes.get(e2.getToNodeIndex()).isContracted()) {
						if (!existingPaths.get(i)) {
							edgesToAdd.add(new Shortcut((long)edges.size(), e1, e2));
							System.out.println("Adding shortcut from " + e1.getFromNodeIndex() + " to " + e2.getToNodeIndex());
						}
					}
				}
			}
		}
		for (Edge e : edgesToAdd) addEdge(e);
		node.setContracted(true);
	}
}
