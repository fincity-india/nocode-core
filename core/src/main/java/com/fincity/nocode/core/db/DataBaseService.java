package com.fincity.nocode.core.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fincity.nocode.core.mongo.MultitenantMongoConnectionService;
import com.fincity.nocode.core.system.CoreConstants;
import com.fincity.nocode.core.system.tenant.Tenant;

import reactor.core.publisher.Mono;

@Service(DataBaseService.SERVICE_NAME)
public class DataBaseService {

	public static final String SERVICE_NAME = "dataService";

	public static final Logger logger = LoggerFactory.getLogger(DataBaseService.class);

	@Autowired
	private MultitenantMongoConnectionService connectionService;
	
	@Autowired
	private IBase masterData;
	
	private Map<String, IBase> data = new ConcurrentHashMap<>();
	
	public void postConstructor() {
		data.put(masterData.getTenant(), masterData);
	}

	public Mono<IBase> getBase(String tenant) {
		
		if (data.containsKey(tenant)) return Mono.just(data.get(tenant));
		
		IStore masterTenantStore = masterData.getStore(Tenant.SCHEMA);
		
//		tenantTable.find(Condition)
		
		return Mono.just(masterData);
	}

	public Mono<IStore> getStore(String tenant, String namespace, String store) {

		// Don't give the table directly. Check in the data table if there is a different connection that is used.
		// That gives the flexibility for connecting to other databases for just one table.
		return this.getBase(tenant).map(e -> e.getTable(namespace, table));
	}
}
