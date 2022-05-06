package com.fincity.nocode.core.system.schema.connection;

import java.io.Serializable;

import com.fincity.nocode.core.system.schema.connection.Connection.ConnectionType;

public interface IConnectionProperties extends Serializable {

	public ConnectionType getConnectionType();
}
