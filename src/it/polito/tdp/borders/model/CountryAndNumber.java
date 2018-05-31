package it.polito.tdp.borders.model;

public class CountryAndNumber implements Comparable<CountryAndNumber>{      //Wrapper per risultati

	private Country country;
	private int number;
	
	public CountryAndNumber(Country country, int number) {
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

	@Override
	public int compareTo(CountryAndNumber arg0) {
		return -(this.number-arg0.number);
	}
	
	
}
