package com.fincity.nocode.core.db;

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
import static com.fincity.nocode.kirun.engine.constant.KIRunConstants.*;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
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

	private Map<String, IBase> data = new ConcurrentHashMap<>();

	public void postConstructor() {
		data.put(masterBase.getTenant(), masterBase);
	}

	public Mono<IBase> getBase(String tenant) {

		if (data.containsKey(tenant))
			return Mono.just(data.get(tenant));

		Mono<IStore> mtStore = masterBase.getStore(Tenant.SCHEMA);

		Mono<IStore> mcStore = masterBase.getStore(Connection.SCHEMA);

		Gson gson = new Gson();

		return mtStore
				// Get Tenant Object
				.flatMap(tenantStore -> tenantStore
						.filter(tenantStore.getField("code").equalTo(new JsonPrimitive(tenant))).next())
				.map(jTenant -> gson.fromJson(jTenant, Tenant.class))
				// Get Connection Id
				.map(Tenant::getConnectionId)
				.flatMap(cid -> getBaseFromConnectionId(tenant, mcStore, cid));

	}

	private Mono<IBase> getBaseFromConnectionId(String tenant, Mono<IStore> mcStore, String cid) {

		if (cid == null)
			return masterBase.copy(tenant);

		return mcStore.flatMap(connectionStore -> connectionStore
				// Get Connection Object
				.filter(connectionStore.getField(KIRunConstants.ID).equalTo(new JsonPrimitive(cid))).next())
				.map(Connection::from)
				// Create Connection based on the properties
				.map(conn -> connectionService.createBase(tenant, conn.getProps()));
	}

	public Mono<IStore> getStore(String tenant, String namespace, String storeName) {
		
		Mono<IBase> tenantBase = this.getBase(tenant);

		tenantBase.flatMap(b -> b.getStore(Store.SCHEMA))
			.map(s -> s.filter(s.getField(NAMESPACE).equalTo(new JsonPrimitive(namespace)).and(s.getField(NAME).equalTo(new JsonPrimitive(storeName)))).next())
			.map
		
		return .map(e -> e.getStore(namespace, store));
	}
}
