/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

/**
 * The <code>MergeDestination</code> configuration is obtained from a {@link Merge} configuration, which is part of the configuration initialized
 * by a call to {@link FieldMappingSetup#merge(String...)}.
 *
 * <p>It is used to define what field of the destination entity in a {@link FieldMappingSetup} should receive the value from a merge operation applied to multiple input fields.
 *
 * @see FieldMappingSetup
 * @see Merge
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface MergeDestination {
	/**
	 * Defines what field of the destination entity in a {@link FieldMappingSetup} should receive the value from a merge operation applied to multiple input fields.
	 *
	 * <p><i>This completes the configuration started in {@link FieldMappingSetup#merge(String...)}</i>
	 *
	 * @param destinationField the field name in the destination entity to receive the result of the merge operation.
	 */
	public void into(String destinationField);
}
