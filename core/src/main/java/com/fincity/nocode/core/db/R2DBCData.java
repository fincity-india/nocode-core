package com.fincity.nocode.core.db;

import com.fincity.nocode.core.NocodeException;
import com.fincity.nocode.core.configuration.R2DBCProperties;

public class R2DBCData implements IData {

	public R2DBCData(R2DBCProperties r2dbc) {
		throw new NocodeException(2, "Not implemented");
	}

	@Override
	public ITable getTable(String namespace, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
