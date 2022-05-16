package com.fincity.nocode.core.system.db.mongo;

import org.springframework.data.domain.Sort;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.mongo.MongoBase;
import com.fincity.nocode.core.mongo.MongoStore;
import com.fincity.nocode.core.system.model.Store;
import com.fincity.nocode.core.system.model.Tenant;
import com.fincity.nocode.kirun.engine.json.schema.Schema;

import reactor.core.publisher.Flux;

public class TenantMongoStore extends MongoStore {

	public TenantMongoStore(MongoBase mongoData, Schema schema, Store store, String cName) {
		super(mongoData, schema, store, cName);
	}

	public Flux<Tenant> filter(Condition condition) {

		return this.filter(condition, (Sort) null);
	}

	public Flux<Tenant> filter(Condition condition, Sort sort) {

		return this.getMongoData()
		        .getTemplate()
		        .find(this.toQuery(condition, sort), Tenant.class);
	}
}
