package edu.sjsu.cmpe275.lab2.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.sjsu.cmpe275.lab2.model.Flight;
import edu.sjsu.cmpe275.lab2.model.Plane;
import edu.sjsu.cmpe275.lab2.service.FlightService;

@RestController
public class FlightController {
	
	@Autowired
	FlightService flightService;
	
	
	//Plane plane;
	private final AtomicLong flightCounter = new AtomicLong();
	
	
	//POST&PUT
	//@RequestMapping(method = RequestMethod.POST, value="/flight")
    //public Flight postPut(@RequestParam String number, @RequestParam int price,@RequestParam String from, @RequestParam String to,@RequestParam String departureTime, @RequestParam String arrivalTime,  @RequestParam String description, @RequestParam  int capacity, @RequestParam String model, @RequestParam String manufacturer, @RequestParam int yearOfManufacture) {
	@RequestMapping(value="/flight/{flight_number}")
	public String createOrUpdateFlight(@PathVariable(value = "flight_number") String flightNumber, @RequestParam(value="price") int price, @RequestParam(value="from") String from, @RequestParam(value="to") String to, @RequestParam(value="departureTime") String departureTime, @RequestParam(value="arrivalTime") String arrivalTime,@RequestParam(value="description") String description,@RequestParam(value="capacity") int capacity,@RequestParam(value="model") String model,@RequestParam(value="manufacturer") String manufacturer,@RequestParam(value="yearOfManufacture") int yearOfManufacture) throws ParseException, JsonProcessingException, JSONException{	
	try{
    			//int seatsLeft= capacity;   	
    			Plane plane = new Plane(capacity,model,manufacturer,yearOfManufacture);
		    	//Flight flight= new Flight(number,price,from,to,departureTime,arrivalTime,description,capacity, model, manufacturer, yearOfManufacture);
    			Flight flight= new Flight(flightNumber,price,from,to,departureTime,arrivalTime,description, plane);
		    	return flightService.postPut(flight);
		    	
		    	
    		} catch (RuntimeException e){ throw e;}
    }  
	
	
	@RequestMapping(method = RequestMethod.GET, value="/flight/{id}", params = "json", produces="application/json")
	public String getFlightJSON(@PathVariable String id, @RequestParam boolean json) throws JsonProcessingException, JSONException{
		
		return flightService.getFlightJSON(id, json);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/flight/{id}", params = "xml", produces="application/xml")
	public String getFlightXML(@PathVariable String id, @RequestParam boolean xml) throws JsonProcessingException, JSONException{
		
		return flightService.getFlightXML(id, xml);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/flight/{id}")
	public String deleteFlight(@PathVariable String id)
	{
		return flightService.deleteFlight(id);
		
	}
	

}
