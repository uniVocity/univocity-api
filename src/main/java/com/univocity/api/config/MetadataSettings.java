/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config;

import java.sql.*;

import javax.sql.*;

import com.univocity.api.common.*;
import com.univocity.api.engine.*;
import com.univocity.api.entity.jdbc.*;

/**
 * This class provides the required configuration for uniVocity's metadata handling.
 * If this configuration is not provided by the user, then a in-memory instance of a database with the essential
 * metadata structure will be created automatically. This of course, means any metadata information will be lost
 * when the application is shut down.
 *
 * <p>uniVocity requires two database tables only: by default, these have the names "univocity_metadata" and "univocity_tmp".
 * The scripts to create these tables are provided with the univocity implementation package, under the "metadata" folder.</p>
 *
 * <p><i><b>Important: </b> always refer to the script in the implementation package as it could have been modified to adapt to the specific implementation requirements.</i></p>
 *
 * <p>Just as a reference, the table creation script should be similar to the following:</p>
 *
 * <hr><blockquote><pre>
 *	CREATE TABLE univocity_metadata (
 *		source              VARCHAR(256) NOT NULL,
 *		source_id           VARCHAR(256) NOT NULL,
 *		source_hash         INTEGER,
 *		destination         VARCHAR(256) NOT NULL,
 *		destination_id      VARCHAR(256) NOT NULL,
 *		destination_hash    INTEGER,
 *		flag                CHARACTER(1) DEFAULT 'N',
 *		batch_id            CHARACTER(36) DEFAULT NULL,
 *		cycle_id            INTEGER NOT NULL,
 *		CONSTRAINT pk_univocity_md PRIMARY KEY (source, source_id, destination, destination_id)
 *	)
 *
 *	CREATE TABLE univocity_tmp (
 *		table_name          VARCHAR(256) NOT NULL,
 *		table_id            VARCHAR(256) NOT NULL,
 *		table_hash          INTEGER,
 *		batch_id            CHARACTER(36) DEFAULT NULL,
 *		CONSTRAINT pk_univocity_tmp PRIMARY KEY (table_name, table_id)
 *	)
 * </pre></blockquote><hr>
 *
 * @see EngineConfiguration
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class MetadataSettings {

	private DataSource dataSource;
	private String metadataTableName = "univocity_metadata";
	private String temporaryTableName = "univocity_tmp";
	private DatabaseCapabilities databaseInformation = null;
	private int batchSize = 10000;
	private int transactionTimeout = -1;
	private int transactionIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ;
	private int fetchSize = 10000;

	/**
	 * Creates a new metadata setting configuration with a {@link javax.sql.DataSource}.
	 * The data source must provide a connection to the database that contains the metadata tables.
	 * @param dataSource the factory that provides connections to a database configured to use the necessary metadata tables.
	 */
	public MetadataSettings(DataSource dataSource) {
		Args.notNull(dataSource, "Data source");
		this.dataSource = dataSource;
	}

	/**
	 * Obtains the number of metadata rows to be persisted in a single batch execution.
	 * <p>This setting has an effect only when {@link DatabaseCapabilities#isBatchSupported()} evaluates to {@code true}.
	 * <p>Batching database operations greatly improves performance in general, but you might want to adjust the batch size to better control memory usage and batch duration.
	 * <p><i>Defaults to 10,000 rows</i>
	 * @return the batch size to use when persisting metadata records into the configured metadata table
	 * @see DatabaseCapabilities
	 */
	public final int getBatchSize() {
		return batchSize;
	}

	/**
	 * Defines the number of metadata rows to be persisted in a single batch execution.
	 * <p>This setting has an effect only when {@link DatabaseCapabilities#isBatchSupported()} evaluates to true.
	 * <p>Batching database operations greatly improves performance in general, but you might want to adjust the batch size to better control memory usage and batch duration.
	 * @param batchSize the batch size to use when persisting metadata records into the configured metadata table
	 * @see DatabaseCapabilities
	 */
	public final void setBatchSize(int batchSize) {
		Args.positive(batchSize, "Batch size");
		this.batchSize = batchSize;
	}

	/**
	 * Obtains the configured metadata table name configured for uniVocity.
	 *  <p><i>Defaults to "univocity_metadata"</i></p>
	 * @return the name of the metadata table
	 */
	public final String getMetadataTableName() {
		return metadataTableName;
	}

	private final void validateTableNames(String meta, String tmp) {
		if (meta.equalsIgnoreCase(tmp)) {
			throw new IllegalArgumentException("Metadata table name cannot be the same as the temporary table name");
		}
	}

	/**
	 * Defines the name of the table configured for uniVocity metadata storage.
	 * @param metadataTableName the name of the metadata table
	 */
	public final void setMetadataTableName(String metadataTableName) {
		Args.notBlank(metadataTableName, "Metadata table name");
		validateTableNames(metadataTableName, this.temporaryTableName);
		this.metadataTableName = metadataTableName.trim();
	}

	/**
	 * Obtains the configured table name for storing temporary data produced during uniVocity's metadata operations.
	 *  <p><i>Defaults to "univocity_tmp"</i>
	 * @return the name of the temporary metadata table
	 */
	public final String getTemporaryTableName() {
		return temporaryTableName;
	}

	/**
	 * Defines the name of the table that should be used by uniVocity's to store temporary data for its metadata operations.
	 * @param temporaryTableName the name of the temporary metadata table
	 */
	public final void setTemporaryTableName(String temporaryTableName) {
		Args.notBlank(metadataTableName, "Metadata table name");
		validateTableNames(this.metadataTableName, temporaryTableName);
		this.temporaryTableName = temporaryTableName.trim();
	}

	/**
	 * Obtains an object describing the capabilities of the database that stores uniVocity metadata.
	 * <p><i>By default, uniVocity tries to detect this information automatically.</i>
	 * @return the database capabilities information
	 */
	public final DatabaseCapabilities getDatabaseInformation() {
		return databaseInformation;
	}

	/**
	 * Defines the capabilities of the database that stores uniVocity metadata.
	 * <p><i>By default, uniVocity tries to detect this information automatically. The configuration you set here takes precedence over any auto-detected data.</i>
	 * @param databaseInformation the database information.
	 */
	public final void setDatabaseInformation(DatabaseCapabilities databaseInformation) {
		Args.notNull(databaseInformation, "Database capabilities object");
		this.databaseInformation = databaseInformation;
	}

	/**
	 * @return the {@link javax.sql.DataSource} that provides connections to a database configured to use the necessary metadata tables.
	 */
	public final DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Obtains the timeout, in number of seconds, for transactions created for metadata manipulation.
	 * <p>Transactions will be created automatically by uniVocity while performing data mapping operations between two entities.
	 * <br>A new transaction will be created for each data mapping cycle executed with {@link DataIntegrationEngine#executeCycle()}.
	 * <p><i>Defaults to -1 (no timeout)</i>
	 * @return the transaction timeout.
	 */
	public final int getTransactionTimeout() {
		return transactionTimeout;
	}

	/**
	 * Defines a timeout, in number of seconds, for transactions created for metadata manipulation.
	 * <p>Transactions will be created automatically by uniVocity while performing data mapping cycles that involve multiple entity mappings.
	 * <br>A new transaction will be created for mapping cycle executed with {@link DataIntegrationEngine#executeCycle()}.
	 * @param transactionTimeout the transaction timeout.
	 */
	public final void setTransactionTimeout(int transactionTimeout) {
		Args.positive(transactionTimeout, "Transaction timeout");
		this.transactionTimeout = transactionTimeout;
	}

	/**
	 * Obtains the transaction isolation level used when persisting metadata.
	 * <br>The transaction isolation level code returned by this method is passed on
	 * to {@link java.sql.Connection#setTransactionIsolation(int)} before writing to the metadata table
	 * <p><i>Defaults to {@link java.sql.Connection#TRANSACTION_REPEATABLE_READ}</i></p>
	 * @return the transaction isolation level
	 */
	public final int getTransactionIsolationLevel() {
		return transactionIsolationLevel;
	}

	/**
	 * Defines the transaction isolation level used when persisting metadata.
	 * <br>The transaction isolation level code returned by this method is passed on
	 * to {@link java.sql.Connection#setTransactionIsolation(int)} before writing to the metadata table
	 * <p><i>Only the constants defined in java.sql.Connection are accepted.</i></p>
	 * @param transactionIsolationLevel the transaction isolation level
	 */
	public final void setTransactionIsolationLevel(int transactionIsolationLevel) {
		Args.validTransactionIsolationLevel(transactionIsolationLevel);
		this.transactionIsolationLevel = transactionIsolationLevel;
	}

	/**
	 * Obtains the number of rows to return for an open {@code ResultSet} in a each each trip to the database.
	 * <p>uniVocity passes this value directly to your database driver using {@link java.sql.Statement#setFetchSize(int)}.
	 * <p>For performance reasons, you might want to adjust the fetch size to reflect the common number of rows returned
	 *    for metadata operations. A big fetch size number might consume too many resources and will be excessive for a small number of records.
	 *    A small fetch size for too many rows will cause slowness (e.g. a fetch size of 100 to read 10,000 rows and will generate 100 roundtrips to the database).
	 *
	 * <p><i>Defaults to 10,000 rows</i>
	 * @return the fetch size to use when reading metadata.
	 * @see java.sql.Statement
	 */
	public final int getFetchSize() {
		return fetchSize;
	}

	/**
	 * Defines the number of rows to return for an open {@code ResultSet} in a each each trip to the database.
	 * <p>uniVocity passes this value directly to your database driver using {@link java.sql.Statement#setFetchSize(int)}.
	 * <p>For performance reasons, you might want to adjust the fetch size to reflect the common number of rows returned
	 *    for metadata operations. A big fetch size number might consume too many resources and will be excessive for a small number of records.
	 *    A small fetch size for too many rows will cause slowness (e.g. a fetch size of 100 to read 10,000 rows and will generate 100 roundtrips to the database).
	 *
	 * @param fetchSize the fetch size to use when reading metadata.
	 * <br>This parameter is not validated as some database drivers accept special settings for the fetch size, such as Integer.MIN_VALUE.
	 * @see java.sql.Statement
	 */
	public final void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

}
