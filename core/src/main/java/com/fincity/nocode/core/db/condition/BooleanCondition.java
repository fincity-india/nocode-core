package com.fincity.nocode.core.db.condition;

import com.fincity.nocode.core.db.condition.exception.ConditionException;
import com.fincity.nocode.core.db.field.Field;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.google.gson.JsonPrimitive;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BooleanCondition implements Condition {

	private static final long serialVersionUID = -484077392499495165L;

	private final ConditionType type;
	private final Field field;
	private final JsonPrimitive value; // NOSONAR - Gson decided not to serialise the json library.
	private final Field compareTo;

	public BooleanCondition(ConditionType type, Field field, JsonPrimitive value) {
		this(type, field, value, null);
		
		if (value == null || value.isBoolean() || value.isJsonNull())
			return;
		
		throw new ConditionException("Value have to be a boolean");
	}

	public BooleanCondition(ConditionType type, Field field, Field compareTo) {
		this(type, field, null, compareTo);

		if (field.getSchemaType() != SchemaType.BOOLEAN || compareTo.getSchemaType() != SchemaType.BOOLEAN)
			throw new ConditionException("Cannot compare if the field is not boolean");
	}

	@Override
	public ConditionType getType() {
		return this.type;
	}

}
