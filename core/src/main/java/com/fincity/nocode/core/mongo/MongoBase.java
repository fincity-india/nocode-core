package com.fincity.nocode.core.mongo;

import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.NAME;
import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.NAMESPACE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.fincity.nocode.core.db.IBase;
import com.fincity.nocode.core.db.IStore;
import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.core.system.db.mongo.ConnectionMongoStore;
import com.fincity.nocode.core.system.db.mongo.SchemaMongoStore;
import com.fincity.nocode.core.system.db.mongo.StoreMongoStore;
import com.fincity.nocode.core.system.db.mongo.TenantMongoStore;
import com.fincity.nocode.core.system.schema.CoreSchema;
import com.fincity.nocode.core.system.schema.Store;
import com.fincity.nocode.core.system.schema.Tenant;
import com.fincity.nocode.core.system.schema.connection.Connection;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.google.gson.JsonPrimitive;

import reactor.core.publisher.Mono;

public class MongoBase implements IBase {

	private ReactiveMongoTemplate template;
	private String tenant;
	private Map<String, IStore> tables = new ConcurrentHashMap<>();

	public MongoBase(String tenant, ReactiveMongoTemplate template) {

		this.template = template;
		this.tenant = tenant;

		String cName = this.getCollectionName(Schema.SCHEMA);
		tables.put(cName,
				new SchemaMongoStore(this, Schema.SCHEMA, CoreSchema.STORE_RECORD, cName).checkInitialization());

		cName = this.getCollectionName(Connection.SCHEMA);
		tables.put(cName, new ConnectionMongoStore(this, Connection.SCHEMA, Connection.STORE_RECORD, cName)
				.checkInitialization());

		cName = this.getCollectionName(Store.SCHEMA);
		tables.put(cName, new StoreMongoStore(this, Store.SCHEMA, Store.STORE_RECORD, cName).checkInitialization());

		if (tenant.equals(CoreConstants.TENANT_MASTER)) {

			cName = this.getCollectionName(Tenant.SCHEMA);
			tables.put(cName,
					new TenantMongoStore(this, Tenant.SCHEMA, Tenant.STORE_RECORD, cName).checkInitialization());
		}
	}

	@Override
	public String getTenant() {

		return this.tenant;
	}

	public ReactiveMongoTemplate getTemplate() {
		return template;
	}

	@Override
	public Mono<IStore> getStore(final String namespace, final String storeName) {

		final String cName = this.getCollectionName(namespace, storeName);

		SchemaMongoStore schemaStore = (SchemaMongoStore) this.tables.get(this.getCollectionName(Schema.SCHEMA));

		Mono<Schema> schema = schemaStore.filter(schemaStore.getField(NAMESPACE).equalTo(new JsonPrimitive(namespace))
				.and(schemaStore.getField(NAME).equalTo(new JsonPrimitive(storeName)))).next();

		StoreMongoStore storeStore = (StoreMongoStore) this.tables.get(this.getCollectionName(Store.SCHEMA));

		Mono<Store> store = storeStore.filter(storeStore.getField(NAMESPACE).equalTo(new JsonPrimitive(namespace))
				.and(storeStore.getField(NAME).equalTo(new JsonPrimitive(storeName)))).next();

		return schema.flatMap(sch -> store.map(sto -> new MongoStore(this, sch, sto, cName).checkInitialization()));
	}

	private String getCollectionName(String namespace, String storeName) {
		return this.tenant + "_" + namespace + "_" + storeName;
	}

	private String getCollectionName(Schema schema) {
		return this.tenant + "_" + schema.getNamespace() + "_" + schema.getName();
	}

	@Override
	public Mono<IStore> getStore(Schema s) {

		final String cName = this.getCollectionName(s);

		if (tables.containsKey(cName))
			return Mono.just(this.tables.get(cName));

		StoreMongoStore storeStore = (StoreMongoStore) this.tables.get(this.getCollectionName(Store.SCHEMA));

		Mono<Store> store = storeStore
				.filter(storeStore.getField(NAMESPACE).equalTo(new JsonPrimitive(s.getNamespace()))
						.and(storeStore.getField(NAME).equalTo(new JsonPrimitive(s.getName()))))
				.next();

		return store.map(e -> this.getStore(cName, s, e));
	}

	private IStore getStore(String cName, Schema schema, Store store) {

		tables.put(cName, new MongoStore(this, schema, store, cName).checkInitialization());
		return tables.get(cName);
	}

	@Override
	public Mono<IBase> copy(String tenant) {

		return Mono.just(new MongoBase(tenant, this.template));
	}
}
