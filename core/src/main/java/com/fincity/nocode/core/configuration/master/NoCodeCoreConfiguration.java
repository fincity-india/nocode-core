package com.fincity.nocode.core.configuration.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.fincity.nocode.core.NocodeException;
import com.fincity.nocode.core.configuration.MasterDBConfigurationProperties;
import com.fincity.nocode.core.db.IBase;
import com.fincity.nocode.core.mongo.MongoBase;
import com.fincity.nocode.core.mongo.MultitenantMongoConnectionService;

public abstract class NoCodeCoreConfiguration {

	@Autowired
	private MasterDBConfigurationProperties masterDBProps;

	@Autowired
	private MultitenantMongoConnectionService mongoService;

	@Bean(name = "masterData")
	public IBase getMasterData() {

		if (masterDBProps.getMongo() != null) {

			mongoService.createConnection(MultitenantMongoConnectionService.MASTER_TENANT, masterDBProps.getMongo());
			return new MongoBase(MultitenantMongoConnectionService.MASTER_TENANT,
					mongoService.getDatabase(MultitenantMongoConnectionService.MASTER_TENANT));
		} // Add the r2dbc connection.

		throw new NocodeException(1, "Unable to find the master DB configuration");
	}
}
