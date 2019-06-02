package org.distance.bean;

import org.distance.util.impl.EntityDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = EntityDeserializer.class)
public class Entity {

	int id;
	String name;
	Location location;

	public Entity() {
		super();
	}

	public Entity(int id, String name, Location location) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + ", name=" + name + ", location=" + location + "]";
	}
	
}
