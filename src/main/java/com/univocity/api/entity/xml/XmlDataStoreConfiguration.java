/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.xml;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import com.univocity.api.common.*;
import com.univocity.api.entity.custom.*;
import com.univocity.api.exception.*;

public class XmlDataStoreConfiguration extends DataStoreConfiguration {

	private int limitOfRowsLoadedInMemory = 10000;

	private final FileProvider xmlFile;
	private final ReaderProvider xmlInput;

	private final Map<String, ElementPaths> entities = new TreeMap<String, ElementPaths>();
	private final Map<String, XmlQueryConfiguration> queries = new TreeMap<String, XmlQueryConfiguration>();

	public static class ElementPaths {
		final Set<String> elements = new LinkedHashSet<String>();
		final Set<String> elementsOfList = new LinkedHashSet<String>();
		final Set<String> elementsOfGroup = new LinkedHashSet<String>();

		public final Set<String> getElements() {
			return Collections.unmodifiableSet(elements);
		}

		public final Set<String> getElementsOfList() {
			return Collections.unmodifiableSet(elementsOfList);
		}

		public final Set<String> getElementsOfGroup() {
			return Collections.unmodifiableSet(elementsOfGroup);
		}

	}

	public XmlDataStoreConfiguration(String dataStoreName, File xmlFile) {
		this(dataStoreName, new FileProvider(xmlFile));
	}

	public XmlDataStoreConfiguration(String dataStoreName, File xmlFile, String encoding) {
		this(dataStoreName, new FileProvider(xmlFile, encoding));
	}

	public XmlDataStoreConfiguration(String dataStoreName, File xmlFile, Charset encoding) {
		this(dataStoreName, new FileProvider(xmlFile, encoding));
	}

	public XmlDataStoreConfiguration(String dataStoreName, String pathToXmlFile) {
		this(dataStoreName, new FileProvider(pathToXmlFile));
	}

	public XmlDataStoreConfiguration(String dataStoreName, String pathToXmlFile, String encoding) {
		this(dataStoreName, new FileProvider(pathToXmlFile, encoding));
	}

	public XmlDataStoreConfiguration(String dataStoreName, String pathToXmlFile, Charset encoding) {
		this(dataStoreName, new FileProvider(pathToXmlFile, encoding));
	}

	public XmlDataStoreConfiguration(String dataStoreName, UrlReaderProvider xmlInput) {
		this(dataStoreName, (ReaderProvider) xmlInput);
	}

	public XmlDataStoreConfiguration(String dataStoreName, ReaderProvider xmlInput) {
		super(dataStoreName);
		this.xmlInput = xmlInput;
		this.xmlFile = null;
	}

	private XmlDataStoreConfiguration(String dataStoreName, FileProvider xmlFile) {
		super(dataStoreName);
		this.xmlFile = xmlFile;
		this.xmlInput = null;
	}

	public void includeElement(String entityName, String pathToElement) {
		getPaths(entityName).elements.add(pathToElement);
	}

	public void includeElementsOfList(String entityName, String pathToList) {
		getPaths(entityName).elementsOfList.add(pathToList);
	}

	public void includeElementsOfGroup(String entityName, String pathToElement) {
		getPaths(entityName).elementsOfGroup.add(pathToElement);
	}

	private ElementPaths getPaths(String entityName) {
		ElementPaths paths = entities.get(entityName);
		if (paths == null) {
			paths = new ElementPaths();
			entities.put(entityName, paths);
		}
		return paths;
	}

	public void setLimitOfRowsLoadedInMemory(int limitOfRowsLoadedInMemory) {
		this.limitOfRowsLoadedInMemory = limitOfRowsLoadedInMemory;
	}

	@Override
	public int getLimitOfRowsLoadedInMemory() {
		return limitOfRowsLoadedInMemory;
	}

	public final FileProvider getXmlFile() {
		return xmlFile;
	}

	public final ReaderProvider getXmlInput() {
		return xmlInput;
	}

	public void addQuery(XmlQueryConfiguration query) {
		Args.notNull(query, "XML query configuration");
		int pathCount = query.getQueryResultPaths().elements.size();
		pathCount += query.getQueryResultPaths().elementsOfGroup.size();
		pathCount += query.getQueryResultPaths().elementsOfList.size();
		if (pathCount == 0) {
			throw new IllegalConfigurationException("Configuration of XML query '" + query.getName() + "' must contain a list one path to elements of the expected result");
		}
		this.queries.put(query.getName(), query);
	}

	public final Map<String, ElementPaths> getEntities() {
		return Collections.unmodifiableMap(entities);
	}
	
	public final Map<String, XmlQueryConfiguration> getQueries(){
		return Collections.unmodifiableMap(queries);
	}
}
