package com.fincity.nocode.core.db.condition;

import com.google.gson.JsonPrimitive;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Field {

	private String name;
	
	public static Field of(String name) {
		return new Field(name);
	}
	
	public Condition eq(JsonPrimitive value) {
		return new Condition(Condition.EQUAL, value);
	}
}
