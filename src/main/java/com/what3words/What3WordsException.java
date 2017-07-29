package com.what3words;

/**
 * Created by arnaud on 29/07/17.
 */
public class What3WordsException extends Exception {

	public What3WordsException(Exception e) {
		super(e);
	}

	public What3WordsException(String message) {
		super(message);
	}

	public What3WordsException(String message, Throwable cause) {
		super(message, cause);
	}
}
