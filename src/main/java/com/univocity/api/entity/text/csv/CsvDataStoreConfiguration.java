/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text.csv;

import com.univocity.api.engine.*;
import com.univocity.api.entity.text.*;

/**
 * The CSV data store configuration class. Use it to configure the CSV data entities that will be manipulated by a {@link DataIntegrationEngine}.
 * 
 * @see CsvEntityConfiguration
 * @see CsvFormat
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public final class CsvDataStoreConfiguration extends TextDataStoreConfiguration<CsvEntityConfiguration> {

	/**
	 * Creates a new CSV data store configuration
	 * @param dataStoreName the name of the CSV data store.
	 */
	public CsvDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName, new CsvEntityConfiguration());
	}

	/**
	 * Creates a configuration object for a CSV data entity.
	 */
	@Override
	protected final CsvEntityConfiguration newEntityConfiguration() {
		return new CsvEntityConfiguration();
	}
}
