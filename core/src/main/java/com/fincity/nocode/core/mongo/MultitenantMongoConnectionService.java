package com.fincity.nocode.core.mongo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

import com.fincity.nocode.core.NocodeException;
import com.fincity.nocode.core.system.model.connection.MongoDBCProperties;
import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Service
public class MultitenantMongoConnectionService implements DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(MultitenantMongoConnectionService.class);

	private Map<String, MongoClient> tenants = new HashMap<>();

	private Map<String, ReactiveMongoTemplate> templates = new HashMap<>();

	public ReactiveMongoTemplate createConnection(String tenant, MongoDBCProperties properties) {

		logger.debug("Creating connection for {}", tenant);

		ConnectionString cs = new ConnectionString(properties.getUri());
		try (MongoClient client = MongoClients.create(cs)) {

			logger.debug("Created connection for {}", tenant);

			ReactiveMongoTemplate template = new ReactiveMongoTemplate(client, cs.getDatabase());

			tenants.put(tenant, client);
			templates.put(tenant, template);

			return template;
		} catch (Exception ex) {

			var nce = new NocodeException(3, "Unable to create a connection to DB", ex);

			logger.error("{} - Unable to create a connection to DB for tenant : {}", nce.getExceptionId(), tenant, nce);

			throw nce;
		}
	}

	public MongoClient getMongoClient(String tenant) {

		return tenants.get(tenant);
	}

	public ReactiveMongoTemplate getMongoTemplate(String tenant) {

		return templates.get(tenant);
	}

	@Override
	public void destroy() throws Exception {

		logger.debug("Closing all mongo connections...");

		if (tenants.isEmpty())
			return;

		tenants.forEach((k, v) ->
			{

				logger.debug("Closing connection {} ", k);
				v.close();
			});
	}

}
