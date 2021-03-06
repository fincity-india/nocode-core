package com.fincity.nocode.core.db.condition;

import java.util.List;

import com.fincity.nocode.core.db.field.IField;
import com.google.gson.JsonPrimitive;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InCondition implements Condition {

	private static final long serialVersionUID = 4048095333837639139L;

	private final IField field;
	private final List<JsonPrimitive> values; // NOSONAR - GSON made JSON classes non serialised.

	@Override
	public ConditionType getType() {
		return ConditionType.IN;
	}

}
