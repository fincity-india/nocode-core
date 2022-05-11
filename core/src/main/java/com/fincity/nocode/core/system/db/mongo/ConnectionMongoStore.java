package com.fincity.nocode.core.system.db.mongo;

import org.springframework.data.domain.Sort;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.mongo.MongoBase;
import com.fincity.nocode.core.mongo.MongoStore;
import com.fincity.nocode.core.system.schema.Store;
import com.fincity.nocode.core.system.schema.connection.Connection;
import com.fincity.nocode.kirun.engine.json.schema.Schema;

import reactor.core.publisher.Flux;

public class ConnectionMongoStore extends MongoStore {

	public ConnectionMongoStore(MongoBase mongoData, Schema schema, Store store, String cName) {
		super(mongoData, schema, store, cName);
	}
	
	public Flux<Connection> filter(Condition condition) {
		
	}
	
	public Flux<Connection> filter(Condition condition, Sort sort) {
		
	}
}