/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

/**
 * Class used to provide information about the capabilities of a database accessed by uniVocity.
 *
 * <br>uniVocity tries to extract this information automatically, but you can provide it manually. User-provided settings will override auto-detected information.
 *
 * @see JdbcDataStoreConfiguration
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class DatabaseCapabilities {
	private boolean isBatchSupported;
	private boolean isKeyGenerationSupported;

	/**
	 * Identifies whether batching is supported or not. Batch operations provide better performance and uniVocity will try to use batching whenever possible.
	 * @return a flag indicating whether batch operations are supported by the database accessed by entities of a JDBC data store.
	 */
	public final boolean isBatchSupported() {
		return isBatchSupported;
	}

	/**
	 * Defines whether batching is supported or not. Batch operations provide better performance and uniVocity will try to use batching whenever possible.
	 * @param isBatchSupported the indication that batch operations are supported by the database accessed by entities of a JDBC data store.
	 */
	public final void setBatchSupported(boolean isBatchSupported) {
		this.isBatchSupported = isBatchSupported;
	}

	/**
	 * Identifies whether automatic key generation is supported or not. This is used for collecting generated keys when inserting new rows in tables with identity columns.
	 * @return a flag indicating whether generated keys are supported by the database accessed by entities of a JDBC data store.
	 */
	public final boolean isKeyGenerationSupported() {
		return isKeyGenerationSupported;
	}

	/**
	 * Defines whether automatic key generation is supported or not. This is used for collecting generated keys when inserting new rows in tables with identity columns.
	 * @param isKeyGenerationSupported the indication that generated keys are supported by the database accessed by entities of a JDBC data store.
	 */
	public final void setKeyGenerationSupported(boolean isKeyGenerationSupported) {
		this.isKeyGenerationSupported = isKeyGenerationSupported;
	}

	@Override
	public final String toString() {
		return "DatabaseCapabilities [isBatchSupported=" + isBatchSupported + ", isKeyGenerationSupported=" + isKeyGenerationSupported + "]";
	}

}
