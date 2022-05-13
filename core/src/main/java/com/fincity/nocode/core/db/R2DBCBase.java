package com.fincity.nocode.core.db;

import com.fincity.nocode.core.NocodeException;
import com.fincity.nocode.core.system.model.connection.R2DBCProperties;

public class R2DBCBase { // should implement IData when created to support SQL databases.

	public R2DBCBase(R2DBCProperties r2dbc) {
		throw new NocodeException(2, "Not implemented");
	}
}
