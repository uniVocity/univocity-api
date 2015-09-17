/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>Merge</code> configuration is obtained from a  {@link FieldMappingSetup} using {@link FieldMappingSetup#merge(String...)}.
 *
 * <p>It is used to define what function in the {@link DataIntegrationEngine} should be used to merge values from multiple input fields into a value that is
 * then written to a single field at the destination.
 *
 * @see FieldMappingSetup
 * @see FunctionCall
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface Merge {
	/**
	 * Defines what function in the {@link DataIntegrationEngine} should be used to merge values from multiple input fields into a value that is
	 * then written to a single field at the destination.
	 *
	 * @param functionName the name of the merging function.
	 * @return the next step of a field mapping configuration: define what destination field in a
	 * {@link FieldMappingSetup} will receive the value obtained from the merging function.
	 */
	public MergeDestination using(String functionName);

	public MergeDestination using(FunctionCall<?,?> functionCall);
}
