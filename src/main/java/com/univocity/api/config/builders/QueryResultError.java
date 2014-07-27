/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.data.*;
import com.univocity.api.engine.*;
import com.univocity.api.exception.*;

/**
 * The <code>QueryResultError</code> is the last step of the configuration required to use a query as a function,
 * which is initialized in {@link DataIntegrationEngine#addQuery(EngineScope, String)}.
 *
 * It is used to specify how to handle an unexpected result coming from a query. Which can be:
 *
 * <ul>
 *  <li>No results returned</li>
 *  <li>More results than expected</li>
 *  <li>Exceptions while executing the query</li>
 * </ul>
 *
 * @see DataErrorHandler
 * @see QuerySetup
 * @see QueryResult
 * @see DataIntegrationEngine
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface QueryResultError {

	/**
	 * Defines a custom {@link DataErrorHandler} to process the unexpected outcome from a query execution.
	 * @param handler the custom object that handles unexpected results from a query
	 *
	 * <p><i>This completes the configuration started in {@link DataIntegrationEngine#addQuery(EngineScope, String)}</i>
	 */
	public void onErrorHandleWith(DataErrorHandler<?> handler);

	/**
	 * Specifies that if a unexpected result is produced by the query, or an exception happens, <code>null</code> will be returned.
	 *
	 * <p><i>This completes the configuration started in {@link DataIntegrationEngine#addQuery(EngineScope, String)}</i>
	 */
	public void onErrorReturnNull();

	/**
	 * Specifies that if a unexpected result is produced by the query, or an exception happens, the current mapping cycle will be aborted.
	 *
	 * A {@link DataInputException} will be thrown.
	 *
	 * <p><i>This completes the configuration started in {@link DataIntegrationEngine#addQuery(EngineScope, String)}</i>
	 */
	public void onErrorAbort();
}
