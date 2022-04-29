package com.fincity.nocode.core.configuration;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties.Pool;

import com.fincity.nocode.kirun.engine.json.schema.Schema;

import lombok.Data;

@Data
public class R2DBCProperties implements Serializable{

	private static final long serialVersionUID = 5155375924411836727L;
	
	public static final Schema SCHEMA = new Schema(); 
	
	private String name;
	private boolean generateUniqueName;
	private String url;
	private String username;
	private String password;
	private final Map<String, String> properties = new LinkedHashMap<>();
	private final Pool pool = new Pool(); // NOSONAR - Pool is from R2DBC Properties it is not serialised.
	private String uniqueName;
}
