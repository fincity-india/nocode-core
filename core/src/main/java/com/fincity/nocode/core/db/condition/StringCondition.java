package com.fincity.nocode.core.db.condition;

import com.fincity.nocode.core.db.field.Field;
import com.google.gson.JsonPrimitive;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringCondition implements Condition {

	private static final long serialVersionUID = -484077392499495165L;

	private final ConditionType type;
	private final Field field;
	private final JsonPrimitive value; // NOSONAR - Gson decided not to serialise the json library.
	private final Field compareTo;

	public StringCondition(ConditionType type, Field field, JsonPrimitive value) {
		this(type, field, value, null);
	}

	public StringCondition(ConditionType type, Field field, Field compareTo) {
		this(type, field, null, compareTo);
	}

	@Override
	public ConditionType getType() {
		return this.type;
	}

}
