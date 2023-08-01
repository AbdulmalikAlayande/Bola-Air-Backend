package com.example.airlinereservation.services.categories;
import com.example.airlinereservation.data.model.Flight;
import com.example.airlinereservation.data.model.Passenger;

import java.math.BigInteger;

public class FirstClassBookingCategory extends BookingCategory {
	private static FirstClassBookingCategory instance = null;
	private FirstClassBookingCategory (){}
	private static final int lastSeatInTheCategory = BigInteger.valueOf(4).intValue();
	public boolean canBook(Flight flight) {
		return !flight.getAirCraft().getAircraftSeats()[lastSeatInTheCategory];
	}
	
	public static FirstClassBookingCategory getInstance() {
		if (instance == null)
			return new FirstClassBookingCategory();
		return instance;
	}
	
	public void assignSeat(Flight flight) {
		assignSeatToPassenger(flight);
	}
	
	@Override
	public void assignSeat(Passenger passenger, Flight flight) {
	
	}
	
	private void assignSeatToPassenger(Flight flight) {
		for (int firstSeat = 0; firstSeat < lastSeatInTheCategory; firstSeat++) {
			if (!flight.getAirCraft().getAircraftSeats()[firstSeat]) {
				flight.getAirCraft().getAircraftSeats()[firstSeat] = true;
				break;
			}
		}
	}
	
}
