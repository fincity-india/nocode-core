package com.fincity.nocode.core.system.schema.connection;

import java.io.Serializable;

import lombok.Data;

@Data
public class Connection implements Serializable {

	private static final long serialVersionUID = -3705284071463667735L;
	
	private String id;
	private ConnectionType type;
	private IConnectionProperties dbProps;
	
	public enum ConnectionType {

		MONGO,
		R2DBC,
		;
	}
}
