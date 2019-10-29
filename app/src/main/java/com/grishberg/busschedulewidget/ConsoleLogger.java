package com.grishberg.busschedulewidget;

import android.util.Log;

import com.github.grishberg.consoleview.Logger;
import com.grishberg.busschedulewidget.schedule.domain.LogOutput;

public class ConsoleLogger implements LogOutput {
    private final Logger log;

    public ConsoleLogger(Logger log) {
        this.log = log;
    }

    @Override
    public void d(String t, String m) {
        log.d(t, m);
		Log.d(t, m);
    }

    @Override
    public void e(String t, String m, Throwable th) {
        // TODO: Implement this method
        log.d(t, "error: " + m);
        Log.e(t, m, th);
    }
}
