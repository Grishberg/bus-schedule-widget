package com.grishberg.busschedulewidget.schedule.domain;
import com.grishberg.busschedulewidget.schedule.*;

public class Interactor {
	private final BusSchedule busSchedule;
	private final GpsLocation gpsLocation;
	private final OutputBoundary outputBoundary;
	private final GpsLocationReceived locationReceivedAction = new GpsLocationReceived();

	public Interactor(BusSchedule geoLocations, GpsLocation gpsLocation, OutputBoundary outputBoundary) {
		this.busSchedule = geoLocations;
		this.gpsLocation = gpsLocation;
		this.outputBoundary = outputBoundary;
	}
	
	public void requestNextTimeToBuss() {
		gpsLocation.requestLocation(locationReceivedAction);
	}
	
	private void onBusScheduleReceived(int[] durationsInMinutes) {
		
	}
	
	private class GpsLocationReceived implements GpsLocation.Action {
		@Override
		public void onGpsLocationReceived(Location l) {
			onBusScheduleReceived(busSchedule.findNearestLocation(l));
		}
	}
}
