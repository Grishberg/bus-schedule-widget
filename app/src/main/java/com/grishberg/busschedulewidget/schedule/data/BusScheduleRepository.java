package com.grishberg.busschedulewidget.schedule.data;

import com.grishberg.busschedulewidget.schedule.domain.BusSchedule;
import com.grishberg.busschedulewidget.schedule.domain.GeoLocation;
import com.grishberg.busschedulewidget.schedule.domain.GeoLocations;
import com.grishberg.busschedulewidget.schedule.domain.LocationResult;
import com.grishberg.busschedulewidget.schedule.domain.LogOutput;

public class BusScheduleRepository implements BusSchedule {
    private static final String TAG = "BusScheduleRepository";
    private final LogOutput log;
    private final GeoLocations geoLocations;
    private final TimeProvider timeProvider;

    public BusScheduleRepository(LogOutput l, GeoLocations geoLocations, TimeProvider timeProvider) {
        log = l;
        this.geoLocations = geoLocations;
        this.timeProvider = timeProvider;
    }

    @Override
    public int[] findNearestBus(LocationResult l) {
        GeoLocation geoLoc = geoLocations.findNearestLocation(l);
        log.d(TAG, "find nearest bus " + geoLoc);
        return null;
    }
}
