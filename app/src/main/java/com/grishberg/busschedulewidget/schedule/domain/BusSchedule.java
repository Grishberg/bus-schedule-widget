package com.grishberg.busschedulewidget.schedule.domain;

import java.util.List;

public interface BusSchedule {
    List<Integer> findNearestBus(LocationResult l);
}
