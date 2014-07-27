/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

/**
 * The <code>AdditionalIdentifiers</code> configuration is obtained from an {@link IdentifierType} or {@link IdentifierTransform} configuration.
 *
 * <p>It is an optional configuration to associate additional fields from the source entity
 * in an {@link EntityMapping} to other identifier fields in the destination entity.
 *
 * @see IdentifierMappingSetup
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface AdditionalIdentifiers {

	/**
	 * This is an optional configuration used to associate additional fields from the source entity
	 * in an {@link EntityMapping} to other identifier fields in the destination entity.
	 *
	 * @param sourceFields the field names in the source entity to be associated to other identifier fields of the destination.
	 * 		   Expressions are allowed within curly braces (i.e. "{expression}")
	 * @return the next step of this configuration: define what fields of the destination are identifiers and how they will
	 *         receive the values read from the source.
	 */
	public IdentifierCopy and(String... sourceFields);
}
