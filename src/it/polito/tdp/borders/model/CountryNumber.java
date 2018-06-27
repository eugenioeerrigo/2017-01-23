package it.polito.tdp.borders.model;

public class CountryNumber {

	private Country country;
	private int number;
	
	public CountryNumber(Country country, int number) {
		super();
		this.country = country;
		this.number = number;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public String toString() {
		return String.format("%s %s - Stati confinanti: %d", country.getStateAbb(), country.getStateName(), number);
	}
	
}
