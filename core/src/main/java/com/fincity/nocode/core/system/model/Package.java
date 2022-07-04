package com.fincity.nocode.core.system.model;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.OBJECT;

import java.io.Serializable;
import java.util.Map;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Package implements Serializable {

	private static final long serialVersionUID = -3405648660820112395L;

	private static final String SCHEMA_NAME = "Package";

	public static final Schema SCHEMA = new Schema().setType(new SingleType(OBJECT))
	        .setNamespace(CoreConstants.NAMESPACE_CORE)
	        .setName(SCHEMA_NAME)
	        .setVersion(1)
	        .setProperties(Map.of("id", Schema.ofString("id"), "packageName", Schema.ofString("packageName"),
	                "namespace", Schema.ofString("namespace"), "packageObjectName",
	                Schema.ofString("packageObjectName"), "objectName", Schema.ofString("objectName")));

	public static final Store STORE_RECORD = new Store().setAudited(true)
	        .setVersioned(true)
	        .setSoftDelete(true)
	        .setNamespace(CoreConstants.NAMESPACE_CORE)
	        .setName(SCHEMA_NAME)
	        .setWritePermission(CoreConstants.PERMISSION_ADMIN)
	        .setUpdatePermission(CoreConstants.PERMISSION_ADMIN)
	        .setDeletePermission(CoreConstants.PERMISSION_ADMIN);

	private String id;
	private String packageName;
	private String namespace;
	private String packageObjectName;
	private String objectName;
}
