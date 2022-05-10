package com.fincity.nocode.core.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fincity.nocode.core.NocodeException;
import com.fincity.nocode.core.mongo.MongoBase;
import com.fincity.nocode.core.mongo.MultitenantMongoConnectionService;
import com.fincity.nocode.core.system.schema.connection.IConnectionProperties;
import com.fincity.nocode.core.system.schema.connection.MongoDBCProperties;
import com.fincity.nocode.core.system.schema.connection.R2DBCProperties;

@Service(DataBaseConnectionService.SERVICE_NAME)
public class DataBaseConnectionService {

	public static final String SERVICE_NAME = "databaseConnectionSevice";

	@Autowired
	private MultitenantMongoConnectionService mongoService;

	public IBase createBase(String tenant, IConnectionProperties properties) {

		if (properties instanceof MongoDBCProperties mongoProps) {

			return new MongoBase(tenant, mongoService.createConnection(tenant, mongoProps));
		} else if (properties instanceof R2DBCProperties) {
			// here goes implementation for r2dbc like mysql
		}

		throw new NocodeException(4, "Unable to find the type of connection");
	}
}
