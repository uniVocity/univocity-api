/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.data;

import com.univocity.api.config.builders.*;

/**
 * The <code>DataErrorHandler</code> is a handler for errors that may occur when reading data from a query.
 * In case of exceptions or unexpected data, it provides a way to handle and potentially recover from the error.
 *
 * <p>For example, if a query is configured to return a single row (i.e. through {@link QueryResult#returnSingleRow()}, but it actually produces 3 rows,
 * {@link #handleUnexpectedData(Object[][])} can be used to return the first row.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 * @param <T> The type of the data returned in case of an error.
 */
public interface DataErrorHandler<T> {
	/**
	 * Handles an exception that happened when executing a query.
	 * @param t the exception thrown while executing a query.
	 * @return an object with data if the handler recovered from the error.
	 */
	public T handleException(Throwable t);

	/**
	 * Handles unexpected data produced by a query.
	 *
	 * @param data the data produced by a query.
	 * @return an object with data if the handler recovered from the error.
	 */
	public T handleUnexpectedData(Object[][] data);
}
