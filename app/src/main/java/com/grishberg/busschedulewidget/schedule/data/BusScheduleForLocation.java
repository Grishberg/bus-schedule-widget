package com.grishberg.busschedulewidget.schedule.data;

import com.grishberg.busschedulewidget.schedule.domain.GeoLocation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.grishberg.busschedulewidget.schedule.domain.*;

public class BusScheduleForLocation {
	private static final String TAG = "Bus schedule"; 
	private final LogOutput log;
    final GeoLocation location;
    private final int[] times;

    public BusScheduleForLocation(LogOutput l, GeoLocation location, int[] times) {
		log = l;
        this.location = location;
        this.times = times;
    }

    public List<Integer> getTimeLeftForNextBus(Calendar time) {
        int timeAsInt = time.get(Calendar.HOUR_OF_DAY) * 100 + time.get(Calendar.MINUTE);

        ArrayList<Integer> durations = new ArrayList<>();

        for (int value : times) {
            if (value > timeAsInt) {
                durations.add(calculateDiffInMinutes(time, value));
                if (durations.size() == 3) {
                    break;
                }
            }
        }
        return durations;
    }

    private int calculateDiffInMinutes(Calendar currentTime, int time) {
		int hh0 = currentTime.get(Calendar.HOUR_OF_DAY);
		int mm0 = currentTime.get(Calendar.MINUTE);

        int hh1 = time / 100;
        int mm1 = time % 100;
		log.d(TAG,"Next schedule: "+hh1+":"+mm1);
        
        return (hh1 - hh0) * 60 + mm1 - mm0;
    }

    @Override
    public String toString() {
        return "BusScheduleForLocation( location = " + location + ")";
    }
}
