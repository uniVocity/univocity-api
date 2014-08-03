/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.config.*;
import com.univocity.api.engine.*;

/**
 * The <code>InsertionWithMetadata</code> configuration is obtained from a  {@link PersistenceSetup} instance.
 *
 * <p>It is used to define how the {@link DataIntegrationEngine}, with the assistance of metadata,
 *    should insert new records into the destination entity of an {@link EntityMapping} when executing a data mapping cycle.
 *
 * @see EntityMapping
 * @see PersistenceSetup
 * @see DataIntegrationEngine
 * @see MetadataSettings
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface InsertionWithMetadata extends InsertionConfig {

	/**
	 * Configures the {@link EntityMapping} to execute updates using any new records, mapped from the source entity, into the destination entity.
	 * <br>This is particularly useful when multiple {@link EntityMapping}s write to the same destination entity. One mapping can insert rows and the second one,
	 *     using this configuration option, will then update these records with information extracted from another source.
	 *
	 * <p><i>This completes the configuration started with {@link EntityMapping#persistence()}.</i>
	 */
	public void updateNewRows();
}
