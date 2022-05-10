package com.fincity.nocode.core.db;

import com.fincity.nocode.kirun.engine.json.schema.Schema;

import reactor.core.publisher.Mono;

public interface IBase {

	public Mono<IBase> copy(String tenant);
	public String getTenant();
	public Mono<IStore> getStore(Schema s);
	public Mono<IStore> getStore(String namespace, String store);
}
