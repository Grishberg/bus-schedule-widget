package com.grishberg.busschedulewidget.schedule.data;

import com.grishberg.busschedulewidget.schedule.domain.GeoLocation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BusScheduleForLocation {
    final GeoLocation location;
    private final int[] times;

    public BusScheduleForLocation(GeoLocation location, int[] times) {
        this.location = location;
        this.times = times;
    }

    public List<Integer> getTimeLeftForNextBus(Calendar time) {
        int timeAsInt = time.get(Calendar.HOUR_OF_DAY) * 100 + time.get(Calendar.MINUTE);

        ArrayList<Integer> durations = new ArrayList<>();

        for (int value : times) {
            if (value > timeAsInt) {
                durations.add(calculateDiffInMinutes(timeAsInt, value));
                if (durations.size() == 3) {
                    break;
                }
            }
        }
        return durations;
    }

    private int calculateDiffInMinutes(int timeAsInt, int time) {
        int hh0 = timeAsInt / 100;
        int mm0 = timeAsInt % 100;

        int hh1 = time / 100;
        int mm1 = time & 100;
        return (hh1 - hh0) * 60 + mm1 - mm0;
    }

    @Override
    public String toString() {
        return "BusScheduleForLocation( location = " + location + ")";
    }
}
