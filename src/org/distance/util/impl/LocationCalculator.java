package org.distance.util.impl;

import org.distance.bean.Location;

public class LocationCalculator {

	public int calculateDistance(Location location1, Location location2) {
		double earthRadius = 6371;

		double dLatitude = Math.toRadians(location2.getLatitude() - location1.getLatitude());
		double dLongitude = Math.toRadians(location2.getLongitude() - location1.getLongitude());

		double sindLatitude = Math.sin(dLatitude / 2);
		double sindLongitude = Math.sin(dLongitude / 2);

		double a = Math.pow(sindLatitude, 2) + Math.cos(Math.toRadians(location1.getLatitude()))
				* Math.cos(Math.toRadians(location2.getLatitude())) * Math.pow(sindLongitude, 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		int d = (int) (earthRadius * c);

		return d;
	}

	public static void main(String[] args) {
		LocationCalculator locationCalculator = new LocationCalculator();
		Location location1 = new Location(52.986375f, -6.043701f);
		Location location2 = new Location(51.92893f, -10.27699f);
		int calculateDistance = locationCalculator.calculateDistance(location1, location2);
		System.out.println(calculateDistance);
	}

}
