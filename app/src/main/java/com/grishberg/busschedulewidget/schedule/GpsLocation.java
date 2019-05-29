package com.grishberg.busschedulewidget.schedule;

/**
* 
**/
public interface GpsLocation
{
	void requestLocation();
	void addLocationFoundAction(LocationFoundAction action);
	void removeLocationFoundAction(LocationFoundAction action);
}
