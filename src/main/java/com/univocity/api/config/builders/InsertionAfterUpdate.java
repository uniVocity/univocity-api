/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>InsertionAfterUpdate</code> configuration is obtained from the configuration sequence started with a {@link PersistenceSetup} instance.
 *
 * <p>It is used to disable insertion of new records into the destination entity of an {@link EntityMapping}
 *    when executing a data mapping cycle.
 *
 * @see EntityMapping
 * @see PersistenceSetup
 * @see DataIntegrationEngine
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface InsertionAfterUpdate {

	/**
	 * Configures the {@link EntityMapping} to ignore any new records mapped from the source entity. Insertion will be disabled.
	 *
	 * <p><i>This completes the configuration started with {@link EntityMapping#persistence()} </i>
	 */
	public void insertDisabled();
}
