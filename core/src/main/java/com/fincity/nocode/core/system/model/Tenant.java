package com.fincity.nocode.core.system.model;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.OBJECT;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.STRING;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fincity.nocode.core.db.ForeignKey;
import com.fincity.nocode.core.db.Key;
import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.core.system.model.connection.Connection;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;

import lombok.Data;

@Data
public class Tenant implements Serializable {

	private static final long serialVersionUID = -7363439760557588035L;

	private static final String SCHEMA_NAME = "Tenant";

	private static final String CONNECTION_ID = "connectionId";

	public static final Schema SCHEMA = new Schema().setType(new SingleType(OBJECT))
	        .setNamespace(CoreConstants.NAMESPACE_CORE)
	        .setName(SCHEMA_NAME)
	        .setTitle(SCHEMA_NAME)
	        .setVersion(1)
	        .setProperties(Map.of("id", Schema.of("id", STRING), "code", Schema.of("code", STRING)
	                .setMinLength(5)
	                .setMaxLength(5), "name", Schema.of("name", STRING), CONNECTION_ID,
	                Schema.of(CONNECTION_ID, STRING)));

	public static final Store STORE_RECORD = new Store().setAudited(true)
	        .setVersioned(true)
	        .setSoftDelete(true)
	        .setNamespace(CoreConstants.NAMESPACE_CORE)
	        .setName(SCHEMA_NAME)
	        .setWritePermission(CoreConstants.PERMISSION_ADMIN)
	        .setUpdatePermission(CoreConstants.PERMISSION_ADMIN)
	        .setDeletePermission(CoreConstants.PERMISSION_ADMIN)
	        .setUniqueKeys(List.of(new Key().setFields(List.of("name")), new Key().setFields(List.of("code"))))
	        .setForeignKeys(List.of(new ForeignKey().setField(CONNECTION_ID)
	                .setNamespace(Connection.SCHEMA.getNamespace())
	                .setName(Connection.SCHEMA.getName())
	                .setForeignField("id")));

	private String id;
	private String code;
	private String name;
	private String connectionId;
}
