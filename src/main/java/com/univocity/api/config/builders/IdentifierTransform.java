/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>IdentifierTransform</code> configuration is obtained from an {@link IdentifierType} configuration 
 * which is part of the configuration initialized by a call to {@link IdentifierMappingSetup#associate(String...)} 
 * 
 * <p>It is used to define what functions in the {@link DataIntegrationEngine} should be used to process values from an input field and transform them into values 
 * that will then be associated to the identifiers of a destination field.    
 * 
 * @see IdentifierMappingSetup
 * @see FunctionCall
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface IdentifierTransform extends AdditionalIdentifiers {
	/**
	 * Defines what sequence of functions in the {@link DataIntegrationEngine} should be used to process values from an input field and transform them into values 
	 * that will then be associated to the identifiers of a destination entity.   
	 * 
	 * @param functionNames the sequence of function names that will be executed against each value read from the input.
	 * <p><i><i>Note: </i> this establishes a chaining of functions: 
	 *  				   If the first function trims strings, then the second function will receive a trimmed String instead of the original value.
	 *  
	 * @return an optional next step of this configuration to associate additional fields from the source entity in to more identifier fields of the destination entity.  
	 */
	public AdditionalIdentifiers readWith(String... functionNames);

}
