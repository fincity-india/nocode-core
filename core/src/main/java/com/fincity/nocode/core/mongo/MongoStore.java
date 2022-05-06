package com.fincity.nocode.core.mongo;

import org.springframework.data.domain.Page;

import com.fincity.nocode.core.db.IStore;
import com.fincity.nocode.core.exception.CoreException;
import com.fincity.nocode.core.system.schema.Store;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.google.gson.JsonObject;

import reactor.core.publisher.Mono;

public class MongoStore implements IStore {

	private MongoBase mongoData;
	private Schema schema;
	private Store store;
	private String collectionName;

	public MongoStore(MongoBase mongoData, Schema schema, Store store, String cName) {

		if (schema == null || schema.getType() == null || schema.getType().getAllowedSchemaTypes().size() != 1
				|| !schema.getType().getAllowedSchemaTypes().contains(SchemaType.OBJECT))
			throw new CoreException("Expected an object type schema and received : " + schema);
		
		if (cName == null || cName.isBlank())
			throw new CoreException("Collection for a Mongo DB Store cannot be null or blank");

		this.mongoData = mongoData;
		this.schema = schema;
		this.store = store;
		this.collectionName = cName;
	}

	public MongoBase getMongoData() {
		return mongoData;
	}

	@Override
	public Schema getSchema() {
		return schema;
	}

	public Store getStore() {
		return store;
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
