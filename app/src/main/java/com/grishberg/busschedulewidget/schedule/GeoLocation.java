package com.grishberg.busschedulewidget.schedule;

public class GeoLocation
{
	private final float x;
	private final float y;
	private final float r;

	public GeoLocation(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	boolean isIn(Location l) {
		return l.distance(x,y) <= r;
	}
	
	float distance(Location l) {
		return l.distance(x,y);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GeoLocation)){
			return false;
		}
		if(this == obj) {
			return true;
		}
		
		GeoLocation l = (GeoLocation) obj;
		return x==l.x && y==l.y && r==l.r;
	}
	
}
