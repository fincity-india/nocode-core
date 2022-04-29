package com.fincity.nocode.core.configuration;

import java.io.Serializable;
import java.util.Map;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;

import lombok.Data;

@Data
public class MongoDBCProperties implements Serializable {

	private static final long serialVersionUID = -5172109308367728569L;

	public static final Schema SCHEMA = new Schema().setTitle("MongoDB Connection Properties")
			.setNamespace(CoreConstants.NAMESPACE_CORE)
			.setId("MongoDBCProperties").setType(new SingleType(SchemaType.OBJECT))
			.setProperties(Map.of("uri", new Schema().setType(new SingleType(SchemaType.STRING))));

	private String uri;
}
