package com.fincity.nocode.core.db;

import com.fincity.nocode.kirun.engine.json.schema.Schema;

public interface IBase {

	public String getTenant();
	public IStore getStore(Schema s);
}
