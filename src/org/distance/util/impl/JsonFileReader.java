package org.distance.util.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.distance.bean.Entity;
import org.distance.util.InputReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFileReader implements InputReader {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<Entity> parse(String path) {
		if (StringUtils.isBlank(path)) {
			throw new IllegalArgumentException("ERR01: input file path cannot be null.");
		}
		LinkedList<Entity> allRecordedEntity = new LinkedList<>();
		List<String> allLineOfFIle;
		try {
			allLineOfFIle = Files.readAllLines(Paths.get(path));
			for (String eachLine : allLineOfFIle) {
				Entity emp = objectMapper.readValue(eachLine, Entity.class);
				allRecordedEntity.add(emp);
			}
		} catch (IOException e) {
			throw new RuntimeException("ERR07: Unable to process file.");
		}

		return allRecordedEntity;
	}

}
