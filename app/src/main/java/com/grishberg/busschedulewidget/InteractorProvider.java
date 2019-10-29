package com.grishberg.busschedulewidget;
import com.grishberg.busschedulewidget.schedule.domain.*;
import com.grishberg.busschedulewidget.schedule.data.*;
import android.content.*;
import java.util.*;

public class InteractorProvider {
	private final Context appContext;
	private Interactor interactor;

	public InteractorProvider(Context appContext) {
		this.appContext = appContext;
	}
	
	public synchronized Interactor getInteractor(){
		if ( interactor ==  null){
			interactor = createInteractor();
		}
		return interactor;
	}
	
	private Interactor createInteractor() {
		LogcatLogger log = new LogcatLogger();

        TimeProvider t = new TimeProvider();

        BusScheduleStorage scheduleStorage = new BusScheduleStorage(log);
        GeoLocations geoLocations = new GeoLocationsRepository(log, scheduleStorage.requestSchedule());
        BusSchedule scheduler = new BusScheduleRepository(log, geoLocations, t);
        GpsLocation gpsLocation = new GpsLocationRepository(log, appContext);
        return new Interactor(log, scheduler, gpsLocation, new OutputBoundaryImpl());
	}
	
	private static class OutputBoundaryImpl implements OutputBoundary {

		@Override
		public void updateNextTime(List<Integer> minutesBeforeArrived) {
			// TODO: Implement this method
		}
	}
}
