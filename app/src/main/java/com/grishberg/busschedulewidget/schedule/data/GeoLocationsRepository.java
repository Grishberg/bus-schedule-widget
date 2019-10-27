package com.grishberg.busschedulewidget.schedule.data;
import com.grishberg.busschedulewidget.schedule.domain.*;
import java.util.*;

public class GeoLocationsRepository implements GeoLocations
{
	private static final String TAG = "GeoLocationRepository";
	private final LogOutput log;
	private final List<GeoLocation> geoLocations;

	public GeoLocationsRepository(LogOutput l, List<GeoLocation> geoLocations)
	{
		log = l;
		this.geoLocations = geoLocations;
	}
	
	
	@Override
	public GeoLocation findNearestLocation(LocationResult l)
	{
		double minDistance= Integer.MAX_VALUE;
		GeoLocation nearestLocation = null;
		for(GeoLocation geoLocation : geoLocations){
			double d = geoLocation.distance(l);
			if(d < minDistance){
				minDistance = d;
				nearestLocation = geoLocation;
				log.d(TAG, "find nearest location, geo = " + geoLocation+ " dist = " + nearestLocation);
			}
		}
		return nearestLocation;
	}
}
