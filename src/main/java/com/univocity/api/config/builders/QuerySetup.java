/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>QuerySetup</code> provides builder-style configuration options for setting up a query that will be used as a function of a {@link DataIntegrationEngine}.
 * Instances of this interface are obtained from a {@link DataIntegrationEngine} instance, using {@link DataIntegrationEngine#addQuery(EngineScope, String)}.
 *
 * @see FunctionCall
 * @see DataIntegrationEngine
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface QuerySetup {
	/**
	 * Determines which data store will execute the query being built.
	 * @param dataStoreName the name of the data store that will execute the query whenever it is invoked.
	 * @return the next step of this configuration: defining the query to be executed.
	 */
	public QueryDefinition onDataStore(String dataStoreName);
}
