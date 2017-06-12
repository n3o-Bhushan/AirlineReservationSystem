package edu.sjsu.cmpe275.lab2.service;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.lab2.dao.FlightDAO;
//import edu.sjsu.cmpe275.lab2.dao.FlightDAO_GHOST;
import edu.sjsu.cmpe275.lab2.model.Flight;

@Service
public class FlightService {
	@Autowired
	private FlightDAO flightDAO;
	
//	@Autowired
//	private FlightDAO_GHOST flightDAO_GHOST;

	public String postPut(Flight flight) throws JsonProcessingException, JSONException {
		flightDAO.save(flight);
		//flightDAO_GHOST.save(flight);
		ObjectMapper mapperObj = new ObjectMapper();
		String str = mapperObj.writeValueAsString(flight);
		
		int part1End =str.indexOf("passengers");
		String str1 = str.substring(0, part1End+("passengers").length()+2);
		//jsonObject.put("flight", jso);
		int part2Start = str.indexOf("[",part1End);
		String str2 = str.substring(part2Start)+"}";
		String addition = "{\"passenger\":";
		String newStr = str1+addition+ str2;
		
		JSONObject j = new JSONObject("{\"flight\":"+newStr+"}");
		
		
		//return XML.toString(j);
		return j.toString();
	}
	
	public String deleteFlight(String id) {
		// TODO Auto-generated method stub
		
		flightDAO.delete(id);
		//flightDAO_GHOST.delete(id);
		return "delete successful";
	}

	public String getFlightJSON(String id,boolean json) throws JsonProcessingException, JSONException {
		// TODO Auto-generated method stub
		if (json){
		ObjectMapper mapperObj = new ObjectMapper();
		String str = mapperObj.writeValueAsString(flightDAO.findOne(id));
		System.out.println("JSON$$$$$$$$$$"+str);
		
		int part1End =str.indexOf("passengers");
		String str1 = str.substring(0, part1End+("passengers").length()+2);
		//jsonObject.put("flight", jso);
		int part2Start = str.indexOf("[",part1End);
		String str2 = str.substring(part2Start)+"}";
		String addition = "{\"passenger\":";
		String newStr = str1+addition+ str2;
		///String newStr = str1+
		JSONObject j = new JSONObject("{\"flight\":"+newStr+"}");
			return j.toString();
		}
		return null;
		
	}
	
	public String getFlightXML(String id,boolean json) throws JsonProcessingException, JSONException {
		// TODO Auto-generated method stub
		if (json){
		ObjectMapper mapperObj = new ObjectMapper();
		String str = mapperObj.writeValueAsString(flightDAO.findOne(id));

		
		int part1End =str.indexOf("passengers");
		String str1 = str.substring(0, part1End+("passengers").length()+2);
		//jsonObject.put("flight", jso);
		int part2Start = str.indexOf("[",part1End);
		String str2 = str.substring(part2Start)+"}";
		String addition = "{\"passenger\":";
		String newStr = str1+addition+ str2;
		
		JSONObject j = new JSONObject("{\"flight\":"+newStr+"}");
		
		
			return XML.toString(j);
		}
		return null;
		
	}
	

}

//Important Rough Work - Ignore below
//JSONObject jsonObject = new JSONObject();
		/*SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd-HH");
		sdf.setTimeZone(TimeZone.getTimeZone("PST"));
		String departureTime = sdf.format(flightDAO.findOne(id).getDepartureTime());
		String arrivalTime = sdf.format(flightDAO.findOne(id).getArrivalTime());
		//System.out.println(departureTime + "  "+ arrivalTime);
		//System.out.println("This is JSO"+jso);
		
		
		//String str = "{\"number\":\"ABC12\",\"price\":120,\"from\":\"AA\",\"to\":\"BB\",\"departureTime\":1492185600000,\"arrivalTime\":1492203600000,\"seatsLeft\":150,\"description\":\"EE\"}";
		int part1End = str.indexOf("departureTime");
		String str1 = str.substring(0,part1End);
		
		int part2Start = str.indexOf("seatsLeft")-1;
		String str2 = str.substring(part2Start);
		
		int dpt = str.indexOf("departureTime"+("departureTime").length())+2;
		int avt = str.indexOf("arrivalTime"+("arrivalTime").length())+2;
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
		//sdf.setTimeZone(TimeZone.getTimeZone("PST"));
		//String departureTime = sdf.format(new Date());
		//String arrivalTime = sdf.format(new Date());
		
		String newStr = str1+"departureTime\":\""+departureTime+"\",\"arrivalTime\":\""+arrivalTime+"\","+str2;
		System.out.println(newStr);
		
		*/
		
		//int part1End =str.indexOf("passengers");
		//String str1 = str.substring(0, part1End);
		//jsonObject.put("flight", jso);
		
		//JSONObject j = new JSONObject("{\"flight\":"+str+"}");