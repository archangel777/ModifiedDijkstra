package model;

import main.Graph;
import utils.BigArray;

public class DistanceVector{
	
	private DistanceVectorStrategy strategy;
	
	public DistanceVector(long sourceIndex, Graph g) {
		long size = g.getNumberOfNodes();
		if (size <= Integer.MAX_VALUE)
			strategy = new NormalDistanceVector((int)sourceIndex, g);
		else
			strategy = new HugeDistanceVector(sourceIndex, g);
	}
		
	public void addElement(DistanceElement element) {
		strategy.addElement(element);
	}
	
	public DistanceElement getElement(long index) {
		return strategy.getElement(index);
	}
	
	public boolean hasElement(long index) {
		return strategy.hasElement(index);
	}
		
	public void print(long source, long target) {
		strategy.print(source, target);
	}
	
	public double getDistance(long target) {
		return strategy.getDistance(target);
	}
	
}
