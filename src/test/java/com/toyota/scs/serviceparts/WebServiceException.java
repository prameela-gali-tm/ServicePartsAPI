package com.toyota.scs.serviceparts;
import com.toyota.scs.serviceparts.AutolibRuntimeException;

		public class WebServiceException extends AutolibRuntimeException {
	    private static final long serialVersionUID = -8710980695994382082L;

	    public WebServiceException(String message) {
	    	  super(message);
	    }

	    public WebServiceException(String message, Throwable cause) {
	    	super(cause, message);
	    }

	}
