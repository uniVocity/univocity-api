package com.univocity.api.entity.html;

import com.univocity.api.*;
import com.univocity.api.entity.*;
import com.univocity.api.entity.html.builders.*;

import java.util.*;

public class HtmlEntityConfiguration extends Configuration {

	private final String entityName;

	private final Map<String, HtmlPath> fields = new LinkedHashMap<String, HtmlPath>();
	private final Map<String, PartialHtmlPathStart> partialPaths = new TreeMap<String, PartialHtmlPathStart>();
	private final Map<String, HtmlGroupStart> groups = new TreeMap<String, HtmlGroupStart>();

	HtmlEntityConfiguration() {
		this.entityName = null;
	}

	HtmlEntityConfiguration(String entityName) {
		this.entityName = entityName;
	}

	public HtmlPath addField(String fieldName){
		HtmlPath pathBuilder = Univocity.provider().newBuilder(HtmlPath.class);
		fields.put(fieldName, pathBuilder);
		return pathBuilder;
	}

	public PartialHtmlPathStart getPartialPath(String pathName){
		return partialPaths.get(pathName);
	}

	public PartialHtmlPathStart newPath(){
		return Univocity.provider().newBuilder(PartialHtmlPathStart.class);
	}

	public PartialHtmlPathStart newPath(String pathName){
		PartialHtmlPathStart builder = newPath();
		partialPaths.put(pathName, builder);
		return builder;
	}

	public HtmlGroupStart newGroup(){
		return Univocity.provider().newBuilder(HtmlGroupStart.class);
	}

	public HtmlGroupStart getGroup(String groupName){
		return groups.get(groupName);
	}

	public HtmlGroupStart newGroup(String groupName){
		HtmlGroupStart builder = newGroup();
		groups.put(groupName, builder);
		return builder;
	}

	@Override
	protected void copyDefaultsFrom(Configuration defaultConfig) {
		HtmlEntityConfiguration defaults = (HtmlEntityConfiguration) defaultConfig;
	}
}
