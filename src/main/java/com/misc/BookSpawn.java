package com.misc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class BookSpawn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar cal = new GregorianCalendar();
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY,03);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		System.out.println("Next pop is: " + cal.getTime());

		
		while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			//Add 3 hours
			cal.add(Calendar.HOUR, 3);
			
			//Add random minutes from 1-60
			Random r = new Random();
			cal.add(Calendar.MINUTE, r.nextInt(59));
			
			//Display time
			System.out.println("Next pop is: " + cal.getTime());
		}

	}

}
