package com.fincity.nocode.core.db.field;

import java.io.Serializable;
import java.util.List;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.google.gson.JsonPrimitive;

public interface Field extends Serializable {

	public String getName();

	public SchemaType getSchemaType();

	public Condition equalTo(JsonPrimitive value);

	public Condition notEqualTo(JsonPrimitive value);

	public Condition lessThan(JsonPrimitive value);

	public Condition lessThanOrEqual(JsonPrimitive value);

	public Condition greaterThan(JsonPrimitive value);

	public Condition greaterThanOrEqual(JsonPrimitive value);

	public Condition equalTo(Field value);

	public Condition notEqualTo(Field value);

	public Condition lessThan(Field value);

	public Condition lessThanOrEqual(Field value);

	public Condition greaterThan(Field value);

	public Condition greaterThanOrEqual(Field value);

	public Condition in(List<JsonPrimitive> values);
	
	public Condition in(JsonPrimitive ...values);
}
