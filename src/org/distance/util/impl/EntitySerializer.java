package org.distance.util.impl;

import java.io.IOException;

import org.distance.bean.Entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class EntitySerializer extends StdSerializer<Entity> {

	private static final long serialVersionUID = -613072616695561768L;

	public EntitySerializer() {
		this(null);
	}

	public EntitySerializer(Class<Entity> arg0) {
		super(arg0);
	}


	@Override
	public void serialize(Entity value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();      
		gen.writeStringField("latitude", ""+value.getLocation().getLatitude());
		gen.writeNumberField("user_id", value.getId()); 
		gen.writeStringField("name", value.getName()); 
		gen.writeStringField("longitude", ""+value.getLocation().getLongitude());
        gen.writeEndObject();
	}

}
