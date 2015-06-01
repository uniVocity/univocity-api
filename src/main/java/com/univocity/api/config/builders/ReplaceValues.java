package com.univocity.api.config.builders;

import com.univocity.api.entity.*;

public interface ReplaceValues {

	public ValueReplacement fromFields(String... fieldNames);

	public ValueReplacement fromFields(FieldIdentifier... fieldIdentifiers);

	public UnmatchedReference associatedWith(String sourceEntity, String sourceFieldName);

	public UnmatchedReference associatedWith(String sourceEntity, FieldIdentifier fieldIdentifier);
}
