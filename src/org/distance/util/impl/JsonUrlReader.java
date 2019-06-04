package org.distance.util.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import org.distance.bean.Entity;
import org.distance.util.InputReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUrlReader implements InputReader {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<Entity> parse(String urlPath) {
		LinkedList<Entity> allRecordedEntity = new LinkedList<>();

		try {
			URL url = new URL("http://s3.amazonaws.com/intercom-take-home-test/customers.txt");

			URLConnection urlConnection = url.openConnection();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				Entity emp = objectMapper.readValue(line, Entity.class);
				allRecordedEntity.add(emp);
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allRecordedEntity;
	}
}
