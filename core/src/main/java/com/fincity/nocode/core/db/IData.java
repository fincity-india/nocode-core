package com.fincity.nocode.core.db;

public interface IData {

	public String getTenant();
	public ITable getTable(String namespace, String name);
}
