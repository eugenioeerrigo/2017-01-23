package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country, DefaultEdge> graph;
	private List<Country> countries;
	private Map<Integer, Country> cmap;
	
	public Model() {
		
	}
	
	public void creaGrafo(int anno) {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		
		BordersDAO dao = new BordersDAO();
		countries = dao.getCountryByYear(anno);
		cmap = new HashMap<>();
		for(Country cc : countries)
			cmap.put(cc.getcCode(),  cc);
		
		//aggiungo vertici
		Graphs.addAllVertices(this.graph, countries);
		
		//aggiungo archi
		List<CoppiaNoStati> archi = dao.getCoppieAdiacenti(anno);
		for(CoppiaNoStati c : archi) {
			graph.addEdge(cmap.get(c.getState1no()), cmap.get(c.getState2no()));
		}
		
		//System.out.format("Grafo creato con %d vertici e %d archi\n", graph.vertexSet().size(), graph.edgeSet().size());
		
	}
	
	public List<CountryAndNumber> getCountryAndNumber(){
		List<CountryAndNumber> list = new ArrayList<>();
		
		for(Country c : graph.vertexSet())
			list.add(new CountryAndNumber(c, graph.degreeOf(c)));      //.neighborListOf(graph, c).size()
		
		Collections.sort(list);
		return list;
	}

}
