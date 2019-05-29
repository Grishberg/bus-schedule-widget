package com.grishberg.busschedulewidget.schedule;

public class Location
{
	private final float x;
	private final float y;

	public Location(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float distance(float x, float y){
		return (float) Math.sqrt(x*x - y*y);
	}
}
