package model.graph_entities;

public class Coordinate {
	private double lat;
	private double lng;
	
	public Coordinate(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	@Override
	public boolean equals(Object obj) {
		Coordinate other = (Coordinate) obj;
		return this.lat == other.lat && this.lng == other.lng;
	}
	
	@Override
	public int hashCode() {
		double hash = 1;
		hash = hash * 17 + lat;
		hash = hash * 31 + lng;
		hash *= 10000;
		return (int)hash;
	}
	
	public double distanceTo(Coordinate other) {
		return Math.sqrt(Math.pow(this.lat - other.getLat(), 2) + Math.pow(this.lng - other.getLng(), 2));
	}
	
	@Override
	public String toString() {
		return lat + "," + lng;
	}
}
