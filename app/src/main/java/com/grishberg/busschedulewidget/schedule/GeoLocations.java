package com.grishberg.busschedulewidget.schedule;
import java.util.*;

public class GeoLocations
{
	private final ArrayList<GeoLocation> locations = new ArrayList<>();
	
	public void addGeoLocation(GeoLocation l){
		locations.add(l);
	}
	
	public GeoLocation fromGpsLocation(Location l){
		float minDistance = Integer.MAX_VALUE;
		int nearestLocationIndex = 0;
		for(int i = 0; i < locations.size(); i++){
			float d = locations.get(i).distance(l);
			if( d < minDistance ){
				minDistance = d;
				nearestLocationIndex = i;
			}
		}
		return locations.get(nearestLocationIndex);
	}
}
