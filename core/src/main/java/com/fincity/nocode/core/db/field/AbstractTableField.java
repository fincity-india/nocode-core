package com.fincity.nocode.core.db.field;

import java.util.List;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.db.condition.ConditionType;
import com.fincity.nocode.core.db.condition.InCondition;
import com.fincity.nocode.core.db.condition.UnaryCondition;
import com.google.gson.JsonPrimitive;

public abstract class AbstractTableField implements IField {

	private static final long serialVersionUID = 8705867846156922835L;

	@Override
	public  Condition equalTo(JsonPrimitive value) {
		return getCondition(ConditionType.EQUAL, value);
	}

	@Override
	public  Condition notEqualTo(JsonPrimitive value) {
		return getCondition(ConditionType.NOT_EQUAL, value);
	}

	@Override
	public  Condition lessThan(JsonPrimitive value) {
		return getCondition(ConditionType.LESS_THAN, value);
	}

	@Override
	public  Condition lessThanOrEqual(JsonPrimitive value) {
		return getCondition(ConditionType.LESS_THAN_EQUAL, value);
	}

	@Override
	public  Condition greaterThan(JsonPrimitive value) {
		return getCondition(ConditionType.GREATER_THAN, value);
	}

	@Override
	public  Condition greaterThanOrEqual(JsonPrimitive value) {
		return getCondition(ConditionType.GREATER_THAN_EQUAL, value);
	}

	@Override
	public  Condition equalTo(IField value) {
		return getCondition(ConditionType.EQUAL, value);
	}

	@Override
	public  Condition notEqualTo(IField value) {
		return getCondition(ConditionType.NOT_EQUAL, value);
	}

	@Override
	public  Condition lessThan(IField value) {
		return getCondition(ConditionType.LESS_THAN, value);
	}

	@Override
	public  Condition lessThanOrEqual(IField value) {
		return getCondition(ConditionType.LESS_THAN_EQUAL, value);
	}

	@Override
	public  Condition greaterThan(IField value) {
		return getCondition(ConditionType.GREATER_THAN, value);
	}

	@Override
	public  Condition greaterThanOrEqual(IField value) {
		return getCondition(ConditionType.GREATER_THAN_EQUAL, value);
	}
	
	@Override
	public  Condition in(List<JsonPrimitive> values) {
		return new InCondition(this, values);
	}
	
	@Override
	public  Condition in(JsonPrimitive... values) {
		return in(List.of(values));
	}
	
	@Override
	public Condition isNotNull() {
		return new UnaryCondition(ConditionType.NOT_NULL);
	}
	
	@Override
	public Condition isNull() {
		return new UnaryCondition(ConditionType.NULL);
	}

	protected abstract Condition getCondition(ConditionType conditionType, JsonPrimitive value) ;

	protected abstract Condition getCondition(ConditionType conditionType, IField value) ;
}