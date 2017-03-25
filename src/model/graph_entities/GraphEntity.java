package model.graph_entities;

public abstract class GraphEntity {
	private long graphIndex;
	
	public long getGraphIndex() {
		return graphIndex;
	}
	
	public void setGraphIndex(long l) {
		this.graphIndex = l;
	}
}
