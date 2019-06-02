package org.distance.util.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.distance.bean.Location;
import org.distance.util.impl.LocationCalculator;
import org.junit.Before;
import org.junit.Test;

public class LocationCalculatorTest {

	LocationCalculator locationCalculator;
	Location location1;
	Location location2;
	
	@Before
	public void setup() {
		locationCalculator = new LocationCalculator();
		location1 = new Location(52.986375, -6.043701);
		location2 = new Location(51.92893, -10.27699);
	}

	@Test
	public void calculateDistance() throws IOException {
		double calculateDistance = locationCalculator.calculateDistance(location1, location2);
		assertTrue(calculateDistance == 309);
	}
	
	@Test
	public void calculateDistanceZero() throws IOException {
		location1 = new Location(0, 0);
		location2 = new Location(0, 0);
		double calculateDistance = locationCalculator.calculateDistance(location1, location2);
		assertTrue(calculateDistance == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void calculateDistanceNull() throws IOException {
		locationCalculator.calculateDistance(null, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateDistanceFirstLocationNull() throws IOException {
		locationCalculator.calculateDistance(null, location2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateDistanceSecondLocationNull() throws IOException {
		locationCalculator.calculateDistance(location1, null);
	}

}
