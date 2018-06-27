package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Simulatore {
	
	private PriorityQueue<Evento> queue;
	private int numMigranti = 1000;
	private List<CountryNumber> stanziali;
	private SimpleGraph<Country, DefaultEdge> graph;
	private int t;

	public Simulatore(Country start, SimpleGraph<Country, DefaultEdge> graph) {
		queue = new PriorityQueue<>();
		stanziali = new ArrayList<>();
		this.graph = graph;
		t = 1;
		
		for(Country c: graph.vertexSet()) {
			stanziali.add(new CountryNumber(c,0));
		}
		
		queue.add(new Evento(start, t, numMigranti));
	}
	
	public void run() {
		Evento e;
		while((e = queue.poll())!=null) {
			
			t = e.getTime();
			
			List<Country> confinanti = Graphs.neighborListOf(graph, e.getCountry());
			
			int migranti = (e.getNumMigranti()/2) / confinanti.size();
			
			if(migranti!=0) {
				for(Country c : confinanti)
					queue.add(new Evento(c, t+1, migranti));
			}
			
			int numStanziali = e.getNumMigranti()-migranti*confinanti.size();
			
			for(CountryNumber c : stanziali)
				if(c.getCountry().equals(e.getCountry()))
					c.setNumber(c.getNumber()+numStanziali);
		}
	}

	public List<CountryNumber> getStanziali() {
		return stanziali;
	}

	public int getT() {
		return t;
	}
	
	
}
