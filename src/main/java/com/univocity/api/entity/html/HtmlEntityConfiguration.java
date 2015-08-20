package com.univocity.api.entity.html;

import com.univocity.api.*;
import com.univocity.api.entity.*;
import com.univocity.api.entity.html.builders.*;

import java.util.*;

public class HtmlEntityConfiguration extends Configuration {

	private final String entityName;

	final Map<String, HtmlPath> fields = new LinkedHashMap<String, HtmlPath>();

	HtmlEntityConfiguration() {
		this.entityName = null;
	}

	HtmlEntityConfiguration(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityName(){
		return entityName;
	}

	public HtmlPathStart addField(String fieldName){
		if(fields.keySet().contains(fieldName)){
			throw new IllegalArgumentException("Field cannot add duplicate field name to HTML entity " + entityName);
		}
		HtmlPath pathBuilder = Univocity.provider().newBuilder(HtmlPath.class, this);
		fields.put(fieldName, pathBuilder);
		return pathBuilder;
	}


	public PartialHtmlPathStart newPath(){
		return Univocity.provider().newBuilder(PartialHtmlPathStart.class, this);
	}

	public HtmlGroupStart newGroup(){
		return Univocity.provider().newBuilder(HtmlGroupStart.class, this);
	}

	@Override
	protected void copyDefaultsFrom(Configuration defaultConfig) {
		HtmlEntityConfiguration defaults = (HtmlEntityConfiguration) defaultConfig;
	}
}
