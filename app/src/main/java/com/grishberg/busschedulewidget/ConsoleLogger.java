package com.grishberg.busschedulewidget;
import com.github.grishberg.consoleview.*;
import com.grishberg.busschedulewidget.schedule.domain.*;
import android.util.*;

public class ConsoleLogger implements LogOutput
{
	private final Logger log;

	public ConsoleLogger(Logger log) {
		this.log = log;
	}

	@Override
	public void d(String t, String m) {
		log.d(t,m);
	}

	@Override
	public void e(String t, String m, Throwable th) {
		// TODO: Implement this method
		log.d(t,"error: "+m);
		Log.e(t,m,th);
	}
}
