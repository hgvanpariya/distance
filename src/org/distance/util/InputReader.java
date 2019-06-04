package org.distance.util;

import java.util.List;

import org.distance.bean.Entity;

/**
 * Interface to support different types of readers.
 * 
 * @author hvanpariya
 *
 */
public interface InputReader {

	/**
	 * Read input file/URL and convert it to {@link List} of {@link Entity} objects.
	 * 
	 * @param path
	 * @return
	 */
	List<Entity> parse(String path);

}
