package com.grishberg.busschedulewidget.schedule.domain;

import java.util.List;

public interface OutputBoundary {
    void updateNextTime(List<Integer> minutesBeforeArrived);
}
