package com.fincity.nocode.core.db.field;

import java.util.List;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.db.condition.ConditionType;
import com.fincity.nocode.core.db.condition.InCondition;
import com.google.gson.JsonPrimitive;

public abstract class AbstractTableField implements IField {

	private static final long serialVersionUID = 8705867846156922835L;

	public Condition equalTo(JsonPrimitive value) {
		return getCondition(ConditionType.EQUAL, value);
	}

	public Condition notEqualTo(JsonPrimitive value) {
		return getCondition(ConditionType.NOT_EQUAL, value);
	}

	public Condition lessThan(JsonPrimitive value) {
		return getCondition(ConditionType.LESS_THAN, value);
	}

	public Condition lessThanOrEqual(JsonPrimitive value) {
		return getCondition(ConditionType.LESS_THAN_EQUAL, value);
	}

	public Condition greaterThan(JsonPrimitive value) {
		return getCondition(ConditionType.GREATER_THAN, value);
	}

	public Condition greaterThanOrEqual(JsonPrimitive value) {
		return getCondition(ConditionType.GREATER_THAN_EQUAL, value);
	}

	public Condition equalTo(IField value) {
		return getCondition(ConditionType.EQUAL, value);
	}

	public Condition notEqualTo(IField value) {
		return getCondition(ConditionType.NOT_EQUAL, value);
	}

	public Condition lessThan(IField value) {
		return getCondition(ConditionType.LESS_THAN, value);
	}

	public Condition lessThanOrEqual(IField value) {
		return getCondition(ConditionType.LESS_THAN_EQUAL, value);
	}

	public Condition greaterThan(IField value) {
		return getCondition(ConditionType.GREATER_THAN, value);
	}

	public Condition greaterThanOrEqual(IField value) {
		return getCondition(ConditionType.GREATER_THAN_EQUAL, value);
	}
	
	public Condition in(List<JsonPrimitive> values) {
		return new InCondition(values);
	}
	
	public Condition in(JsonPrimitive... values) {
		return in(List.of(values));
	}

	protected abstract Condition getCondition(ConditionType conditionType, JsonPrimitive value) ;

	protected abstract Condition getCondition(ConditionType conditionType, IField value) ;
}