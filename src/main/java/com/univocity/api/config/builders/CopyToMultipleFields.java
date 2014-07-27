/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>CopyToMultipleFields</code> configuration is obtained from a  {@link FieldMappingSetup} using {@link FieldMappingSetup#copy(String...)}.
 * It is used to define what fields of a destination entity should receive values extracted from a selection of fields in the source entity.
 *
 * @see FieldMappingSetup
 * @see FunctionCall
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface CopyToMultipleFields {
	/**
	 * Defines what what fields of a destination entity should receive values extracted from a selection of fields in the source entity.
	 *
	 * @param destinationFields the fields of the destination entity. The number of destination fields must match the number of source fields.
	 * @return the next (optional) step of a field mapping configuration: defining what functions should be executed
	 * to transform input values before copying them to the destination fields.
	 */
	public CopyTransform to(String... destinationFields);
}
