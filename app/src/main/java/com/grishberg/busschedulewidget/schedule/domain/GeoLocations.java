package com.grishberg.busschedulewidget.schedule.domain;

import com.grishberg.busschedulewidget.schedule.data.BusScheduleForLocation;

public interface GeoLocations {
    BusScheduleForLocation findNearestLocation(LocationResult l);
}
