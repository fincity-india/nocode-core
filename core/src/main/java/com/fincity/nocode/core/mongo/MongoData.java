package com.fincity.nocode.core.mongo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fincity.nocode.core.db.IData;
import com.fincity.nocode.core.db.ITable;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class MongoData implements IData {

	private MongoDatabase db;
	private String tenant;
	private Map<String, ITable> tables = new ConcurrentHashMap<>();

	public MongoData(String tenant, MongoDatabase db) {

		this.db = db;
		this.tenant = tenant;
	}

	@Override
	public String getTenant() {

		return this.tenant;
	}
	
	public MongoDatabase getDb() {
		return db;
	}

	@Override
	public ITable getTable(final String namespace, final String name) {

		final var cName = this.getCollectionName(namespace, name);
		tables.computeIfAbsent(cName, k -> tables.put(cName, new MongoTable(this, namespace, name, k)));

		return tables.get(cName);
	}

	private String getCollectionName(String namespace, String name) {
		return this.tenant + "_" + namespace + "_" + name;
	}
}
