package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;
import it.polito.tdp.borders.db.CountryPair;

public class Model {
	
	private BordersDAO dao;
	private SimpleGraph<Country, DefaultEdge> graph;
	private CountryIdMap cmap;
	private List<Country> vertici;
	
	public Model() {
		dao = new BordersDAO();
		cmap = new CountryIdMap();
	}

	public List<CountryNumber> creaGrafo(int anno) {
		graph = new SimpleGraph<>(DefaultEdge.class);
		
		vertici = dao.loadAllCountries(anno, cmap);
		
		Graphs.addAllVertices(graph, vertici);
		
		for(CountryPair c : dao.countryPairs(anno, cmap)) {
			graph.addEdge(c.getC1(), c.getC2());
		}
		
		List<CountryNumber> results = new ArrayList<>();
		
		for(Country v : vertici) {
			results.add(new CountryNumber(cmap.get(v), graph.outgoingEdgesOf(v).size()));
		}
		
		return results;
	}
	
	public List<Country> getVertici() {
		return vertici;
	}

	public void simula(Country start) {
		Simulatore sim = new Simulatore(start, this.graph);
		sim.run();
	}

}
