/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text.tsv;

import com.univocity.api.engine.*;
import com.univocity.api.entity.text.*;

/**
 * The TSV data store configuration class. Use it to configure the TSV data entities that will be manipulated by a {@link DataIntegrationEngine}.
 *
 * @see TsvEntityConfiguration
 * @see TsvFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public final class TsvDataStoreConfiguration extends TextDataStoreConfiguration<TsvEntityConfiguration> {

	/**
	 * Creates a new TSV data store configuration
	 * @param dataStoreName the name of the TSV data store.
	 */
	public TsvDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName, new TsvEntityConfiguration());
	}

	/**
	 * Creates a configuration object for a TSV data entity.
	 */
	@Override
	public final TsvEntityConfiguration newEntityConfiguration() {
		return new TsvEntityConfiguration();
	}
	
	@Override
	protected String getDefaultFileExtension() {
		return "tsv";
	}
}
