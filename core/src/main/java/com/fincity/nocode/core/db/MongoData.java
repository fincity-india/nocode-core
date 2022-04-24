package com.fincity.nocode.core.db;

import com.mongodb.reactivestreams.client.MongoDatabase;

public class MongoData implements IData {

	private MongoDatabase db;
	private String tenant;

	public MongoData(String tenant, MongoDatabase db) {

		this.db = db;
		this.tenant = tenant;
	}
	

	public ITable getTable(String alias)  {
		
	}
	
	public ITable getTable(String namespace, String name) {
		
	}
}
