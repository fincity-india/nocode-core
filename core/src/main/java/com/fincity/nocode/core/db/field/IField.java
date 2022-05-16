package com.fincity.nocode.core.db.field;

import java.io.Serializable;
import java.util.List;

import com.fincity.nocode.core.db.condition.Condition;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.google.gson.JsonPrimitive;

public interface IField extends Serializable {

	public String getName();

	public SchemaType getSchemaType();

	public Condition equalTo(JsonPrimitive value);

	public Condition notEqualTo(JsonPrimitive value);

	public Condition lessThan(JsonPrimitive value);

	public Condition lessThanOrEqual(JsonPrimitive value);

	public Condition greaterThan(JsonPrimitive value);

	public Condition greaterThanOrEqual(JsonPrimitive value);

	public Condition equalTo(IField value);

	public Condition notEqualTo(IField value);

	public Condition lessThan(IField value);

	public Condition lessThanOrEqual(IField value);

	public Condition greaterThan(IField value);

	public Condition greaterThanOrEqual(IField value);

	public Condition in(List<JsonPrimitive> values);

	public Condition in(JsonPrimitive... values);

	public Condition isNull();

	public Condition isNotNull();
}
