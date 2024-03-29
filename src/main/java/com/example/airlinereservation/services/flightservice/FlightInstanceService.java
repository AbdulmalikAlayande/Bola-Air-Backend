package com.example.airlinereservation.services.flightservice;

import com.example.airlinereservation.data.model.enums.FlightStatus;
import com.example.airlinereservation.dtos.Request.CreateFlightInstanceRequest;
import com.example.airlinereservation.dtos.Response.FlightInstanceResponse;
import com.example.airlinereservation.exceptions.InvalidRequestException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface FlightInstanceService {
	
	FlightInstanceResponse createNewInstance(CreateFlightInstanceRequest flightInstanceRequest) throws InvalidRequestException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
	
	List<FlightInstanceResponse> findAllBy(FlightStatus status);
	
	void removeAll();
}
