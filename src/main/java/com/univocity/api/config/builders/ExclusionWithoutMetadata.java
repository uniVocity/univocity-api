/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>ExclusionWithoutMetadata</code> configuration is obtained from a  {@link PersistenceSetup} using {@link PersistenceSetup#notUsingMetadata()}.
 * It provides configuration options for deletion operations that do not rely on uniVocity metadata
 *
 * <p>It is used to define how the {@link DataIntegrationEngine} should delete records in the destination entity of a {@link EntityMapping}
 *    when executing a data mapping cycle.
 *
 * @see EntityMapping
 * @see PersistenceSetup
 * @see DataIntegrationEngine
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface ExclusionWithoutMetadata {
	/**
	 * Configures the {@link EntityMapping} to delete all records stored in the destination entity.
	 * @return the next step of this configuration: determine how to insert records in the destination entity
	 */
	public InsertionConfig deleteAll();

	/**
	 * Configures the {@link EntityMapping} to keep all records stored in the destination entity.
	 * @return the next step of this configuration: determine how to update records in the destination entity, without
	 *         the usage of metadata.
	 */
	public UpdateWithoutMetadata deleteDisabled();
}
