package com.fincity.nocode.core.system.schema.connection;

import java.util.Map;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.core.system.schema.connection.Connection.ConnectionType;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;

import lombok.Data;

@Data
public class MongoDBCProperties implements IConnectionProperties {

	private static final long serialVersionUID = -5172109308367728569L;

	public static final String SCHEMA_NAME = "MongoDBCProperties";

	public static final Schema SCHEMA = new Schema().setTitle(SCHEMA_NAME).setId(SCHEMA_NAME)
			.setNamespace(CoreConstants.NAMESPACE_CORE).setType(new SingleType(SchemaType.OBJECT))
			.setProperties(Map.of("uri", Schema.of("uri", SchemaType.STRING)));

	private String uri;

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.MONGO;
	}

}
