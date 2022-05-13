package com.fincity.nocode.core.mongo;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.BOOLEAN;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.DOUBLE;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.FLOAT;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.INTEGER;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.LONG;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.STRING;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.MultiValueMap;

import com.fincity.nocode.core.db.IStore;
import com.fincity.nocode.core.db.Key;
import com.fincity.nocode.core.db.condition.BooleanCondition;
import com.fincity.nocode.core.db.condition.CompoundCondition;
import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.core.db.condition.ConditionType;
import com.fincity.nocode.core.db.condition.InCondition;
import com.fincity.nocode.core.db.condition.NumberCondition;
import com.fincity.nocode.core.db.condition.StringCondition;
import com.fincity.nocode.core.db.field.BooleanField;
import com.fincity.nocode.core.db.field.IField;
import com.fincity.nocode.core.db.field.NumberField;
import com.fincity.nocode.core.db.field.StringField;
import com.fincity.nocode.core.db.field.exception.FieldException;
import com.fincity.nocode.core.db.request.FilterRequest;
import com.fincity.nocode.core.exception.CoreException;
import com.fincity.nocode.core.system.model.Store;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.SingleType;
import com.google.gson.JsonObject;

import reactor.core.publisher.Mono;

public class MongoStore implements IStore {

	private static final Logger logger = LoggerFactory.getLogger(MongoStore.class);

	private MongoBase mongoData;
	private Schema schema;
	private Store store;
	private String collectionName;

	public MongoStore(MongoBase mongoData, Schema schema, Store store, String cName) {

		if (schema == null || schema.getType() == null || schema.getType().getAllowedSchemaTypes().size() != 1
				|| !schema.getType().getAllowedSchemaTypes().contains(SchemaType.OBJECT))
			throw new CoreException("Expected an object type schema and received : " + schema);

		if (cName == null || cName.isBlank())
			throw new CoreException("Collection for a Mongo DB Store cannot be null or blank");

		this.mongoData = mongoData;
		this.schema = schema;
		this.store = store;
		this.collectionName = cName;
	}

	public MongoStore checkInitialization() {

		ReactiveMongoTemplate template = mongoData.getTemplate();

		boolean exists = template.collectionExists(this.collectionName).block();
		if (exists) {
			logger.debug("Collection {} exists for tenant {}", this.collectionName, this.getMongoData().getTenant());
			return this;
		}

		logger.info("Creating a collection {}", this.collectionName);
		template.createCollection(this.collectionName).map(c -> {

			createIndexes(template, store.getUniqueKeys(), true);
			createIndexes(template, store.getKeys(), false);
			return c;
		}).block();

		return this;
	}

	private void createIndexes(ReactiveMongoTemplate temp, List<Key> keys, boolean isUnique) {

		if (keys != null && !keys.isEmpty()) {
			logger.info("Creating unique indexes");
			for (Key key : keys) {

				Index index = new Index();
				for (String field : key.getFields()) {
					index = index.on(field, key.isDescending() ? Sort.Direction.DESC : Sort.Direction.ASC);
				}

				temp.indexOps(this.collectionName).ensureIndex(isUnique ? index.unique() : index);
			}
		}
	}

	public MongoBase getMongoData() {
		return mongoData;
	}

	@Override
	public Schema getSchema() {
		return schema;
	}

	public Store getStore() {
		return store;
	}

	public String getCollectionName() {
		return collectionName;
	}

	@Override
	public IField getField(String fieldName) {

		if (fieldName == null || fieldName.isBlank())
			return null;

		String[] parts = fieldName.split("\\.");
		Schema s = this.schema;

		for (int i = 0; i < parts.length; i++) {
			if (s.getProperties() == null || s.getProperties().isEmpty())
				throw new FieldException(fieldName + " is not found");
			s = s.getProperties().get(parts[i]);
		}

		if (s.getType() instanceof SingleType singleType) {

			SchemaType type = singleType.getAllowedSchemaTypes().iterator().next();
			if (type == BOOLEAN) {
				return new BooleanField(type, fieldName);
			} else if (type == FLOAT || type == DOUBLE || type == INTEGER || type == LONG) {
				return new NumberField(type, fieldName);
			}
		}

		return new StringField(STRING, fieldName);
	}

	protected Query toQuery(Condition condition, Sort sort) {

		Query query = new Query();

		Criteria crt = this.toCriteria(condition);

		if (crt != null)
			query.addCriteria(crt);

		if (sort != null)
			return query.with(sort);

		return query;
	}

