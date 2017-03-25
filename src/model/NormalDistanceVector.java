package model;
import java.util.ArrayList;
import java.util.List;

import main.Graph;

public class NormalDistanceVector  implements DistanceVectorStrategy{
	private DistanceElement[] vector;
	
	public NormalDistanceVector(int sourceIndex, Graph g) {
		vector = new DistanceElement[(int)g.getNumberOfNodes()];
		DistanceElement first = new DistanceElement(sourceIndex);
		first.changeDistance(0);
		vector[sourceIndex] = first;
	}
	
	public void addElement(DistanceElement element) {
		vector[(int)element.getIndex()] = element;
	}
	
	public DistanceElement getElement(long index) {
		if (vector[(int)index] == null)
			vector[(int)index] = new DistanceElement(index);
		return vector[(int)index];
	}
	
	public boolean hasElement(long index) {
		return index < vector.length;
	}
	
	public void print() {
		for (DistanceElement element : vector) {
			System.out.println("Distance to node " + element.getIndex() + ": " + element.getDistance() + " | Previous node: " + element.getPreviousIndex());
		}
	}
	
	public void print(long source, long target) {
		if (getElement(target).getPreviousIndex() == -1) {
			System.out.println("No path between them");
			return;
		}
		List<Long> l = new ArrayList<>();
		l.add(vector[(int)target].getRealId());
		int parent = (int)target;
		while ((parent = (int)vector[parent].getPreviousIndex()) != -1) {
			l.add(0, vector[parent].getRealId());
			if (parent == source) break;
		}
		for (Long id : l) {
			System.out.print(" -> " + id);
		}
		System.out.println();
	}
	
	public double getDistance(long target) {
		if (vector[(int)target] == null) return 0;
		return vector[(int)target].getDistance();
	}
}
