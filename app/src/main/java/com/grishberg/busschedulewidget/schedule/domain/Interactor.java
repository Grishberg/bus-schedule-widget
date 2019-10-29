package com.grishberg.busschedulewidget.schedule.domain;

import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Interactor {
    private static final String TAG = Interactor.class.getSimpleName();
    private final LogOutput log;
    private final BusSchedule busSchedule;
    private final GpsLocation gpsLocation;
    private final OutputBoundary outputBoundary;
    private final GpsLocationReceived locationReceivedAction = new GpsLocationReceived();
    @Nullable
    private CountDownLatch countDown;
    private final ArrayList<Integer> timesBeforeBus = new ArrayList<>();

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
            gpsLocation.requestLocation(locationReceivedAction);
            countDown.await(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.e(TAG, "interrupted");
        }
        return timesBeforeBus;
    }

    private void onBusScheduleReceived(List<Integer> durationsInMinutes) {
        log.d(TAG, "on schedule received " + durationsInMinutes);
        outputBoundary.updateNextTime(durationsInMinutes);
        if (countDown != null) {
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
