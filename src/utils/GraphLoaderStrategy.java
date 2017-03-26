package utils;

import model.graphs.Graph;

public interface GraphLoaderStrategy {
	public Graph getYuriGraph();
	
	public Graph getSmallTestGraph();
	
	public Graph getMediumTestGraph();
	
	public Graph getOSMGraph(String filename);
	
	public Graph importOrLoad(String osmFile);
		
	public void saveGraph(Graph g);
		
	public Graph loadGraph();
	
}
