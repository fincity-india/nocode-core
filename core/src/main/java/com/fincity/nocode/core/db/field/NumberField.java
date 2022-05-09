package com.fincity.nocode.core.db.field;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.DOUBLE;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.FLOAT;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.INTEGER;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.LONG;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.db.condition.ConditionType;
import com.fincity.nocode.core.db.condition.NumberCondition;
import com.fincity.nocode.core.db.field.exception.FieldException;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.google.gson.JsonPrimitive;

public class NumberField extends AbstractTableField {

	private static final long serialVersionUID = -5278424530084685285L;

	private final String name;
	private final SchemaType schemaType;

	public NumberField(SchemaType schemaType, String name) {

		this.name = name;
		this.schemaType = schemaType;

		if (schemaType != null && name != null
				&& (schemaType == DOUBLE || schemaType == INTEGER || schemaType == FLOAT || schemaType == LONG))
			return;

		throw new FieldException("Only number fields are allowed");
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

		return new NumberCondition(conditionType, this, value);
	}

	@Override
	protected Condition getCondition(ConditionType conditionType, IField value) {

		return new NumberCondition(conditionType, this, value);
	}

}
