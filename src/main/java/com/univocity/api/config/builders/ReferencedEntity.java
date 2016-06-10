/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

/**
 * The <code>ReferencedEntity</code> configuration is obtained from a {@link ReferenceMappingSetup} with {@link ReferenceMappingSetup#using(String...)}.
 *
 * <p>It is used to identify what source entity has an {@link IdentifierMappingSetup} compatible with the selection of reference fields, and
 *    what destination entity had its identifier associated with these fields.
 *
 * @see ReferenceMappingSetup
 * @see IdentifierMappingSetup
 * @see EntityMapping
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface ReferencedEntity {

	/**
	 * Identifies what entity is being referred to. An {@link EntityMapping} between these entities must exist or, at least the metadata
	 * should contain the association between them.
	 *
	 * @param sourceEntityName the name of the source entity whose identifier can be represented by the source values read from this reference mapping.
	 * @param destinationEntityName the name of the destination entity who is mapped to the given source entity.
	 * @return the next step of a reference mapping configuration: define what destination fields in a
	 * {@link ReferenceMappingSetup} will receive the values of an identifier of the referenced entity.
	 */
	public ReferencedDestinationFields referTo(String sourceEntityName, String destinationEntityName);


	public ReferencedDestinationFields referTo(EntityMapping entityMapping);

}
