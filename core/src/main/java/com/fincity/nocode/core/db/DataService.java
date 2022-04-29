package com.fincity.nocode.core.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fincity.nocode.core.mongo.MultitenantMongoConnectionService;
import com.fincity.nocode.kirun.engine.function.namespaces.Namespaces;

import reactor.core.publisher.Mono;

@Service(DataService.SERVICE_NAME)
public class DataService {

	public static final String SERVICE_NAME = "dataService";

	public static final Logger logger = LoggerFactory.getLogger(DataService.class);

	@Autowired
	private MultitenantMongoConnectionService connectionService;
	
	@Autowired
	private IData masterData;
	
	private Map<String, IData> data = new ConcurrentHashMap<>();
	
	public void postConstructor() {
		data.put(masterData.getTenant(), masterData);
	}

	public Mono<IData> getData(String tenant) {
		
		if (data.containsKey(tenant)) return Mono.just(data.get(tenant));
		
		masterData.getTable(Namespaces.SYSTEM, );
		
		return Mono.just(masterData);
	}

	public Mono<ITable> getTable(String tenant, String namespace, String table) {

		return this.getData(tenant).map(e -> e.getTable(namespace, table));
	}
}
