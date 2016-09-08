/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.hierarchical;

import com.univocity.api.common.*;
import com.univocity.api.entity.custom.*;
import com.univocity.api.entity.hierarchical.PathReaderConfiguration.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public abstract class HierarchicalDataStoreConfiguration extends DataStoreConfiguration {

	private int limitOfRowsLoadedInMemory = 5;

	private final FileProvider inputFile;
	private final ReaderProvider inputProvider;
	private boolean pathValidationEnabled = false; //TODO use default configuration class instead.

	private final PathReaderConfiguration entityConfig = new PathReaderConfiguration();

	private final Set<HierarchyQueryConfiguration> queries = new HashSet<HierarchyQueryConfiguration>();

	public HierarchicalDataStoreConfiguration(String dataStoreName, File inputFile) {
		this(dataStoreName, new FileProvider(inputFile));
	}

	public HierarchicalDataStoreConfiguration(String dataStoreName, File inputFile, String encoding) {
		this(dataStoreName, new FileProvider(inputFile, encoding));
	}

	public HierarchicalDataStoreConfiguration(String dataStoreName, File inputFile, Charset encoding) {
		this(dataStoreName, new FileProvider(inputFile, encoding));
	}

	public HierarchicalDataStoreConfiguration(String dataStoreName, String pathToinputFile) {
		this(dataStoreName, new FileProvider(pathToinputFile));
	}

	public HierarchicalDataStoreConfiguration(String dataStoreName, String pathToinputFile, String encoding) {
		this(dataStoreName, new FileProvider(pathToinputFile, encoding));
	}

	public HierarchicalDataStoreConfiguration(String dataStoreName, String pathToinputFile, Charset encoding) {
		this(dataStoreName, new FileProvider(pathToinputFile, encoding));
	}

	public HierarchicalDataStoreConfiguration(String dataStoreName, UrlReaderProvider inputProvider) {
		this(dataStoreName, (ReaderProvider) inputProvider);
	}

	public HierarchicalDataStoreConfiguration(String dataStoreName, ReaderProvider inputProvider) {
		super(dataStoreName);
		this.inputProvider = inputProvider;
		this.inputFile = null;
	}

	public HierarchicalDataStoreConfiguration(String dataStoreName, FileProvider inputFile) {
		super(dataStoreName);
		this.inputFile = inputFile;
		this.inputProvider = null;
	}

	public HierarchicalDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName);
		this.inputFile = null;
		this.inputProvider = null;
	}

	public void setLimitOfRowsLoadedInMemory(int limitOfRowsLoadedInMemory) {
		this.limitOfRowsLoadedInMemory = limitOfRowsLoadedInMemory;
	}

	@Override
	public int getLimitOfRowsLoadedInMemory() {
		return limitOfRowsLoadedInMemory;
	}

	public final FileProvider getInputFile() {
		return inputFile;
	}

	public final ReaderProvider getInputProvider() {
		return inputProvider;
	}

	public void includeElement(String entityName, String pathToElement) {
		entityConfig.includeElement(entityName, pathToElement);
		entityConfig.setPathValidationEnabled(entityName, pathValidationEnabled);
	}

	public void accumulateElementsOfList(String entityName, String pathToList) {
		entityConfig.accumulateElementsOfList(entityName, pathToList);
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

	public void setPathValidationEnabled(boolean validatePaths) {
		pathValidationEnabled = validatePaths;
	}

	public void setPathValidationEnabled(String entityName, boolean validatePaths) {
		entityConfig.setPathValidationEnabled(entityName, validatePaths);
	}

	public boolean isPathValidationEnabled(String entityName) {
		return entityConfig.isPathValidationEnabled(entityName);
	}

	public void addQuery(HierarchyQueryConfiguration query) {
		Args.notNull(query, "Query configuration");

		entityConfig.validateNamesAreUnique(query);
		for (HierarchyQueryConfiguration q : this.queries) {
			q.validateNamesAreUnique(query);
		}

		this.queries.add(query);
	}

	public final Set<HierarchyQueryConfiguration> getQueries() {
		return Collections.unmodifiableSet(queries);
	}

	public final Map<String, ElementPaths> getEntities() {
		return entityConfig.getEntities();
	}
}
