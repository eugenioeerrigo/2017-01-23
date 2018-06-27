package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{
	
	private Country Country;
	private int time;
	private int numMigranti;
	
	public Evento(Country Country, int time, int numMigranti) {
		this.Country = Country;
		this.time = time;
		this.numMigranti = numMigranti;
	}

	public int getNumMigranti() {
		return numMigranti;
	}

	public void setNumMigranti(int numMigranti) {
		this.numMigranti = numMigranti;
	}

	public Country getCountry() {
		return Country;
	}

	public void setCountry(Country Country) {
		this.Country = Country;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public int compareTo(Evento arg0) {
		return this.time-arg0.time;
	}
	
	

}
