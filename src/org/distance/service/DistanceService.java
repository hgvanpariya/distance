package org.distance.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang3.StringUtils;
import org.distance.bean.Entity;
import org.distance.bean.Location;
import org.distance.util.impl.DistanceCalculatorThread;
import org.distance.util.impl.JsonFileReader;

/**
 * DistanceService can be used to get the distance for list of user compare to
 * the any specific point.
 * 
 * @author hvanpariya
 *
 */
public class DistanceService {

	public Set<Entity> checkNearbyCustomers(String customerInputFile, Location locationOfOffice, int maxDistance)
			throws IOException {

		if (StringUtils.isBlank(customerInputFile)) {
			throw new IllegalArgumentException("ERR03: input file path cannot be null.");
		}
		if (locationOfOffice == null) {
			throw new IllegalArgumentException("ERR04: Reference location cannot be null.");
		}
		if (maxDistance < 0) {
			throw new IllegalArgumentException("ERR05: distance cannot be negative.");
		}

		Set<Entity> nearByUsersR = new TreeSet<>((entity1, entity2) -> entity1.getId() - entity2.getId());
		Set<Entity> nearByUsers = Collections.synchronizedSet(nearByUsersR);
		JsonFileReader jsonFileReader = new JsonFileReader();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);

		List<Entity> allCustomers = jsonFileReader.parse(customerInputFile);

		for (Entity entity : allCustomers) {
			DistanceCalculatorThread distanceCalculatorThread = new DistanceCalculatorThread(entity, locationOfOffice,
					maxDistance, nearByUsers);
			executor.execute(distanceCalculatorThread);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}

		return nearByUsers;
	}

	

}
