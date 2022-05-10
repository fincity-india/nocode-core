package com.fincity.nocode.core.db;

import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.ID;
import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.NAME;
import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.NAMESPACE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.core.system.schema.Store;
import com.fincity.nocode.core.system.schema.Tenant;
import com.fincity.nocode.core.system.schema.connection.Connection;
import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;

import reactor.core.publisher.Mono;

@Service(DataBaseService.SERVICE_NAME)
public class DataBaseService {

	public static final String SERVICE_NAME = "dataService";

	public static final Logger logger = LoggerFactory.getLogger(DataBaseService.class);

	@Autowired
	private DataBaseConnectionService connectionService;

	@Autowired
	@Qualifier(CoreConstants.BASE_MASTER)
	private IBase masterBase;

	private Map<String, IBase> bases = new ConcurrentHashMap<>();

	public void postConstructor() {
		bases.put(masterBase.getTenant(), masterBase);
	}

	public Mono<IBase> getBase(String tenant) {

		if (bases.containsKey(tenant))
			return Mono.just(bases.get(tenant));

		Mono<IStore> mTenantStore = masterBase.getStore(Tenant.SCHEMA);

		Mono<IStore> mConnectionStore = masterBase.getStore(Connection.SCHEMA);

		Gson gson = new Gson();

		return mTenantStore
				// Get Tenant Object
				.flatMap(tenantStore -> tenantStore
						.filter(tenantStore.getField("code").equalTo(new JsonPrimitive(tenant))).next())
				.map(jTenant -> gson.fromJson(jTenant, Tenant.class))
				// Get Connection Id
				.map(Tenant::getConnectionId)
				.flatMap(cid -> getBaseFromConnectionId(tenant, mConnectionStore, cid, masterBase)).map(base -> {
					this.bases.put(tenant, base);
					return base;
				});

	}

	private Mono<IBase> getBaseFromConnectionId(String tenant, Mono<IStore> mConnectionStore, String cid,
			IBase baseTenant) {

		if (cid == null)
			return baseTenant.copy(tenant);

		final String tenant_cid = tenant + "_" + cid;
		if (this.bases.containsKey(tenant_cid))
			return Mono.just(this.bases.get(tenant_cid));

		return mConnectionStore
				.flatMap(connectionStore -> connectionStore
						.filter(connectionStore.getField(ID).equalTo(new JsonPrimitive(cid))).next())
				.map(Connection::from)
				// Create Connection based on the properties
				.map(connection -> connectionService.createBase(tenant, connection.getProps())).map(base -> {
					this.bases.put(tenant_cid, base);
					return base;
				});
	}

	public Mono<IStore> getStore(String tenant, String namespace, String storeName) {

		Mono<IBase> mTenantBase = this.getBase(tenant);

		Gson gson = new Gson();

		return mTenantBase.flatMap(b -> b.getStore(Store.SCHEMA))
				.flatMap(s -> s.filter(s.getField(NAMESPACE).equalTo(new JsonPrimitive(namespace))
						.and(s.getField(NAME).equalTo(new JsonPrimitive(storeName)))).next())
				.map(jStore -> gson.fromJson(jStore, Store.class)).flatMap(sStore -> {

					if (sStore.getConnectionId() == null)
						return mTenantBase;

					return mTenantBase.flatMap(tenantBase -> this.getBaseFromConnectionId(tenant,
							tenantBase.getStore(Connection.SCHEMA), sStore.getConnectionId(), tenantBase));
				}).flatMap(base -> base.getStore(namespace, storeName));
	}
}
