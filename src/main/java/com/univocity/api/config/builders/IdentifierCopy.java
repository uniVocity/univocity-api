/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

/**
 * The <code>IdentifierCopy</code> configuration is obtained from a  {@link IdentifierMappingSetup} using {@link IdentifierMappingSetup#associate(String...)}.
 *
 * <p>It is used to define which identifier fields of a destination entity should receive values extracted from a selection of fields in the source entity.
 *
 * @see FieldMappingSetup
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface IdentifierCopy {
	/**
	 * Defines which identifier fields of a destination entity should receive values extracted from a selection of fields in the source entity.
	 *
	 * @param destinationFields the fields of the destination entity. Source and destination fields must:
	 * <ul>
	 *  <li>have the same number of elements, or</li>
	 *  <li>either the source or destination fields must contain one element only if the other has multiple fields.
	 *  	In this case a function or reference mapping will be necessary to transform the input.
	 *  </li>
	 * </ul>
	 *
	 * @return the next (optional) step of an identifier mapping configuration:
	 * <ul>
	 * <li>define what functions should be executed to transform input values before copying them to the destination fields</li>
	 * <li>associate more source fields to identifiers of the destination entity</li>
	 * </ul>
	 */
	public IdentifierTransform to(String... destinationFields);
}
