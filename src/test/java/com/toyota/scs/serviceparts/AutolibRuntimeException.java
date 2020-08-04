package com.toyota.scs.serviceparts;

public class AutolibRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Shows a custom message alongside with the original exception thrown by the system.
	 * @param t Exception
	 * @param message Custom message.
	 */
	public AutolibRuntimeException(Throwable t, String message) {
		super(message + "\nOriginal exception:\n" + t.getMessage());
	}

	/**
	 * Creates a new exception with a custom message type.
	 * @param message Custom message.
	 */
		

	public AutolibRuntimeException(String message) {
		super(message);
		
	}

}
