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
import com.univocity.api.entity.xml.XmlReadingConfiguration.ElementPaths;

public class XmlDataStoreConfiguration extends DataStoreConfiguration {

	private int limitOfRowsLoadedInMemory = 10000;

	private final FileProvider xmlFile;
	private final ReaderProvider xmlInput;
	private boolean pathValidationEnabled = false; //TODO use default configuration class instead.

	private final XmlReadingConfiguration entityConfig = new XmlReadingConfiguration();

	private final Set<XmlQueryConfiguration> queries = new HashSet<XmlQueryConfiguration>();

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
	
	public XmlDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName);
		this.xmlFile = null;
		this.xmlInput = null;
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

	public void includeElement(String entityName, String pathToElement) {
		entityConfig.includeElement(entityName, pathToElement);
		entityConfig.setPathValidationEnabled(entityName, pathValidationEnabled);
	}

	public void includeElementsOfList(String entityName, String pathToList) {
		entityConfig.includeElementsOfList(entityName, pathToList);
		entityConfig.setPathValidationEnabled(entityName, pathValidationEnabled);
	}

	public void includeElementsOfGroup(String entityName, String pathToElement) {
		entityConfig.includeElementsOfGroup(entityName, pathToElement);
		entityConfig.setPathValidationEnabled(entityName, pathValidationEnabled);
	}

	public void setPathValidationEnabled(boolean validatePaths){
		pathValidationEnabled = validatePaths;
	}
	
	public void setPathValidationEnabled(String entityName, boolean validatePaths){
		entityConfig.setPathValidationEnabled(entityName, validatePaths);
	}
	
	public boolean isPathValidationEnabled(String entityName){
		return entityConfig.isPathValidationEnabled(entityName);
	}
	
	public void addQuery(XmlQueryConfiguration query) {
		Args.notNull(query, "XML query configuration");

		entityConfig.validateNamesAreUnique(query);
		for (XmlQueryConfiguration q : this.queries) {
			q.validateNamesAreUnique(query);
		}

		this.queries.add(query);
	}

	public final Set<XmlQueryConfiguration> getQueries() {
		return Collections.unmodifiableSet(queries);
	}

	public final Map<String, ElementPaths> getEntities() {
		return entityConfig.getEntities();
	}
}
