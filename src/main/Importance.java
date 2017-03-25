package main;

public class Importance {
	private int edgeDifference = 0;
	private int contractedNeighbors = 0;
	private int shortcutCover = 0;
	private int nodeLevel = 0;
	
	public int getContractedNeighbors() {
		return contractedNeighbors;
	}
	
	public int getEdgeDifference() {
		return edgeDifference;
	}
	
	public int getNodeLevel() {
		return nodeLevel;
	}
	
	public int getShortcutCover() {
		return shortcutCover;
	}
	
	public void setContractedNeighbors(int contractedNeighbors) {
		this.contractedNeighbors = contractedNeighbors;
	}
	
	public void setEdgeDifference(int edgeDifference) {
		this.edgeDifference = edgeDifference;
	}
	
	public void setNodeLevel(int nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	
	public void updateNodeLevel(int newValue) {
		this.nodeLevel = Math.max(this.nodeLevel, newValue);
	}
	
	public void setShortcutCover(int shortcutCover) {
		this.shortcutCover = shortcutCover;
	}
	
	public void reset() {
		this.contractedNeighbors = 0;
		this.edgeDifference = 0;
		this.nodeLevel = 0;
		this.shortcutCover = 0;
	}
	
	public int getValue() {
		return this.contractedNeighbors + this.edgeDifference + this.nodeLevel + this.shortcutCover;
	}
}
