package com.fincity.nocode.core.db.condition;

import java.io.Serializable;

public class Condition implements Serializable {

	private static final long serialVersionUID = -1332747834270025482L;

	private ConditionType type;
	
	public ConditionType getType() { return this.type; }
}
