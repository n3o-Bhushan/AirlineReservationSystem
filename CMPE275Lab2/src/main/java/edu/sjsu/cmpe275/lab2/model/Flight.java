package edu.sjsu.cmpe275.lab2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name="Flight")
public class Flight {
	
	@Id
	@Column(name="FLIGHT_NUMBER")
	private String number; // Each flight has a unique flight number.
	
	@Column(name="PRICE")
    private int price;
	
	@Column(name="SOURCE")
    private String from;
	
	@Column(name="DEST")
    private String to; 
	
	@Column(name="DEPARTURETIME")
    private String departureTime;     
	
	@Column(name="ARRIVALTIME")
    private String arrivalTime;
    
	@Column(name="SEATSLEFT")
    private int seatsLeft; 
    
	@Column(name="DESCRIPTION")
    private String description;
    
	@Embedded
    private Plane plane;  // Embedded
    
	//@Column(name="PASSENGERS")
	@ManyToMany()
	@JoinTable(name = "PASSENGERS_FLIGHT", 
			joinColumns={@JoinColumn(name="FLIGHT_NUMBER", referencedColumnName="FLIGHT_NUMBER")}, 
			   inverseJoinColumns = { @JoinColumn(name = "PASSENGER_ID", referencedColumnName="PASSENGER_ID") })
	
    private List<Passenger> passengers = new ArrayList<Passenger>();
	
    
    public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public Flight(){}
	
	public Flight(String number, int price, String from, String to, String departureTime, String arrivalTime,
			String description, Plane plane, List<Passenger> passengers) {
		super();
		//int capacity, String model, String manufacturer, int yearOfManufacture
		this.number = number;
		this.price = price;
		this.from = from;
		this.to = to;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.seatsLeft = plane.getCapacity();
		this.description = description;
//		Plane plane = new Plane(capacity, model, manufacturer,yearOfManufacture);
		this.plane = plane;
		this.passengers = passengers;
	}
	
	public Flight(String number, int price, String from, String to, String departureTime, String arrivalTime,
			String description, Plane plane) {
		super();
		//int capacity, String model, String manufacturer, int yearOfManufacture
		this.number = number;
		this.price = price;
		this.from = from;
		this.to = to;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.seatsLeft = plane.getCapacity();
		this.description = description;
//		Plane plane = new Plane(capacity, model, manufacturer,yearOfManufacture);
		this.plane = plane;
		//this.passengers = passengers;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getSeatsLeft() {
		return seatsLeft;
	}
	public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Plane getPlane() {
		return plane;
	}
	public void setPlane(Plane plane) {
		this.plane = plane;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
    
    
    
    

}
