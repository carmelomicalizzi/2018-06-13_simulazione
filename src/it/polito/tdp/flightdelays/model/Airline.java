package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.List;

public class Airline {

	private int id;
	private String iataCode;
	private String name;
	private List<Flight> flights;

	public Airline(int id, String iataCode, String name) {
		this.id = id;
		this.iataCode = iataCode;
		this.name = name;
		this.flights = new ArrayList<Flight>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Flight> getFlights() {
		return flights;
	}

	@Override
	public String toString() {

		return name;

	}
}
