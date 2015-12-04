/*
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 *
 */
package com.univocity.api.engine;

import com.univocity.api.*;
import com.univocity.api.config.*;

/**
 * The <code>DataIntegrationEngineFactory</code> is loaded internally by {@link Univocity} and creates instances of
 * {@link DataIntegrationEngine} that can be configured to execute data mapping cycles.
 *
 * @see Univocity
 * @see DataIntegrationEngine
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface DataIntegrationEngineFactory {

	/**
	 * Obtains the {@link DataIntegrationEngine} instance which was configured using {@link Univocity#registerEngine(com.univocity.api.config.EngineConfiguration)}
	 * @param engineName the name of the data integration engine
	 * @return the {@link DataIntegrationEngine} associated with the given engine name
	 */
	DataIntegrationEngine getEngine(String engineName);

	/**
	 * Shuts down the {@link DataIntegrationEngine} associated with the given engine name. All resources allocated by this engine will be released.
	 * <br>If the engine was started using an in-memory database to store metadata information, its values will be lost.
	 * <p>A new engine instance can be started with {@link DataIntegrationEngineFactory#getEngine(String)}.
	 *
	 * @param engineName the name of the engine to be stopped.
	 */
	void shutdown(String engineName);

	/**
	 * Is the data integration engine with the given name active?
	 * <p>An active engine is one that has been instantiated via {@link DataIntegrationEngineFactory#getEngine(String)} and has yet to be shut down.
	 *
	 * @param engineName the name of the engine to be verified.
	 * @return true if the {@link DataIntegrationEngine} associated with the given engine name is active, false otherwise
	 */
	boolean isActive(String engineName);
	
	/**
	 * Queries whether a data integration engine with the given name has been registered
	 * <p>A registered engine is one that has been registered via {@link Univocity#registerEngine(com.univocity.api.config.EngineConfiguration)} .</p>
	 *
	 * @param engineName the name of the engine to be verified.
	 * @param engineConfiguration the configuration object associated with the given engine (if available).
	 * @return true if a {@link DataIntegrationEngine} associated with the given engine name exists, false otherwise
	 */
	boolean isRegistered(String engineName, EngineConfiguration engineConfiguration);

	/**
	 * De-registers a data integration engine with the given name. Only engines that have been be shut down can be de-registered.
	 * This allows a new data integration engine with the same name, but different configuration, to be registered.
	 * <p>A registered engine is one that has been registered via {@link Univocity#registerEngine(com.univocity.api.config.EngineConfiguration)} .</p>
	 * @param engineName the name of the engine to be de-registered.
	 */
	void deRegister(String engineName);


	DataStreamingProcess getDataStream(DataStreamConfiguration streamingEngineConfiguration);
}
