package com.fincity.nocode.core.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.MultiValueMap;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.google.gson.JsonObject;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStore {
	
	public Schema getSchema();

	public Mono<JsonObject> create(JsonObject obj);

	public Mono<JsonObject> getById(String id);

	public Mono<Page<JsonObject>> filter(MultiValueMap<String, String> parameters);
	
	public Mono<Page<JsonObject>> filter(JsonObject filter);
	
	public Mono<Page<JsonObject>> filter(Condition condition, Pageable pageable);
	
	public Flux<JsonObject> filter(Condition condition, Sort sort);

	public Mono<JsonObject> update(JsonObject obj);

	public Mono<JsonObject> patch(JsonObject obj);

	public Mono<JsonObject> deleteById(String id);

	public Mono<Integer> deleteByFilter(Condition condition);

	public Mono<Integer> deleteByFilter(JsonObject filter);
}
