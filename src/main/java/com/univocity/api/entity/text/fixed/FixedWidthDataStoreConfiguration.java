/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text.fixed;

import com.univocity.api.engine.*;
import com.univocity.api.entity.text.*;

/**
 * The Fixed-width data store configuration class. Use it to configure the fixed-width data entities that will be manipulated by a {@link DataIntegrationEngine}.
 *
 * @see FixedWidthEntityConfiguration
 * @see FixedWidthFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public final class FixedWidthDataStoreConfiguration extends TextDataStoreConfiguration<FixedWidthEntityConfiguration> {

	/**
	 * Creates a new fixed-width data store configuration
	 * @param dataStoreName the name of the fixed-width data store.
	 */
	public FixedWidthDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName, new FixedWidthEntityConfiguration());
	}

	/**
	 * Creates a configuration object for a fixed-width data entity.
	 */
	@Override
	public final FixedWidthEntityConfiguration newEntityConfiguration() {
		return new FixedWidthEntityConfiguration();
	}
}
