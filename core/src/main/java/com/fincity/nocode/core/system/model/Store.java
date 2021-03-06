package com.fincity.nocode.core.system.model;

import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.NAME;
import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.NAMESPACE;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.OBJECT;
import static java.util.Map.entry;

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
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Store implements Serializable {

	private static final String CONNECTION_ID = "connectionId";

	private static final long serialVersionUID = 192762479116755294L;

	private static final String SCHEMA_NAME = "Store";

	public static final Schema SCHEMA = new Schema().setType(new SingleType(OBJECT))
	        .setNamespace(CoreConstants.NAMESPACE_CORE)
	        .setName(SCHEMA_NAME)
	        .setVersion(1)
	        .setProperties(Map.ofEntries(entry(NAMESPACE, Schema.ofString(NAMESPACE)),
	                entry("id", Schema.ofString("id")), entry("readPermission", Schema.ofString("readPermission")),
	                entry(NAME, Schema.ofString(NAME)), entry("writePermission", Schema.ofString("writePermission")),
	                entry("updatePermission", Schema.ofString("updatePermission")),
	                entry("deletePermission", Schema.ofString("deletePermission")),
	                entry(CONNECTION_ID, Schema.ofString(CONNECTION_ID)),
	                entry("versioned", Schema.ofBoolean("versioned")),
	                entry("softDelete", Schema.ofBoolean("softDelete")), entry("audited", Schema.ofBoolean("audited")),
	                entry("uniqueKeys", Schema.ofArray("uniqueKeys", Key.SCHEMA)),
	                entry("keys", Schema.ofArray("keys", Key.SCHEMA)),
	                entry("foreignKeys", Schema.ofArray("foreignKeys", ForeignKey.SCHEMA))));

	public static final Store STORE_RECORD = new Store().setAudited(true)
	        .setVersioned(true)
	        .setSoftDelete(true)
	        .setNamespace(CoreConstants.NAMESPACE_CORE)
	        .setName(SCHEMA_NAME)
	        .setWritePermission(CoreConstants.PERMISSION_DEVELOPER)
	        .setUpdatePermission(CoreConstants.PERMISSION_DEVELOPER)
	        .setDeletePermission(CoreConstants.PERMISSION_DEVELOPER)
	        .setUniqueKeys(List.of(new Key().setFields(List.of(NAMESPACE, NAME))))
	        .setForeignKeys(List.of(new ForeignKey().setField(CONNECTION_ID)
	                .setNamespace(Connection.SCHEMA.getNamespace())
	                .setName(Connection.SCHEMA.getName())
	                .setForeignField("id")));

	private String id;
	private String namespace;
	private String name;
	private String readPermission;
	private String writePermission;
	private String updatePermission;
	private String deletePermission;
	private String connectionId;
	private boolean versioned = false;
	private boolean audited = false;
	private boolean softDelete = false;
	private List<Key> uniqueKeys;
	private List<Key> keys;
	private List<ForeignKey> foreignKeys;
}
