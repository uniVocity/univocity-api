package com.univocity.api.entity.html;

import com.univocity.api.*;
import com.univocity.api.entity.*;
import com.univocity.api.entity.html.builders.*;

import java.util.*;

public class HtmlEntityConfiguration extends Configuration implements FieldAdder {

	private final String entityName;

	final Map<String, List<HtmlPath>> fields = new LinkedHashMap<String, List<HtmlPath>>();

	HtmlEntityConfiguration() {
		this.entityName = null;
	}

	HtmlEntityConfiguration(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityName(){
		return entityName;
	}

	@Override
	public HtmlPathStart addSilentField(String fieldName) {
		return newField(fieldName, false, true);
	}

	@Override
	public HtmlPathStart addSilentPersistentField(String fieldName) {
		return newField(fieldName, true, true);
	}

	public HtmlPathStart addField(String fieldName){
		return newField(fieldName, false, false);
	}

	public HtmlPathStart addPersistentField(String fieldName){
		return newField(fieldName, true, false);
	}

	private HtmlPathStart newField(String fieldName, boolean persistent, boolean inhibitNewRows){
		HtmlPath pathBuilder = Univocity.provider().newBuilder(HtmlPath.class, this, persistent, inhibitNewRows);
		addPathToField(fieldName, pathBuilder);
		return pathBuilder;
	}

	void addPathToField(String fieldName, HtmlPath path){
		List<HtmlPath> paths = fields.get(fieldName);
		if(paths == null){
			paths = new ArrayList<HtmlPath>();
			fields.put(fieldName, paths);
		}
		paths.add(path);
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
