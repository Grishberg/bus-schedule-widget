package com.grishberg.busschedulewidget.schedule.data;

import com.grishberg.busschedulewidget.schedule.domain.BusSchedule;
import com.grishberg.busschedulewidget.schedule.domain.GeoLocations;
import com.grishberg.busschedulewidget.schedule.domain.LocationResult;
import com.grishberg.busschedulewidget.schedule.domain.LogOutput;

import java.util.List;
import java.util.*;

public class BusScheduleRepository implements BusSchedule {
    private static final String TAG = "BusScheduleRepository";
    private final LogOutput log;
    private final GeoLocations geoLocations;
    private final TimeProvider timeProvider;

    public BusScheduleRepository(LogOutput l,
                                 GeoLocations geoLocations,
                                 TimeProvider timeProvider) {
        log = l;
        this.geoLocations = geoLocations;
        this.timeProvider = timeProvider;
    }

    @Override
    public List<Integer> findNearestBus(LocationResult l) {
        BusScheduleForLocation geoLoc = geoLocations.findNearestLocation(l);
        log.d(TAG, "find nearest bus " + geoLoc);
		if(geoLoc == null){
			log.d(TAG, "geo loc is null for location: " + l);
			return Collections.emptyList();
		}
        List<Integer> timeLeftForNextBus = geoLoc.getTimeLeftForNextBus(timeProvider.getTime());
        for (int minutesLeft : timeLeftForNextBus) {
            log.d(TAG, "times left for next bus " + minutesLeft);
        }
        return timeLeftForNextBus;
    }
}
