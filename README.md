# AirlineReservationSystem
A REST API to implement a simple airline reservation system through CRUD. The app is hosted on Amazon EC2. Developed in Spring Boot.

#Requirement
In this lab, you build a REST API to implement a simple airline reservation system through create, get, update, and delete. 
 
Please design your data model to hold information for the airline reservation system. We define the following requirements and constraints:
-Each passenger can make one or more reservations. Time overlap is not allowed among any of his reservation.

-Each reservation may consist of one or more flights.

-Each flight can carry one or more passengers.

-Each flight uses one plane, which is an embedded object with four fields mapped to the corresponding four columns in the airline table.

-The total amount of passengers can not exceed the capacity of an plane.

-When a passenger is deleted, all reservation made by him are automatically canceled for him.

-A flight can not be deleted if it needs to carry at least one passenger.
