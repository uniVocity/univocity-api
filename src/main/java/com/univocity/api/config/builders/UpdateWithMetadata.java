/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.data.*;
import com.univocity.api.engine.*;

/**
 * The <code>UpdateWithMetadata</code> configuration is obtained from a  {@link PersistenceSetup} using {@link PersistenceSetup#usingMetadata()}.
 * It provides configuration options for update operations that rely on uniVocity metadata
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
public interface UpdateWithMetadata extends UpdateWithoutMetadata {

	/**
	 * Configures the {@link EntityMapping} to update records of the destination entity only 
	 * if the associated source records have changed since the previous mapping cycle. 
	 * 
	 * <p><i>Records flagged as disabled for updates (using {@link DataIntegrationEngine#disableUpdateOnRecords(String, Dataset)}) will remain unchanged. </i>
	 * 
	 * @return the next step of this configuration: configure insertion of new records into the destination entity.
	 */
	public InsertionWithMetadata updateModified();

	/**
	 * Configures the {@link EntityMapping} to use any mapped records to update and override what is stored in the destination entity.
	 * <ul>
	 *  <li>Records flagged as disabled for updates will remain unchanged (using {@link DataIntegrationEngine#disableUpdateOnRecords(String, Dataset)}) </li>
	 *  <li>Existing records in the destination entity will remain unchanged if a record previously mapped does not exist in the source anymore.</li>
	 * </ul>
	 * @return the next step of this configuration: configure insertion of new records into the destination entity.
	 */
	@Override
	public InsertionWithMetadata updateOverride();

	/**
	 * Configures the {@link EntityMapping} to keep all existing records in the destination entity unchanged.
	 * @return the next step of this configuration: configure insertion of new records into the destination entity.
	 */
	@Override
	public InsertionWithMetadata updateDisabled();
}
