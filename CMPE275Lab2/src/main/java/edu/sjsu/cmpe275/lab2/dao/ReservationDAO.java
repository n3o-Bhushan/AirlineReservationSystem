package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.sjsu.cmpe275.lab2.model.Passenger;
import edu.sjsu.cmpe275.lab2.model.Reservation;

public interface ReservationDAO extends CrudRepository<Reservation,Long>{

	List<Reservation> findByPassenger(Passenger passenger);

}
