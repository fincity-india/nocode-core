package com.fincity.nocode.core.system.model.connection;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.OBJECT;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.STRING;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.core.system.model.Store;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection implements Serializable {

	private static final long serialVersionUID = -3705284071463667735L;

	private static final String SCHEMA_NAME = "Connection";
	private static final String PROPS_STRING = "props";

	public static final Schema SCHEMA = new Schema().setType(new SingleType(OBJECT))
			.setNamespace(CoreConstants.NAMESPACE_CORE).setName(SCHEMA_NAME).setTitle(SCHEMA_NAME).setVersion(1)
			.setProperties(Map.of("id", Schema.of("id", STRING),
					"type", Schema.of("type", STRING).setEnums(Stream.of(ConnectionType.values()).map(Object::toString)
							.map(JsonPrimitive::new).map(JsonElement.class::cast).toList()),
					PROPS_STRING, Schema.of(PROPS_STRING, OBJECT).setAnyOf(List.of(MongoDBCProperties.SCHEMA, R2DBCProperties.SCHEMA))
					));
	
	public static final Store STORE_RECORD = new Store().setAudited(true).setVersioned(true).setSoftDelete(true)
			.setNamespace(CoreConstants.NAMESPACE_CORE)
			.setName(SCHEMA_NAME)
			.setWritePermission(CoreConstants.PERMISSION_ADMIN)
			.setUpdatePermission(CoreConstants.PERMISSION_ADMIN)
			.setDeletePermission(CoreConstants.PERMISSION_ADMIN);
	
	public static Connection from(JsonObject jsonObject) {
		
		if (jsonObject == null || jsonObject.isJsonNull()) return null;
		
		JsonElement id = jsonObject.get("id");
		
		ConnectionType cType = ConnectionType.valueOf(jsonObject.get("type").getAsString());
		
		IConnectionProperties props = null;
		
		JsonElement propsElement = jsonObject.get(PROPS_STRING);
		
		if (propsElement != null && !propsElement.isJsonNull()) {
			
			Gson gson = new Gson();
			
			if (cType == ConnectionType.MONGO)
				props = gson.fromJson(propsElement, MongoDBCProperties.class);
			else if (cType == ConnectionType.R2DBC)
				props = gson.fromJson(propsElement, R2DBCProperties.class);
		}
		
		return new Connection(id == null ? null : id.getAsString(), cType, props);
	}

	private String id;
	private ConnectionType type;
	private IConnectionProperties props;

	public enum ConnectionType {

		MONGO, R2DBC,;
	}
}
