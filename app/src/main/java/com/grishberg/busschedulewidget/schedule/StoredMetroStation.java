package com.grishberg.busschedulewidget.schedule;
import java.util.*;

/**
* Stored metro station in preferences.
**/
public class StoredMetroStation
{
	enum MetroStation {
		LENINA,
		NOVOCHER
	};
	
	public interface MetroStationReadedAction{
		void onStationReaded(MetroStation s);
	}
	
	private ArrayList<MetroStationReadedAction> actions = new ArrayList<>();
	
	public void addStationReadAction(MetroStationReadedAction a) {
		actions.add(a);
	}
	
	private void notifyStationReaded(MetroStation s) {
		for(int i= 0; i<actions.size();i++) {
			actions.get(i).onStationReaded(s);
		}
	}
}
