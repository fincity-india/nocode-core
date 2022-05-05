package com.fincity.nocode.core.db;

import org.springframework.data.domain.Page;

import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.google.gson.JsonObject;

import reactor.core.publisher.Mono;

public interface IStore {
	
	public Schema getSchema();

	public Mono<JsonObject> create(JsonObject obj);

	public Mono<JsonObject> getById(String id);

	public Mono<Page<JsonObject>> filter();

	public Mono<JsonObject> update(JsonObject obj);

	public Mono<JsonObject> patch(JsonObject obj);

	public Mono<JsonObject> deleteById(String id);

	public Mono<Integer> deleteByFilter();
}
