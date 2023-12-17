package com.example.airlinereservation.services.flightservice;

import com.example.airlinereservation.data.model.Airport;
import com.example.airlinereservation.data.model.enums.Destinations;
import com.example.airlinereservation.data.model.flight.Flight;
import com.example.airlinereservation.data.repositories.AirportRepository;
import com.example.airlinereservation.data.repositories.FlightRepository;
import com.example.airlinereservation.dtos.Request.FlightRequest;
import com.example.airlinereservation.dtos.Request.FlightUpdateRequest;
import com.example.airlinereservation.dtos.Response.FlightResponse;
import com.example.airlinereservation.exceptions.InvalidRequestException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static com.example.airlinereservation.utils.Constants.BOLA_AIR;

@Service
@AllArgsConstructor
public class BolaAir_FlightService implements FlightService{
	
	private FlightRepository flightRepository;
	private AirportRepository airportRepository;
	private ModelMapper mapper;
	
	@Override
	public FlightResponse addFlight(FlightRequest flightRequest) throws InvalidRequestException {
		try{
			Flight mappedFlight = mapper.map(flightRequest, Flight.class);
			Airport arrivalAirport = buildAirport();
			arrivalAirport.setIcaoCode(flightRequest.getArrivalAirportCode());
			arrivalAirport.setAirportName(flightRequest.getArrivalAirportName());
			arrivalAirport.setAirportAddress(flightRequest.getArrivalAirportAddress());
			Airport savedArrivalAirport = airportRepository.save(arrivalAirport);
			
			Airport departureAirport = buildAirport();
			departureAirport.setIcaoCode(flightRequest.getDepartureAirportCode());
			departureAirport.setAirportName(flightRequest.getDepartureAirportName());
			departureAirport.setAirportAddress(flightRequest.getDepartureAirportAddress());
			Airport savedDepartureAirport = airportRepository.save(departureAirport);
			
			mappedFlight.setArrivalAirport(savedArrivalAirport);
			mappedFlight.setDepartureAirport(savedDepartureAirport);
			mappedFlight.setAirline(BOLA_AIR);
			mappedFlight.setEstimatedFlightDurationInMinutes(flightRequest.getEstimatedFlightDurationInMinutes());
			
			Flight savedFlight = flightRepository.save(mappedFlight);
			return buildFlightResponse(savedFlight);
		}catch(Throwable exception){
			System.out.println(exception.getMessage());
			throw new InvalidRequestException(exception.getMessage(), exception);
		}
	}
	

	private FlightResponse buildFlightResponse(Flight savedFlight) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		FlightResponse response = FlightResponse.class.getDeclaredConstructor().newInstance();
		mapper.map(savedFlight, response);
		response.setArrivalAirportAddress(savedFlight.getArrivalAirport().getAirportAddress());
		response.setArrivalAirportCode(savedFlight.getArrivalAirport().getIcaoCode());
		response.setArrivalAirportName(savedFlight.getArrivalAirport().getAirportName());
		
		response.setDepartureAirportAddress(savedFlight.getDepartureAirport().getAirportAddress());
		response.setDepartureAirportCode(savedFlight.getDepartureAirport().getIcaoCode());
		response.setDepartureAirportName(savedFlight.getDepartureAirport().getAirportName());
		return response;
	}
	
	private Airport buildAirport() {
		return Airport.builder().build();
	}
	
	@Override
	public FlightResponse updateFlight(FlightUpdateRequest flightRequest) {
		return null;
	}
	
	@Override
	public Long getCountOfAllFlights() {
		return flightRepository.count();
	}
	
	@Override
	public List<Flight> getAllFLights() {
		return null;
	}
	
	@Override
	public FlightResponse getFlightByArrivalAndDepartureLocation(Destinations arrivalState, Destinations departureState) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidRequestException {
		Optional<Flight> foundFlight = flightRepository.findByArrivalAndDepartureAirport("arrivalState", "departureState");
		if (foundFlight.isPresent())
			return buildFlightResponse(foundFlight.get());
		throw new InvalidRequestException("Flight Not Found");
	}
	
	@Override
	public void removeAll() {
		flightRepository.deleteAll();
		airportRepository.deleteAll();
	}
	
	
}