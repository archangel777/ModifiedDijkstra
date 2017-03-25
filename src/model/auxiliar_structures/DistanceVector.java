package model.auxiliar_structures;

public class DistanceVector{
	
	private DistanceVectorStrategy strategy;
	
	public DistanceVector(long sourceIndex, long numberOfNodes) {
		if (numberOfNodes <= Integer.MAX_VALUE)
			strategy = new NormalDistanceVector((int)sourceIndex, numberOfNodes);
		else
			strategy = new HugeDistanceVector(sourceIndex, numberOfNodes);
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
