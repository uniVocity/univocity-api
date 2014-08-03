/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import com.univocity.api.common.*;
import com.univocity.api.entity.custom.*;

/**
 * This is the parent class for all configuration classes used by uniVocity text-based data stores.
 * It provides essential configuration settings for managing inputs and outputs of text-based data entities.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 * @param <T> the configuration class that manages a specific text-based data entity.
 */
public abstract class TextDataStoreConfiguration<T extends TextEntityConfiguration<?>> extends DataStoreConfiguration {

	private final Set<FileProvider> fileDirectories = new HashSet<FileProvider>();
	private final Map<String, ReaderProvider> entityReaders = new TreeMap<String, ReaderProvider>();
	private final Map<String, WriterProvider> entityWriters = new TreeMap<String, WriterProvider>();
	private final Map<String, FileProvider> namedEntityFiles = new TreeMap<String, FileProvider>();
	private final Set<FileProvider> unnamedEntityFiles = new HashSet<FileProvider>();

	private final Map<String, T> entityConfigurations = new TreeMap<String, T>();

	private int maxRowsInMemory = 10000;

	/**
	 * This object contains the default configurations for all data entities in this data store.
	 */
	private final T defaultEntityConfiguration;

	/**
	 * Initializes the configuration object of a text-based data store.
	 *
	 * @param dataStoreName the name of the data store.
	 * @param defaultEntityConfiguration an object with default configurations for data entities associated with this data store.
	 */
	public TextDataStoreConfiguration(String dataStoreName, T defaultEntityConfiguration) {
		super(dataStoreName);
		Args.notNull(defaultEntityConfiguration, "Default configuration settings for entities of data store " + dataStoreName);
		this.defaultEntityConfiguration = defaultEntityConfiguration;
	}

	/**
	 * Adds a collection of files to this data store. Each file will be used as an individual data entity for reading/writing.
	 * <p>The file names will be used as entity names.
	 * <p><i><b>Note: </b>As the encoding of these files is not provided, the default system encoding will be used.</i>
	 * @param files the collection of files to use as data entities of this data store.
	 */
	public final void addEntities(Collection<File> files) {
		addEntities(files, (Charset) null);
	}

	/**
	 * Adds a collection of files to this data store. Each file will be used as an individual data entity for reading/writing.
	 * <p>The file names will be used as entity names.
	 * @param files the collection of files to use as data entities of this data store.
	 * @param encoding the encoding to be used when handling the provided files.
	 */
	public final void addEntities(Collection<File> files, Charset encoding) {
		if (files == null || files.isEmpty()) {
			throw new IllegalArgumentException("List of files cannot be null or empty");
		}
		for (File file : files) {
			addEntity(file, encoding);
		}
	}

	/**
	 * Adds a collection of files to this data store. Each file will be used as an individual data entity for reading/writing.
	 * <p>The file names will be used as entity names.
	 * @param files the collection of files to use as data entities of this data store.
	 * @param encoding the encoding to be used when handling the provided files.
	 */
	public final void addEntities(Collection<File> files, String encoding) {
		if (files == null || files.isEmpty()) {
			throw new IllegalArgumentException("List of files cannot be null or empty");
		}
		for (File file : files) {
			addEntity(file, encoding);
		}
	}

	/**
	 * Adds all files under the given directory path to this data store. Each file will be used as an individual data entity for reading/writing.
	 * <p>The file names will be used as entity names.
	 * <p><i><b>Note: </b>As the encoding of these files is not provided, the default system encoding will be used.</i>
	 * @param directoryPath the path to directory in the file system that contains files to be used as data entities.
	 */
	public final void addEntities(String directoryPath) {
		fileDirectories.add(new FileProvider(directoryPath));
	}

