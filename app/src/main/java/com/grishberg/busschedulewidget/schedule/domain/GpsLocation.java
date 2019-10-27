package com.grishberg.busschedulewidget.schedule.domain;

public interface GpsLocation
{
	public interface Action {
		void onGpsLocationReceived(LocationResult l);
	}
	
	void requestLocation(Action action);
}
