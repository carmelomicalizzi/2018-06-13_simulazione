package it.polito.tdp.flightdelays.model;

import java.util.Comparator;

public class ComparatorePeso implements Comparator<Rotta> {

	@Override
	public int compare(Rotta uno, Rotta due) {

		double pesouno = uno.getWeight() * 1000000;
		double pesodue = due.getWeight() * 1000000;

		return (int) -(pesouno - pesodue);
	}

}
