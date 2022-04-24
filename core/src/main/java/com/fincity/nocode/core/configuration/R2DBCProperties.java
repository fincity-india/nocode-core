package com.fincity.nocode.core.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties.Pool;

import lombok.Data;

@Data
public class R2DBCProperties {

	private String name;
	private boolean generateUniqueName;
	private String url;
	private String username;
	private String password;
	private final Map<String, String> properties = new LinkedHashMap<>();
	private final Pool pool = new Pool();
	private String uniqueName;
}
