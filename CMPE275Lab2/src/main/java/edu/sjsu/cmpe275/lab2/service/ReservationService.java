package edu.sjsu.cmpe275.lab2.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.lab2.dao.FlightDAO;
//import edu.sjsu.cmpe275.lab2.dao.FlightDAO_GHOST;
import edu.sjsu.cmpe275.lab2.dao.PassengerDAO;
import edu.sjsu.cmpe275.lab2.dao.ReservationDAO;
import edu.sjsu.cmpe275.lab2.model.Flight;
import edu.sjsu.cmpe275.lab2.model.Passenger;
import edu.sjsu.cmpe275.lab2.model.Plane;
import edu.sjsu.cmpe275.lab2.model.Reservation;

@Service
public class ReservationService {

	@Autowired
	private PassengerDAO passengerDAO;
	
	@Autowired 
	private ReservationDAO reservationDAO;
//	
//	@Autowired
//	private FlightDAO_GHOST flightDAO_GHOST;
	
	@Autowired
	private FlightDAO flightDAO;
	
	class Interval{
		public Date start;
		public Date end;
		
	}

	private boolean checkOverlapping(String [] flightList) throws ParseException{
		boolean isOverlapped = false;
		Interval[] schedule = new Interval[flightList.length];
		for(int i=0;i<flightList.length;i++){
			Flight flight = new Flight();
			flight = flightDAO.findOne(flightList[i]);
			//Flight flight = flightList.get(i);
			Date startTime = (Date) new SimpleDateFormat("yyyy-MM-dd-HH").parse(flight.getDepartureTime());
			Date endTime = (Date) new SimpleDateFormat("yyyy-MM-dd-HH").parse(flight.getArrivalTime());
			Interval interval = new Interval();
			interval.start = startTime;
			interval.end = endTime;
			
			//Date[] dateArr = new Date[2];
			//dateArr[0] = startTime;
			//dateArr[1] = endTime;
			schedule[i]=interval;
			
		}
		Date[] dateArr = new Date[2];
		//Collections.sort(schedule);
		for(int i=0;i<schedule.length;i++){
			System.out.println("Interval"+i+":start: "+schedule[i].start);
			System.out.println("Interval"+i+":end: "+schedule[i].end);
			
		}
		System.out.println("------------------");
		Arrays.sort(schedule, new Comparator<Interval>(){
			public int compare(Interval a, Interval b){
				//System.out.println((int)(a.start.getTime()) - (int)(b.start.getTime()));
				return a.end.compareTo(b.start);
				//return (int)(a.start.getTime()) - (int)(b.start.getTime());
			}
			
		});
		
		for(int i=0;i<schedule.length;i++){
			System.out.println("Interval"+i+":start: "+schedule[i].start);
			System.out.println("Interval"+i+":end: "+schedule[i].end);
			
		}
		for (int i=1;i<schedule.length;i++){
			if (schedule[i].start.before(schedule[i-1].end)){
				return true;
			}
		}
		
		return isOverlapped;
	}
	public String makeReservation(long passengerId, String [] flightList) throws ParseException, JsonProcessingException, JSONException {
		// TODO Auto-generated method stub
		Reservation reserv = new Reservation();
		List<JSONObject> flightJSONLists = new ArrayList<JSONObject>();
		List<Flight> flightLists = new ArrayList<Flight>();
		int totalPrice = 0;
		Passenger passenger = passengerDAO.findOne(passengerId);
		//reserv.setPassenger(passenger);
		//Need to append the already existing flightlist of passenger in this
		boolean isOverlapped= checkOverlapping(flightList);
		if (!isOverlapped){
			for(int i=0;i<flightList.length;i++)
			{
				
				Flight flight = new Flight();
				flight = flightDAO.findOne(flightList[i]);
				
				if(flight.getSeatsLeft() > 0)
				{
				totalPrice += flight.getPrice();
				flight.setSeatsLeft(flight.getSeatsLeft()-1);
				JSONObject flightJSON = new JSONObject();
				flightJSON.put("number", flight.getNumber());
				flightJSON.put("price", flight.getPrice());
				flightJSON.put("from", flight.getFrom());
				flightJSON.put("to", flight.getTo());
				flightJSON.put("departureTime", flight.getDepartureTime());
				flightJSON.put("arrivalTime", flight.getArrivalTime());
				flightJSON.put("seatsLeft", flight.getSeatsLeft());
				flightJSON.put("description", flight.getDescription());
				
				Plane jPlane = new Plane();
				jPlane = flight.getPlane();
				ObjectMapper mapperObj = new ObjectMapper();
	    		String jp = mapperObj.writeValueAsString(jPlane);
	    		
	    		JSONObject jsonObject = new JSONObject(jp);
				
				flightJSON.put("plane", jsonObject);
				
				//flightJSON.put("plane", flight.getPlane());
				
				flightJSONLists.add(flightJSON);
				flightLists.add(flight);
				flight.getPassengers().add(passenger);
				
				flightDAO.save(flight);
				
				}
			}
			
			reserv.setPrice(totalPrice);
			reserv.setFlights(flightLists);
			reserv.setPassenger(passenger);
			//passenger.setFlights(flightList);
			//passengerDAO.save(passenger);
			reservationDAO.save(reserv);
			JSONObject jnew = new JSONObject();
			jnew.put("orderNumber", reserv.getOrderNumber());
			jnew.put("price", reserv.getPrice());
			Passenger jPassenger =new Passenger();
			jPassenger = reserv.getPassenger();
			ObjectMapper mapperObj = new ObjectMapper();
    		String jp = mapperObj.writeValueAsString(jPassenger);
    		
    		JSONObject jsonObject = new JSONObject(jp);
			
			jnew.put("passenger", jsonObject);
			JSONObject flightJ = new JSONObject();
			flightJ.put("flight", flightJSONLists);
			jnew.put("flights", flightJ);
			
			JSONObject returnVal = new JSONObject("{\"reservation\":"+jnew.toString()+"}");
			//returnVal.put("reservation", jnew);
			System.out.println(returnVal);
			//ObjectMapper mapperObj = new ObjectMapper();
    		//String jso = mapperObj.writeValueAsString(reserv);
    		
    		//JSONObject j = new JSONObject("{\"reservation\":"+jso+"}");
			return XML.toString(returnVal);
			
		}
		else{
			System.out.println("<<<<<OVERLAPPING>>>>>>>>>>");
		}
		return null;
	}
	public String getReservationJSON(long orderNumber) throws JsonProcessingException, JSONException, ParseException {
		//ObjectMapper mapperObj = new ObjectMapper();
		//String str = mapperObj.writeValueAsString(reservationDAO.findOne(orderNumber));
		
//		Reservation reserv = new Reservation();
		Reservation reserv = reservationDAO.findOne(orderNumber);
		Passenger passenger = reserv.getPassenger();
		List<Flight> flightL= reserv.getFlights();
		String[] flightList = new String[flightL.size()];
		for (int i=0;i<flightL.size();i++){
			flightList[i] = flightL.get(i).getNumber();
		}
		
		
		//Reservation reserv = new Reservation();
		List<JSONObject> flightJSONLists = new ArrayList<JSONObject>();
		List<Flight> flightLists = new ArrayList<Flight>();
		int totalPrice = 0;
		//Passenger passenger = passengerDAO.findOne(passengerID);
		//reserv.setPassenger(passenger);
		//Need to append the already existing flightlist of passenger in this
		boolean isOverlapped= checkOverlapping(flightList);
		if (!isOverlapped){
			for(int i=0;i<flightList.length;i++)
			{
				
				Flight flight = new Flight();
				flight = flightDAO.findOne(flightList[i]);
				
				if(flight.getSeatsLeft() > 0)
				{
				totalPrice += flight.getPrice();
				//flight.setSeatsLeft(flight.getSeatsLeft()-1);
				JSONObject flightJSON = new JSONObject();
				flightJSON.put("number", flight.getNumber());
				flightJSON.put("price", flight.getPrice());
				flightJSON.put("from", flight.getFrom());
				flightJSON.put("to", flight.getTo());
				flightJSON.put("departureTime", flight.getDepartureTime());
				flightJSON.put("arrivalTime", flight.getArrivalTime());
				flightJSON.put("seatsLeft", flight.getSeatsLeft());
				flightJSON.put("description", flight.getDescription());
				
				Plane jPlane = new Plane();
				jPlane = flight.getPlane();
				ObjectMapper mapperObj = new ObjectMapper();
	    		String jp = mapperObj.writeValueAsString(jPlane);
	    		
	    		JSONObject jsonObject = new JSONObject(jp);
				
				flightJSON.put("plane", jsonObject);
				
				//flightJSON.put("plane", flight.getPlane());
				
				flightJSONLists.add(flightJSON);
				flightLists.add(flight);
				//flight.getPassengers().add(passenger);
				
				//flightDAO.save(flight);
				
				}
			}
			
			reserv.setPrice(totalPrice);
			reserv.setFlights(flightLists);
			reserv.setPassenger(passenger);
			//passenger.setFlights(flightList);
			//passengerDAO.save(passenger);
			///reservationDAO.save(reserv);
			JSONObject jnew = new JSONObject();
			jnew.put("orderNumber", reserv.getOrderNumber());
			jnew.put("price", reserv.getPrice());
			Passenger jPassenger =new Passenger();
			jPassenger = reserv.getPassenger();
			ObjectMapper mapperObj = new ObjectMapper();
    		String jp = mapperObj.writeValueAsString(jPassenger);
    		
    		JSONObject jsonObject = new JSONObject(jp);
			
			jnew.put("passenger", jsonObject);
			JSONObject flightJ = new JSONObject();
			flightJ.put("flight", flightJSONLists);
			jnew.put("flights", flightJ);
			
			JSONObject returnVal = new JSONObject("{\"reservation\":"+jnew.toString()+"}");
			//returnVal.put("reservation", jnew);
			System.out.println(returnVal);
			//ObjectMapper mapperObj = new ObjectMapper();
    		//String jso = mapperObj.writeValueAsString(reserv);
    		
    		//JSONObject j = new JSONObject("{\"reservation\":"+jso+"}");
			return returnVal.toString();
		//return XML.toJSONObject(makeReservation(id, flightArray)).toString();
		
//		JSONObject j = new JSONObject("{\"reservations\":"+str+"}");
//		System.out.println(j);
//		return j.toString();
		}
		return null;
	
	}
	public String updateFlightsJSON(long number, String[] flightsAdd, String[] flightsRem) throws JSONException, JsonProcessingException, ParseException {
		// TODO Auto-generated method stub
		Reservation reserv = new Reservation();
		reserv = reservationDAO.findOne(number);
		HashSet<Flight> flightsToRemove = new HashSet<Flight>();
		for(int i=0;i<flightsRem.length ;i++){
			flightsToRemove.add(flightDAO.findOne(flightsRem[i]));
			
		}
		for(int i=0;i<flightsAdd.length ;i++){
			reserv.getFlights().add(flightDAO.findOne(flightsAdd[i]));
			
		}
		
		for(Iterator<Flight> iter = reserv.getFlights().listIterator();iter.hasNext();){
			Flight flight = iter.next();
			if(flightsToRemove.contains(flight)){
				iter.remove();
			}
			
		}
		
		
		Passenger passenger = reserv.getPassenger();
		List<Flight> flightL= reserv.getFlights();
		String[] flightList = new String[flightL.size()];
		for (int i=0;i<flightL.size();i++){
			flightList[i] = flightL.get(i).getNumber();
		}
		
		
		//Reservation reserv = new Reservation();
		List<JSONObject> flightJSONLists = new ArrayList<JSONObject>();
		List<Flight> flightLists = new ArrayList<Flight>();
		int totalPrice = 0;
		//Passenger passenger = passengerDAO.findOne(passengerID);
		//reserv.setPassenger(passenger);
		//Need to append the already existing flightlist of passenger in this
		boolean isOverlapped= checkOverlapping(flightList);
		if (!isOverlapped){
			for(int i=0;i<flightList.length;i++)
			{
				
				Flight flight = new Flight();
				flight = flightDAO.findOne(flightList[i]);
				
				if(flight.getSeatsLeft() > 0)
				{
				totalPrice += flight.getPrice();
				//flight.setSeatsLeft(flight.getSeatsLeft()-1);
				JSONObject flightJSON = new JSONObject();
				flightJSON.put("number", flight.getNumber());
				flightJSON.put("price", flight.getPrice());
				flightJSON.put("from", flight.getFrom());
				flightJSON.put("to", flight.getTo());
				flightJSON.put("departureTime", flight.getDepartureTime());
				flightJSON.put("arrivalTime", flight.getArrivalTime());
				flightJSON.put("seatsLeft", flight.getSeatsLeft());
				flightJSON.put("description", flight.getDescription());
				
				Plane jPlane = new Plane();
				jPlane = flight.getPlane();
				ObjectMapper mapperObj = new ObjectMapper();
	    		String jp = mapperObj.writeValueAsString(jPlane);
	    		
	    		JSONObject jsonObject = new JSONObject(jp);
				
				flightJSON.put("plane", jsonObject);
				
				//flightJSON.put("plane", flight.getPlane());
				
				flightJSONLists.add(flightJSON);
				flightLists.add(flight);
				//flight.getPassengers().add(passenger);
				
				//flightDAO.save(flight);
				
				}
			}
			
			reserv.setPrice(totalPrice);
			reserv.setFlights(flightLists);
			reserv.setPassenger(passenger);
			//passenger.setFlights(flightList);
			//passengerDAO.save(passenger);
			///reservationDAO.save(reserv);
			JSONObject jnew = new JSONObject();
			jnew.put("orderNumber", reserv.getOrderNumber());
			jnew.put("price", reserv.getPrice());
			Passenger jPassenger =new Passenger();
			jPassenger = reserv.getPassenger();
			ObjectMapper mapperObj = new ObjectMapper();
    		String jp = mapperObj.writeValueAsString(jPassenger);
    		
    		JSONObject jsonObject = new JSONObject(jp);
			
			jnew.put("passenger", jsonObject);
			JSONObject flightJ = new JSONObject();
			flightJ.put("flight", flightJSONLists);
			jnew.put("flights", flightJ);
			
			JSONObject returnVal = new JSONObject("{\"reservation\":"+jnew.toString()+"}");
			//returnVal.put("reservation", jnew);
			System.out.println(returnVal);
			//ObjectMapper mapperObj = new ObjectMapper();
    		//String jso = mapperObj.writeValueAsString(reserv);
    		
    		//JSONObject j = new JSONObject("{\"reservation\":"+jso+"}");
			return returnVal.toString();
		
		
		
		
	}
		return null;
}
	public String deleteReservation(long number) {
		// TODO Auto-generated method stub
		Reservation reserv = new Reservation();
		reserv = reservationDAO.findOne(number);
		List<Flight> flights = reserv.getFlights();
		for(int i=0;i<flights.size();i++){
			flights.get(i).setSeatsLeft(flights.get(i).getSeatsLeft()+1);
			List<Passenger> allPassengers = flights.get(i).getPassengers();
			//Remove the passenger from the list
			for(Iterator<Passenger> iter = allPassengers.listIterator();iter.hasNext();){
				Passenger passenger = iter.next();
				if(passenger.equals(reserv.getPassenger())){
					iter.remove();
				}
			}
		}
		
		reservationDAO.delete(number);
		
		return "reservation deleted";
	}
}
