package org.distance;

import java.io.IOException;

import org.distance.service.DistanceService;

public class Distance {

	
	public static void main(String[] args) throws IOException {

		DistanceService distanceService = new DistanceService();
		distanceService.checkNearbyCustomers("/Users/hvanpariya/junk/input",null,100);
	}

}
