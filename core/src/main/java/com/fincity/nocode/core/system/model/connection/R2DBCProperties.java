package com.fincity.nocode.core.system.model.connection;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties.Pool;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.*;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.core.system.model.connection.Connection.ConnectionType;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.object.AdditionalPropertiesType;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;

import lombok.Data;

@Data
public class R2DBCProperties implements IConnectionProperties {

	private static final long serialVersionUID = 5155375924411836727L;

	public static final String SCHEMA_NAME = "R2DBCProperties";

	public static final Schema SCHEMA = new Schema().setName(SCHEMA_NAME)
	        .setNamespace(CoreConstants.NAMESPACE_CORE)
	        .setType(new SingleType(OBJECT))
	        .setProperties(Map.of("name", Schema.ofString("name"), "generateUniqueName",
	                Schema.ofBoolean("generateUniqueName"), "url", Schema.ofString("url"), "username",
	                Schema.ofString("username"), "password", Schema.ofString("password"), "properties",
	                Schema.ofObject("properties")
	                        .setAdditionalProperties(
	                                new AdditionalPropertiesType().setSchemaValue(Schema.ofString("property"))),
	                "pool", Schema.ofObject("pool")
	                        .setProperties(Map.of("maxIdleTime", Schema.ofString("maxIdleTime"), "maxLifeTime",
	                                Schema.ofString("maxLifeTime"), "maxAcquireTime", Schema.ofString("maxAcquireTime"),
	                                "maxCreateConnectionTime", Schema.ofString("maxCreateConnectionTime"),
	                                "initialSize", Schema.ofInteger("initialSize"), "maxSize",
	                                Schema.ofInteger("maxSize"), "validationQuery",
	                                Schema.ofString("validationQuery"))),
	                "uniqueName", Schema.ofString("uniqueName")));

	private String name;
	private boolean generateUniqueName;
	private String url;
	private String username;
	private String password;
	private final Map<String, String> properties = new LinkedHashMap<>();
	private final Pool pool = new Pool(); // NOSONAR - Pool is from R2DBC Properties it is not serialised.
	private String uniqueName;

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.R2DBC;
	}
}