	/**
	 * Adds all files under the given directory path to this data store. Each file will be used as an individual data entity for reading/writing.
	 * <p>The file names will be used as entity names.
	 * @param directoryPath the path to directory in the file system that contains files to be used as data entities.
	 * @param encoding the encoding to be used when handling the provided files.
	 */
	public final void addEntities(String directoryPath, String encoding) {
		fileDirectories.add(new FileProvider(directoryPath, encoding));
	}

	/**
	 * Adds all files under the given directory path to this data store. Each file will be used as an individual data entity for reading/writing.
	 * <p>The file names will be used as entity names.
	 * @param directoryPath the path to directory in the file system that contains files to be used as data entities.
	 * @param encoding the encoding to be used when handling the provided files.
	 */
	public final void addEntities(String directoryPath, Charset encoding) {
		fileDirectories.add(new FileProvider(directoryPath, encoding));
	}

	/**
	 * Adds all files under the given directory to this data store. Each file will be used as an individual data entity for reading/writing.
	 * <p>The file names will be used as entity names.
	 * <p><i><b>Note: </b>As the encoding of these files is not provided, the default system encoding will be used.</i>
	 * @param directory the directory in the file system that contains files to be used as data entities.
	 */
	public final void addEntities(File directory) {
		fileDirectories.add(new FileProvider(directory));
	}

	/**
	 * Adds all files under the given directory to this data store. Each file will be used as an individual data entity for reading/writing.
	 * <p>The file names will be used as entity names.
	 * @param directory the directory in the file system that contains files to be used as data entities.
	 * @param encoding the encoding to be used when handling the provided files.
	 */
	public final void addEntities(File directory, String encoding) {
		fileDirectories.add(new FileProvider(directory, encoding));
	}

	/**
	 * Adds all files under the given directory to this data store. Each file will be used as an individual data entity for reading/writing.
	 * <p>The file names will be used as entity names.
	 * @param directory the directory in the file system that contains files to be used as data entities.
	 * @param encoding the encoding to be used when handling the provided files.
	 */
	public final void addEntities(File directory, Charset encoding) {
		fileDirectories.add(new FileProvider(directory, encoding));
	}

	/**
	 * Adds a read-only data entity to this data store.
	 *
	 * @param entityName the entity name
	 * @param reader the handle to obtain input readers ({@link java.io.Reader}) for this data entity.
	 */
	public final void addEntity(String entityName, ReaderProvider reader) {
		entityName = getValidatedEntityName(entityName);
		entityReaders.put(entityName, reader);
	}

	/**
	 * Adds a write-only data entity to this data store.
	 *
	 * @param entityName the entity name
	 * @param writer the handle to obtain output writers ({@link java.io.Writer}) for this data entity.
	 */
	public final void addEntity(String entityName, WriterProvider writer) {
		entityName = getValidatedEntityName(entityName);
		entityWriters.put(entityName, writer);
	}

	/**
	 * Adds a file to this data store. It will be used as an individual data entity for reading/writing.
	 * <p><i><b>Note: </b>As the file encoding is not provided, the default system encoding will be used.</i>
	 * @param file the file to use as a data entity of this data store.
	 */
	public final void addEntity(File file) {
		unnamedEntityFiles.add(new FileProvider(file));
	}

	/**
	 * Adds a file to this data store. It will be used as an individual data entity for reading/writing.
	 * <p>The file name will be used as the entity name.
	 * @param file the file to use as a data entity of this data store.
	 * @param encoding the encoding to be used when handling the provided file.
	 */
	public final void addEntity(File file, String encoding) {
		unnamedEntityFiles.add(new FileProvider(file, encoding));
	}

	/**
	 * Adds a file to this data store. It will be used as an individual data entity for reading/writing.
	 * <p>The file name will be used as the entity name.
	 * @param file the file to use as a data entity of this data store.
	 * @param encoding the encoding to be used when handling the provided file.
	 */
	public final void addEntity(File file, Charset encoding) {
		unnamedEntityFiles.add(new FileProvider(file, encoding));
	}

