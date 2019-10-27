package com.grishberg.busschedulewidget.schedule.data;

import com.grishberg.busschedulewidget.schedule.domain.GeoLocations;
import com.grishberg.busschedulewidget.schedule.domain.LocationResult;
import com.grishberg.busschedulewidget.schedule.domain.LogOutput;

import java.util.List;

public class GeoLocationsRepository implements GeoLocations {
    private static final String TAG = "GeoLocationRepository";
    private final LogOutput log;
    private final List<BusScheduleForLocation> geoLocations;

    public GeoLocationsRepository(LogOutput l, List<BusScheduleForLocation> geoLocations) {
        log = l;
        this.geoLocations = geoLocations;
    }

    @Override
    public BusScheduleForLocation findNearestLocation(LocationResult l) {
        double minDistance = Integer.MAX_VALUE;
        BusScheduleForLocation nearestLocation = null;
        for (BusScheduleForLocation geoLocation : geoLocations) {
            double d = geoLocation.location.distance(l);
            if (d < minDistance) {
                minDistance = d;
                nearestLocation = geoLocation;
                log.d(TAG, "find nearest location, geo = " + geoLocation + " dist = " + nearestLocation);
            }
        }
        return nearestLocation;
    }
}
