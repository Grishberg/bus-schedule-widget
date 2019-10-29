package com.grishberg.busschedulewidget.schedule.domain;

import java.util.List;
import android.support.annotation.*;
import java.util.concurrent.*;
import android.util.*;
import java.util.*;

public class Interactor {
    private static final String TAG = "inter";
    private final LogOutput log;
    private final BusSchedule busSchedule;
    private final GpsLocation gpsLocation;
    private final OutputBoundary outputBoundary;
    private final GpsLocationReceived locationReceivedAction = new GpsLocationReceived();
	@Nullable
	private CountDownLatch countDown;
	private final ArrayList<Integer> timesBeforeBus = new ArrayList();

    public Interactor(LogOutput l,
                      BusSchedule geoLocations, 
					  GpsLocation gpsLocation, 
					  OutputBoundary outputBoundary) {
        log = l;
        this.busSchedule = geoLocations;
        this.gpsLocation = gpsLocation;
        this.outputBoundary = outputBoundary;
    }

    public void requestNextTimeToBuss() {
        log.d(TAG, "request");
        gpsLocation.requestLocation(locationReceivedAction);
    }
	
	@WorkerThread
	public List<Integer> getNextTimeToBus() {
		try {
			countDown = new CountDownLatch(1);
			countDown.await(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Log.d(TAG, "interrupted");
		}
		return timesBeforeBus;
	}

    private void onBusScheduleReceived(List<Integer> durationsInMinutes) {
        log.d(TAG, "on schedule received " + durationsInMinutes);
        outputBoundary.updateNextTime(durationsInMinutes);
		if(countDown != null) {
			timesBeforeBus.clear();
			timesBeforeBus.addAll(durationsInMinutes);
			countDown.countDown();
		}
    }

    private class GpsLocationReceived implements GpsLocation.Action {
        @Override
        public void onGpsLocationReceived(LocationResult l) {
            onBusScheduleReceived(busSchedule.findNearestBus(l));
        }
    }
}
