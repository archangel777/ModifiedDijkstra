package utils;

import model.graphs.Graph;

public class GraphLoader {
	
	private GraphLoaderStrategy strategy;
	
	public GraphLoader(boolean isHuge) {
		if (isHuge) strategy = new HugeGraphLoader("./test/myHugeGraph/");
		else strategy = new MemoryGraphLoader("./test/myGraph/");
	}
	
	public Graph getYuriGraph() {
		return strategy.getYuriGraph();
	}
	
	public Graph getSmallTestGraph() {
		return strategy.getSmallTestGraph();
	}
	
	public Graph getMediumTestGraph() {
		return strategy.getMediumTestGraph();
	}
	
	public Graph getOSMGraph(String filename) {
		return strategy.getOSMGraph(filename);
    }
	
	public Graph importOrLoad(String osmFile) {
		return strategy.importOrLoad(osmFile);
	}

	public void saveGraph(Graph g) {
		strategy.saveGraph(g);
	}
	
	public Graph loadGraph() {
		return strategy.loadGraph();
	}
	
}