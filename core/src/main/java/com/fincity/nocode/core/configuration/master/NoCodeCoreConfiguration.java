package com.fincity.nocode.core.configuration.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.fincity.nocode.core.NocodeException;
import com.fincity.nocode.core.configuration.MasterDBConfigurationProperties;
import com.fincity.nocode.core.db.DataBaseConnectionService;
import com.fincity.nocode.core.db.IBase;
import com.fincity.nocode.core.system.CoreConstants;

public abstract class NoCodeCoreConfiguration {

	@Autowired
	private MasterDBConfigurationProperties masterDBProps;

	@Autowired
	private DataBaseConnectionService dbcService;

	@Bean(name = CoreConstants.BASE_MASTER)
	public IBase getMasterStore() {

		if (masterDBProps.getMongo() != null) {
			
			return dbcService.createBase(CoreConstants.TENANT_MASTER, masterDBProps.getMongo());
		} // Add the r2dbc connection.

		throw new NocodeException(1, "Unable to find the master DB configuration");
	}
}
