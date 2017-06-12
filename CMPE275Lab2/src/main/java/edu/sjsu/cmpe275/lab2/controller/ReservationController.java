package edu.sjsu.cmpe275.lab2.controller;

import java.text.ParseException;
import java.util.List;
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
import edu.sjsu.cmpe275.lab2.service.ReservationService;

@RestController
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
//	private final AtomicLong reservationCounter = new AtomicLong();
	//POST
	@RequestMapping(method = RequestMethod.POST,value="/reservation")
	public String makeReservation(@RequestParam long passengerId, @RequestParam(value="flightLists") String flightList) throws ParseException, JsonProcessingException, JSONException{
		String [] flightLists = flightList.split(","); 
		return reservationService.makeReservation(passengerId, flightLists);
		
		
	}
	
	//GET
	@RequestMapping(method = RequestMethod.GET, value="/reservation/{number}", produces="application/json")
	public String getFlightJSON(@PathVariable long number) throws JsonProcessingException, JSONException, ParseException{
		
		return reservationService.getReservationJSON(number);
	}
	
	//PUT
	@RequestMapping(method = RequestMethod.PUT, value="/reservation/{number}", produces="application/json")
	public String updateFlightsJSON(@PathVariable long number, 
			                        @RequestParam (value="flightsAdded", defaultValue="null") String flightsAdd, 
			                        @RequestParam (value="flightsRemoved", defaultValue="null") String flightsRem) throws JsonProcessingException, JSONException, ParseException{
		
		String[] flights_to_add = flightsAdd.split(",");
		String[] flights_to_rem = flightsRem.split(",");
		return reservationService.updateFlightsJSON(number,flights_to_add,flights_to_rem);
	}
	
	//DELETE
	@RequestMapping(method = RequestMethod.DELETE, value="/reservation/{number}")
	public String deleteFlight(@PathVariable long number){
		return reservationService.deleteReservation(number);
	}

}
