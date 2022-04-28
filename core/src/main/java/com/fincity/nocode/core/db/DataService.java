package com.fincity.nocode.core.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service(DataService.SERVICE_NAME)
public class DataService {

	public static final String SERVICE_NAME = "dataService";

	public static final Logger logger = LoggerFactory.getLogger(DataService.class);

	@Autowired
	private IData masterData;
	
	@Autowired

	public Mono<IData> getData(String tenant) {

		return Mono.just(masterData);
	}

	public Mono<ITable> getTable(String tenant, String namespace, String table) {

		return this.getData(tenant).map(e -> e.getTable(namespace, table));
	}
}
