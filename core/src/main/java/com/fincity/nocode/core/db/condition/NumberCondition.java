package com.fincity.nocode.core.db.condition;

import com.fincity.nocode.core.db.condition.exception.ConditionException;
import com.fincity.nocode.core.db.field.Field;
import com.google.gson.JsonPrimitive;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NumberCondition implements Condition {

	private static final long serialVersionUID = -484077392499495165L;

	private final ConditionType type;
	private final Field field;
	private final JsonPrimitive value; // NOSONAR - Gson decided not to serialise the json library.
	private final Field compareTo;

	public NumberCondition(ConditionType type, Field field, JsonPrimitive value) {
		this(type, field, value, null);
		
		if (value == null || value.isNumber() || value.isJsonNull())
			return;
		
		throw new ConditionException("Value have to be a number");
	}

	public NumberCondition(ConditionType type, Field field, Field compareTo) {
		this(type, field, null, compareTo);

		switch (field.getSchemaType()) { // NOSONAR - you are wrong, I get more readability with switch compared to if.

		case DOUBLE, INTEGER, FLOAT, LONG:
			break;
		default:
			throw new ConditionException("Cannot compare if the field is not numeric");
		}

		switch (compareTo.getSchemaType()) { // NOSONAR - you are wrong, I get more readability with switch compared to if.

		case DOUBLE, INTEGER, FLOAT, LONG:
			break;
		default:
			throw new ConditionException("Cannot compare if the field is not numeric");
		}
	}

	@Override
	public ConditionType getType() {
		return this.type;
	}

}
