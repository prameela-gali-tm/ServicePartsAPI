package com.toyota.scs.serviceparts.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public DateUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String convertfromDateToStringFmt(final Date dateFormatIn,
			final String dateInStr) {
		String responseDate = null;
		try {
			/*System.out.println("--PR-- dateFormatIn : " + dateFormatIn
					+ " , dateInStr : " + dateInStr);*/
			SimpleDateFormat sdf = new SimpleDateFormat(dateInStr);
			responseDate = sdf.format(dateFormatIn);
		} catch (Exception e) {

		}

		return responseDate;
	}
}