	/**
	 * Adds a resource to this data store. The resource path can be a file in the class path or in the file system.
	 * It will be used as an individual data entity for reading/writing.
	 * <p><i><b>Note: </b>As the resource encoding is not provided, the default system encoding will be used.</i>
	 * @param entityName the name of the given data entity in this data store.
	 * @param resource the path to a resource to be used as a data entity of this data store
	 */
	public final void addEntity(String entityName, String resource) {
		entityName = getValidatedEntityName(entityName);
		namedEntityFiles.put(entityName, new FileProvider(resource));
	}

	/**
	 * Adds a resource to this data store. The resource path can be a file in the class path or in the file system.
	 * It will be used as an individual data entity for reading/writing.
	 * @param entityName the name of the given data entity in this data store.
	 * @param resource the path to a resource to be used as a data entity of this data store
	 * @param encoding the encoding to be used when handling the provided resource.
	 */
	public final void addEntity(String entityName, String resource, String encoding) {
		entityName = getValidatedEntityName(entityName);
		namedEntityFiles.put(entityName, new FileProvider(resource, encoding));
	}

	/**
	 * Adds a resource to this data store. The resource path can be a file in the class path or in the file system.
	 * It will be used as an individual data entity for reading/writing.
	 * @param entityName the name of the given data entity in this data store.
	 * @param resource the path to a resource to be used as a data entity of this data store
	 * @param encoding the encoding to be used when handling the provided resource.
	 */
	public final void addEntity(String entityName, String resource, Charset encoding) {
		entityName = getValidatedEntityName(entityName);
		namedEntityFiles.put(entityName, new FileProvider(resource, encoding));
	}

	/**
	 * Adds a file to this data store. It will be used as an individual data entity for reading/writing.
	 * <p><i><b>Note: </b>As the file encoding is not provided, the default system encoding will be used.</i>
	 * @param entityName the name of the data entity in this data store.
	 * @param file the file to use as a data entity of this data store.
	 */
	public final void addEntity(String entityName, File file) {
		entityName = getValidatedEntityName(entityName);
		namedEntityFiles.put(entityName, new FileProvider(file));
	}

	/**
	 * Adds a file to this data store. It will be used as an individual data entity for reading/writing.
	 * @param entityName the name of the data entity in this data store.
	 * @param file the file to use as a data entity of this data store.
	 * @param encoding the encoding to be used when handling the provided file.
	 */
	public final void addEntity(String entityName, File file, String encoding) {
		entityName = getValidatedEntityName(entityName);
		namedEntityFiles.put(entityName, new FileProvider(file, encoding));
	}

	/**
	 * Adds a file to this data store. It will be used as an individual data entity for reading/writing.
	 * @param entityName the name of the data entity in this data store.
	 * @param file the file to use as a data entity of this data store.
	 * @param encoding the encoding to be used when handling the provided file.
	 */
	public final void addEntity(String entityName, File file, Charset encoding) {
		entityName = getValidatedEntityName(entityName);
		namedEntityFiles.put(entityName, new FileProvider(file, encoding));
	}

	/**
	 * Validates the name given to an entity and returns a trimmed copy.
	 * @param entityName the name to be validated
	 * @return a trimmed copy of the given entity name
	 */
	private final String getValidatedEntityName(String entityName) {
		if (entityName == null || entityName.trim().isEmpty()) {
			throw new IllegalArgumentException("Entity name cannot be null or empty");
		}
		return entityName.trim();
	}

