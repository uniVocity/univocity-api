/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

import com.univocity.api.common.*;
import com.univocity.api.entity.dbdump.*;

/**
 * This class provides basic settings that control how to read a database dump file and load its data into any database represented by a JDBC data store.
 * The dump file can be produced by a database implementation that does not match the destination.
 * For example, you can load a dump file generated from MySQL into Oracle or Postgres.
 *
 * <p>
 * If the dump file matches the destination database, you can execute the DDL scripts (if any) using {@link #setExecuteScripts(boolean)}.
 * Alternatively, you can use a {@link DatabaseScriptCallback} to modify the scripts parsed by uniVocity in order to make them work on the destination database.
 * </p>
 *
 *
 * @see JdbcDataStoreConfiguration
 * @see DumpFileFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public class JdbcDataStoreDumpLoad {

	private boolean executeScripts = false;
	private int batchSize = 10000;
	final DumpDataStoreConfiguration<?> databaseDumpDataStore;

	public <T extends DumpDataStoreConfiguration<?>> JdbcDataStoreDumpLoad(T databaseDumpDataStore) {
		this.databaseDumpDataStore = databaseDumpDataStore;
	}

	/**
	 * Flag that tells uniVocity to parse the DDL scripts in the dump file and execute them accordingly. Defaults to {@code false}.
	 * If {@code false}, uniVocity will simply try to insert the data from the dump file into the corresponding database table. Please ensure the table exists.
	 * @return true if uniVocity should read the DDL scripts (such as CREATE TABLE) and execute them before attempting to insert data to the destination table.
	 */
	public final boolean isExecuteScripts() {
		return executeScripts;
	}

	/**
	 * Defines whether uniVocity should parse the DDL scripts in the dump file and execute them before attempting to insert data to the destination table.
	 * If set to {@code false}, uniVocity will simply try to insert the data from the dump file into the corresponding database table. Please ensure the table exists.
	 * @param processDDLScripts true if uniVocity should read the DDL scripts (such as CREATE TABLE) and execute them before attempting to insert data to the destination table.
	 */
	public final void setExecuteScripts(boolean processDDLScripts) {
		this.executeScripts = processDDLScripts;
	}

	/**
	 * Obtains the number of rows to be persisted in a single batch execution.
	 * <p>This setting has an effect only when {@link JdbcDataStoreConfiguration#getDatabaseInformation()} returns {@link DatabaseCapabilities#isBatchSupported()} == true.
	 * <p>Batching database operations greatly improves performance in general, but you might want to adjust the batch size to better control memory usage and batch duration.
	 * <p><i>Defaults to 10,000 rows</i>
	 * @return the batch size to use when loading values from a dump file into the configured JDBC data store.
	 * @see DatabaseCapabilities
	 */
	public final int getBatchSize() {
		return this.batchSize;
	}

	/**
	 * Defines the number of rows to be persisted in a single batch execution.
	 * <p>This setting has an effect only when {@link JdbcDataStoreConfiguration#getDatabaseInformation()} returns {@link DatabaseCapabilities#isBatchSupported()} == true.
	 * <p>Batching database operations greatly improves performance in general, but you might want to adjust the batch size to better control memory usage and batch duration.
	 * <p><i>Defaults to 10,000 rows</i>
	 * @param batchSize the batch size to use when loading values from a dump file into the configured JDBC data store.
	 * @see DatabaseCapabilities
	 */
	public final void setBatchSize(int batchSize) {
		Args.positive(batchSize, "Batch size");
		this.batchSize = batchSize;
	}

}
