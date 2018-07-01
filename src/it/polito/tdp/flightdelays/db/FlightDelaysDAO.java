package it.polito.tdp.flightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.flightdelays.model.AirlineIdMap;
import it.polito.tdp.flightdelays.model.AirportIdMap;
import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Airport;
import it.polito.tdp.flightdelays.model.Flight;
import it.polito.tdp.flightdelays.model.FlightIdMap;

public class FlightDelaysDAO {

	public List<Airline> loadAllAirlines(AirlineIdMap airlineIdMap) {
		String sql = "SELECT id, IATA_CODE, airline from airlines ORDER BY IATA_CODE ASC";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airline airline = new Airline(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("airline"));
				result.add(airlineIdMap.get(airline));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Airport> loadAllAirports(AirportIdMap airportIdMap) {
		String sql = "SELECT id, iata_code, airport, city, state, country, latitude, longitude FROM airports";
		List<Airport> result = new ArrayList<Airport>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport airport = new Airport(rs.getInt("id"), rs.getString("iata_code"), rs.getString("airport"),
						rs.getString("city"), rs.getString("state"), rs.getString("country"), rs.getDouble("latitude"),
						rs.getDouble("longitude"));
				result.add(airportIdMap.get(airport));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Flight> loadAllFlights(AirlineIdMap airlineIdMap, AirportIdMap airportIdMap, FlightIdMap flightIdMap) {
		String sql = "SELECT id, airline_id, flight_number, origin_airport_id, destination_airport_id, scheduled_departure_date, "
				+ "arrival_date, departure_delay, arrival_delay, distance FROM flights";
		List<Flight> result = new LinkedList<Flight>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Airport sourceAirport = airportIdMap.get(rs.getInt("Origin_airport_ID"));
				Airport destinationAirport = airportIdMap.get(rs.getInt("Destination_airport_ID"));
				Airline airline = airlineIdMap.get(rs.getInt("Airline_ID"));
				
				Flight flight = new Flight(rs.getInt("id"), rs.getInt("airline_id"), rs.getInt("flight_number"),
						rs.getInt("origin_airport_id"), rs.getInt("destination_airport_id"),
						rs.getTimestamp("scheduled_departure_date").toLocalDateTime(),
						rs.getTimestamp("arrival_date").toLocalDateTime(), rs.getInt("departure_delay"),
						rs.getInt("arrival_delay"), rs.getInt("distance"));
				result.add(flightIdMap.get(flight));
				
				sourceAirport.getFlights().add(flightIdMap.get(flight));
				destinationAirport.getFlights().add(flightIdMap.get(flight));
				airline.getFlights().add(flightIdMap.get(flight));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public double getAvg(Flight f) {

		String sql = "SELECT AVG(ARRIVAL_DELAY) as avg " + "FROM flights "
				+ "WHERE airline_id = ? AND ORIGIN_AIRPORT_ID = ? AND DESTINATION_AIRPORT_ID = ? ";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, f.getAirlineId());
			st.setInt(2, f.getOriginAirportId());
			st.setInt(3, f.getDestinationAirportId());

			ResultSet rs = st.executeQuery();

			rs.first();
			double avg = rs.getDouble("avg");

			conn.close();
			return avg;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Flight> loadAllFlightsAirline(Airline a, AirlineIdMap airlineIdMap, AirportIdMap airportIdMap, FlightIdMap flightIdMap) {
		String sql = "SELECT id, airline_id, flight_number, origin_airport_id, destination_airport_id, scheduled_departure_date, "
				+ "arrival_date, departure_delay, arrival_delay, distance FROM flights WHERE airline_id=?";
		List<Flight> result = new LinkedList<Flight>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Airport sourceAirport = airportIdMap.get(rs.getInt("Origin_airport_ID"));
				Airport destinationAirport = airportIdMap.get(rs.getInt("Destination_airport_ID"));
				Airline airline = airlineIdMap.get(rs.getInt("Airline_ID"));
				
				Flight flight = new Flight(rs.getInt("id"), rs.getInt("airline_id"), rs.getInt("flight_number"),
						rs.getInt("origin_airport_id"), rs.getInt("destination_airport_id"),
						rs.getTimestamp("scheduled_departure_date").toLocalDateTime(),
						rs.getTimestamp("arrival_date").toLocalDateTime(), rs.getInt("departure_delay"),
						rs.getInt("arrival_delay"), rs.getInt("distance"));
				result.add(flightIdMap.get(flight));
				
				sourceAirport.getFlights().add(flightIdMap.get(flight));
				destinationAirport.getFlights().add(flightIdMap.get(flight));
				airline.getFlights().add(flightIdMap.get(flight));
			
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public Airport getAirportFromId(int destinationAirportId) {
		String sql = "SELECT DISTINCT id, iata_code, airport, city, state, country, latitude, longitude FROM airports WHERE id = ?";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, destinationAirportId);
			ResultSet rs = st.executeQuery();

			rs.first();
			Airport airport = new Airport(rs.getInt("id"), rs.getString("iata_code"), rs.getString("airport"),
					rs.getString("city"), rs.getString("state"), rs.getString("country"), rs.getDouble("latitude"),
					rs.getDouble("longitude"));

			conn.close();
			return airport;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
