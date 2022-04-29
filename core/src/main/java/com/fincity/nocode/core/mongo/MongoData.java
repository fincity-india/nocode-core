package com.fincity.nocode.core.mongo;

import com.fincity.nocode.core.db.IData;
import com.fincity.nocode.core.db.ITable;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class MongoData implements IData {

	private MongoDatabase db;
	private String tenant;

	public MongoData(String tenant, MongoDatabase db) {

		this.db = db;
		this.tenant = tenant;
	}
	
	@Override
	public String getTenant() {
		
		return this.tenant;
	}

	@Override
	public ITable getTable(String namespace, String name) {

		return null;
	}
}
