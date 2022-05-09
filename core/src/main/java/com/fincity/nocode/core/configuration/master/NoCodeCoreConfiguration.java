package com.fincity.nocode.core.configuration.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.fincity.nocode.core.NocodeException;
import com.fincity.nocode.core.configuration.MasterDBConfigurationProperties;
import com.fincity.nocode.core.db.IBase;
import com.fincity.nocode.core.mongo.MongoBase;
import com.fincity.nocode.core.mongo.MultitenantMongoConnectionService;
import com.fincity.nocode.core.system.CoreConstants;

public abstract class NoCodeCoreConfiguration {

	@Autowired
	private MasterDBConfigurationProperties masterDBProps;

	@Autowired
	private MultitenantMongoConnectionService mongoService;

	@Bean(name = CoreConstants.BASE_MASTER)
	public IBase getMasterStore() {

		if (masterDBProps.getMongo() != null) {

			mongoService.createConnection(CoreConstants.TENANT_MASTER, masterDBProps.getMongo());
			return new MongoBase(CoreConstants.TENANT_MASTER, mongoService.getDatabase(CoreConstants.TENANT_MASTER));
		} // Add the r2dbc connection.

		throw new NocodeException(1, "Unable to find the master DB configuration");
	}
}
