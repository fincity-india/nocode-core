package com.fincity.nocode.core.configuration;

import lombok.Data;

@Data
public class MasterMongoProperties {

	public static final int DEFAULT_PORT = 27017;
	public static final String DEFAULT_URI = "mongodb://localhost/test";

	private String host;
	private Integer port = null;

	private String uri;
	private String database;
	private String authenticationDatabase;

	private String username;
	private char[] password;
	private String replicaSetName;
	private Class<?> fieldNamingStrategy;
	private Boolean autoIndexCreation;
}
