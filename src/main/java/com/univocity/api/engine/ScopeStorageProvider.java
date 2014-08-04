/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

import com.univocity.api.*;
import com.univocity.api.config.*;

/**
 * User provided storage mechanism to enable the persistent scope ({@link EngineScope#PERSISTENT}).
 *
 * <p>A persistent scope ideally retains values produced by a {@link DataIntegrationEngine} even after the application stops.
 *    Its expected to be able to restore these values once the application is started again, so no expensive processing has to happen
 *
 * <p>The <code>ScopeStorageProvider</code> instance will have its {@link #initialize()} method when a {@link DataIntegrationEngine} is loaded
 * from {@link Univocity}. A call to {@link #deactivate()} is made when the engine is shut down.
 *
 * <p> uniVocity will obtain instances of this interface through {@link EngineConfiguration#getPersistentScopeStorageProvider()}.
 *
 * @see EngineConfiguration
 * @see DataIntegrationEngine
 * @see EngineScope
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface ScopeStorageProvider {

	/**
	 * Obtains a value associated with a key in the scope storage.
	 * @param key the key that refers to a value stored in this scope
	 * @return the value associated with the given key, or null if this scope does not contain a value for the key.
	 */
	public Object getValue(Object key);

	/**
	 * Associates a value to a key in this scope storage.
	 * @param key the key to be associated with a value in this storage
	 * @param value the value associated with the given key
	 * @return the previous value associated with the given key, if any.
	 */
	public Object setValue(Object key, Object value);

	/**
	 * Queries the storage for the presence of a key.
	 * @param key that refers to a value stored in this scope
	 * @return true if this storage contains the given key, false otherwise.
	 */
	public boolean contains(Object key);

	/**
	 * Initializes the storage provider. This is invoked when a {@link DataIntegrationEngine} that uses this storage provider is started.
	 */
	public void initialize();

	/**
	 * Deactivates the storage provider. This is invoked when the {@link DataIntegrationEngine} that uses this storage provider is shut down.
	 */
	public void deactivate();
}
