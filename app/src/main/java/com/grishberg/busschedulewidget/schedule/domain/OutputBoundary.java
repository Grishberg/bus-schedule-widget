package com.grishberg.busschedulewidget.schedule.domain;

public interface OutputBoundary {
    void updateNextTime(int[] minutesBeforeArrived);
}
