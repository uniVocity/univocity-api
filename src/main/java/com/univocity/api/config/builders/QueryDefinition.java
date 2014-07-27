/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import java.io.*;
import java.nio.charset.*;

import com.univocity.api.engine.*;

/**
 * The <code>QueryDefinition</code> configuration is obtained from a {@link QuerySetup}, and is used to determine what query should be executed against the data store.
 *
 * @see QuerySetup
 * @see QueryResult
 * @see DataIntegrationEngine
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface QueryDefinition {
	/**
	 * Uses a query definition from a string.
	 * @param query the query definition that the data store is able to recognize and execute to produce values.
	 * @return the next step of this configuration: determine how the result of this query should be read.
	 */
	public QueryResult fromString(String query);

	/**
	 * Uses a query definition from a file.
	 * @param query the file containing a query definition that the data store is able to recognize and execute to produce values.
	 * <p><i><b>Note: </b>As the file encoding is not provided, the default system encoding will be used to read the file.</i>
	 * @return the next step of this configuration: determine how the result of this query should be read.
	 */
	public QueryResult fromFile(File query);

	/**
	 * Uses a query definition from a resource available either in the classpath or the file system.
	 * @param filePath the path to a resource containing a query definition that the data store is able to recognize and execute to produce values.
	 * <p><i><b>Note: </b>As the resource encoding is not provided, the default system encoding will be used to read the resource.</i>
	 * @return the next step of this configuration: determine how the result of this query should be read.
	 */
	public QueryResult fromResource(String filePath);

	/**
	 * Uses a query definition from a file.
	 * @param query the file containing a query definition that the data store is able to recognize and execute to produce values.
	 * @param encoding the encoding that should be used to read the given file.</i>
	 * @return the next step of this configuration: determine how the result of this query should be read.
	 */
	public QueryResult fromFile(File query, String encoding);

	/**
	 * Uses a query definition from a resource available either in the classpath or the file system.
	 * @param resourcePath the path to a resource containing a query definition that the data store is able to recognize and execute to produce values.
	 * @param encoding the encoding that should be used to read the given resource.</i>
	 * @return the next step of this configuration: determine how the result of this query should be read.
	 */
	public QueryResult fromResource(String resourcePath, String encoding);

	/**
	 * Uses a query definition from a file.
	 * @param query the file containing a query definition that the data store is able to recognize and execute to produce values.
	 * @param encoding the encoding that should be used to read the given file.</i>
	 * @return the next step of this configuration: determine how the result of this query should be read.
	 */
	public QueryResult fromFile(File query, Charset encoding);

	/**
	 * Uses a query definition from a resource available either in the classpath or the file system.
	 * @param resourcePath the path to a resource containing a query definition that the data store is able to recognize and execute to produce values.
	 * @param encoding the encoding that should be used to read the given resource.</i>
	 * @return the next step of this configuration: determine how the result of this query should be read.
	 */
	public QueryResult fromResource(String resourcePath, Charset encoding);

}
