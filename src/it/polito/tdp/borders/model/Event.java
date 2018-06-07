package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{

	private int time;
	private int numMigranti;
	private Country destination;
	
	
	public Event(int time, int numMigranti, Country destination) {
		super();
		this.time = time;
		this.numMigranti = numMigranti;
		this.destination = destination;
	}
	
	public int getTime() {
		return time;
	}

	public int getNumMigranti() {
		return numMigranti;
	}

	public Country getDestination() {
		return destination;
	}

	public void setDestination(Country destination) {
		this.destination = destination;
	}


	@Override
	public String toString() {
		return "Event [time=" + time + ", numMigranti=" + numMigranti + ", destination=" + destination + "]";
	}

	@Override
	public int compareTo(Event arg0) {
		return this.time-arg0.time;
	}
}
