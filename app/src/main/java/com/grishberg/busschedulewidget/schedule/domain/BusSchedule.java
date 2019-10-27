package com.grishberg.busschedulewidget.schedule.domain;

public interface BusSchedule {
    public int[] findNearestBus(LocationResult l);
}
