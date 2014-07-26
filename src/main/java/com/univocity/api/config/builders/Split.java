/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>Split</code> configuration is obtained from a  {@link FieldMappingSetup} using {@link FieldMappingSetup#split(String)}.
 *  
 * <p>It is used to define what function in the {@link DataIntegrationEngine} should be used to split values in an input field into multiple values that are
 * then written to multiple fields at the destination.    
 * 
 * @see FieldMappingSetup
 * @see FunctionCall
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface Split {
	/**
	 * Defines what function in the {@link DataIntegrationEngine} should be used to split values in an input field into multiple values that are
	 * then written to multiple fields at the destination.
	 *   
	 * @param functionName the name of the splitting function.
	 * @return the next step of a field mapping configuration: define what destination fields in a 
	 * {@link FieldMappingSetup} will receive values obtained from the splitting function.
	 */
	public SplitDestination with(String functionName);
}
