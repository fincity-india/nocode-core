package com.fincity.nocode.core.system.db.mongo;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.mongo.MongoBase;
import com.fincity.nocode.core.mongo.MongoStore;
import com.fincity.nocode.core.system.model.Store;
import com.fincity.nocode.kirun.engine.json.schema.Schema;

import reactor.core.publisher.Flux;

public class StoreMongoStore extends MongoStore {

	public StoreMongoStore(MongoBase mongoData, Schema schema, Store store, String cName) {
		super(mongoData, schema, store, cName);
	}
	
	public void initialize(List<Store> baseStores) {
		
	}
	
	public Flux<Store> filter(Condition condition) {
		
		return this.filter(condition, (Sort) null);
	}
	
	public Flux<Store> filter(Condition condition, Sort sort) {
		
		return this.getMongoData().getTemplate().find(this.toQuery(condition, sort), Store.class);
	}
}