	/**
	 * Returns the configuration of a given data entity. The returned configuration object will contain default values provided in the object returned by {@link #getDefaultEntityConfiguration()}.
	 * <br>If the entity was not explicitly configured before, a new configuration object will be created and associated with the entity.
	 * <p>You can change the default configuration at any time by calling {@link #getDefaultEntityConfiguration()} and altering the settings you need.
	 *    Subsequent calls to {@link #getEntityConfiguration(String)} will return a configuration object with the new defaults.
	 *    Existing configurations will retain their original values, including the default settings previously used.
	 * @param entityName the name of the entity to be configured
	 * @return a configuration object for the given data entity.
	 */
	public final T getEntityConfiguration(String entityName) {
		entityName = getValidatedEntityName(entityName);
		T out = entityConfigurations.get(entityName);
		if (out == null) {
			out = newEntityConfiguration();
			entityConfigurations.put(entityName, out);
		}

		out.copyDefaultsFrom(defaultEntityConfiguration);

		return out;
	}

	/**
	 * Returns an unmodifiable map containing the configurations of all entities in this data store
	 * @return an unmodifiable map containing the configurations of all entities in this data store
	 */
	public final Map<String, T> getEntityConfigurations() {
		return Collections.unmodifiableMap(entityConfigurations);
	}

	/**
	 * Returns the default configuration object.
	 * This object returns all default values used for creating new configuration objects for data entities.
	 * @return the {@link #defaultEntityConfiguration} object which was provided in the constructor of this data store.
	 */
	public final T getDefaultEntityConfiguration() {
		return defaultEntityConfiguration;
	}

	/**
	 * Returns the maximum number of rows loaded in memory from data entities of this data store at any given time.
	 * <br>This is required to avoid getting an {@link OutOfMemoryError} when reading from large inputs.
	 * <br>uniVocity will block the input reading thread until there is room for more rows.
	 * <p><i>defaults to 10,000.</i>
	 * @return the maximum number of rows loaded in memory from any data entity at any given time.
	 */
	@Override
	public final int getLimitOfRowsLoadedInMemory() {
		return maxRowsInMemory;
	}

	/**
	 * Defines the maximum number of rows loaded in memory from data entities of this data store at any given time.
	 * <br>This is required to avoid getting an {@link OutOfMemoryError} when reading from large inputs.
	 * <br>uniVocity will block the input reading thread until there is room for more rows.
	 * @param maxRowsInMemory the maximum number of rows loaded in memory from any data entity at any given time.
	 * <i>This value must be greater than 0.</i>
	 */
	public final void setLimitOfRowsLoadedInMemory(int maxRowsInMemory) {
		Args.positive(maxRowsInMemory, "Maximum number of rows in memory");
		this.maxRowsInMemory = maxRowsInMemory;
	}

	/**
	 * Returns an unmodifiable set of file providers for directories.
	 * @return an unmodifiable set of file providers for directories
	 */
	public final Set<FileProvider> getFileDirectories() {
		return Collections.unmodifiableSet(fileDirectories);
	}

	/**
	 * Returns an unmodifiable map of writer providers for read-only entities.
	 * @return an unmodifiable map of writer providers for read-only entities
	 */
	public final Map<String, ReaderProvider> getEntityReaders() {
		return Collections.unmodifiableMap(entityReaders);
	}

	/**
	 * Returns an unmodifiable map of writer providers for write-only entities.
	 * @return an unmodifiable map of writer providers for write-only entities
	 */
	public final Map<String, WriterProvider> getEntityWriters() {
		return Collections.unmodifiableMap(entityWriters);
	}

	/**
	 * Returns an unmodifiable map of file providers for entities that were created with an explicit name.
	 * @return an unmodifiable map of file providers for entities that were created with an explicit name
	 */
	public final Map<String, FileProvider> getNamedEntityFiles() {
		return Collections.unmodifiableMap(namedEntityFiles);
	}

	/**
	 * Returns an unmodifiable set of file providers for entities that were created without an explicit name.
	 * @return an unmodifiable set of file providers for entities that were created without an explicit name
	 */
	public final Set<FileProvider> getUnnamedEntityFiles() {
		return Collections.unmodifiableSet(unnamedEntityFiles);
	}

	/**
	 * Creates a new configuration object for an entity of this data store.
	 * @return new configuration object for an entity of this data store
	 */
	protected abstract T newEntityConfiguration();
}
