/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.data.*;
import com.univocity.api.engine.*;

/**
 * The <code>QueryResult</code> configuration is obtained from a {@link QueryDefinition}, and is used to determine
 * how to read the results obtained by the execution of a query.
 * 
 * Any query can be used as a regular {@link FunctionCall}. In case a query returns a {@link Dataset},
 * it can also be used as the source entity in one or more {@link EntityMapping}s.
 * 
 * @see QuerySetup
 * @see QueryResult
 * @see QueryResultReader
 * @see EntityMapping
 * @see DataIntegrationEngine
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface QueryResult {
	/**
	 * Expects the query to return a single value. 
	 * @return the next step of this configuration: configure functions to be executed against the result.
	 */
	public QueryResultReader returnSingleValue();

	/**
	 * Expects the query to return a single row, with multiple values. 
	 * @return the next step of this configuration: configure functions to be executed against the result.
	 * <p><i><b>Note:</b> the functions must support Object[] as a parameter.</i>
	 */
	public QueryResultReader returnSingleRow();

	/**
	 * Expects the query to return multiple rows. 
	 * @return the next step of this configuration: configure functions to be executed against the result.
	 * <p><i><b>Note:</b> the functions must support Object[][] as a parameter.</i>
	 */
	public QueryResultReader returnMultipleRows();

	/**
	 * Uses the result of the query as a {@link Dataset}. This allows the query to be used as the source entity of any {@link EntityMapping}.
	 * Note that the results of this query are kept in memory and reused according to the scope associated to it.
	 * <p><i>This completes the configuration started in {@link DataIntegrationEngine#addQuery(EngineScope, String)}</i>
	 */
	public void returnDataset();
}
