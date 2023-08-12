package com.example.airlinereservation.services.aircraftsservice;

import com.example.airlinereservation.data.model.aircraft.AirCraft;
import com.example.airlinereservation.dtos.Request.AirCraftRequest;
import com.example.airlinereservation.dtos.Response.AirCraftResponse;

public interface AirCraftManagementService {

	AirCraftResponse addAircraftToHanger(AirCraftRequest airCraftRequest);
	AirCraft getAvailableAirCraft(String location, boolean availability);
	void removeAircraft(AirCraft aircraft);
	
	boolean hangerContainsAirCraftByName(String airCraftName);
	
	boolean hangerContainsAirCraftByModel(String airCraftName);
	
	boolean hangerContainsAirCraft(AirCraft airCraft);
}