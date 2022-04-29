package com.fincity.nocode.core.mongo;

import com.fincity.nocode.core.db.ITable;

public class MongoTable implements ITable {
	
	private MongoData mongoData;
	private String table;

	public MongoTable(MongoData mongoData, String table)  {
		
		this.mongoData = mongoData;
		this.table = table;
	}
	
	
}
