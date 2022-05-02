package com.fincity.nocode.core.mongo;

import com.fincity.nocode.core.db.ITable;

public class MongoTable implements ITable {

	private MongoData mongoData;
	private String table;
	private String namespace;
	private String collectionName;

	public MongoTable(MongoData mongoData, String namespace, String table, String cName) {

		this.mongoData = mongoData;
		this.table = table;
		this.namespace = namespace;
		this.collectionName = cName;
	}

	public MongoData getMongoData() {
		return mongoData;
	}

	public String getTable() {
		return table;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getCollectionName() {
		return collectionName;
	}
}
