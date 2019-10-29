package com.grishberg.busschedulewidget.schedule.data;

import com.grishberg.busschedulewidget.schedule.domain.GeoLocation;
import com.grishberg.busschedulewidget.schedule.domain.LocationResult;
import com.grishberg.busschedulewidget.schedule.domain.*;

public class GeoLocationContainer implements GeoLocation {
	private static final String TAG = "GLC";
	private final LogOutput log;
    public Locations location;
    private final double x, y;

    public GeoLocationContainer(LogOutput l, 
							Locations location, double x, double y) {
		log = l;
        this.location = location;
        this.x = x;
        this.y = y;
    }

    @Override
    public double distance(LocationResult l) {
        double d = Math.hypot(l.x() - x, l.y() - y);
		log.d(TAG, "distance = "+d);
		return d;
    }

    @Override
    public String toString() {
        return "GeoLocationContainer(location=" + location + ", x=" + x + ", y=" + y + ")";
    }
}
