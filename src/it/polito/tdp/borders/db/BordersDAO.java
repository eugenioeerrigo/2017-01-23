package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryIdMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BordersDAO {
	
	public List<Country> loadAllCountries(int year, CountryIdMap cmap) {
		
		String sql = 
				"SELECT DISTINCT ccode,StateAbb,StateNme " +
				"FROM country as o, contiguity as c "
				+ "WHERE o.ccode=c.state1no "
				+ "AND c.year<= ? AND conttype =1 " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, year);
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(cmap.get(c)) ;
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	
public List<CountryPair> countryPairs(int year, CountryIdMap cmap) {
		
		String sql = "SELECT state1no, state2no FROM contiguity WHERE year<= ? AND state1no<state2no AND conttype = 1" ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, year);
			
			ResultSet rs = st.executeQuery() ;
			
			List<CountryPair> list = new LinkedList<>() ;
			
			while( rs.next() ) {
				
				CountryPair c = new CountryPair(cmap.get(rs.getInt("state1no")), cmap.get(rs.getInt("state2no"))) ;
				
				list.add(c) ;
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
}
