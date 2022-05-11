package com.fincity.nocode.core.system.db.mongo;

import org.springframework.data.domain.Sort;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.mongo.MongoBase;
import com.fincity.nocode.core.mongo.MongoStore;
import com.fincity.nocode.core.system.schema.Store;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.google.gson.JsonObject;

import reactor.core.publisher.Flux;

public class SchemaMongoStore extends MongoStore {

	public SchemaMongoStore(MongoBase mongoData, Schema schema, Store store, String cName) {
		super(mongoData, schema, store, cName);
	}
	
	public Flux<Schema> filter(Condition condition) {
		
	}
	
	public Flux<Schema> filter(Condition condition, Sort sort) {
		
	}
}
