package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirlineIdMap {
	
	private Map<Integer, Airline> map;
	
	public AirlineIdMap() {
		map = new HashMap<>();
	}
	
	public Airline get(int airlineId) {
		return map.get(airlineId);
	}
	
	public Airline get(Airline airline) {
		Airline old = map.get(airline.getId());
		if (old == null) {
			map.put(airline.getId(), airline);
			return airline;
		}
		return old;
	}
	
	public void put(Airline airline, int airlineId) {
		map.put(airlineId, airline);
	}
	
	public List<Airline> getAirlines() {
		List<Airline> airlines = new ArrayList<Airline>(map.values());
		return airlines;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AirlineIdMap [map=");
		builder.append(map.values());
		builder.append("]");
		return builder.toString();
	}
	
	
}
