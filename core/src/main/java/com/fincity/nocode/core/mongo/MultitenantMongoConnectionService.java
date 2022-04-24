package com.fincity.nocode.core.mongo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import com.fincity.nocode.core.NocodeException;
import com.fincity.nocode.core.configuration.MongoDBCProperties;
import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

@Service
public class MultitenantMongoConnectionService implements DisposableBean {

	public static final String MASTER_TENANT = "master";

	private static final Logger logger = LoggerFactory.getLogger(MultitenantMongoConnectionService.class);

	private Map<String, MongoClient> tenants = new HashMap<>();

	private Map<String, MongoDatabase> databases = new HashMap<>();

	public MongoClient createConnection(String tenant, MongoDBCProperties properties) {

		logger.debug("Creating connection for {}", tenant);

		ConnectionString cs = new ConnectionString(properties.getUri());
		try (MongoClient client = MongoClients.create(cs)) {

			logger.debug("Created connection for {}", tenant);

			MongoDatabase db = client.getDatabase(cs.getDatabase());

			tenants.put(tenant, client);
			databases.put(tenant, db);

			return client;
		} catch (Exception ex) {

			var nce = new NocodeException(3, "Unable to create a connection to DB", ex);

			logger.error("{} - Unable to create a connection to DB for tenant : {}", nce.getExceptionId(), tenant, nce);

			throw nce;
		}
	}

	public MongoDatabase getDatabase(String tenant) {

		return databases.get(tenant);
	}

	@Override
	public void destroy() throws Exception {

		logger.debug("Closing all mongo connections...");

		if (tenants.isEmpty())
			return;

		tenants.forEach((k, v) -> {

			logger.debug("Closing connection {} ", k);
			v.close();
		});
	}

}
