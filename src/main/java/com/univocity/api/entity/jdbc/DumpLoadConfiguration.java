/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

import java.sql.*;
import java.util.*;

import com.univocity.api.common.*;
import com.univocity.api.entity.dbdump.*;

/**
 * This class provides basic settings that control how to read a database dump file and load its data into any database represented by a JDBC data store.
 * Use it in conjunction with a {@link JdbcDataStoreConfiguration} in {@link JdbcDataStoreConfiguration#setInitialDumpLoadConfiguration(DumpLoadConfiguration)}.
 * The dump file can be produced by a database implementation that does not match the destination.
 * For example, you can load a dump file generated from MySQL into Oracle or Postgres.
 *
 * <p>
 * If the dump file matches the destination database, you can execute the DDL scripts (if any) using {@link #setExecuteScripts(boolean)}.
 * Alternatively, you can use a {@link DatabaseScriptCallback} to modify the scripts parsed by uniVocity in order to make them work on the destination database.
 * </p>
 *
 * @see JdbcDataStoreConfiguration
 * @see DumpFileFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public class DumpLoadConfiguration {

	private boolean executeScripts = false;
	private int batchSize = 10000;
	private boolean parameterConversionEnabled = false;
	private final Set<String> tablesToLoad = new TreeSet<String>();
	private final Set<String> tablesToSkip = new TreeSet<String>();
	final DumpDataStoreConfiguration<?> databaseDumpDataStore;

	public <T extends DumpDataStoreConfiguration<?>> DumpLoadConfiguration(T databaseDumpDataStore) {
		Args.notNull(databaseDumpDataStore, "Database dump configuration");
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

	/**
	 * Indicates whether values read from the dump file will be converted to the expected database type by uniVocity.
	 * This might be required for some JDBC drivers that won't convert values automatically when
	 * {@link PreparedStatement#setObject(int, Object)} is used. Defaults to {@code false}.
	 *
	 * For example, on insertion if a field of type Integer is written, this setting will make uniVocity try to convert the value and
	 * then invoke {@link PreparedStatement#setInt(int, int)}.
	 *
	 * @return parameterConversionEnabled indicates whether uniVocity will convert parameter values when inserting data from the dump file.
	 */
	public boolean isParameterConversionEnabled() {
		return parameterConversionEnabled;
	}

	/**
	 * Attempts to convert values read from the dump file will be converted to the expected database type.
	 * This might be required for some JDBC drivers that won't convert values automatically when
	 * {@link PreparedStatement#setObject(int, Object)} is used.
	 *
	 * For example, on insertion if a field of type Integer is written, this setting will make uniVocity try to convert the value and
	 * then invoke {@link PreparedStatement#setInt(int, int)}.
	 *
	 * @param parameterConversionEnabled indicates whether to convert parameter values on prepared statements.
	 */
	public void setParameterConversionEnabled(boolean parameterConversionEnabled) {
		this.parameterConversionEnabled = parameterConversionEnabled;
	}

	/**
	 * Defines a set of tables to be read from the database dump file. Values of tables that are not part of the given collection will be skipped.
	 * An empty or null set means all tables will be processed.
	 * @param tableNames names of those tables in the dump file that should be processed.
	 */
	public void setTablesToLoad(Collection<String> tableNames) {
		setTables(tablesToLoad, tablesToSkip, tableNames);
	}
	
	/**
	 * Defines a set of tables to be read from the database dump file. Values of tables that are not part of the given collection will be skipped.
	 * An empty or null set means all tables will be processed.
	 * @param tableNames names of those tables in the dump file that should be processed.
	 */
	public void setTablesToLoad(String ... tableNames) {
		setTables(tablesToLoad, tablesToSkip, tableNames);
	}

	/**
	 * Returns the set of tables to be read from the database dump file. Values of tables that are not part of the given collection will be skipped.
	 * An empty set means all tables will be processed.
	 * @return the names of those tables in the dump file that should be processed.
	 */
	public Collection<String> getTablesToLoad() {
		return Collections.unmodifiableSet(tablesToLoad);
	}

	/**
	 * Defines a set of tables to be skipped from the database dump file. Values of tables that are part of the given collection will be skipped.
	 * An empty or null set means no tables will be skipped.
	 * @param tableNames names of those tables in the dump file that should not be processed.
	 */
	public void setTablesToSkip(String ... tableNames) {
		setTables(tablesToSkip, tablesToLoad, tableNames);
	}

	
	/**
	 * Defines a set of tables to be skipped from the database dump file. Values of tables that are part of the given collection will be skipped.
	 * An empty or null set means no tables will be skipped.
	 * @param tableNames names of those tables in the dump file that should not be processed.
	 */
	public void setTablesToSkip(Collection<String> tableNames) {
		setTables(tablesToSkip, tablesToLoad, tableNames);
	}

	/**
	 * Returns the set of tables to be skipped from the database dump file. Values of tables that are part of the given collection will be skipped.
	 * An empty set means no tables will be skipped.
	 * @return the names of those tables in the dump file that should not be processed.
	 */
	public Collection<String> getTablesToSkip() {
		return Collections.unmodifiableSet(tablesToSkip);
	}
	
	private void setTables(Set<String> set, Set<String> other,String ... tableNames) {
		Set<String> names = new HashSet<String>();
		for(String name : tableNames){
			if(name != null){
				names.add(name);
			}
		}
		setTables(set, other, names);
	}

	private void setTables(Set<String> set, Set<String> other, Collection<String> tableNames) {
		if (tableNames == null || tableNames.isEmpty()) {
			set.clear();
		} else {
			for (String table : tableNames) {
				Args.notBlank(table, "Name of table in database dump");
				if (other.contains(table)) {
					throw new IllegalArgumentException("Cannot set table '" + table + "' to be loaded and skipped at the same time.");
				}
			}
			set.clear();
			for (String table : tableNames) {
				set.add(table);
			}
		}
	}
}
