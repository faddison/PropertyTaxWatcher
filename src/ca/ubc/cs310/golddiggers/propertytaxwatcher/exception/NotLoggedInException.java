package ca.ubc.cs310.golddiggers.propertytaxwatcher.exception;

import java.io.Serializable;

public class NotLoggedInException extends Exception implements Serializable {

	// Auto generated Serial Version UID.
	private static final long serialVersionUID = 1L;

	public NotLoggedInException() {
		super();
	}

	public NotLoggedInException(String message) {
		super(message);
	}

}