package org.distance.util.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.distance.bean.Entity;
import org.distance.util.impl.JsonFileReader;
import org.junit.Before;
import org.junit.Test;

public class JsonFileTest {

	JsonFileReader jsonFile;

	@Before
	public void setup() {
		jsonFile = new JsonFileReader();
	}

	@Test
	public void parseFile() throws IOException {
		List<Entity> allEntity = jsonFile.parse("/Users/hvanpariya/junk/input");
		assertTrue(allEntity.size() == 32);
		assertTrue(allEntity.get(0).getLocation().getLongitude() == -6.043701);
		assertTrue(allEntity.get(0).getLocation().getLatitude() == 52.986375);
		assertTrue(allEntity.get(0).getId() == 12);
		assertTrue(allEntity.get(0).getName().equals("Christina McArdle"));
	}

	@Test(expected = IOException.class)
	public void parseFileInvalid() throws IOException {
		jsonFile.parse("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseFileNull() throws IOException {
		jsonFile.parse(null);
	}

}
