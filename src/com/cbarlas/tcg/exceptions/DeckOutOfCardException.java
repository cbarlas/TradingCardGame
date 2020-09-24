package com.cbarlas.tcg.exceptions;

public class DeckOutOfCardException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5893974991045629094L;
	
	public DeckOutOfCardException(String message) {
		super(message);
	}
}
