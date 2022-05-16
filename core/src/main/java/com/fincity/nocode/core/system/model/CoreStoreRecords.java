package com.fincity.nocode.core.system.model;

import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.NAME;
import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.NAMESPACE;

import java.util.List;

import com.fincity.nocode.core.db.Key;
import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.model.FunctionDefinition;

public class CoreStoreRecords {

	public static final Store STORE_RECORD_SCHEMA = new Store().setAudited(true)
	        .setVersioned(true)
	        .setSoftDelete(true)
	        .setNamespace(Schema.SCHEMA.getNamespace())
	        .setName(Schema.SCHEMA.getName())
	        .setWritePermission(CoreConstants.PERMISSION_DEVELOPER)
	        .setUpdatePermission(CoreConstants.PERMISSION_DEVELOPER)
	        .setDeletePermission(CoreConstants.PERMISSION_DEVELOPER)
	        .setUniqueKeys(List.of(new Key().setFields(List.of(NAMESPACE, NAME))));

	public static final Store STORE_RECORD_FUNCTION_DEFINITION = new Store().setAudited(true)
	        .setVersioned(true)
	        .setSoftDelete(true)
	        .setNamespace(FunctionDefinition.SCHEMA.getNamespace())
	        .setName(FunctionDefinition.SCHEMA.getName())
	        .setWritePermission(CoreConstants.PERMISSION_DEVELOPER)
	        .setUpdatePermission(CoreConstants.PERMISSION_DEVELOPER)
	        .setDeletePermission(CoreConstants.PERMISSION_DEVELOPER)
	        .setUniqueKeys(List.of(new Key().setFields(List.of(NAMESPACE, NAME))));

	private CoreStoreRecords() {
	}
}
