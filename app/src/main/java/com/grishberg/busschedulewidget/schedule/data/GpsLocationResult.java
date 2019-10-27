package com.grishberg.busschedulewidget.schedule.data;
import com.grishberg.busschedulewidget.schedule.domain.*;

public class GpsLocationResult implements LocationResult
{
	private final double x;
	private final double y;

	public GpsLocationResult(double x, double y) {
		this.x = x;
		this.y = y;
	}
	

	@Override
	public double x() {
		return x;
	}

	@Override
	public double y() {
		return y;
	}

	@Override
	public String toString() {
		return "GpsLocationResult(x="+x+", y="+y+")";
	}
}
