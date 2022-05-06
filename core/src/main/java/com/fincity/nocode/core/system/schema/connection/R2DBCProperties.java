package com.fincity.nocode.core.system.schema.connection;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties.Pool;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.*;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.core.system.schema.connection.Connection.ConnectionType;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.object.AdditionalPropertiesType;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;

import lombok.Data;

@Data
public class R2DBCProperties implements IConnectionProperties {

	private static final long serialVersionUID = 5155375924411836727L;

	public static final String SCHEMA_NAME = "R2DBCProperties";

	public static final Schema SCHEMA = new Schema().setTitle(SCHEMA_NAME).setId(SCHEMA_NAME)
			.setNamespace(CoreConstants.NAMESPACE_CORE).setType(new SingleType(OBJECT))
			.setProperties(Map.of(
					"name", Schema.of("name", STRING),
					"generateUniqueName", Schema.of("generateUniqueName", BOOLEAN),
					"url", Schema.of("url", STRING),
					"username", Schema.of("username", STRING),
					"password", Schema.of("password", STRING),
					"properties",
						Schema.of("properties", OBJECT).setAdditionalProperties(
								new AdditionalPropertiesType().setSchemaValue(Schema.of("property", STRING))),
					"pool", Schema.of("pool", OBJECT).setProperties(Map.of(
							"maxIdleTime", Schema.of("maxIdleTime",  STRING),
							"maxLifeTime", Schema.of("maxLifeTime",  STRING),
							"maxAcquireTime", Schema.of("maxAcquireTime",  STRING),
							"maxCreateConnectionTime", Schema.of("maxCreateConnectionTime",  STRING),
							"initialSize", Schema.of("initialSize",  INTEGER),
							"maxSize", Schema.of("maxSize",  INTEGER),
							"validationQuery", Schema.of("validationQuery", STRING)
							)),
					"uniqueName", Schema.of("uniqueName", STRING)));

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
