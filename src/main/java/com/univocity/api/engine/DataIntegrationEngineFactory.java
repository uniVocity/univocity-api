/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

import com.univocity.api.*;

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
	 * @return the {@link DataIntegrationEngine} associated to the given engine name
	 */
	public DataIntegrationEngine getEngine(String engineName);

	/**
	 * Shuts down the {@link DataIntegrationEngine} associated to the given engine name. All resources allocated by this engine will be released. 
	 * <br>If the engine was started using an in-memory database to store metadata information, its values will be lost.
	 * <p>A new engine instance can be started with {@link DataIntegrationEngineFactory#getEngine(String)}. 
	 * 
	 * @param engineName the name of the engine to be stopped.
	 */
	public void shutdown(String engineName);

	/**
	 * Informs whether or not a given data integration engine is active. 
	 * <p>An active engine is one that has been instantiated through {@link DataIntegrationEngineFactory#getEngine(String)} and has to been shut down yet 
	 * 
	 * @param engineName the name of the engine to be verified.
	 * @return true if the {@link DataIntegrationEngine} associated to the given engine name is active, false otherwise
	 */
	public boolean isActive(String engineName);
}
