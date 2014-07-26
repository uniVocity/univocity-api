/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

import com.univocity.api.config.builders.*;

/**
 * Provides support for custom implementations of {@link CustomReadableEntity} with parameterization support.
 * 
 * <p>The query definition is given by a String. New instances of custom queries should be created by the parent data store (using {@link CustomDataStore#addQuery(String, String)}).
 * <p>Instances of this interface are expected to be obtained via a {@link CustomDataStore} through {@link CustomDataStore#getQueries()}
 * 
 * <br>Implementations of CustomQuery provide a query-like behavior:
 * <ul>
 * 	<li>First, parameters are set by uniVocity using {@link #setParameter(String, Object)}</li>
 *  <li>Then, {@link #preareToRead(String[])} is invoked, producing a {@link ReadingProcess} that extracts data from this CustomQuery instance.
 *  	The result can be influenced by the parameter values provided, but this is implementation dependent.
 *  </li>
 * </ul>
 * 
 *  
 * <i><b>Note</b> uniVocity does not allow writing operations on such entities.</i>
 * 
 * @see QuerySetup
 * @see CustomDataStore
 * @see CustomReadableEntity
 * @see ReadingProcess
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public interface CustomQuery extends CustomReadableEntity {

	/**
	 * Informs what parameters are available through this entity, and in what order.
	 * <br> The returned parameter names array cannot contain duplicates. Parameter names are case insensitive, therefore "param1" and "Param1" are considered the same name.
	 * 
	 * @return the sequence of parameters supported by this entity.
	 */
	public String[] getParameters();

	/**
	 * Associates a value, provided by uniVocity, to a parameter.
	 * @param parameterName the name of the parameter to be set
	 * @param parameterValue the value associated to the given parameter.
	 */
	public void setParameter(String parameterName, Object parameterValue);
}
