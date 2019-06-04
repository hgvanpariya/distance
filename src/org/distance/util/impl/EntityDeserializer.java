package org.distance.util.impl;

import java.io.IOException;

import org.distance.bean.Entity;
import org.distance.bean.Location;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * This class is responsible for converting JSON to {@link Entity} object.
 * @author hvanpariya
 *
 */
public class EntityDeserializer extends StdDeserializer<Entity> {

	private static final long serialVersionUID = -613072616695561768L;

	public EntityDeserializer() {
		this(null);
	}

	protected EntityDeserializer(Class<?> arg0) {
		super(arg0);
	}

	@Override
	public Entity deserialize(JsonParser jsonParser, DeserializationContext desContext)
			throws IOException, JsonProcessingException {
		JsonNode productNode = jsonParser.getCodec().readTree(jsonParser);
		Entity entity = new Entity();
		entity.setId(productNode.get("user_id").intValue());
		entity.setName(productNode.get("name").textValue());
		Location location = new Location();
		location.setLatitude(Double.parseDouble(productNode.get("latitude").textValue()));
		location.setLongitude(Double.parseDouble(productNode.get("longitude").textValue()));
		entity.setLocation(location);
		return entity;
	}

}
