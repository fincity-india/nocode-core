package com.fincity.nocode.core.mongo;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.MultiValueMap;

import com.fincity.nocode.core.db.IStore;
import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.db.field.BooleanField;
import com.fincity.nocode.core.db.field.IField;
import com.fincity.nocode.core.db.field.NumberField;
import com.fincity.nocode.core.db.field.StringField;
import com.fincity.nocode.core.db.field.exception.FieldException;
import com.fincity.nocode.core.db.request.FilterRequest;
import com.fincity.nocode.core.exception.CoreException;
import com.fincity.nocode.core.system.schema.Store;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;
import com.google.gson.JsonObject;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MongoStore implements IStore {
	
	private static final Logger logger = LoggerFactory.getLogger(MongoStore.class);

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
		
		long count = Flux.from(mongoData.getDb().listCollectionNames()).filter(c -> c.equals(cName)).count().block();
		
		if (count != 0l)
			return;
		
		mongoData.getDb().createCollection();
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
	public IField getField(String fieldName) {

		if (fieldName == null || fieldName.isBlank())
			return null;

		String[] parts = fieldName.split("\\.");
		Schema s = this.schema;

		for (int i = 0; i < parts.length; i++) {
			if (s.getProperties() == null || s.getProperties().isEmpty())
				throw new FieldException(fieldName + " is not found");
			s = s.getProperties().get(parts[i]);
		}

		if (s.getType() instanceof SingleType singleType) {

			SchemaType type = singleType.getAllowedSchemaTypes().iterator().next();
			if (type == BOOLEAN) {
				return new BooleanField(type, fieldName);
			} else if (type == FLOAT || type == DOUBLE || type == INTEGER || type == LONG) {
				return new NumberField(type, fieldName);
			}
		}

		return new StringField(STRING, fieldName);
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
	public Mono<Page<JsonObject>> filter(MultiValueMap<String, String> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Page<JsonObject>> filter(FilterRequest filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Page<JsonObject>> filter(Condition condition, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<JsonObject> filter(Condition condition, Sort sort) {
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
	public Mono<Integer> deleteByFilter(Condition condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Integer> deleteByFilter(FilterRequest filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<JsonObject> filter(Condition condition) {
		// TODO Auto-generated method stub
		return null;
	}

}
