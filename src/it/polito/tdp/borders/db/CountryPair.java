package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Country;

public class CountryPair {

	private Country c1;
	private Country c2;
	
	public CountryPair(Country c1, Country c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	public Country getC1() {
		return c1;
	}

	public void setC1(Country c1) {
		this.c1 = c1;
	}

	public Country getC2() {
		return c2;
	}

	public void setC2(Country c2) {
		this.c2 = c2;
	}
	
	
	
}
