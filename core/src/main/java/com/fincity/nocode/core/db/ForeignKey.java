package com.fincity.nocode.core.db;

import java.io.Serializable;
import java.util.Map;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.Type;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ForeignKey implements Serializable {

	private static final long serialVersionUID = -7238337001282516018L;

	private static final String SCHEMA_NAME = "ForeignKey";

	public static final Schema SCHEMA = new Schema().setNamespace(CoreConstants.NAMESPACE_CORE)
	        .setName(SCHEMA_NAME)
	        .setTitle(SCHEMA_NAME)
	        .setType(Type.of(SchemaType.OBJECT))
	        .setProperties(Map.of("field", Schema.of("field", SchemaType.STRING), "namespace",
	                Schema.of("namespace", SchemaType.STRING), "name", Schema.of("name", SchemaType.STRING),
	                "foreignField", Schema.of("foreignField", SchemaType.STRING)));

	private String field;
	private String namespace;
	private String name;
	private String foreignField;
}
