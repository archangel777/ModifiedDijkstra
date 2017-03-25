package test;

import model.graphs.Graph;
import utils.GraphLoader;

public class RandomTest {
	public static void main(String[] args) {
		Graph g = GraphLoader.importOrLoadHuge("./test/myHugeGraph/", "berlin-latest.osm.pbf");
		System.out.println(g.getNumberOfNodes() + " | " + g.getNumberOfEdges());
	}
}
