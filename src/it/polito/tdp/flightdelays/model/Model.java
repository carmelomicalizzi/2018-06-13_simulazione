package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;

public class Model {

	FlightDelaysDAO dao;

	private List<Airline> airlines;
	private List<Airport> airports;
	private List<Rotta> rotte;
	private List<Flight> flightsAirline;
	private List<Flight> flights;
	
	private AirlineIdMap airlineIdMap;
	private AirportIdMap airportIdMap;
	private FlightIdMap flightIdMap;

	
	private Graph<Airport, DefaultWeightedEdge> graph;
	
	public Model() {
		
		dao = new FlightDelaysDAO();

		
		airlineIdMap = new AirlineIdMap();
		airportIdMap = new AirportIdMap();
		flightIdMap = new FlightIdMap();
		
		airlines = dao.loadAllAirlines(airlineIdMap);
		// System.out.println(airlines.size());

		airports = dao.loadAllAirports(airportIdMap);
		// System.out.println(airports.size());

		flights = dao.loadAllFlights(airlineIdMap, airportIdMap, flightIdMap);
		// System.out.println(flights.size());

	}

	public List<Airline> getAllAirlines() {
		return airlineIdMap.getAirlines();
	}

	public List<Airport> getAllAirport() {
		return airportIdMap.getAirports();
	}
	
	public Airport getAirportFromId(int id) {
		return airportIdMap.get(id);
	}

	public List<Flight> getAllFlightsAirline(Airline a) {
		dao = new FlightDelaysDAO();
		return dao.loadAllFlightsAirline(a, airlineIdMap, airportIdMap, flightIdMap);
	}

	public void creaGrafo(Airline a) {

		
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		airports = new ArrayList<Airport>(this.getAllAirport());
		flightsAirline = new ArrayList<Flight>(this.getAllFlightsAirline(a));
		rotte = new ArrayList<Rotta>();

		Graphs.addAllVertices(graph, airports);
		// System.out.println(graph.vertexSet().size());
		// System.out.println(graph.vertexSet());

		for (Flight f : flightsAirline) {
			// System.out.println(f);

			if(airports.contains(new Airport(f.getOriginAirportId())) && airports.contains(new Airport(f.getDestinationAirportId()))) {
			Airport a1 = airportIdMap.get(f.getOriginAirportId());
			System.out.println(a1);
			Airport a2 = airportIdMap.get(f.getDestinationAirportId());
			System.out.println(a2);

			if (!a1.equals(a2) && a1.getLatitude() != 0.0 && a2.getLongitude() != 0.0) {
				double media = dao.getAvg(f);
				double distanza = LatLngTool.distance(new LatLng(a1.getLatitude(), a1.getLongitude()),
						new LatLng(a2.getLatitude(), a2.getLongitude()), LengthUnit.KILOMETER);
				double weight = media / distanza;
				Rotta r = new Rotta(a1.getId(), a1.getAirportIataCode(), a2.getId(), a2.getAirportIataCode(), weight);
				if (!rotte.contains(r))
					rotte.add(r);
				Graphs.addEdge(graph, a1, a2, weight);
				 }
			}
		}
			 System.out.println(this.airlineIdMap.toString());
			 System.out.println(this.airportIdMap.toString());
			 System.out.println(this.flightIdMap.getFlights().size());
	}

	public String ottieniTrattePeggiori() {

		List<Rotta> rotteOrdinate = new ArrayList<Rotta>();
		String elenco = "";
		Collections.sort(rotte, new ComparatorePeso());

		for (int i = 0; i < 10; i++)
			rotteOrdinate.add(rotte.get(i));

		for (Rotta r : rotteOrdinate)
			elenco += r.getNome1() + " " + r.getNome2() + " " + r.getWeight() + "\n";

		return elenco;

	}
	
	public List<Airport> getShortestPath(Airline a, int id1, int id2) {

		if(graph == null)
			this.creaGrafo(a);
		
		ShortestPathAlgorithm<Airport, DefaultWeightedEdge> spa = new FloydWarshallShortestPaths<Airport, DefaultWeightedEdge>(graph);

		GraphPath<Airport, DefaultWeightedEdge> gp = spa.getPath(this.getAirportFromId(id1), this.getAirportFromId(id2));
		// double weight = gp.getWeight();
		
		return gp.getVertexList();
	}
	
	public List<Airport> trovaVicini(Airline a, Airport ap) {

		if (graph == null)
			this.creaGrafo(a);

		List<Airport> aeroportiConnessi = new ArrayList<Airport>(Graphs.neighborListOf(graph, ap));
		return aeroportiConnessi;

	}
	
	public void printStats(Airline a) {
		if (graph == null) {
			this.creaGrafo(a);
		}
		
		ConnectivityInspector<Airport, DefaultWeightedEdge> ci = new ConnectivityInspector<Airport, DefaultWeightedEdge>(graph);
		System.out.println(ci.connectedSets());
		}
	
	public Set<Airport> getBiggestSCC(Airline a) {
		
		if (graph == null) {
			this.creaGrafo(a);
		}
		
		ConnectivityInspector<Airport, DefaultWeightedEdge> ci = new ConnectivityInspector<Airport, DefaultWeightedEdge>(graph);
		
		Set<Airport> bestSet = null;
		int bestSize = 0;
		
		for (Set<Airport> s : ci.connectedSets()) {
			if (s.size() > bestSize) {
				bestSet = new HashSet<Airport>(s);
				bestSize = s.size();
			}	
		}
		
		return bestSet;
	}

}
