package com.fincity.nocode.core.db.condition;

import com.fincity.nocode.core.db.field.IField;
import com.google.gson.JsonPrimitive;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringCondition implements Condition {

	private static final long serialVersionUID = -484077392499495165L;

	private final ConditionType type;
	private final IField field;
	private final JsonPrimitive value; // NOSONAR - Gson decided not to serialise the json library.
	private final IField compareTo;
	private boolean ignoreCase = true;

	public StringCondition(ConditionType type, IField field, JsonPrimitive value, boolean ignoreCase) {
		this(type, field, value, null, ignoreCase);
	}

	public StringCondition(ConditionType type, IField field, IField compareTo, boolean ignoreCase) {
		this(type, field, null, compareTo, ignoreCase);
	}

	@Override
	public ConditionType getType() {
		return this.type;
	}

}
