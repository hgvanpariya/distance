package org.distance.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.apache.commons.lang3.StringUtils;
import org.distance.bean.Entity;
import org.distance.bean.Location;
import org.distance.util.InputReader;
import org.distance.util.impl.DistanceCalculatorThread;
import org.distance.util.impl.EntitySerializer;
import org.distance.util.impl.JsonFileReader;
import org.distance.util.impl.JsonUrlReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * DistanceService can be used to get the distance for list of user compare to
 * the any specific point.
 * 
 * @author hvanpariya
 *
 */
public class DistanceService {
	
    ObjectMapper mapper = new ObjectMapper();


	/**
	 * This method will check all the nearby customers and return the {@code Set} of
	 * {@code Entity} containing the details about nearby customers.
	 * 
	 * @param customerInput    This contains URL or the file path of the list of
	 *                         customers with the location details.s
	 * @param locationOfOffice Reference location to which the all customer distance
	 *                         has to be measured.
	 * @param maxDistance      Maximum distance which should be considered as near.
	 * @return {@code Set} of {@code Entity} in sorted order as per ID, which are
	 *         within maxDistance from locationOfOffice.
	 * @throws IOException
	 */
	public Set<Entity> checkNearbyCustomers(String customerInput, Location locationOfOffice, int maxDistance)
			throws IOException {

		if (StringUtils.isBlank(customerInput)) {
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
		
		InputReader inputReader = null;
		// Deciding input reader base on the input.
		if (customerInput.contains("http")) {
			inputReader = new JsonUrlReader();
		} else {
			inputReader = new JsonFileReader();
		}
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);

		List<Entity> allCustomers = inputReader.parse(customerInput);

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

	/**
	 * This method will check all the nearby customers and return the {@code Set} of
	 * {@code Entity} containing the details about nearby customers.
	 * 
	 * @param customerInput    This contains URL or the file path of the list of
	 *                         customers with the location details.s
	 * @param locationOfOfficeLat Reference location to which the all customer distance
	 *                         has to be measured.
	 * @param locationOfOfficeLon Reference location to which the all customer distance
	 *                         has to be measured.
	 * @param maxDistanceStr      Maximum distance which should be considered as near.
	 * @return {@code Set} of {@code Entity} in sorted order as per ID, which are
	 *         within maxDistance from locationOfOffice.
	 */
	public List<String> checkNearbyCustomers(String customerInput, String locationOfOfficeLat,
			String locationOfOfficeLon, String maxDistanceStr) {

		if (StringUtils.isBlank(customerInput)) {
			throw new IllegalArgumentException("ERR03: input file path cannot be null.");
		}
		if (StringUtils.isBlank(locationOfOfficeLat)) {
			throw new IllegalArgumentException("ERR04: Reference location cannot be null.");
		}
		if (StringUtils.isBlank(locationOfOfficeLon)) {
			throw new IllegalArgumentException("ERR04: Reference location cannot be null.");
		}
		if (StringUtils.isBlank(maxDistanceStr)) {
			throw new IllegalArgumentException("ERR06: distance cannot be empty.");
		}
		int maxDistance = Integer.parseInt(maxDistanceStr);
		Location location = new Location(Double.parseDouble(locationOfOfficeLat),
				Double.parseDouble(locationOfOfficeLon));
		try {
			

			Set<Entity> checkNearbyCustomers = checkNearbyCustomers(customerInput, location, maxDistance);
			List<String> checkNearbyCustomersStr = convertEntityToJsonString(checkNearbyCustomers);
			return checkNearbyCustomersStr;
		} catch (IOException e) {
			throw new IllegalArgumentException("ERR03: input file path cannot be null.");
		}
	}
	
	/**
	 * This method will convert 
	 * @param checkNearbyCustomers
	 * @return
	 * @throws JsonProcessingException
	 */
	public List<String> convertEntityToJsonString(Set<Entity> checkNearbyCustomers) throws JsonProcessingException{
		SimpleModule mod = new SimpleModule("Entity Module");	
        mod.addSerializer(new EntitySerializer(Entity.class));
        mapper.registerModule(mod);
        
        List<String> checkNearbyCustomersStr = new LinkedList<>();
        for (Iterator<Entity> iterator = checkNearbyCustomers.iterator(); iterator.hasNext();) {
			Entity entity = iterator.next();
			checkNearbyCustomersStr.add(mapper.writeValueAsString(entity));
		}
		return checkNearbyCustomersStr;
		
	}
	
	public void printCustomers(Collection<String> nearbyCustomers) throws JsonProcessingException{
		for (String eachCutomer : nearbyCustomers) {
			System.out.println(eachCutomer);
		}
	}

}
