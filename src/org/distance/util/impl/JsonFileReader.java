package org.distance.util.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.distance.bean.Entity;
import org.distance.util.FileReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFileReader implements FileReader {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<Entity> parse(String path) throws IOException {
		if (StringUtils.isBlank(path)) {
			throw new IllegalArgumentException("ERR01: input file path cannot be null.");
		}
		LinkedList<Entity> allRecordedEntity = new LinkedList<>();
		List<String> allLineOfFIle = Files.readAllLines(Paths.get(path));

		for (String eachLine : allLineOfFIle) {
			Entity emp = objectMapper.readValue(eachLine, Entity.class);
			allRecordedEntity.add(emp);
		}

		return allRecordedEntity;
	}

}
