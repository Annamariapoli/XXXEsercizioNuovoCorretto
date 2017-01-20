package application;

import java.util.List;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import bean.Station;
import bean.Trip;
import db.Dao;

public class Model {
	
	private Dao dao = new Dao();
	private SimpleWeightedGraph<Station, DefaultWeightedEdge> grafo ;
	
	public List<Station> getAllStation(){
		List<Station> allS = dao.getAllStation();
		return allS;
	}

	public List<Trip> getAllTrip(){
		List<Trip> allT= dao.getAllT();
		return allT;
	}
	
	public double getDistanzaTraStation(Station start, Station end){   //ritorna i km di distanza
		double distanza =-1;
		if(start!= null && end!=null){
			if(!start.equals(end)){
				LatLng c1 = new LatLng (start.getLat(), start.getLon());
				LatLng c2 = new LatLng(end.getLat(), end.getLon());
				distanza = LatLngTool.distance(c1,  c2, LengthUnit.KILOMETER);
			}
		}
		return distanza;
	}
	
	public void buildGraph(){  //funziona
		grafo = new SimpleWeightedGraph<Station, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo,  getAllStation());
		for(Trip trip : getAllTrip()){
			for(Station s1 : grafo.vertexSet()){
			for(Station s2 : grafo.vertexSet()){
			    if(trip.getStartStationID()==s1.getStationID() && trip.getEndStationID()==s2.getStationID()){
			    	int durataSecondi = trip.getDuration();
			    	if(!s1.equals(s2)){
			    	Graphs.addEdge(grafo,  s1,  s2,  durataSecondi);
			         }
			    	}
		    }
    	    }	
		}
		System.out.println(grafo.toString());
	}
	
	public int durataStazioniGrafo(Station start, Station end){      //devono essere pero collegate da uno stesso arco
		int durataSecondi =-1;
		if(start!=null && end != null){
			if(!start.equals(end)){
				DefaultWeightedEdge arco = grafo.getEdge(start, end);
				int durata= (int) grafo.getEdgeWeight(arco);
			}
		}
		return durataSecondi;
	}
	
	public List<Station> getCammino(Station start, Station end){  //ritorna le stazioni intermedie incluse start ed end
		if(start!=null && end!= null){
			DijkstraShortestPath<Station , DefaultWeightedEdge> cammino = new DijkstraShortestPath<Station , DefaultWeightedEdge> (grafo, start, end);
			GraphPath<Station , DefaultWeightedEdge> path= cammino.getPath();   
			if(path==null){
				return null;
			   }
			return Graphs.getPathVertexList(path);
		}
		return null;
	}

	public double lunghezzaCammino(Station start, Station end){   //ritorna i km e so la distanza
		if(start!=null && end!= null){
			DijkstraShortestPath<Station , DefaultWeightedEdge> cammino = new DijkstraShortestPath<Station , DefaultWeightedEdge> (grafo, start, end);
			double lunghezzaCammino = cammino.getPathLength();  
			return lunghezzaCammino;
		}
		return -1;
	}
	
	
	
	
	public double getTempoPercorrenzaMinimo(Station start, Station end){ 
		double tempoTotale=0;
		DijkstraShortestPath<Station , DefaultWeightedEdge> cammino = new DijkstraShortestPath<Station , DefaultWeightedEdge> (grafo, start, end);
		List<DefaultWeightedEdge> archi = cammino.getPathEdgeList();
		for(DefaultWeightedEdge arco :archi){
			tempoTotale += grafo.getEdgeWeight(arco);
		}
		return tempoTotale;
		
	
		
	}
	
	
	
	
	
	
	
	
	
	public static void main(String [] args){
		Model m = new Model();
		m.buildGraph();
	}
	
	
	
//	public int durataMinima(Station start, Station end){  //tra due stazioni vicine
//		int durata = dao.durataMinima(start, end);
//		return durata;
//	}
}
