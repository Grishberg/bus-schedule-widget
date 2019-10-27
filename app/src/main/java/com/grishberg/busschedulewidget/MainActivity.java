package com.grishberg.busschedulewidget;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.github.grishberg.consoleview.LoggerImpl;
import com.grishberg.busschedulewidget.schedule.data.BusScheduleRepository;
import com.grishberg.busschedulewidget.schedule.data.BusScheduleStorage;
import com.grishberg.busschedulewidget.schedule.data.GeoLocationsRepository;
import com.grishberg.busschedulewidget.schedule.data.GpsLocationRepository;
import com.grishberg.busschedulewidget.schedule.data.TimeProvider;
import com.grishberg.busschedulewidget.schedule.domain.BusSchedule;
import com.grishberg.busschedulewidget.schedule.domain.GeoLocations;
import com.grishberg.busschedulewidget.schedule.domain.GpsLocation;
import com.grishberg.busschedulewidget.schedule.domain.Interactor;
import com.grishberg.busschedulewidget.schedule.domain.OutputBoundary;

import java.util.List;

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

        BusScheduleStorage scheduleStorage = new BusScheduleStorage();
        GeoLocations geoLocations = new GeoLocationsRepository(log, scheduleStorage.requestSchedule());
        BusSchedule scheduler = new BusScheduleRepository(log, geoLocations, t);
        GpsLocation gpsLocation = new GpsLocationRepository(log, this.getApplicationContext());
        interactor = new Interactor(log, scheduler, gpsLocation, this);

        ActivityCompat.requestPermissions(this, LOCATION_PERMS, LOCATION_REQUEST);
    }

    @Override
    public void updateNextTime(List<Integer> minutesBeforeArrived) {
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
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));
    }

    private void doLocationThing() {
        interactor.requestNextTimeToBuss();
    }
}
