package model;

import java.util.ArrayList;
import java.util.List;

import main.Graph;
import utils.BigArray;

public class HugeDistanceVector implements DistanceVectorStrategy{
	private BigArray<DistanceElement> vector;
	
	public HugeDistanceVector(long sourceIndex, Graph g) {
		DistanceElement first = new DistanceElement(sourceIndex);
		first.changeDistance(0);
		vector = new BigArray<>(g.getNumberOfNodes());
		vector.set(sourceIndex, first);
	}
	
	public void addElement(DistanceElement element) {
		vector.set(element.getIndex(), element);
	}
	
	public DistanceElement getElement(long index) {
		if (vector.get(index) == null)
			vector.set(index, new DistanceElement(index));
		return vector.get(index);
	}
	
	public boolean hasElement(long index) {
		return vector.get(index) != null;
	}
	
	public void print(long source, long target) {
		if (getElement(target).getPreviousIndex() == -1) {
			System.out.println("No path between them");
			return;
		}
		List<Long> l = new ArrayList<>();
		l.add(vector.get(target).getRealId());
		long parent = target;
		while ((parent = vector.get(parent).getPreviousIndex()) != -1) {
			l.add(0, vector.get(parent).getRealId());
			if (parent == source) break;
		}
		for (Long id : l) {
			System.out.print(" -> " + id);
		}
		System.out.println();
	}
	
	public double getDistance(long target) {
		if (vector.get(target) == null) return 0;
		return vector.get(target).getDistance();
	}
	
}
