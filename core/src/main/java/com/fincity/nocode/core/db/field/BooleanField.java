package com.fincity.nocode.core.db.field;

import com.fincity.nocode.core.db.condition.BooleanCondition;
import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.db.condition.ConditionType;
import com.fincity.nocode.core.db.field.exception.FieldException;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.google.gson.JsonPrimitive;

public class BooleanField extends AbstractTableField {

	private static final long serialVersionUID = 2455623617476260851L;
	
	private final String name;
	private final SchemaType schemaType;

	public BooleanField(SchemaType schemaType, String name) {

		this.name = name;
		this.schemaType = schemaType;

		if (schemaType != null && name != null && schemaType == SchemaType.BOOLEAN)
			return;

		throw new FieldException("Only boolean fields are allowed");
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

		return new BooleanCondition(conditionType, this, value);
	}

	@Override
	protected Condition getCondition(ConditionType conditionType, IField value) {

		return new BooleanCondition(conditionType, this, value);
	}
}
