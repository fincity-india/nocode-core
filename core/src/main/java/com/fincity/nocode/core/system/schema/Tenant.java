package com.fincity.nocode.core.system.schema;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.LONG;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.OBJECT;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.STRING;

import java.io.Serializable;
import java.util.Map;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;

import lombok.Data;

@Data
public class Tenant implements Serializable {

	private static final long serialVersionUID = -7363439760557588035L;

	private static final String SCHEMA_NAME = "Tenant";

	public static final Schema SCHEMA = new Schema().setType(new SingleType(OBJECT))
			.setNamespace(CoreConstants.NAMESPACE_CORE).setId(SCHEMA_NAME).setTitle(SCHEMA_NAME)
			.setVersion(1)
			.setProperties(Map.of(
					"id", Schema.of("id", STRING),
					"code", Schema.of("code", STRING).setMinLength(5).setMaxLength(5),
					"name", Schema.of("name", STRING),
					"connectionId", Schema.of("connectionId", STRING),
					"createdAt", Schema.of("createdAt", LONG),
					"createdBy", Schema.of("createdBy", STRING),
					"updatedAt", Schema.of("updatedAt", LONG),
					"updatedBy", Schema.of("updatedBy", STRING)));

	private String id;
	private String code;
	private String name;
	private String connectionId;
	private Long createdAt;
	private String createdBy;
	private Long updatedAt;
	private String updatedBy;
}
