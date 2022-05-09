package com.fincity.nocode.core.system.schema.connection;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.OBJECT;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.STRING;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.core.system.schema.Store;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import lombok.Data;

@Data
public class Connection implements Serializable {

	private static final long serialVersionUID = -3705284071463667735L;

	private static final String SCHEMA_NAME = "Connection";

	public static final Schema SCHEMA = new Schema().setType(new SingleType(OBJECT))
			.setNamespace(CoreConstants.NAMESPACE_CORE).setId(SCHEMA_NAME).setTitle(SCHEMA_NAME).setVersion(1)
			.setProperties(Map.of("id", Schema.of("id", STRING),
					"type", Schema.of("type", STRING).setEnums(Stream.of(ConnectionType.values()).map(Object::toString)
							.map(JsonPrimitive::new).map(JsonElement.class::cast).toList()),
					"props", Schema.of("props", OBJECT).setAnyOf(List.of(MongoDBCProperties.SCHEMA, R2DBCProperties.SCHEMA))
					));
	
	public static final Store STORE_RECORD = new Store().setAudited(true).setVersioned(true).setSoftDelete(true)
			.setNamespace(CoreConstants.NAMESPACE_CORE)
			.setName(SCHEMA_NAME)
			.setWritePermission(CoreConstants.PERMISSION_ADMIN)
			.setUpdatePermission(CoreConstants.PERMISSION_ADMIN)
			.setDeletePermission(CoreConstants.PERMISSION_ADMIN);

	private String id;
	private ConnectionType type;
	private IConnectionProperties props;

	public enum ConnectionType {

		MONGO, R2DBC,;
	}
}
