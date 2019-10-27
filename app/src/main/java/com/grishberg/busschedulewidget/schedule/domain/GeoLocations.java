package com.grishberg.busschedulewidget.schedule.domain;

public interface GeoLocations
{
	GeoLocation findNearestLocation(LocationResult l);
}
