/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>CopyToSingleField</code> configuration is obtained from a  {@link FieldMappingSetup} using {@link FieldMappingSetup#copy(String)}.
 * It is used to define what field of a destination entity should receive values extracted from a field of the source entity.
 *
 * @see FieldMappingSetup
 * @see FunctionCall
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface CopyToSingleField {
	/**
	 * Defines what what field of a destination entity should receive values extracted from a field of the source entity.
	 *
	 * @param destinationField the field of the destination entity
	 * @return the next (optional) step of a field mapping configuration: defining what functions should be executed
	 * to transform input values before copying them to the destination field.
	 */
	public CopyTransform to(String destinationField);
}
