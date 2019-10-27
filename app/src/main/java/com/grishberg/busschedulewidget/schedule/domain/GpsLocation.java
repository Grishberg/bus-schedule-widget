package com.grishberg.busschedulewidget.schedule.domain;

public interface GpsLocation {
    interface Action {
        void onGpsLocationReceived(LocationResult l);
    }

    void requestLocation(Action action);
}
