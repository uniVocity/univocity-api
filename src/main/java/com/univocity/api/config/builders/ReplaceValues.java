package com.univocity.api.config.builders;

public interface ReplaceValues {

	public ValueReplacement fromFields(String... fieldNames);

	public UnmatchedReference associatedWith(String sourceEntity, String sourceFieldName);
}
