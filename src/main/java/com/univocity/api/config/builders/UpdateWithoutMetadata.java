/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>UpdateWithoutMetadata</code> configuration is obtained from a  {@link PersistenceSetup} using {@link PersistenceSetup#notUsingMetadata()}.
 * It provides configuration options for update operations that do not rely on uniVocity metadata
 *
 * <p>It is used to define how the {@link DataIntegrationEngine} should update records in the destination entity of a {@link EntityMapping}
 *    when executing a data mapping cycle.
 *
 * @see EntityMapping
 * @see PersistenceSetup
 * @see DataIntegrationEngine
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface UpdateWithoutMetadata {

	/**
	 * Configures the {@link EntityMapping} to use any mapped records to update and override the records stored in the destination entity.
	 * <ul>
	 * 	<li>New records mapped from the source entity will be ignored and won't be inserted into the destination.</li>
	 * 	<li>Existing records in the destination entity will remain unchanged if a record previously mapped does not exist in the source anymore.</li>
	 * </ul>
	 * @return the (optional) next step of this configuration: explicitly disable the insertion of new records. Without using metadata,
	 *         uniVocity is currently unable to distinguish whether to update or insert new records.
	 */
	public InsertionAfterUpdate updateOverride();

	/**
	 * Configures the {@link EntityMapping} to keep all existing records in the destination entity unchanged.
	 * @return the next step of this configuration: configure insertion of new records into the destination entity.
	 */
	public InsertionConfig updateDisabled();
}
