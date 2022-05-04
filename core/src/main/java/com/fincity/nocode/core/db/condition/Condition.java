package com.fincity.nocode.core.db.condition;

import java.io.Serializable;

public interface Condition extends Serializable {

	public ConditionType getType();

	public default Condition and(Condition condition) {
		return new CompoundCondition(ConditionType.AND, condition);
	}

	public default Condition or(Condition condition) {
		return new CompoundCondition(ConditionType.OR, condition);
	}

	public default Condition not() {
		return new UnaryCondition(ConditionType.UNARY_NOT, this);
	}
}
