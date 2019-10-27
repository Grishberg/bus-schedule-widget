package com.grishberg.busschedulewidget;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.github.grishberg.consoleview.LoggerImpl;
import com.grishberg.busschedulewidget.schedule.data.BusScheduleRepository;
import com.grishberg.busschedulewidget.schedule.data.GeoLocationContainer;
import com.grishberg.busschedulewidget.schedule.data.GeoLocationsRepository;
import com.grishberg.busschedulewidget.schedule.data.GpsLocationRepository;
import com.grishberg.busschedulewidget.schedule.data.TimeProvider;
import com.grishberg.busschedulewidget.schedule.domain.BusSchedule;
import com.grishberg.busschedulewidget.schedule.domain.GeoLocation;
import com.grishberg.busschedulewidget.schedule.domain.GeoLocations;
import com.grishberg.busschedulewidget.schedule.domain.GpsLocation;
import com.grishberg.busschedulewidget.schedule.domain.Interactor;
import com.grishberg.busschedulewidget.schedule.domain.OutputBoundary;

import java.util.ArrayList;

public class MainActivity extends Activity implements OutputBoundary {

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;

    Interactor interactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConsoleLogger log = new ConsoleLogger(new LoggerImpl());

        TimeProvider t = new TimeProvider();
        ArrayList<GeoLocation> points = new ArrayList<>();
        // benua
        points.add(new GeoLocationContainer(59.9590719, 30.4055854));
        // pl lenina
        points.add(new GeoLocationContainer(59.9540323, 30.3562962));

        GeoLocations geoLocations = new GeoLocationsRepository(log, points);
        BusSchedule scheduler = new BusScheduleRepository(log, geoLocations, t);
        GpsLocation gpsLocation = new GpsLocationRepository(log, this.getApplicationContext());
        interactor = new Interactor(log, scheduler, gpsLocation, this);

        ActivityCompat.requestPermissions(this, LOCATION_PERMS, LOCATION_REQUEST);
    }

    @Override
    public void updateNextTime(int[] minutesBeforeArrived) {
        Toast.makeText(this, "received next time", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_REQUEST) {
            if (canAccessLocation()) {
                doLocationThing();
            }
        }
    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
    }

    private void doLocationThing() {
        interactor.requestNextTimeToBuss();
    }
}
