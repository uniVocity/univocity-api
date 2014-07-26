/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

/**
 * The <code>ReferencedDestinationFields</code> configuration is obtained from a {@link ReferencedEntity} with {@link ReferencedEntity#referTo(String, String)}. 
 *
 * <p>It is used to define what destination fields in a {@link ReferenceMappingSetup} will receive
 *    the identifier values of the referenced entity.
 * 
 * @see ReferenceMappingSetup
 * @see IdentifierMappingSetup
 * @see EntityMapping
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface ReferencedDestinationFields {
	/**
	 * Defines the destination fields in a {@link ReferenceMappingSetup} that will receive
	 * the identifier values of the referenced entity.
	 * 
	 * @param fieldsOnDestination the fields in the destination entity that refer to another destination entity.
	 * @return the (optional) next steps of a reference mapping configuration:
	 * <ul>
	 *  <li>provide a function sequence that should be executed to transform input values before using them to look for referenced identifiers in the metadata</li>
	 *  <li>configure the course of action in case a reference cannot be matched (defaults to "ignore and proceed", leaving it null)</li>
	 * </ul>
	 */
	public ReferenceTransform on(String... fieldsOnDestination);
}
