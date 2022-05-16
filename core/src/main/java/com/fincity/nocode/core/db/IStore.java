package com.fincity.nocode.core.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.db.field.IField;
import com.fincity.nocode.core.db.request.FilterRequest;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.google.gson.JsonObject;

import reactor.core.publisher.Mono;

public interface IStore {

	public Schema getSchema();

	public Mono<JsonObject> create(JsonObject obj);

	public Mono<JsonObject> getById(String id);

	public Mono<Page<JsonObject>> filter(MultiValueMap<String, String> parameters);

	public Mono<Page<JsonObject>> filter(FilterRequest filter);

	public Mono<Page<JsonObject>> filter(Condition condition, Pageable pageable);

	public Mono<JsonObject> update(JsonObject obj);

	public Mono<JsonObject> patch(JsonObject obj);

	public Mono<JsonObject> deleteById(String id);

	public Mono<Integer> deleteByFilter(Condition condition);

	public Mono<Integer> deleteByFilter(FilterRequest filter);

	public IField getField(String fieldName);
}
