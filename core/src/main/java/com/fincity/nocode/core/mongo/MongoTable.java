package com.fincity.nocode.core.mongo;

import org.springframework.data.domain.Page;

import com.fincity.nocode.core.db.ITable;
import com.google.gson.JsonObject;

import reactor.core.publisher.Mono;

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

	@Override
	public Mono<JsonObject> create(JsonObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<JsonObject> getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Page<JsonObject>> filter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<JsonObject> update(JsonObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<JsonObject> patch(JsonObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<JsonObject> deleteById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Integer> deleteByFilter() {
		// TODO Auto-generated method stub
		return null;
	}
}
