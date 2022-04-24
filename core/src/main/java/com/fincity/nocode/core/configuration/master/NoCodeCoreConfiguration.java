package com.fincity.nocode.core.configuration.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.fincity.nocode.core.NocodeException;
import com.fincity.nocode.core.configuration.MasterDBConfigurationProperties;
import com.fincity.nocode.core.db.IData;
import com.fincity.nocode.core.db.MongoData;
import com.fincity.nocode.core.db.R2DBCData;
import com.fincity.nocode.core.mongo.MultitenantMongoConnectionService;

public abstract class NoCodeCoreConfiguration {

	@Autowired
	private MasterDBConfigurationProperties masterDBProps;

	@Autowired
	private MultitenantMongoConnectionService mongoService;

	@Bean(name = "masterData")
	public IData getMasterData() {

		if (masterDBProps.getMongo() != null) {

			mongoService.createConnection(MultitenantMongoConnectionService.MASTER_TENANT, masterDBProps.getMongo());
			return new MongoData(mongoService.getDatabase(MultitenantMongoConnectionService.MASTER_TENANT));
		} else if (masterDBProps.getR2dbc() != null)
			return new R2DBCData(masterDBProps.getR2dbc());

		throw new NocodeException(1, "Unable to find the master DB configuration");
	}
}
