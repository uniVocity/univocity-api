/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>ReferenceTransform</code> configuration is obtained from an {@link UnmatchedReference} configuration 
 * which is part of the configuration initialized by a call to {@link ReferenceMappingSetup#using(String...)} 
 * 
 * <p>It is used to define what functions in the {@link DataIntegrationEngine} should be used to process values from an input field and transform them into values 
 * that will then be used to restore the identifier of a destination field.    
 * 
 * @see ReferenceMappingSetup
 * @see FunctionCall
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface ReferenceTransform {
	/**
	 * Defines what sequence of functions in the {@link DataIntegrationEngine} should be used to process values from an input field and transform them into values 
	 * that will then be used to restore the identifiers of an entity in the destination.   
	 * 
	 * @param functionNames the sequence of function names that will be executed against each value read from the input.
	 * <p><i>Note: this establishes a chaining of functions: 
	 *  		   If the first function trims strings, then the second function will receive a trimmed String instead of the original value. </i>
	 * @return an optional next configuration step to define the course of action in case a reference cannot 
	 *         be matched (defaults to "ignore and proceed", leaving it null).  
	 */
	public UnmatchedReference readingWith(String... functionNames);

	/**
	 * Does not execute any transformation on the input fields. The original values from the input will be used to restore the identifiers of an entity in the destination
	 * @return an optional next configuration step to define the course of action in case a reference cannot 
	 *         be matched (defaults to "ignore and proceed", leaving it null).
	 */
	public UnmatchedReference directly();
}
