package org.distance.util;

import java.io.IOException;
import java.util.List;

import org.distance.bean.Entity;

public interface FileReader {

	List<Entity> parse(String path) throws IOException;

}
