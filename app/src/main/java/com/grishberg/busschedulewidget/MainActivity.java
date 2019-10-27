package com.grishberg.busschedulewidget;

import android.*;
import android.app.*;
import android.content.pm.*;
import android.os.*;
import android.widget.*;
import com.grishberg.busschedulewidget.*;
import com.grishberg.busschedulewidget.schedule.data.*;
import com.grishberg.busschedulewidget.schedule.domain.*;
import java.util.*;

import com.grishberg.busschedulewidget.R;
import com.github.grishberg.consoleview.*;

public class MainActivity extends Activity implements OutputBoundary {

	private static final String[] INITIAL_PERMS={
		Manifest.permission.ACCESS_FINE_LOCATION
	};
	private static final String[] LOCATION_PERMS={
		Manifest.permission.ACCESS_FINE_LOCATION
	};
	private static final int INITIAL_REQUEST=1337;
	private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;
	
	Interactor interactor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		ConsoleLogger log = new ConsoleLogger( new LoggerImpl());
	
		TimeProvider t = new TimeProvider();
		ArrayList<GeoLocation> points = new ArrayList<>();
		// benua
		points.add(new GeoLocationContainer(59.9590719, 30.4055854));
		// pl lenina
		points.add(new GeoLocationContainer(59.9540323, 30.3562962));
	
		GeoLocations geoLocations = new GeoLocationsRepository(log, points);
		BusSchedule shceduler = new BusScheduleRepository(log, geoLocations, t);
		GpsLocation gpsLocation = new GpsLocationRepository(log, this.getApplicationContext());
		interactor = new Interactor(log, shceduler, gpsLocation, this);
		
		requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
    }
	
	@Override
	public void updateNextTime(int[] minutesBeforeArrived)
	{
		// TODO: Implement this method
		Toast.makeText(this, "received next time",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if(requestCode == LOCATION_REQUEST) {
			if (canAccessLocation()) {
				doLocationThing();
			}
		}
	}
	
	private boolean canAccessLocation() {
		return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
	}
	
	private boolean hasPermission(String perm) {
		return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
	}
	
	private void doLocationThing(){
		interactor.requestNextTimeToBuss();
	}
}
