package com.fincity.nocode.core.mongo;

import static com.fincity.nocode.kirun.engine.constant.KIRUNConstants.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fincity.nocode.core.db.IBase;
import com.fincity.nocode.core.db.IStore;
import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.core.system.schema.CoreSchema;
import com.fincity.nocode.core.system.schema.Store;
import com.fincity.nocode.core.system.schema.Tenant;
import com.fincity.nocode.core.system.schema.connection.Connection;
import com.fincity.nocode.kirun.engine.constant.KIRUNConstants;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.google.gson.JsonPrimitive;
import com.mongodb.reactivestreams.client.MongoDatabase;

import reactor.core.publisher.Mono;

public class MongoBase implements IBase {

	private MongoDatabase db;
	private String tenant;
	private Map<String, IStore> tables = new ConcurrentHashMap<>();

	public MongoBase(String tenant, MongoDatabase db) {

		this.db = db;
		this.tenant = tenant;

		String cName = this.getCollectionName(Schema.SCHEMA);
		tables.put(cName, new MongoStore(this, Schema.SCHEMA, CoreSchema.STORE_RECORD, cName));

		cName = this.getCollectionName(Store.SCHEMA);
		tables.put(cName, new MongoStore(this, Store.SCHEMA, Store.STORE_RECORD, cName));

		if (tenant.equals(CoreConstants.TENANT_MASTER)) {

			cName = this.getCollectionName(Tenant.SCHEMA);
			tables.put(cName, new MongoStore(this, Tenant.SCHEMA, Tenant.STORE_RECORD, cName));
		}

		cName = this.getCollectionName(Connection.SCHEMA);
		tables.put(cName, new MongoStore(this, Connection.SCHEMA, Connection.STORE_RECORD, cName));
	}

	@Override
	public String getTenant() {

		return this.tenant;
	}

	public MongoDatabase getDb() {
		return db;
	}

	@Override
	public Mono<IStore> getStore(final String namespace, final String storeName) {

		IStore schemaStore = this.getStore(Schema.SCHEMA);
		schemaStore.filter(schemaStore.getField(NAMESPACE).equalTo(new JsonPrimitive(namespace))
				.and(schemaStore.getField(ID).equalTo(new JsonPrimitive(storeName)))).next().map(Schema);
	}

	private String getCollectionName(Schema schema) {
		return this.tenant + "_" + schema.getNamespace() + "_" + schema.getId();
	}

	@Override
	public IStore getStore(Schema s) {

		final var cName = this.getCollectionName(s.getNamespace(), s.getId());

		if (tables.containsKey(cName))
			return this.tables.get(cName);

//		this.getStore(Store.SCHEMA)
	}

	private IStore getStore(String cName, Schema schema, Store store) {

		tables.put(cName, new MongoStore(this, schema, store, cName));
		return tables.get(cName);
	}
}
