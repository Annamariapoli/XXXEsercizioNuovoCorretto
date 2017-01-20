package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

import bean.Station;
import bean.Trip;

public class Dao {
	
	public List<Station> getAllStation(){
		String query=" select *   from station s   order by s.name asc";
		Connection conn = DBConnect.getConnection();
		List<Station> allS = new LinkedList<>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				Station s = new Station(rs.getInt("station_id"), rs.getString("name"), rs.getDouble("lat"), rs.getDouble("long"), rs.getInt("dockcount"), rs.getString("landmark"), rs.getDate("installation").toLocalDate());
				allS.add(s);
			}
			conn.close();
			return allS;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Trip> getAllT(){
		String query="select * from trip ";
		Connection conn = DBConnect.getConnection();
		List<Trip> allt = new LinkedList<>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				Trip t = new Trip(rs.getInt("tripid"),rs.getInt("duration"),rs.getTimestamp("startdate").toLocalDateTime(),
					rs.getString("startstation"),rs.getInt("startterminal"),rs.getTimestamp("enddate").toLocalDateTime(),
					rs.getString("endstation"),rs.getInt("endterminal"),rs.getInt("bikenum"),rs.getString("SubscriptionType"),rs.getInt("Zip Code"));
				    allt.add(t);
			}
			conn.close();
			return allt;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	
	//crep grafo dove il peso è la durata e collego due stazioni vicine
	
	
	public int  durataMinima(Station start, Station end){
	String query="select t.TripID, t.Duration as durataSecondi  "
			+ " from station s1 , trip t, station s2  "
			+ "where s1.station_id=t.StartTerminal and s1.station_id=?  "
			+ "and t.EndTerminal=s2.station_id and s2.station_id=?   "
			+ "order by durataSecondi asc   limit 1";
	Connection conn = DBConnect.getConnection();
	int durataSecondi =0;
	try{
		PreparedStatement st = conn.prepareStatement(query);
		st.setInt(1,  start.getStationID());
		st.setInt(2,  end.getStationID());
		ResultSet rs = st.executeQuery();
		if(rs.next()){
			durataSecondi = rs.getInt("durataSecondi");
		}
		conn.close();
		return durataSecondi;
	}catch(SQLException e ){
		e.printStackTrace();
		return -1;
	}
	}
	
	
	
	
	public void  getData(){
		String query="select *  "
				+ "from trip t   where Month(t.StartDate)=8  "
				+ "and time(t.StartDate)='14:13:00'";
		Connection conn = DBConnect.getConnection();
		try{
			
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			if(rs.next()){
			Trip t = new Trip (rs.getInt("tripid"),
					rs.getInt("duration"),
					
					rs.getTimestamp("startdate").toLocalDateTime(),
					rs.getString("startstation"),
					rs.getInt("startterminal"),
					
					rs.getTimestamp("enddate").toLocalDateTime(),
					rs.getString("endstation"),
					rs.getInt("endterminal"),

					rs.getInt("bikenum"),
					rs.getString("SubscriptionType"),
					rs.getInt("Zip Code")
					) ;
			Month mese = t.getStartDate().getMonth(); 
			int ora = t.getStartDate().getHour();
			LocalTime orario = t.getStartDate().toLocalTime();
			DayOfWeek day =t.getStartDate().getDayOfWeek();
			int anno = t.getStartDate().getYear();
			int min =t.getStartDate().getMinute();
			int seco = t.getStartDate().getSecond();
			System.out.println(mese);
			System.out.println(ora);
			System.out.println(orario);
			System.out.println(day);
			System.out.println(anno);
			
			conn.close();
			}
		
		}catch(SQLException e ){
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String [] args){
		Dao dao = new Dao();
		dao.getData();
	}
	
	
	
	
	//A
	
	//tutti i trip di gennaio ke partono in : ora = 00:01
	
//	select Count(*)
//	from trip t
//	where Month(t.StartDate)=1  //gennaio
//	and t.StartDate>'00:01:00'
	//andT.startDate< '08:00:00';
	
	
   //B
	
	//tutti i trip di gennaio ke partono in :
	
//	select Count(*)
//	from trip t
//	where Month(t.StartDate)=1  //gennaio
//	and t.StartDate>'08:01:00'
	
	
	//C

	//tutti i trip di gennaio ke partono in :
	
//	select Count(*)
//	from trip t
//	where Month(t.StartDate)=1  //gennaio
//	and t.StartDate='16:01:00'
	
	
	
    //D
	
	//tutti i trip  di aprile ke partono in : 
	
//	select Count(*)
//	from trip t
//	where Month(t.startDate)=4 and t.startDate='00:01:00';
	

	//E
	
	//tutti i trip  di aprile ke partono in : 
	
//	select Count(*)
//	from trip t
//	where Month(t.startDate)=4 and t.startDate='08:01:00';
	

	//F 
	
	//tutti i trip  di aprile ke partono in : 
	
//	select Count(*)
//	from trip t
//	where Month(t.startDate)=4 and t.startDate='16:01:00';
	

	//G 
	
	//tutti i trip  di set ke partono in : 
	
//	select Count(*)
//	from trip t
//	where Month(t.startDate)=9 and t.startDate='00:01:00';
	

	
	//H
	
	
	//tutti i trip  di set ke partono in : 
	
//	select Count(*)
//	from trip t
//	where Month(t.startDate)=9 and t.startDate='08:01:00';
	

	//I
	
	
	//tutti i trip  di set ke partono in : 
	
//	select  Count(*)
//	from trip t
//	where Month(t.startDate)=9 and t.startDate='16:01:00';
	
	
	//calcolo percentuale :
	
	//int sommaTotaleViaggi(tutti) = A+B+C+D+E+F+G+H+I;   //NO
	
	//PERCENTUALE X LA PRIMA FASCIA ORARIA : 
	//
	//int sommaPrimaFascia = A + D+ G
	
	//int sommaSecondafascia = B+E+H
	
	//int sommaTerzafascia = C+F+I
	
	
	//int gennaioPrimaFascia = A 
	//percentualeGP =( A/sommaPrimaFascia )* 100  //ok
	
	
	
	
	
}
