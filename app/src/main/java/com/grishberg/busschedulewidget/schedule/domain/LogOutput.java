package com.grishberg.busschedulewidget.schedule.domain;

public interface LogOutput {
    void d(String t, String m);

    void e(String t, String m, Throwable th);
}
