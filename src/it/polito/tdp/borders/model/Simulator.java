package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulator {

	//Tipi di eventi /Coda degli eventi
	private PriorityQueue<Event> queue;
	
	//Modello del mondo (parte statica, fotografia in un momento
	private Map<Country, Integer> stanziali;
	private Graph<Country, DefaultEdge> grafo;
	
	//Parametri simulazione
	private int N_MIGRANTI = 1000;
	
	//Variabili output
	private int T;     //Passi simulazione
	
	
	public void init(Graph<Country, DefaultEdge> grafo, Country partenza) {
		queue = new PriorityQueue<>();
		T = 1;
		stanziali = new HashMap<>();
		for(Country c : grafo.vertexSet())
			stanziali.put(c, 0);
		
		queue.add(new Event(T, N_MIGRANTI, partenza));      //a T=1 arriveranno N_MIGRANTI persone in quello stato (partenza=destinazione)
		this.grafo = grafo;
	}
	
	public void run() {
		Event e;
		while((e = queue.poll())!= null) {
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		
		this.T= e.getTime();                             //Evento col T più alto che abbia visto = ultimo evento di queue
		//Sono dentro il while, quando esco T sarà uguale al time dell'ultimo evento estratto
		
		int arrivi = e.getNumMigranti();
		Country stato = e.getDestination();
		
		List<Country> confinanti = Graphs.neighborListOf(this.grafo, stato);         //Stati confinanti
		
		//quante persone devono migrare in questi stati confinanti??
		int migranti = (arrivi/2) / confinanti.size();
		if(migranti!=0) {                 //c'è qualcuno che si può spostare
			for(Country arrivo : confinanti)
				queue.add(new Event(e.getTime()+1, migranti, arrivo));
		}
		
		int rimasti = arrivi - migranti*confinanti.size();
		
		this.stanziali.put(stato, this.stanziali.get(stato)+rimasti);        //quanti c'erano già in quello stato + quelli che aggiungo
		
	}

	public Map<Country, Integer> getStanziali() {
		return stanziali;
	}

	public int getT() {
		return T;
	}
	
	
}
