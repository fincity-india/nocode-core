package com.fincity.nocode.core.system.schema;

import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.NAME;
import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.NAMESPACE;

import java.util.List;

import com.fincity.nocode.core.db.Key;
import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;

public class CoreSchema {

	public static final Store STORE_RECORD = new Store().setAudited(true).setVersioned(true).setSoftDelete(true)
			.setNamespace(Schema.SCHEMA.getNamespace()).setName(Schema.SCHEMA.getName())
			.setWritePermission(CoreConstants.PERMISSION_DEVELOPER)
			.setUpdatePermission(CoreConstants.PERMISSION_DEVELOPER)
			.setDeletePermission(CoreConstants.PERMISSION_DEVELOPER)
			.setUniqueKeys(List.of(new Key().setFields(List.of(NAMESPACE, NAME))));

	private CoreSchema() {
	}
}
