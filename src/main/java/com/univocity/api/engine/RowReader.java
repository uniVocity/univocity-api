/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

import com.univocity.api.config.builders.*;

/**
 * A <code>RowReader</code> provides access to rows being processed during the mapping between two data entities.
 * Use {@link EntityMapping} to provide row readers for accessing data while reading from a source entity and before or after writing to a destination entity.
 * 
 * <br> With a <code>RowReader</code>, the user has easy access to the data:
 * <ul>
 *  <li>loaded from an input</li>
 *  <li>transformed and ready to be written to an output</li>
 *  <li>written to the output</li>
 * </ul>  
 * 
 * The user can read and modify the contents of each rows, and easily perform common tasks such as data monitoring, logging and statistics.
 * <br>The {@link #initialize(RowMappingContext)} and {@link #cleanup(RowMappingContext)} methods can be overridden to allow initialization before
 *     and after the <code>RowReader</code> receives rows for processing. 
 * 
 * <p> A {@link RowMappingContext} object is available to allow greater control and provide information about the process in execution. 
 * <p> Functions, variables and other entities used by the {@link DataIntegrationEngine} are also available. 
 * 
 * 
 * @see EntityMapping
 * @see RowMappingContext
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public abstract class RowReader {

	/**
	 * Gives the RowReader a chance to perform any initialization it requires before the first call to {@link #processRow(Object[], Object[], RowMappingContext)} is made.
	 * <p><b>Note: </b> this method is only invoked by uniVocity if there are rows in the input entity.   
	 * @param context the contextual information and controls available to the user during the execution of a data mapping process. 
	 */
	public void initialize(RowMappingContext context) {
	}

	/**
	 * Processes a row extracted from the input. 
	 * @param inputRow the original data in a record read from the input data entity
	 * @param outputRow the transformed data. The value of this parameter varies according to where the <code>RowReader</code> is applied in the {@link EntityMapping}:
	 * <p>
	 * <ul>
	 * 	<li><i><b>while reading from input </b></i><code>{@link EntityMapping#addInputRowReader(RowReader)}</code>: 
	 * 		<br>outputRow will be null.
	 * </li>
	 *  <li><i><b>before writing to the output </b></i><code>{@link EntityMapping#addOutputRowReader(RowReader)}</code>: 
	 *  	<br>outputRow will contain all values transformed from the input, with values for references to fully populated and 
	 *  	ready to be written to the destination entity.
	 *  </li>
	 *  <li><i><b>after writing to the output </b></i><code>{@link EntityMapping#addPersistedRowReader(RowReader)}</code>: 
	 *  	<br>outputRow will contain all rows actually written to the destination entity. Generated values, if any, will be available in the outputRow.
	 *  </li>
	 * </ul>
	 * @param context the contextual information and controls available to the user during the execution of a data mapping process.
	 */
	public abstract void processRow(Object[] inputRow, Object[] outputRow, RowMappingContext context);

	/**
	 * Cleans up the RowReader after the reading process.
	 * <b>Note: </b> this method is always invoked by uniVocity even if the input entity is empty or in case of exceptions.   
	 * @param context the contextual information and controls available to the user after the execution of a data mapping process. 
	 */
	public void cleanup(RowMappingContext context) {
	}
}
