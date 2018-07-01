package it.polito.tdp.flightdelays.model;

import java.time.LocalDateTime;

public class Flight {

	private int id;
	private int airlineId;
	private int flightNumber;
	private int originAirportId;
	private int destinationAirportId;
	private LocalDateTime scheduledDepartureDate;
	private LocalDateTime arrivalDate;
	private int departureDelay;
	private int arrivalDelay;
	private int distance;

	public Flight(int id, int airlineId, int flightNumber, int originAirportId, int destinationAirportId,
			LocalDateTime scheduledDepartureDate, LocalDateTime arrivalDate, int departureDelay, int arrivalDelay,
			int distance) {
		this.id = id;
		this.airlineId = airlineId;
		this.flightNumber = flightNumber;
		this.originAirportId = originAirportId;
		this.destinationAirportId = destinationAirportId;
		this.scheduledDepartureDate = scheduledDepartureDate;
		this.arrivalDate = arrivalDate;
		this.departureDelay = departureDelay;
		this.arrivalDelay = arrivalDelay;
		this.distance = distance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(int airlineId) {
		this.airlineId = airlineId;
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getOriginAirportId() {
		return originAirportId;
	}

	public void setOriginAirportId(int originAirportId) {
		this.originAirportId = originAirportId;
	}

	public int getDestinationAirportId() {
		return destinationAirportId;
	}

	public void setDestinationAirportId(int destinationAirportId) {
		this.destinationAirportId = destinationAirportId;
	}

	public LocalDateTime getScheduledDepartureDate() {
		return scheduledDepartureDate;
	}

	public void setScheduledDepartureDate(LocalDateTime scheduledDepartureDate) {
		this.scheduledDepartureDate = scheduledDepartureDate;
	}

	public LocalDateTime getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDateTime arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public int getDepartureDelay() {
		return departureDelay;
	}

	public void setDepartureDelay(int departureDelay) {
		this.departureDelay = departureDelay;
	}

	public int getArrivalDelay() {
		return arrivalDelay;
	}

	public void setArrivalDelay(int arrivalDelay) {
		this.arrivalDelay = arrivalDelay;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Flight [id=");
		builder.append(id);
		builder.append(", airlineId=");
		builder.append(airlineId);
		builder.append(", flightNumber=");
		builder.append(flightNumber);
		builder.append(", originAirportId=");
		builder.append(originAirportId);
		builder.append(", destinationAirportId=");
		builder.append(destinationAirportId);
		builder.append(", scheduledDepartureDate=");
		builder.append(scheduledDepartureDate);
		builder.append(", arrivalDate=");
		builder.append(arrivalDate);
		builder.append(", departureDelay=");
		builder.append(departureDelay);
		builder.append(", arrivalDelay=");
		builder.append(arrivalDelay);
		builder.append(", distance=");
		builder.append(distance);
		builder.append("]");
		return builder.toString();
	}
}
