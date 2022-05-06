package com.fincity.nocode.core.system.schema;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.BOOLEAN;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.LONG;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.OBJECT;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.STRING;
import static java.util.Map.entry;

import java.io.Serializable;
import java.util.Map;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;

import lombok.Data;

@Data
public class Store implements Serializable {
	
	private static final long serialVersionUID = 192762479116755294L;
	
	private static final String SCHEMA_NAME = "Store";

	public static final Schema SCHEMA = new Schema().setType(new SingleType(OBJECT))
			.setNamespace(CoreConstants.NAMESPACE_CORE).setId(SCHEMA_NAME).setTitle(SCHEMA_NAME)
			.setVersion(1)
			.setProperties(Map.ofEntries(
					entry("namespace", Schema.of("namespace", STRING)),
					entry("id", Schema.of("id", STRING)),
					entry("readPermission", Schema.of("readPermission", STRING)),
					entry("writePermission", Schema.of("writePermission", STRING)),
					entry("updatePermission", Schema.of("updatePermission", STRING)),
					entry("deletePermission", Schema.of("deletePermission", STRING)),
					entry("versioned", Schema.of("versioned", BOOLEAN)),
					entry("audited", Schema.of("audited", BOOLEAN)),
					entry("createdAt", Schema.of("createdAt", LONG)),
					entry("createdBy", Schema.of("createdBy", STRING)),
					entry("updatedAt", Schema.of("updatedAt", LONG)),
					entry("updatedBy", Schema.of("updatedBy", STRING))));
	
	private String namespace;
	private String id;
	private String readPermission;
	private String writePermission;
	private String updatePermission;
	private String deletePermission;
	private boolean versioned = false;
	private boolean audited = false;
	private Long createdAt;
	private String createdBy;
	private Long updatedAt;
	private String updatedBy;
}
