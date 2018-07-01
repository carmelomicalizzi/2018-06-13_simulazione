package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportIdMap {
	
	private Map<Integer, Airport> map;
	
	public AirportIdMap() {
		map = new HashMap<>();
	}
	
	public Airport get(int airportId) {
		return map.get(airportId);
	}
	
	public Airport get(Airport airport) {
		Airport old = map.get(airport.getId());
		if (old == null) {
			map.put(airport.getId(), airport);
			return airport;
		}
		return old;
	}
	
	public void put(Airport airport, int airportId) {
		map.put(airportId, airport);
	}
	
	public List<Airport> getAirports() {
		List<Airport> airports = new ArrayList<Airport>(map.values());
		return airports;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AirportIdMap [map=");
		builder.append(map.values());
		builder.append("]");
		return builder.toString();
	}
	
	
}
