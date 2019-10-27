package com.grishberg.busschedulewidget.schedule.data;

import com.grishberg.busschedulewidget.schedule.domain.GeoLocation;
import com.grishberg.busschedulewidget.schedule.domain.LocationResult;

public class GeoLocationContainer implements GeoLocation {
    private final double x, y;

    public GeoLocationContainer(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double distance(LocationResult l) {
        return Math.sqrt(
                (l.x() - x) * (l.x() - x) -
                        (l.y() - y) * (l.y() - y));
    }

    @Override
    public String toString() {
        return "GeoLocationContainer(x=" + x + ", y=" + y + ")";
    }
}
