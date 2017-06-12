package edu.sjsu.cmpe275.lab2.controller;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.lab2.dao.PassengerDAO;
import edu.sjsu.cmpe275.lab2.model.Passenger;
import edu.sjsu.cmpe275.lab2.service.PassengerService;

@RestController

public class PassengerController {
	
	
	@Autowired
	private PassengerService passServ;
	@Autowired
	private PassengerDAO passengerDAO;
	private final AtomicLong counter = new AtomicLong();
	
	//GET BY XML

	@RequestMapping(method = RequestMethod.GET, value="/passenger/{id}",params="xml", produces="application/xml")
	public String getPassengerXML(@PathVariable long id, @RequestParam boolean xml)throws Exception{
		try{
			return passServ.getPassengerXML(id, xml);
		}
		catch (RuntimeException e){ e.printStackTrace();}
		return null;
		
	} 
	
	//GET BY JSON
	@RequestMapping(method = RequestMethod.GET, value="/passenger/{id}",params="json", produces="application/json")
	@ResponseBody
	public String getPassengerJSON(@PathVariable long id, @RequestParam boolean json)throws Exception{
		try{	System.out.println("Im inside");
			return passServ.getPassengerJSON(id, json);
			
		
		} catch (RuntimeException e){ throw e;}
	}
	
	//POST
	@RequestMapping(method = RequestMethod.POST, value="/passenger")
	public Passenger addPassengerJSON(@RequestParam String firstname,@RequestParam String lastname, 
												   @RequestParam int age, @RequestParam String gender,
												   @RequestParam String phone)
	{
		//long id = (counter.incrementAndGet());
		Passenger passenger = new Passenger(firstname, lastname, age, gender, phone);
		return passServ.addPassengerJSON(passenger);
		
		
	}
	
	
	//PUT
	@RequestMapping(method = RequestMethod.PUT, value="/passenger/{id}")
	public String updatePassanger(@PathVariable long id, @RequestParam String firstname,@RequestParam String lastname, 
			   @RequestParam int age, @RequestParam String gender,
			   @RequestParam String phone ) throws JsonProcessingException, JSONException
	{
		return passServ.updatePassenger(id, firstname, lastname, age, gender, phone);}
	
	
	
	//DELETE
	@RequestMapping(method = RequestMethod.DELETE, value="/passenger/{id}")
	public String deletePassenger(@PathVariable long id) throws JSONException
	{
		return passServ.deletePassenger(id);
		
	}
	
	
}
