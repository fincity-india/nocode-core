package com.fincity.nocode.core.db.condition;

import com.fincity.nocode.core.exception.CoreException;

import lombok.Data;

@Data
public class UnaryCondition implements Condition {

	private static final long serialVersionUID = -3078498243608148890L;

	private final ConditionType type;
	private final Condition condition;

	public UnaryCondition(ConditionType type, Condition condition) {

		if (type == ConditionType.UNARY_NOT && condition == null) {
			throw new CoreException("Not condition cannot be applied for a null condition");
		}

		this.type = type;
		this.condition = condition;
	}

	public UnaryCondition(ConditionType type) {
		this(type, null);
	}

	@Override
	public ConditionType getType() {
		return this.type;
	}
}
