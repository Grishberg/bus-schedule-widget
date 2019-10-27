package com.grishberg.busschedulewidget.schedule.domain;

public class Interactor {
    private static final String TAG = "inter";
    private final LogOutput log;
    private final BusSchedule busSchedule;
    private final GpsLocation gpsLocation;
    private final OutputBoundary outputBoundary;
    private final GpsLocationReceived locationReceivedAction = new GpsLocationReceived();

    public Interactor(LogOutput l,
                      BusSchedule geoLocations, GpsLocation gpsLocation, OutputBoundary outputBoundary) {
        log = l;
        this.busSchedule = geoLocations;
        this.gpsLocation = gpsLocation;
        this.outputBoundary = outputBoundary;
    }

    public void requestNextTimeToBuss() {
        log.d(TAG, "request");
        gpsLocation.requestLocation(locationReceivedAction);
    }

    private void onBusScheduleReceived(int[] durationsInMinutes) {
        log.d(TAG, "on schedule received " + durationsInMinutes);
        outputBoundary.updateNextTime(durationsInMinutes);
    }

    private class GpsLocationReceived implements GpsLocation.Action {
        @Override
        public void onGpsLocationReceived(LocationResult l) {
            onBusScheduleReceived(busSchedule.findNearestBus(l));
        }
    }
}
