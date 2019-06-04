package org.distance.util;

import java.util.List;

import org.distance.bean.Entity;

public interface InputReader {

	List<Entity> parse(String path);

}
