package com.example.airlinereservation.exceptions;

import javax.security.auth.login.LoginException;

public class FailedRegistrationException extends LoginException {
	public FailedRegistrationException(String message){
		super(message);
	}
	
}
