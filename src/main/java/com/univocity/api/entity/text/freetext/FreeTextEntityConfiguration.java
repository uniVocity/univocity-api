package com.univocity.api.entity.text.freetext;

import com.univocity.api.entity.*;

import java.util.*;

public class FreeTextEntityConfiguration extends Configuration {

	private final String entityName;
	private final LinkedHashMap<String, FreeTextFieldConfiguration> fields = new LinkedHashMap<String, FreeTextFieldConfiguration>();

	FreeTextEntityConfiguration(){
		this.entityName = null;
	}

	FreeTextEntityConfiguration(String entityName){
		this.entityName = entityName;
	}

	public String getEntityName(){
		return entityName;
	}

	public Set<String> getFieldNames(){
		return Collections.unmodifiableSet(fields.keySet());
	}

	@Override
	protected void copyDefaultsFrom(Configuration defaults) {
		FreeTextEntityConfiguration config = (FreeTextEntityConfiguration)defaults;
	}

	public FreeTextFieldConfiguration configureField(String fieldName){
		FreeTextFieldConfiguration field = fields.get(fieldName);
		if(field == null) {
			field = new FreeTextFieldConfiguration();
			fields.put(fieldName, field);
		}
		return field;
	}

}
