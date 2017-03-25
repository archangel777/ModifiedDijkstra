package model;

public interface DistanceVectorStrategy {
	
	public void addElement(DistanceElement element);
	
	public DistanceElement getElement(long index);
	
	public boolean hasElement(long index);
		
	public void print(long source, long target);
	
	public double getDistance(long target);
	
}
