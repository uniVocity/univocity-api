/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api;

import java.util.*;

import com.univocity.api.common.*;
import com.univocity.api.config.*;
import com.univocity.api.data.*;
import com.univocity.api.engine.*;
import com.univocity.api.entity.custom.*;

/**
 * This is the entry point to uniVocity data integration engine. It connects the resources in this API to their actual implementations in univocity.jar.
 *
 * <p>uniVocity abstracts the specifics of any source of data. It works with the concept of <i>data stores</i>, <i>data entities</i> and <i>queries</i>.
 * <ul>
 *  <li>A <b>data entity</b> is a structure that contains fields and data. uniVocity reads and writes the data stored in its records
 *      when mapping information from one entity to another.
 *      <p>There is a default set of data entities supported by uniVocity,
 *      but you can write your own data entities by implementing {@link CustomDataEntity}.
 *  </li>
 *  <li>A <b>query</b> is a special read-only data entity that provides fields and data. It is represented by a String and can be parameterized. The
 *      actual behavior of a query is dependent on the underlying data store and there is no restriction on what a query represent. It can be a SQL query,
 *      a web service call, or anything the data store supports.
 *      <p>Queries can also act as <i>functions</i> in uniVocity, and can be used within <i>expressions</i>
 *      <p>You can provide your own query implementation by implementing a {@link CustomQuery}.
 *  </li>
 * 	<li>A <b>data store</b> is the structure that provides access to its data entities and queries. It knows how to interpret queries and what entities
 * 		are available to uniVocity. Data stores are also responsible for managing transactional operations when data in its entities is modified.
 *      <p>It is important to notice that the implementation of transactional behavior is optional and there are potential limitations in the underlying
 *      data storage mechanism. For example, for its supported file-based data stores, uniVocity implements a backup and restore mechanism, which can
 *      fail if the file system does not allow file write operations.
 *      <p>There is a default set of data stores supported by uniVocity, but you can write your own data stores by implementing {@link CustomDataStore}
 *  </li>
 * </ul>
 *
 * <p>In some circumstances, you might need to configure the class loader before being able to obtain instances of {@link UnivocityFactoryProvider} from univocity.jar.
 *    If that is the case, use the {@link #setClassLoader(ClassLoader)} method before calling the {@link #engineFactory()} and {@link #datasetFactory()} method.
 *
 * @see EngineConfiguration
 * @see DataIntegrationEngineFactory
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class Univocity {

	private static ServiceLoader<UnivocityFactoryProvider> factoryProviderLoader = ServiceLoader.load(UnivocityFactoryProvider.class);
	private static String licensePath = "license.zip";
	static final Map<String, EngineConfiguration> engineConfigurations = new HashMap<String, EngineConfiguration>();
	static final String API_VERSION = "1.0.0";

	private static UnivocityFactoryProvider loadProvider() {
		for (UnivocityFactoryProvider provider : factoryProviderLoader) {
			return provider;
		}
		throw new IllegalStateException("Unable to load uniVocity provider. You might need to use a different classloader in order to load it from uniVocity's jar file");
	}

	private static UnivocityFactoryProvider provider;

	/**
	 * Defines the class loader to be used to load uniVocity implementation classes (from univocity.jar)
	 *
	 * @param classLoader The class loader to be used to load provider classes, or <tt>null</tt> if the system class loader is to be used
	 */
	public static final synchronized void setClassLoader(ClassLoader classLoader) {
		factoryProviderLoader = ServiceLoader.load(UnivocityFactoryProvider.class, classLoader);
	}

	/**
	 * Obtains the configured path to the uniVocity license file. The path can be either relative or absolute.
	 * <p><i>By default, uniVocity will look for a file named "license.zip" on the classpath.</i>
	 * <p><i>To request a license, execute the <b>licenseRequest script</b> that comes with <b>univocity.jar</b></i></p>
	 * @return the path to the license file.
	 */
	public static final synchronized String getLicensePath() {
		return licensePath;
	}

	/**
	 * Sets the path to the uniVocity license file. The path can be either relative or absolute.
	 * <p><i>By default, uniVocity will look for a file named "license.zip" on the classpath.</i>
	 * <p><i>To request a license, execute the <b>licenseRequest script</b> that comes with <b>univocity.jar</b></i></p>
	 * @param path the path to the license file.
	 */
	public static final synchronized void setLicensePath(String path) {
		Args.notBlank(path, "License path");
		licensePath = path;
	}

	private static final synchronized UnivocityFactoryProvider provider() {
		if (provider == null) {
			provider = loadProvider();
		}

		return provider;
	}

	/**
	 * Registers a uniVocity engine configuration. Once the engine configuration is registered,
	 * the {@link DataIntegrationEngine} instance can be initialized and obtained via {@link #getEngine(String)}.
	 *
	 * @param engineConfiguration the new engine configuration.
	 */
	public static final synchronized void registerEngine(EngineConfiguration engineConfiguration) {
		Args.notNull(engineConfiguration, "Engine configuration");
		Args.notBlank(engineConfiguration.getEngineName(), "Engine name in engine configuration");
		Args.notEmpty(engineConfiguration.getDataStoreConfigurations(), "Data store configurations in engine configuration");

		String engineName = engineConfiguration.getEngineName();
		engineName = engineName.trim().toLowerCase();

		if (engineConfigurations.containsKey(engineName)) {
			if (engineConfigurations.get(engineName) != engineConfiguration) {
				throw new IllegalArgumentException("Duplicate engine name: '" + engineConfiguration.getEngineName() + "'");
			}
		} else {
			engineConfigurations.put(engineName, engineConfiguration);
		}
	}

	/**
	 * Loads and returns the {@link DatasetFactory} that provides convenient implementations of {@link Dataset}
	 * @return the singleton instance of {@link DatasetFactory} provided by the implementation of the uniVocity API (from univocity.jar)
	 */
	public static final synchronized DatasetFactory datasetFactory() {
		return provider().getDatasetFactory();
	}

	/**
	 * Loads and returns the {@link DataIntegrationEngineFactory} that obtains {@link DataIntegrationEngine} instances registered in {@link #registerEngine(EngineConfiguration)}
	 * @return the singleton instance of {@link DataIntegrationEngineFactory} provided by the implementation of the uniVocity API (from univocity.jar)
	 */
	private static final synchronized DataIntegrationEngineFactory engineFactory() {
		return provider().getDataIntegrationEngineFactory();
	}

	/**
	 * Obtains the {@link DataIntegrationEngine} instance which was configured using {@link Univocity#registerEngine(com.univocity.api.config.EngineConfiguration)}
	 * @param engineName the name of the data integration engine
	 * @return the {@link DataIntegrationEngine} associated to the given engine name
	 */
	public static final synchronized DataIntegrationEngine getEngine(String engineName) {
		return engineFactory().getEngine(engineName);
	}

	/**
	 * Shuts down the {@link DataIntegrationEngine} associated to the given engine name. All resources allocated by this engine will be released.
	 * <br>If the engine was started using an in-memory database to store metadata information, its values will be lost.
	 * <p>A new engine instance can be started with {@link #getEngine(String)}.
	 *
	 * @param engineName the name of the engine to be stopped.
	 */
	public static final synchronized void shutdown(String engineName) {
		engineFactory().shutdown(engineName);
	}

	/**
	 * Informs whether or not a given data integration engine is active.
	 * <p>An active engine is one that has been instantiated through {@link Univocity#getEngine(String)} and has to been shut down yet
	 *
	 * @param engineName the name of the engine to be verfied.
	 * @return true if the {@link DataIntegrationEngine} associated to the given engine name is active, false otherwise
	 */
	public static final synchronized boolean isActive(String engineName) {
		return engineFactory().isActive(engineName);
	}
}
