package com.univocity.api.entity.html;

import com.univocity.api.entity.custom.*;

import java.util.*;

public class HtmlDataStoreConfiguration extends DataStoreConfiguration {

	private final Map<String, HtmlEntityConfiguration> entities = new TreeMap<String, HtmlEntityConfiguration>();
	private final HtmlEntityConfiguration defaultEntityConfiguration = new HtmlEntityConfiguration();



	public HtmlDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName);
	}

	public HtmlEntityConfiguration configureEntity(String entityName){
		if(entities.containsKey(entityName)){
			return entities.get(entityName);
		} else{
			HtmlEntityConfiguration config = new HtmlEntityConfiguration(entityName);
			entities.put(entityName, config);
			return config;
		}
	}

	@Override
	public int getLimitOfRowsLoadedInMemory() {
		return 0;
	}
}
