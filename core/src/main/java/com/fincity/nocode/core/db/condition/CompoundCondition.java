package com.fincity.nocode.core.db.condition;

import java.util.ArrayList;
import java.util.Collections;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompoundCondition extends ArrayList<Condition> implements Condition {

	private static final long serialVersionUID = -7472518798765764775L;

	public final ConditionType type;

	public CompoundCondition(ConditionType type, Condition... conditions) {

		this.type = type;
		if (conditions != null && conditions.length != 0)
			Collections.addAll(this, conditions);
	}

	@Override
	public ConditionType getType() {
		return this.type;
	}
	
	@Override
	public Condition and(Condition condition) {
		if (type != ConditionType.AND)
			return Condition.super.and(condition);
		this.add(condition);
		return this;
	}
	
	@Override
	public Condition or(Condition condition) {
		if (type != ConditionType.OR)
			return Condition.super.and(condition);
		this.add(condition);
		return this;
	}
}
