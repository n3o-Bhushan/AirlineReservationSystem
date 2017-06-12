package edu.sjsu.cmpe275.lab2.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import edu.sjsu.cmpe275.lab2.model.Passenger;
@Transactional
public interface PassengerDAO extends CrudRepository<Passenger,Long>{

//public Passenger findByID(long id);	
}


