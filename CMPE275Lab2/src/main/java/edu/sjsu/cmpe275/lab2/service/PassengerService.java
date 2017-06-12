package edu.sjsu.cmpe275.lab2.service;

import java.io.IOException;
import java.util.List;

import javax.xml.XMLConstants;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.lab2.dao.PassengerDAO;
import edu.sjsu.cmpe275.lab2.dao.ReservationDAO;
import edu.sjsu.cmpe275.lab2.model.Passenger;
import edu.sjsu.cmpe275.lab2.model.Reservation;

@Service
public class PassengerService {
	
	@Autowired
	PassengerDAO passDao;
	
	@Autowired
	ReservationDAO reservationDAO;
	public String getPassengerXML(long id, boolean xml) throws JsonProcessingException, JSONException {
		// TODO Auto-generated method stub
		try{	System.out.println("Im inside");
		if (xml){
    		
    		ObjectMapper mapperObj = new ObjectMapper();
    		String jso = mapperObj.writeValueAsString(passDao.findOne(id));
    		//jso =jso.substring(1, jso.length());
    		//String reserv = mapperObj.writeValueAsString(pr.findPassengerReservations(id));
    		System.out.println("string" + jso);
    		//return ResponseEntity.status(HttpStatus.OK).body(new JSONObject("{\"passenger\":{"+jso+"}}"));
    		JSONObject j = new JSONObject("{\"passenger\":"+jso+"}");
    		System.out.print(j);
//    		return j.toString();
    		System.out.println(XML.toString(j));
    		return XML.toString(j);
		}
		else {return null;}
   	} catch (RuntimeException e){ throw e;}

		
	}

	public String getPassengerJSON(long id, boolean json) throws JSONException, JsonProcessingException {
		try{	System.out.println("Im inside");
		if (json){
    		
    		ObjectMapper mapperObj = new ObjectMapper();
    		String jso = mapperObj.writeValueAsString(passDao.findOne(id));
    		//jso =jso.substring(1, jso.length());
    		//String reserv = mapperObj.writeValueAsString(pr.findPassengerReservations(id));
    		System.out.println("string" + jso);
    		//return ResponseEntity.status(HttpStatus.OK).body(new JSONObject("{\"passenger\":{"+jso+"}}"));
    		List<Reservation> passengerReservationList = reservationDAO.findByPassenger(passDao.findOne(id)); 
    		JSONObject j = new JSONObject("{\"passenger\":"+jso+"}");
    		
    		//JSONObject js = new JSONObject();
    		//js.put("passenger", passDao.findOne(id));
    		//System.out.print(js);
    		return j.toString();
		}
		else {return null;}
   	} catch (RuntimeException e){ throw e;}

}
	
	
	
	public Passenger addPassengerJSON(Passenger passenger) {
		// TODO Auto-generated method stub
		
		passDao.save(passenger);
		return passenger;
	}

	public String updatePassenger(long id, String firstname, String lastname, int age, String gender, String phone) throws JsonProcessingException, JSONException {
		Passenger p = passDao.findOne(id);
		p.setFirstname(firstname);
		p.setLastname(lastname);
		p.setAge(age);
		p.setGender(gender);
		p.setPhone(phone);
		passDao.save(p);
		ObjectMapper mapperObj = new ObjectMapper();
		String jso = mapperObj.writeValueAsString(p);
		JSONObject j = new JSONObject("{\"passenger\":"+jso+"}");
		return j.toString();
		
	}

	public String deletePassenger(long id) throws JSONException {
		passDao.delete(id);
		JSONObject jInner = new JSONObject();
		JSONObject jOuter = new JSONObject();
		jInner.put("code", "200");
		jInner.put("msg", "Passenger with id " +id+" is deleted successfully");
		jOuter.put("Response", jInner);
		System.out.println(jOuter.toString());
		return XML.toString(jOuter);
	}

	
}
