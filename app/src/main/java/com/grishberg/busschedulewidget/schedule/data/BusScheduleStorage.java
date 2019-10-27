package com.grishberg.busschedulewidget.schedule.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Has bus schedule for location.
 */
public class BusScheduleStorage {

    private static final int[] BENUA_TIMES = {920, 945, 1005, 1025, 1045, 1105,
            1125, 1145, 1205, 1225, 1245, 1305, 1715, 1735, 1755, 1815, 1835, 1855,
            1915, 1935, 1955, 2015, 2035, 2055};
    private static final int[] LENINA_SQUARE_TIMES = {850, 915, 935, 1015, 1035, 1055,
            1115, 1135, 1155, 1215, 1235, 1255, 1745, 1735, 1805, 1825, 1845,
            1905, 1925, 1945, 2005, 2025, 2045};

    private final HashMap<Locations, int[]> times = new HashMap<>();
    private final ArrayList<ScheduleReceivedAction> actions = new ArrayList<>();
    private final List<BusScheduleForLocation> locations = new ArrayList<>();

    public BusScheduleStorage() {
        locations.add(new BusScheduleForLocation(
                new GeoLocationContainer(Locations.BENUA, 59.9590719, 30.4055854),
                BENUA_TIMES));
        locations.add(new BusScheduleForLocation(
                new GeoLocationContainer(Locations.LENINA_SQUARE, 59.9540323, 30.3562962),
                LENINA_SQUARE_TIMES));
    }

    public List<BusScheduleForLocation> requestSchedule() {
        return locations;
    }

    public interface ScheduleReceivedAction {
        void onReceived(Locations location, int[] times);
    }
}
