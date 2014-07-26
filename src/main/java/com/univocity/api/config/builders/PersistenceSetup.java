/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.config.*;
import com.univocity.api.engine.*;

/**
 * The <code>PersistenceSetup</code> is the base configuration used to determine how records mapped using an {@link EntityMapping} should be persisted.    
 * 
 * @see MetadataSettings
 * @see DataIntegrationEngine
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface PersistenceSetup {
	/**
	 * Initializes persistence configuration options of an {@link EntityMapping}, that use uniVocity metadata tables. 
	 * <br>Through the usage of metadata, uniVocity is able to execute complex update operations when mapping data between entities.  
	 * @return the next step of this configuration: define how to remove records in the destination entity, with or without
	 *         the usage of metadata.  
	 */
	public ExclusionWithMetadata usingMetadata();

	/**
	 * Initializes persistence configuration options of an {@link EntityMapping}, that do not use uniVocity metadata tables. 
	 * <br>Without metadata, uniVocity is only able to execute simple operations: 
	 *     when mapping data between entities for a second time, it is unable to differentiate between new and old records.
	 *     You can either delete all records in the destination and insert the new mapped records back, 
	 *     or updated and override all previously mapped records without inserting new ones.
	 *
	 * @return the next step of this configuration: define how to remove records in the destination entity, without
	 *         the usage of metadata.
	 */
	public ExclusionWithoutMetadata notUsingMetadata();
}
