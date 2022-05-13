package com.fincity.nocode.core.system.model.connection;

import java.io.Serializable;

import com.fincity.nocode.core.system.model.connection.Connection.ConnectionType;

public interface IConnectionProperties extends Serializable {

	public ConnectionType getConnectionType();
}
