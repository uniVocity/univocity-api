/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

/**
 * The <code>SplitDestination</code> configuration is obtained from a {@link Split} configuration, which is part of the configuration initialized
 * by a call to {@link FieldMappingSetup#split(String)}.
 *
 * <p>It is used to define what fields of the destination entity in a {@link FieldMappingSetup} should receive values from a split operation applied to an input field.
 *
 * @see FieldMappingSetup
 * @see Split
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface SplitDestination {
	/**
	 * Defines what fields of the destination entity in a {@link FieldMappingSetup} should receive values from a split operation applied to an input field.
	 *
	 * <p><i>This completes the configuration started in {@link FieldMappingSetup#split(String)}</i>
	 *
	 * @param destinationFields the field names in the destination entity to receive the result of the split operation.
	 */
	public void into(String... destinationFields);
}
