/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config;

import java.util.*;

import com.univocity.api.*;
import com.univocity.api.common.*;
import com.univocity.api.engine.*;
import com.univocity.api.entity.custom.*;

/**
 * The <code>EngineConfiguration</code> provides the essential configuration of a {@link DataIntegrationEngine}. Use {@link Univocity#registerEngine(EngineConfiguration)},
 * to register your engine, and {@link DataIntegrationEngineFactory#getEngine(String)} to obtain the {@link DataIntegrationEngine} instance.
 *
 * <p>uniVocity depends on a database for manipulating the metadata that enables most of its data mapping operations. Use {@link #setMetadataSettings(MetadataSettings)} to
 * provide the specific settings that enable uniVocity's persistent metadata. If no configuration for metadata is provided, then a in-memory instance 
 * of a database with the essential metadata structure will be created automatically. This of course, means any metadata information will be lost
 *  when the application is shut down.   
 *
 * @see Univocity
 * @see DataIntegrationEngineFactory
 * @see DataIntegrationEngine
 * @see MetadataSettings
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public class EngineConfiguration {

	private final String engineName;
	private final HashSet<DataStoreConfiguration> dataStoreConfigurations = new HashSet<DataStoreConfiguration>();
	private final HashSet<CustomDataStoreFactory<?>> customDataStoreFactories = new HashSet<CustomDataStoreFactory<?>>();
	private MetadataSettings metadataSettings;
	private ScopeStorageProvider persistentStorageProvider;

	/** 
	 * Creates a new engine configuration with the essential configuration required by uniVocity for enabling the definition and execution of data mappings.
	 * @param engineName the name of the new engine. The engine name is used to obtain instances of {@link DataIntegrationEngine} 
	 * and manage them using {@link Univocity} 
	 * @param dataStores optional parameter for the configurations of data stores that will have their entities mapped through this engine.  
	 * More dataStores can be added later on using {@link #addDataStoreConfigurations(DataStoreConfiguration...)}
	 */
	public EngineConfiguration(String engineName, DataStoreConfiguration... dataStores) {
		Args.notBlank(engineName, "Engine name");
		this.engineName = engineName;

		if (dataStores != null && dataStores.length > 0) {
			addDataStoreConfigurations(dataStores);
		}
	}

	/**
	 * Defines the database settings required by uniVocity to persist its metadata information.
	 * <p>If set to null, then uniVocity will create an internal in-memory database for metadata handling while this engine is active.
	 * @param metadataSettings the database settings required for accessing and persisting metadata produced by this engine.
	 */
	public final void setMetadataSettings(MetadataSettings metadataSettings) {
		this.metadataSettings = metadataSettings;
	}

	/**
	 * Returns name of the engine that will be initialized with the configuration provided in this class.
	 * @return the configured engine name
	 */
	public final String getEngineName() {
		return engineName;
	}

	/**
	 * Obtains the database settings required by uniVocity to persist its metadata information.
	 * <p><i>Defaults to to null. If no configuration is provided using {@link #setMetadataSettings(MetadataSettings)}, then  an internal
	 * in-memory database will be used for metadata handling while this engine is active.</i>
	 * @return the database settings required for accessing and persisting metadata produced by this engine.
	 */
	public final MetadataSettings getMetadataSettings() {
		return metadataSettings;
	}

	/**
	 * Adds instances of {@link CustomDataStoreFactory} that should be used by uniVocity to read any custom {@link DataStoreConfiguration}, provided by the user
	 * in the constructor of this class, to properly create instances of {@link CustomDataStore}.
	 * @param customFactories the factories that process user-provided data store configurations and generate custom data store instances.
	 */
	public final void addCustomDataStoreFactories(CustomDataStoreFactory<?>... customFactories) {
		Args.notEmpty(customFactories, "Custom data store factories");
		for (CustomDataStoreFactory<?> customFactory : customFactories) {
			Args.notNull(customFactory, "Custom data store factory");
			customDataStoreFactories.add(customFactory);
		}
	}

	/**
	 * Obtains the (unmodifiable) set of {@link CustomDataStoreFactory} instances that should be used by uniVocity to read any custom {@link DataStoreConfiguration}
	 * to properly create instances of {@link CustomDataStore}.
	 * @return the factories that process user-provided data store configurations and generate custom data store instances.
	 */
	public final Set<CustomDataStoreFactory<?>> getCustomDataStoreFactories() {
		return Collections.unmodifiableSet(customDataStoreFactories);
	}

	/**
	 * Defines a storage provider to keep values stored in the {@link EngineScope#PERSISTENT} scope of the {@link DataIntegrationEngine} which instantiated by uniVocity
	 * with this configuration class. 
	 * @param persistentStorageProvider the storage abstraction that retains and restores values in the {@link EngineScope#PERSISTENT}
	 *  from persistent storage (such as a file, distributed cache, or database)
	 */
	public final void setPersistentStorageProvider(ScopeStorageProvider persistentStorageProvider) {
		this.persistentStorageProvider = persistentStorageProvider;
	}

	/**
	 * Obtains the storage provider configured to keep values stored in the {@link EngineScope#PERSISTENT} scope of the {@link DataIntegrationEngine}.
	 * @return the configured {@link ScopeStorageProvider} instance that retains and restores values in the {@link EngineScope#PERSISTENT}
	 *  from persistent storage (such as a file, distributed cache, or database)
	 */
	public final ScopeStorageProvider getPersistentScopeStorageProvider() {
		return persistentStorageProvider;
	};

	/**
	 * Adds the configurations for data stores whose entities will be mapped using the engine created by this class  
	 * @param dataStores parameter for the configurations of data stores that will have their entities mapped through this engine.
	 */
	public final void addDataStoreConfigurations(DataStoreConfiguration... dataStores) {
		Args.notEmpty(dataStores, "Data stores");
		for (DataStoreConfiguration config : dataStores) {
			Args.notNull(config, "Data store configuration");
			dataStoreConfigurations.add(config);
		}
	}

	/**
	 * Obtains the (unmodifiable) set of {@link DataStoreConfiguration} instances that will be used by uniVocity to instantiate the data stores
	 * manipulated by this engine.
	 * @return the configurations of all data stores whose entities will be mapped using the engine created by this class  
	 */
	public final Set<DataStoreConfiguration> getDataStoreConfigurations() {
		return Collections.unmodifiableSet(dataStoreConfigurations);
	}

}
