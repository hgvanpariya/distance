package org.distance.util.impl;

import java.util.logging.Logger;

import org.distance.bean.Location;

/**
 *  This class is responsible for calculating distance.
 * @author hvanpariya
 *
 */
public class LocationCalculator {
	
	Logger logger = Logger.getLogger(LocationCalculator.class.getName());

	/**
	 * This method will calculate the distance between two points represented with
	 * {@code Location}
	 * 
	 * @param location1
	 *            Instance of the {@code Location} which will represent the first
	 *            point.
	 * @param location2
	 *            Instance of the {@code Location} which will represent the second
	 *            point.
	 * @return
	 */
	public int calculateDistance(Location location1, Location location2) {
		if(location1 == null || location2 == null) {
			logger.warning("ERR02: invalid input for the location. Location1=" + location1 + ", Location2=" + location2 );
			throw new IllegalArgumentException("ERR02: invalid input for the location.");
		}
		double earthRadius = 6371;

		double dLatitude = Math.toRadians(location2.getLatitude() - location1.getLatitude());
		double dLongitude = Math.toRadians(location2.getLongitude() - location1.getLongitude());

		double sindLatitude = Math.sin(dLatitude / 2);
		double sindLongitude = Math.sin(dLongitude / 2);

		double a = Math.pow(sindLatitude, 2) + Math.cos(Math.toRadians(location1.getLatitude()))
				* Math.cos(Math.toRadians(location2.getLatitude())) * Math.pow(sindLongitude, 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		int distance = (int) (earthRadius * c);

		return distance;
	}

}
