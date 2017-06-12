package edu.sjsu.cmpe275.lab2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Reservation")
public class Reservation {
	
	@Id
	@Column(name="ORDER_NUMBER")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long orderNumber;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PASSENGER_ID")
    private Passenger passenger;
	
	@Column(name="PRICE")
    private int price; // sum of each flight's price.
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch= FetchType.EAGER)
	@JoinTable(
				name="T_RESERVATION_FLIGHT",
				joinColumns={@JoinColumn(name="RESERVATION_NO", referencedColumnName="ORDER_NUMBER")},
				inverseJoinColumns={@JoinColumn(name="FLIGHT_ID", referencedColumnName="FLIGHT_NUMBER")}
			
			)
	
	private List<Flight> flights = new ArrayList<Flight>();
    
    
    
    public Reservation(){}
    
    
	public Reservation(long orderNumber, Passenger passenger, int price, List<Flight> flights) {
		super();
		this.orderNumber = orderNumber;
		this.passenger = passenger;
		this.price = price;
		this.flights = flights;
	}
	
	public long getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<Flight> getFlights() {
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
    
    

}
