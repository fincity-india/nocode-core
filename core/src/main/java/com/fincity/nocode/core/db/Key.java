package com.fincity.nocode.core.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.Type;
import com.google.gson.JsonPrimitive;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Key implements Serializable {

	private static final long serialVersionUID = 3730198782497215253L;

	private static final String SCHEMA_NAME = "Key";

	public static final Schema SCHEMA = new Schema().setNamespace(CoreConstants.NAMESPACE_CORE).setName(SCHEMA_NAME)
			.setTitle(SCHEMA_NAME).setType(Type.of(SchemaType.OBJECT))
			.setProperties(Map.of("fields", Schema.ofArray("fields", Schema.of("field", SchemaType.STRING)),
					"isDescending",
					Schema.of("isDescending", SchemaType.BOOLEAN).setDefaultValue(new JsonPrimitive(Boolean.TRUE))));

	private List<String> fields;
	private boolean isDescending;
}
