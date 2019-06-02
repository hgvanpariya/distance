package org.distance.util.impl;

import java.util.Set;

import org.distance.bean.Entity;
import org.distance.bean.Location;

public class DistanceCalculatorThread implements Runnable {

	Entity userDetails;
	Location officeLocation;
	int maxDistance;
	Set<Entity> nearbyUsers;

	public DistanceCalculatorThread() {
		super();
	}

	public DistanceCalculatorThread(Entity userDetails, Location officeLocation, int maxDistance,
			Set<Entity> nearbyUsers) {
		super();
		this.userDetails = userDetails;
		this.officeLocation = officeLocation;
		this.maxDistance = maxDistance;
		this.nearbyUsers = nearbyUsers;
	}

	@Override
	public void run() {
		LocationCalculator locationCalculator = new LocationCalculator();
		int calculateDistance = locationCalculator.calculateDistance(userDetails.getLocation(), officeLocation);
		if (calculateDistance < maxDistance) {
			this.nearbyUsers.add(userDetails);
		}
	}

}
