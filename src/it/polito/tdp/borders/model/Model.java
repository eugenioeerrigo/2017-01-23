package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	private Simulator sim;
	
	public Model() {
		
	}
	
	public void creaGrafo(int anno) {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		
		BordersDAO dao = new BordersDAO();
		countries = dao.getCountryByYear(anno);    //ORDER BY Nome dello stato (alfabetico)
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

	public List<Country> getCountries() {
		return countries;         //ho modificato il metodo del dao che popola la lista per averla ordinata (alfabet.)
	}

	public void simula(Country partenza) {

		sim = new Simulator();
		
		sim.init(graph, partenza);
		sim.run();
		
	}

	public int getTsimulazione() {
		return sim.getT();
	}

	public List<CountryAndNumber> getCountriesStanziali() {
		Map<Country, Integer> map = sim.getStanziali();
		List<CountryAndNumber> stanziali = new ArrayList<>();
		
		for(Country c : map.keySet())
			stanziali.add(new CountryAndNumber(c, map.get(c)));
		
		Collections.sort(stanziali, new Comparator<CountryAndNumber>() {

			@Override
			public int compare(CountryAndNumber arg0, CountryAndNumber arg1) {
				
				return -(arg0.getNumber()-arg1.getNumber());
			}
			
		});
		
		return stanziali;
	}
	
	

}
