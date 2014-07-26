/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.config.*;
import com.univocity.api.data.*;
import com.univocity.api.engine.*;

/**
 * The <code>ExclusionWithMetadata</code> configuration is obtained from a  {@link PersistenceSetup} using {@link PersistenceSetup#usingMetadata()}.
 *  
 * <p>It is used to define how the {@link DataIntegrationEngine} should delete records in the destination entity of a {@link EntityMapping}
 *    when executing a data mapping cycle.
 * 
 * @see EntityMapping
 * @see PersistenceSetup
 * @see DataIntegrationEngine
 * @see MetadataSettings
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface ExclusionWithMetadata extends ExclusionWithoutMetadata {

	/**
	 * Configures the {@link EntityMapping} to delete all records stored in the destination entity, <i>including those flagged as disabled for updates </i> (using {@link DataIntegrationEngine#disableUpdateOnRecords(String, Dataset)}).
	 *
	 * <br>All metadata records associated to the destination entity will also be removed.
	 * 
	 * @return the next step of this configuration: determine how to insert records in the destination entity
	 */
	@Override
	public InsertionConfig deleteAll();

	/**
	 * Configures the {@link EntityMapping} to remove records from the destination entity when the source record does not exist anymore.
	 * 
	 * <p>This depends on metadata information.: 
	 * <ul>
	 * 	<li>A data mapping cycle will be executed by the {@link DataIntegrationEngine} and in the first execution,
	 * 	the identifiers of the source entity will be stored in the metadata, associated to the identifiers of each row mapped in the destination.</li>
	 * 	<li>When a second mapping cycle is executed, the identifiers of the source entity that are in the metadata, but not in the entity itself, will be obtained.</li>
	 *  <li>With this information, uniVocity will get the identifiers in the destination entity that will be deleted, and these rows will be removed</li>
	 * </ul>
	 * 
	 * <p><i><b>Note: </b>This only affects those records that have not been flagged as disabled for updates using {@link DataIntegrationEngine#disableUpdateOnRecords(String, Dataset)}.</i>
	 * 
	 * @return the next step of this configuration: determine how to update records in the destination entity.
	 */
	public UpdateWithMetadata deleteAbsent();

	/**
	 * Configures the {@link EntityMapping} keep all records stored in the destination entity.
	 * @return the next step of this configuration: determine how to update records in the destination entity.
	 */
	@Override
	public UpdateWithMetadata deleteDisabled();

}
