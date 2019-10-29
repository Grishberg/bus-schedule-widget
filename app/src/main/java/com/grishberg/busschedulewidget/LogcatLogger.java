package com.grishberg.busschedulewidget;
import com.grishberg.busschedulewidget.schedule.domain.*;
import android.util.*;

public class LogcatLogger implements LogOutput {
	@Override
	public void d(String t, String m) {
		Log.d(t, m);
	}

	@Override
	public void e(String t, String m, Throwable th) {
		Log.e(t, m, th);
	}
}
