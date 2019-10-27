package com.grishberg.busschedulewidget.schedule.data;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.grishberg.busschedulewidget.schedule.domain.GpsLocation;
import com.grishberg.busschedulewidget.schedule.domain.LogOutput;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GpsLocationRepository implements GpsLocation {
    private static final String TAG = GpsLocationRepository.class.getSimpleName();
    private final LogOutput log;
    private final LocationManager locationManager;
    private final Context context;

    public GpsLocationRepository(LogOutput l, Context c) {
        log = l;
        context = c;
        locationManager = (LocationManager)
                c.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void requestLocation(GpsLocation.Action action) {
        LocationListener locationListener = new MyLocationListener(log, context, action);
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,
                locationListener, Looper.getMainLooper());
    }

    private static class MyLocationListener implements LocationListener {
        private final LogOutput log;
        private final GpsLocation.Action action;
        private final Context context;

        public MyLocationListener(LogOutput l, Context c, GpsLocation.Action action) {
            log = l;
            context = c;
            this.action = action;
        }

        @Override
        public void onLocationChanged(android.location.Location loc) {
            String longitude = "Longitude: " + loc.getLongitude();
            log.d(TAG, longitude);
            String latitude = "Latitude: " + loc.getLatitude();
            Log.v(TAG, latitude);

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                log.e(TAG, "geo error", e);
            }
            String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                    + cityName;
            log.d(TAG, "on location found:" + s);

            action.onGpsLocationReceived(new GpsLocationResult(loc.getLatitude(),
                    loc.getLongitude()));
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
}