	@FunctionalInterface
	private interface ConditionFuntion {
		public Criteria apply(Criteria c, Object o, Condition cond);
	}

	private static final Map<ConditionType, ConditionFuntion> CONDITION_MAPPING = new EnumMap<>(ConditionType.class);

	static {
		CONDITION_MAPPING.put(ConditionType.GREATER_THAN, (c, o, cond) -> c.gt(o));
		CONDITION_MAPPING.put(ConditionType.GREATER_THAN_EQUAL, (c, o, cond) -> c.gte(o));
		CONDITION_MAPPING.put(ConditionType.LESS_THAN, (c, o, cond) -> c.lt(o));
		CONDITION_MAPPING.put(ConditionType.LESS_THAN_EQUAL, (c, o, cond) -> c.lte(o));
		CONDITION_MAPPING.put(ConditionType.NOT_EQUAL, (c, o, cond) -> c.ne(o));
		CONDITION_MAPPING.put(ConditionType.UNARY_NOT, (c, o, cond) -> c.not());
		CONDITION_MAPPING.put(ConditionType.NULL, (c, o, cond) -> c.isNull());
		CONDITION_MAPPING.put(ConditionType.NOT_NULL, (c, o, cond) -> c.isNull().not());

		// See if you have to check the existence of the field when it is a boolean
		// field.
		CONDITION_MAPPING.put(ConditionType.EQUAL, (c, o, cond) -> {
			if (cond instanceof StringCondition sc && sc.isIgnoreCase())
				return c.regex(Pattern.quote(o.toString()), "i");
			return c.is(o);
		});

		CONDITION_MAPPING.put(ConditionType.STARTS_WITH,
				(c, o, cond) -> c.regex("^" + Pattern.quote(o.toString()) + ".*$",
						(cond instanceof StringCondition sc && sc.isIgnoreCase()) ? "i" : null));

		CONDITION_MAPPING.put(ConditionType.CONTAINS,
				(c, o, cond) -> c.regex(".*" + Pattern.quote(o.toString()) + ".*$",
						(cond instanceof StringCondition sc && sc.isIgnoreCase()) ? "i" : null));

		CONDITION_MAPPING.put(ConditionType.ENDS_WITH, (c, o, cond) -> c.regex(".*" + Pattern.quote(o.toString()) + "$",
				(cond instanceof StringCondition sc && sc.isIgnoreCase()) ? "i" : null));
	}

	// Currently only supporting the queries with comparison to some value but not
	// to other fields.
	private Criteria toCriteria(Condition condition) {

		if (condition instanceof NumberCondition n) {

			return CONDITION_MAPPING.getOrDefault(n.getType(), CONDITION_MAPPING.get(ConditionType.EQUAL))
					.apply(Criteria.where(n.getField().getName()), n.getNumberValue(), condition);
		} else if (condition instanceof StringCondition sc) {

			return CONDITION_MAPPING.getOrDefault(sc.getType(), CONDITION_MAPPING.get(ConditionType.EQUAL))
					.apply(Criteria.where(sc.getField().getName()), sc.getValue().getAsString(), condition);
		} else if (condition instanceof BooleanCondition bc) {

			return CONDITION_MAPPING.getOrDefault(bc.getType(), CONDITION_MAPPING.get(ConditionType.EQUAL))
					.apply(Criteria.where(bc.getField().getName()), bc.getBooleanValue(), condition);
		} else if (condition instanceof InCondition ic) {

			return Criteria.where(ic.getField().getName()).in(ic.getValues().stream().map(e -> {
				if (ic.getField() instanceof NumberField)
					return NumberField.toNumber(ic.getField().getSchemaType(), e);
				return e.getAsString();
			}).toList());
		} else if (condition instanceof CompoundCondition cc) {

			if (cc.type == ConditionType.AND)
				new Criteria().andOperator(cc.stream().map(this::toCriteria).filter(Objects::nonNull).toList());
			else
				new Criteria().orOperator(cc.stream().map(this::toCriteria).filter(Objects::nonNull).toList());
		}

		return null;
	}

	@Override
	public Mono<JsonObject> create(JsonObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<JsonObject> getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Page<JsonObject>> filter(MultiValueMap<String, String> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Page<JsonObject>> filter(FilterRequest filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Page<JsonObject>> filter(Condition condition, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<JsonObject> update(JsonObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<JsonObject> patch(JsonObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<JsonObject> deleteById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Integer> deleteByFilter(Condition condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Integer> deleteByFilter(FilterRequest filter) {
		// TODO Auto-generated method stub
		return null;
	}
}
