package com.fincity.nocode.core.db;

import com.fincity.nocode.core.NocodeException;
import com.fincity.nocode.core.configuration.R2DBCProperties;

public class R2DBCData { // should implement IData when created to support SQL databases.

	public R2DBCData(R2DBCProperties r2dbc) {
		throw new NocodeException(2, "Not implemented");
	}
}
