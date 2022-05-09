package com.fincity.nocode.core.system.schema;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;

public class CoreSchema {

	public static final Store STORE_RECORD = new Store().setAudited(true).setVersioned(true).setSoftDelete(true)
			.setNamespace(Schema.SCHEMA.getNamespace()).setName(Schema.SCHEMA.getId())
			.setWritePermission(CoreConstants.PERMISSION_DEVELOPER)
			.setUpdatePermission(CoreConstants.PERMISSION_DEVELOPER)
			.setDeletePermission(CoreConstants.PERMISSION_DEVELOPER);

	private CoreSchema() {
	}
}
