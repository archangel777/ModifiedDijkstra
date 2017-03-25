package test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.graphhopper.util.StopWatch;

import model.auxiliar_structures.DistanceVector;
import model.graph_entities.Coordinate;
import model.graphs.Graph;
import utils.TableParserUtils;;

public class DijkstraTest {
	public static void main(String[] args) {
		//Graph g = TableParserUtils.getBeijingGraph();
		Graph g = TableParserUtils.importOrLoad("./test/mygraph/", "berlin-latest.osm.pbf");
		//Graph g = TableParserUtils.getOSMGraph("monaco-latest.osm.pbf");
		//ContractedGraph cg = new ContractedGraph(g);
		
		if (args.length == 5) {
			Coordinate fromCoord = new Coordinate(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
			Coordinate toCoord = new Coordinate(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
			long sourceIndex = g.getNodeIndexByCoord(fromCoord);
			System.out.println(g.getNode(sourceIndex).getCoord());
			long targetIndex = g.getNodeIndexByCoord(toCoord);
			System.out.println(g.getNode(targetIndex).getCoord());
			long init = System.currentTimeMillis();
			DistanceVector v = g.runDijkstra(sourceIndex, targetIndex);
			//v.print(sourceIndex, targetIndex);
			System.out.println(v.getDistance(targetIndex));
			long time = System.currentTimeMillis() - init;
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(args[4])));
				writer.write(String.valueOf(time));
				writer.flush();
				writer.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
		
		else {
			int numberOfExperiments = 500;
			ArrayList<Long> testSources = new ArrayList<>();
			ArrayList<Long> testTargets = new ArrayList<>();
			for (int i = 0; i < numberOfExperiments; i++) {
				testSources.add(g.getRandomNodeIndex());
				testTargets.add(g.getRandomNodeIndex());
			}
			
			StopWatch sw = new StopWatch();
			for (int i = 0; i < numberOfExperiments; i++) {
				long sourceIndex = testSources.get(i);
				long targetIndex = testTargets.get(i);
				StopWatch local = new StopWatch();
				sw.start();
				local.start();
				g.runDijkstra(sourceIndex, targetIndex);
				local.stop();
				sw.stop();
				//System.out.println("Time: " + local.getTime());
				//System.out.println("Distance: " + v.getDistance(targetIndex) + " meters");
			}
		
			System.out.println("Average Time: " + (sw.getTime()/numberOfExperiments));
			
			sw = new StopWatch();
			for (int i = 0; i < numberOfExperiments; i++) {
				long sourceIndex = testSources.get(i);
				long targetIndex = testTargets.get(i);
				StopWatch local = new StopWatch();
				sw.start();
				local.start();
				g.runLegacyDijkstra(sourceIndex, targetIndex);
				local.stop();
				sw.stop();
				//System.out.println("Time: " + local.getTime());
				//System.out.println("Distance: " + v.getDistance(targetIndex) + " meters");
			}
		
			System.out.println("Average Time of legacy: " + (sw.getTime()/numberOfExperiments));
		
		}

	}
}
