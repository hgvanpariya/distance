package org.distance;

import java.io.IOException;
import java.util.List;

import org.distance.service.DistanceService;

public class Distance {

	
	public static void main(String[] args) throws IOException {

		if(args.length > 3) {
			DistanceService distanceService = new DistanceService();
			List<String> checkNearbyCustomers = distanceService.checkNearbyCustomers(args[0],args[1],args[2],args[3]);
			distanceService.printCustomers(checkNearbyCustomers);
		}else {
			usage();
		}
		
	}

	private static void usage() {
		System.out.println("Example: ");
		System.out.println("mvn exec:java -Dexec.mainClass=org.distance.Distance -Dexec.args='/Users/hvanpariya/other/distance/customer.txt 53.339428 -6.257664 20' -q\n");
		System.out.println("Please refer : https://github.com/hgvanpariya/distance");
	}

}
