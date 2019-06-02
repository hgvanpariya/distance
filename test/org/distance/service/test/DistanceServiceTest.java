package org.distance.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;

import org.distance.bean.Entity;
import org.distance.bean.Location;
import org.distance.service.DistanceService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * DistanceService can be used to get the distance for list of user compare to
 * the any specific point.
 * 
 * @author hvanpariya
 *
 */
public class DistanceServiceTest {

	DistanceService distanceService;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Before
	public void setup() {
		distanceService = new DistanceService();
	}

	@Test
	public void checkNearbyCustomers() throws IOException {
		Set<Entity> checkNearbyCustomers = distanceService.checkNearbyCustomers("/Users/hvanpariya/junk/input",
				new Location(53.339428, -6.257664), 100);
		assertNotNull(checkNearbyCustomers);
		assertTrue(checkNearbyCustomers.size() == 16);
		int previousEntityId = 0;
		for (Entity entity : checkNearbyCustomers) {
			assertTrue(previousEntityId < entity.getId());
			previousEntityId = entity.getId();
		}
	}

	@Test
	public void checkNearbyCustomersNullFile() throws IOException {
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("ERR03: input file path cannot be null.");
		distanceService.checkNearbyCustomers(null, new Location(53.339428, -6.257664), 100);
	}

	@Test
	public void checkNearbyCustomersEmptyFilePath() throws IOException {
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("ERR03: input file path cannot be null.");
		distanceService.checkNearbyCustomers("", new Location(53.339428, -6.257664), 100);
	}

	@Test
	public void checkNearbyCustomersNullReferenceLocation() throws IOException {
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("ERR04: Reference location cannot be null.");
		distanceService.checkNearbyCustomers("/Users/hvanpariya/junk/input", null, 100);
	}

	@Test
	public void checkNearbyCustomersNegativeDistance() throws IOException {
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("ERR05: distance cannot be negative.");
		distanceService.checkNearbyCustomers("/Users/hvanpariya/junk/input", new Location(53.339428, -6.257664), -10);
	}

}
