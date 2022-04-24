package com.fincity.nocode.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "nocode.master.db")
@Data
public class MasterDBConfigurationProperties {

	private MongoDBCProperties mongo;
	private R2DBCProperties r2dbc;
	
}
