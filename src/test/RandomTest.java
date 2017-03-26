package test;

import model.graphs.Graph;
import utils.GraphLoader;

public class RandomTest {
	public static void main(String[] args) {
		GraphLoader loader = new GraphLoader(true);
		Graph g = loader.importOrLoad("berlin-latest.osm.pbf");
		System.out.println(g.getNumberOfNodes() + " | " + g.getNumberOfEdges());
	}
}
