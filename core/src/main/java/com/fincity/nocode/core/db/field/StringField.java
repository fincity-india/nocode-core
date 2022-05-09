package com.fincity.nocode.core.db.field;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.STRING;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.db.condition.ConditionType;
import com.fincity.nocode.core.db.condition.StringCondition;
import com.fincity.nocode.core.db.field.exception.FieldException;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.google.gson.JsonPrimitive;

public class StringField extends AbstractTableField {

	private static final long serialVersionUID = -5278424530084685285L;

	private final String name;
	private final SchemaType schemaType;

	public StringField(SchemaType schemaType, String name) {

		this.name = name;
		this.schemaType = schemaType;

		if (schemaType != null && name != null && schemaType == STRING)
			return;

		throw new FieldException("Only string fields are allowed");
	}

	public Condition startsWith(JsonPrimitive value) {
		return getCondition(ConditionType.STARTS_WITH, value);
	}

	public Condition endsWith(JsonPrimitive value) {
		return getCondition(ConditionType.ENDS_WITH, value);
	}

	public Condition contains(JsonPrimitive value) {
		return getCondition(ConditionType.CONTAINS, value);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public SchemaType getSchemaType() {
		return this.schemaType;
	}

	@Override
	protected Condition getCondition(ConditionType conditionType, JsonPrimitive value) {

		return new StringCondition(conditionType, this, value);
	}

	@Override
	protected Condition getCondition(ConditionType conditionType, IField value) {

		return new StringCondition(conditionType, this, value);
	}
}
