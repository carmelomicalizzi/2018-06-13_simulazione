package it.polito.tdp.flightdelays.model;

public class Rotta {

	int id1;
	int id2;
	double weight;
	String nome1;
	String nome2;

	public Rotta(int id1, String nome1, int id2, String nome2, double weight) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.nome1 = nome1;
		this.nome2 = nome2;
		this.weight = weight;
	}

	public int getId1() {
		return id1;
	}

	public int getId2() {
		return id2;
	}

	public String getNome1() {
		return nome1;
	}

	public String getNome2() {
		return nome2;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id1;
		result = prime * result + id2;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rotta other = (Rotta) obj;
		if (id1 != other.id1)
			return false;
		if (id2 != other.id2)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(nome1);
		builder.append(" ");
		builder.append(nome2);
		builder.append(" ");
		builder.append(weight);
		return builder.toString();
	}

}
