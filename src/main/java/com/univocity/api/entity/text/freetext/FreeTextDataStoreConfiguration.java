package com.univocity.api.entity.text.freetext;

import com.univocity.api.entity.custom.*;

import java.util.*;

public class FreeTextDataStoreConfiguration extends DataStoreConfiguration {

	private final Map<String, FreeTextEntityConfiguration> entities = new TreeMap<String, FreeTextEntityConfiguration>();
	private final FreeTextEntityConfiguration defaultEntityConfiguration = new FreeTextEntityConfiguration();
	private final TextPageProvider textPageProvider;

	public FreeTextDataStoreConfiguration(String dataStoreName, TextPageProvider textPageProvider) {
		super(dataStoreName);
		this.textPageProvider = textPageProvider;
	}

	public Set<String> getEntityNames() {
		return new TreeSet<String>(entities.keySet());
	}

	public FreeTextEntityConfiguration configureEntity(String entityName) {
		if (entities.containsKey(entityName)) {
			return entities.get(entityName);
		} else {
			FreeTextEntityConfiguration config = new FreeTextEntityConfiguration(entityName);
			entities.put(entityName, config);
			return config;
		}
	}

	public final TextPageProvider getTextPageProvider() {
		return textPageProvider;
	}


	@Override
	public int getLimitOfRowsLoadedInMemory() {
		return 0;
	}
}
