package com.fincity.nocode.core.db.condition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnaryCondition implements Condition{

	private static final long serialVersionUID = -3078498243608148890L;
	
	private final ConditionType type;
	private final Condition condition;
	
	@Override
	public ConditionType getType() {
		return this.type;
	}
}
