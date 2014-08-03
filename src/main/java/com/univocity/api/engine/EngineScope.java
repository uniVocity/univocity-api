/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

import com.univocity.api.config.*;

/**
 * The scopes available in an instance of {@link DataIntegrationEngine}.
 *
 * @see DataIntegrationEngine
 * @see ScopeStorageProvider
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public enum EngineScope {
	/**
	 * The persistent scope retains and restores values produced during multiple executions of a {@link DataIntegrationEngine}.
	 * It must be supported by a {@link ScopeStorageProvider}. If a provider is not supplied in the {@link EngineConfiguration}, then
	 * it will behave as the application scope, and no values will exist once the {@link DataIntegrationEngine} is shut down.
	 */
	PERSISTENT,
	/**
	 * The application scope retains and restores values produced while the {@link DataIntegrationEngine} is running.
	 * Data stored in the application scope will be available across multiple data mapping cycles. All values are lost once it is shut down.
	 */
	APPLICATION,
	/**
	 * The cycle scope retains and restores values produced during a data mapping cycle.
	 * Data stored in the cycle scope will be available across each entity mapping executed within the same cycle.
	 * All values are lost once the cycle stops.
	 */
	CYCLE,
	/**
	 * The mapping scope retains and restores values produced during the execution of a single entity mapping.
	 * Data stored in the mapping scope will be available while the entity mapping is being executed.
	 * All values are lost once the mapping between two entities has finished.
	 */
	MAPPING,
	/**
	 * The stateless scope never retains or restores any value. {@link FunctionCall}s will never have their results cached in the scope,
	 * therefore every call to an stateless-scoped function will trigger a new execution.
	 */
	STATELESS
}
