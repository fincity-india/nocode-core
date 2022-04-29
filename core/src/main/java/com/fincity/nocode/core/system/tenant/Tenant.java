package com.fincity.nocode.core.system.tenant;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fincity.nocode.core.configuration.MongoDBCProperties;
import com.fincity.nocode.core.configuration.R2DBCProperties;
import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;
import com.google.gson.JsonPrimitive;

import lombok.Data;

@Data
public class Tenant implements Serializable {

	private static final long serialVersionUID = -7363439760557588035L;

	private static final String SCHEMA_NAME = "Tenant";

	public static final Schema SCHEMA = new Schema().setType(new SingleType(SchemaType.OBJECT))
			.setNamespace(CoreConstants.NAMESPACE_CORE).setId(SCHEMA_NAME).setTitle(SCHEMA_NAME)
			.setProperties(Map.of("code",
					new Schema().setType(new SingleType(SchemaType.STRING)).setMinLength(5).setMaxLength(5), "name",
					new Schema().setType(new SingleType(SchemaType.STRING)), "type",
					new Schema().setType(new SingleType(SchemaType.STRING))
							.setEnums(List.of(new JsonPrimitive("MONGO"), new JsonPrimitive("R2DBC"))),
					"mongo", MongoDBCProperties.SCHEMA, "r2dbc", R2DBCProperties.SCHEMA,
					"createdAt", new Schema().setType(new SingleType(SchemaType.LONG)),
					"createdBy", new Schema().setType(new SingleType(SchemaType.STRING)),
					"updatedAt", new Schema().setType(new SingleType(SchemaType.LONG)),
					"updatedBy", new Schema().setType(new SingleType(SchemaType.STRING))));

	private String code;
	private String name;
	private ConnectionType type;
	private MongoDBCProperties mongo;
	private R2DBCProperties r2dbc;
	private Long createdAt;
	private String createdBy;
	private Long updatedAt;
	private String updatedBy;

	public enum ConnectionType {

		MONGO, R2DBC,;
	}
}
