/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>CopyTransform</code> configuration is obtained from either a {@link CopyToSingleField} configuration, or a {@link CopyToMultipleFields},
 * which are part of the configuration initialized by a call to {@link FieldMappingSetup#copy(String)}, {@link FieldMappingSetup#copy(String...)} or {@link FieldMappingSetup#read(String...)} .
 *
 * <p>It is used to define what functions in the {@link DataIntegrationEngine} should be used to process values from an input field and transform them into values
 * that will then be written to a destination field.
 *
 * @see FieldMappingSetup
 * @see FunctionCall
 * @see CopyToSingleField
 * @see CopyToMultipleFields
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface CopyTransform {
	/**
	 * Defines what sequence of functions in the {@link DataIntegrationEngine} should be used to transform values from an input field and transform them into values
	 * that will then be written to a destination field.
	 *
	 * <p><i>This completes the configuration started in {@link FieldMappingSetup#copy(String)}, {@link FieldMappingSetup#copy(String...)} or {@link FieldMappingSetup#read(String...)} </i>
	 *
	 * @param functionNames the sequence of function names that will be executed against each value read from the input.
	 * <p><i>Note: </i> this establishes a chaining of functions:
	 *  				If the first function trims strings, then the second function will receive a trimmed String instead of the original value.
	 */
	public void readingWith(String... functionNames);
}
