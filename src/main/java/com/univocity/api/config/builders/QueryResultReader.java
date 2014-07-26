/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>QueryResultReader</code> configuration is obtained from a {@link QueryResult}, and is used to determine
 * functions, if any, to be executed against the results obtained by the execution of a query.
 * 
 * @see QuerySetup
 * @see QueryResult
 * @see QueryResultError
 * @see EntityMapping
 * @see DataIntegrationEngine
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface QueryResultReader {

	/**
	 * Specifies a sequence of functions to transform the output of the query. 
	 * 
	 * @param functionNames the sequence of function names that will be executed against each value record read from the input. Note first function must accept
	 * parameters as configured in the {@link QueryResult}
	 * 
	 * <ul>
	 *  <li><b>single value: </b>parameters must be compatible with <code>Object</code></li>
	 *  <li><b>single row: </b>parameters must be compatible with <code>Object[]</code></li> 
	 *  <li><b>multiple rows: </b>parameters must be compatible with <code>Object[][]</code></li> 
	 * </ul>
	 * 
	 * <p><i><i>Note: </i> this establishes a chaining of functions: 
	 *  				   If the first function trims strings, then the second function will receive a trimmed String instead of the original value.
	 * 
	 * @return the last step of the query configuration process: define how to handle an unexpected result from the execution of this query.
	 */
	public QueryResultError readingWith(String... functionNames);

	/**
	 * Specifies the results of this query should be returned directly and no transform to its the output is required. 
	 *  
	 * @return the last step of the query configuration process: define how to handle an unexpected result from the execution of this query.
	 */
	public QueryResultError directly();
}
