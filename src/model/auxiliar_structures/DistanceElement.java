package model.auxiliar_structures;

public class DistanceElement implements Comparable<DistanceElement>{
	private Long realId;
	private long index, previousIndex;
	private double distance;
	private boolean visited;
	
	public DistanceElement() {
		this.distance = Integer.MAX_VALUE;
		previousIndex = -1;
		visited = false;
	}
	
	public DistanceElement(long index) {
		this();
		this.index = index;
	}
	
	public DistanceElement(Long realId, long index) {
		this(index);
		this.realId = realId;
	}
	
	public void changePrevious(long newPreviousIndex) {
		previousIndex = newPreviousIndex;
	}
	
	public void changeDistance(double newDistance) {
		distance = newDistance;
	}
	
	public void update(double newDistance, long newParentIndex) {
		distance = newDistance;
		previousIndex = newParentIndex;
	}
	
	public Long getRealId() {
		return realId;
	}
	
	public long getIndex() {
		return this.index;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public long getPreviousIndex() {
		return previousIndex;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(boolean b) {
		visited = b;
	}

	@Override
	public int compareTo(DistanceElement o) {
		return Double.compare(this.distance, o.getDistance());
	}
	
}
