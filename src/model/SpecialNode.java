package model;

import main.Importance;

public class SpecialNode extends Node implements Comparable<SpecialNode>{
	
	private boolean visitedForward = false;
	private boolean visitedBackward = false;
	private boolean contracted = false;
	private Importance importance = new Importance();

	public SpecialNode(Long id) {
		super(id);
	}
	
	public boolean isContracted() {
		return contracted;
	}
	
	public boolean isVisitedBackward() {
		return visitedBackward;
	}
	
	public boolean isVisitedForward() {
		return visitedForward;
	}
	
	public Importance getImportance() {
		return importance;
	}
	
	public void resetImportance() {
		importance.reset();
	}
	
	public void setContracted(boolean contracted) {
		this.contracted = contracted;
	}
	
	public void setVisitedForward(boolean visitedForward) {
		this.visitedForward = visitedForward;
	}
	
	public void setVisitedBackward(boolean visitedBackward) {
		this.visitedBackward = visitedBackward;
	}
	
	public void reset() {
		this.contracted = false;
		this.visitedBackward = false;
		this.visitedForward = false;
	}

	@Override
	public int compareTo(SpecialNode o) {
		return Integer.compare(this.importance.getValue(), o.getImportance().getValue());
	}

}
