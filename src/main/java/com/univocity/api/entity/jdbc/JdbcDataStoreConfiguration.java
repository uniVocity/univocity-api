/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

import java.io.*;
import java.nio.charset.*;
import java.sql.*;
import java.util.*;

import javax.sql.*;

import com.univocity.api.common.*;
import com.univocity.api.engine.*;
import com.univocity.api.entity.custom.*;

/**
 * The JDBC data store configuration class. Use it to configure the JDBC data entities and queries that will be manipulated by a {@link DataIntegrationEngine}.
 *
 * @see DumpLoadConfiguration
 * @see JdbcEntityConfiguration
 * @see JdbcQueryConfiguration
 * @see DataSource
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public final class JdbcDataStoreConfiguration extends DataStoreConfiguration {

	private final DataSource dataSource;

	private final JdbcEntityConfiguration defaultEntityConfiguration = new JdbcEntityConfiguration();
	private final JdbcQueryConfiguration defaultQueryConfiguration = new JdbcQueryConfiguration();

	final Map<String, JdbcEntityConfiguration> tableConfigurations = new TreeMap<String, JdbcEntityConfiguration>();
	final Map<String, JdbcQueryConfiguration> queryConfigurations = new TreeMap<String, JdbcQueryConfiguration>();

	private String schema;
	private String catalog;

	private int transactionTimeout = -1;
	private int transactionIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ;

	private DatabaseCapabilities databaseInformation = null;
	private final Collection<String> reservedWordsToEscape = new TreeSet<String>();
	private IdentifierEscaper identifierEscaper = null;

	private DumpLoadConfiguration initialDumpLoadConfiguration = null;

	/**
	 * Creates a new JDBC data store configuration
	 * @param dataStoreName the name of the JDBC data store.
	 * @param dataSource the {@code DataSource} used to connect to the underlying database.
	 */
	public JdbcDataStoreConfiguration(String dataStoreName, DataSource dataSource) {
		super(dataStoreName);
		if (dataSource == null) {
			throw new IllegalArgumentException("Datasource cannot be null");
		}

		this.dataSource = dataSource;
	}

	/**
	 * Obtains the DataSource used to connect to the managed JDBC data entities.
	 * @return the DataSource
	 */
	public final DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Obtains the schema name that should be used to refer to elements of the database managed by this JDBC data store.
	 * @return the schema
	 */
	public final String getSchema() {
		return schema;
	}

	/**
	 * Defines the schema name that should be used to refer to elements of the database managed by this JDBC data store.
	 * @param schema the schema name
	 */
	public final void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * Obtains the catalog name that should be used to refer to elements of the database managed by this JDBC data store.
	 * @return the catalog
	 */
	public final String getCatalog() {
		return catalog;
	}

	/**
	 * Defines the catalog name that should be used to refer to elements of the database managed by this JDBC data store.
	 * @param catalog the catalog name
	 */
	public final void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	/**
	 * Obtains an object describing the capabilities of the database managed by this JDBC data store.
	 * <p><i>By default, uniVocity tries to detect this information automatically.</i>
	 * @return the database capabilities information
	 */
	public final DatabaseCapabilities getDatabaseInformation() {
		return databaseInformation;
	}

	/**
	 * Defines the capabilities of the database managed by this JDBC data store.
	 * <p><i>By default, uniVocity tries to detect this information automatically. The configuration you set here takes precedence over any auto-detected data.</i>
	 * @param databaseInformation the database information.
	 */
	public final void setDatabaseInformation(DatabaseCapabilities databaseInformation) {
		Args.notNull(databaseInformation, "Database capabilities object");
		this.databaseInformation = databaseInformation;
	}

	/**
	 * Returns the configuration of a given data entity. The returned configuration object will contain default values provided in the object returned by {@link #getDefaultEntityConfiguration()}.
	 * <br>If the entity was not explicitly configured before, a new configuration object will be created and associated with the entity.
	 * <p>You can change the default configuration at any time by calling {@link #getDefaultEntityConfiguration()} and altering the settings you need.
	 *    Subsequent calls to {@link #getEntityConfiguration(String)} will return a configuration object with the new defaults.
	 *    Existing configurations will retain their original values, including the default settings previously used.
	 * @param tableName the name of the entity to be configured
	 * @return a configuration object for the given data entity.
	 */
	public final JdbcEntityConfiguration getEntityConfiguration(String tableName) {
		Args.notBlank(tableName, "Table name");
		JdbcEntityConfiguration out = tableConfigurations.get(tableName);
		if (out == null) {
			out = new JdbcEntityConfiguration();
			tableConfigurations.put(tableName, out);
		}
		out.copyDefaultsFrom(defaultEntityConfiguration);

		return out;
	}

	/**
	 * Returns the default configuration object for JDBC data entities.
	 * This object returns all default values used for creating new configuration objects for data entities.
	 * @return the {@link #defaultEntityConfiguration} object.
	 */
	public final JdbcEntityConfiguration getDefaultEntityConfiguration() {
		return defaultEntityConfiguration;
	}

	/**
	 * Returns the default configuration object for JDBC query entities.
	 * This object returns all default values used for creating new configuration objects for query entities.
	 * @return the {@link #defaultQueryConfiguration} object.
	 */
	public final JdbcQueryConfiguration getDefaultQueryConfiguration() {
		return defaultQueryConfiguration;
	}

	/**
	 * Creates a new SQL query entity from a resource and adds it to this data store. The resource path can be a file in the class path or in the file system.
	 * @param queryName the name of the query
	 * @param resourcePath the path to a resource that contains an SQL query. Parameters must be prefixed with a colon, for example: <i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
	 * <p><i><b>Note: </b>As the resource encoding is not provided, the default system encoding will be used.</i>
	 * @return a configuration object for the new query.
	 */
	public final JdbcQueryConfiguration addQueryFromResource(String queryName, String resourcePath) {
		return addQuery(queryName, new FileProvider(resourcePath));
	}

	/**
	 * Creates a new SQL query entity from a resource and adds it to this data store. The resource path can be a file in the class path or in the file system.
	 * @param queryName the name of the query
	 * @param resourcePath the path to a resource that contains an SQL query. Parameters must be prefixed with a colon, for example: <i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
	 * @param encoding the encoding of the resource
	 * @return a configuration object for the new query.
	 */
	public final JdbcQueryConfiguration addQueryFromResource(String queryName, String resourcePath, String encoding) {
		return addQuery(queryName, new FileProvider(resourcePath, encoding));
	}

	/**
	 * Creates a new SQL query entity from a resource and adds it to this data store. The resource path can be a file in the class path or in the file system.
	 * @param name queryName the name of the query
	 * @param resourcePath the path to a resource that contains an SQL query. Parameters must be prefixed with a colon, for example: <i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
	 * @param encoding the encoding of the resource
	 * @return a configuration object for the new query.
	 */
	public final JdbcQueryConfiguration addQueryFromResource(String name, String resourcePath, Charset encoding) {
		return addQuery(name, new FileProvider(resourcePath, encoding));
	}

	/**
	 * Creates a new SQL query entity from a file and adds it to this data store. The file name will be used as the query name.
	 * @param queryFile a text file containing an SQL query. Parameters must be prefixed with a colon, for example: <i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
	 * <p><i><b>Note: </b>As the encoding of the query file is not provided, the default system encoding will be used.</i>
	 * @return a configuration object for the new query.
	 */
	public final JdbcQueryConfiguration addQuery(File queryFile) {
		return addQuery(queryFile, (Charset) null);
	}

	/**
	 * Creates a new SQL query entity from a file and adds it to this data store. The file name will be used as the query name.
	 * @param queryFile a text file containing an SQL query. Parameters must be prefixed with a colon, for example: <i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
	 * @param encoding the file encoding
	 * @return a configuration object for the new query.
	 */
	public final JdbcQueryConfiguration addQuery(File queryFile, String encoding) {
		Args.validFile(queryFile, "Query file");
		return addQuery(getQueryNameFromFile(queryFile), new FileProvider(queryFile, encoding));
	}

	/**
	 * Creates a new SQL query entity from a file and adds it to this data store. The file name will be used as the query name.
	 * @param queryFile a text file containing an SQL query. Parameters must be prefixed with a colon, for example: <i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
	 * @param encoding the file encoding
	 * @return a configuration object for the new query.
	 */
	public final JdbcQueryConfiguration addQuery(File queryFile, Charset encoding) {
		Args.validFile(queryFile, "Query file");
		return addQuery(getQueryNameFromFile(queryFile), new FileProvider(queryFile, encoding));
	}

	private String getQueryNameFromFile(File queryFile) {
		String name = queryFile.getName();
		int lastDot = name.lastIndexOf('.');
		if (lastDot != -1) {
			return name.substring(0, lastDot);
		}
		return name;
	}

	/**
	 * Creates a new SQL query entity from a file and adds it to this data store.
	 * @param queryName the name of the query
	 * @param queryFile a text file containing an SQL query. Parameters must be prefixed with a colon, for example: <i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
	 * <p><i><b>Note: </b>As the encoding of the query file is not provided, the default system encoding will be used.</i>
	 * @return a configuration object for the new query.
	 */
	public final JdbcQueryConfiguration addQuery(String queryName, File queryFile) {
		return addQuery(queryName, new FileProvider(queryFile));
	}

	/**
	 * Creates a new SQL query entity from a file and adds it to this data store.
	 * @param queryName the name of the query
	 * @param queryFile a text file containing an SQL query. Parameters must be prefixed with a colon, for example: <i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
	 * @param encoding the file encoding
	 * @return a configuration object for the new query.
	 */
	public final JdbcQueryConfiguration addQuery(String queryName, File queryFile, String encoding) {
		Args.validFile(queryFile, "Query file");
		return addQuery(queryName, new FileProvider(queryFile, encoding));
	}

	/**
	 * Creates a new SQL query entity from a file and adds it to this data store.
	 * @param queryName the name of the query
	 * @param queryFile a text file containing an SQL query. Parameters must be prefixed with a colon, for example: <i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
	 * @param encoding the file encoding
	 * @return a configuration object for the new query.
	 */
	public final JdbcQueryConfiguration addQuery(String queryName, File queryFile, Charset encoding) {
		Args.validFile(queryFile, "Query file");
		return addQuery(queryName, new FileProvider(queryFile, encoding));
	}

	/**
	 * Creates a new SQL query entity and adds it to this data store.
	 * @param queryName the name of the query
	 * @param query the SQL query. Parameters must be prefixed with a colon, for example: <i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
	 * @return a configuration object for the new query.
	 */
	public final JdbcQueryConfiguration addQuery(String queryName, String query) {
		return addQuery(queryName, new JdbcQueryConfiguration(query));
	}

	private JdbcQueryConfiguration addQuery(String queryName, FileProvider fileProvider) {
		return addQuery(queryName, new JdbcQueryConfiguration(fileProvider));
	}

	private JdbcQueryConfiguration addQuery(String queryName, JdbcQueryConfiguration config) {
		Args.notBlank(queryName, "Query name");
		String key = queryName.trim();
		if (queryConfigurations.containsKey(key)) {
			throw new IllegalArgumentException("Duplicate query name: " + key);
		}
		queryConfigurations.put(key, config);

		return config;
	}

	/**
	 * Returns the configuration of a given query entity. The returned configuration object will contain default values provided in the object returned by {@link #getDefaultQueryConfiguration()}.
	 * <br>If the query was not explicitly configured before, a new configuration object will be created and associated with it.
	 * <p>You can change the default configuration at any time by calling {@link #getDefaultQueryConfiguration()} and altering the settings you need.
	 *    Subsequent calls to {@link #getQueryConfiguration(String)} will return a configuration object with the new defaults.
	 *    Existing configurations will retain their original values, including the default settings previously used.
	 * @param queryName the name of the query to be configured
	 * @return a configuration object for the given query entity.
	 */
	public final JdbcQueryConfiguration getQueryConfiguration(String queryName) {
		Args.notBlank(queryName, "Query name");
		queryName = queryName.trim();
		if (!queryConfigurations.containsKey(queryName)) {
			throw new IllegalArgumentException("Cannot find query with name: " + queryName);
		}
		JdbcQueryConfiguration out = queryConfigurations.get(queryName);
		out.copyDefaultsFrom(defaultQueryConfiguration);

		return out;
	}

	/**
	 * Obtains the timeout, in number of seconds, for transactions created from this data store.
	 * <p>Transactions will be created automatically by uniVocity while performing data mapping operations to this data store.
	 * <br>A new transaction will be created for each mapping cycle started with {@link DataIntegrationEngine#executeCycle()}.
	 * <p><i>Defaults to -1 (no timeout)</i>
	 * @return the transaction timeout.
	 */
	public final int getTransactionTimeout() {
		return transactionTimeout;
	}

	/**
	 * Defines a timeout, in number of seconds, for transactions created from this data store.
	 * <p>Transactions will be created automatically by uniVocity while performing data mapping operations to this data store.
	 * <br>A new transaction will be created for each mapping cycle started with {@link DataIntegrationEngine#executeCycle()}.
	 * @param transactionTimeout the transaction timeout.
	 */
	public final void setTransactionTimeout(int transactionTimeout) {
		Args.positive(transactionTimeout, "Transaction timeout");
		this.transactionTimeout = transactionTimeout;
	}

	/**
	 * Obtains the transaction isolation level used when persisting data into entities of this data store.
	 * <br>The transaction isolation level code returned by this method is passed on
	 * to {@link java.sql.Connection#setTransactionIsolation(int)} before mapping data to an entity.
	 * <p><i>Defaults to {@link java.sql.Connection#TRANSACTION_REPEATABLE_READ}</i></p>
	 * @return the transaction isolation level
	 */
	public final int getTransactionIsolationLevel() {
		return transactionIsolationLevel;
	}

	/**
	 * Defines the transaction isolation level used when persisting data into entities of this data store.
	 * <br>The transaction isolation level code returned by this method is passed on
	 * to {@link java.sql.Connection#setTransactionIsolation(int)} before mapping data to an entity.
	 * <p><i>Only the constants defined in {@link java.sql.Connection} are accepted.</i></p>
	 * @param transactionIsolationLevel the transaction isolation level
	 */
	public final void setTransactionIsolationLevel(int transactionIsolationLevel) {
		Args.validTransactionIsolationLevel(transactionIsolationLevel);
		this.transactionIsolationLevel = transactionIsolationLevel;
	}

	/**
	 * Obtains the maximum number of rows loaded in memory at a time when extracting information from entities and queries in this data store.
	 * <p>This number is obtained from default fetch size in {@link #getDefaultEntityConfiguration()}.
	 * <p><i>Defaults to 10,000 rows</i>
	 * @return the maximum number of rows kept in memory at any given time when reading values from any entity or query of this data store.
	 */
	@Override
	public final int getLimitOfRowsLoadedInMemory() {
		return defaultEntityConfiguration.getFetchSize();
	}

	/**
	 * Defines the maximum number of rows loaded in memory at a time when extracting information from entities and queries in this data store.
	 * <p>This number modifies the default fetch size in {@link #getDefaultEntityConfiguration()} and {@link #getDefaultQueryConfiguration()}.
	 * <br>The fetch size of already configured entities and queries won't be modified.
	 * @param rowLimit the maximum number of rows kept in memory at any given time when reading values from any entity or query of this data store.
	 */
	public final void setLimitOfRowsLoadedInMemory(int rowLimit) {
		this.defaultEntityConfiguration.setFetchSize(rowLimit);
		this.defaultQueryConfiguration.setFetchSize(rowLimit);
	}

	/**
	 * Adds user-defined reserved words that might conflict with table and column names in SQL statements.
	 * <p>When generating SQL, an {@link IdentifierEscaper} will handle name conflicts and escape any table or column names that are part of potential reserved words.
	 * <p>uniVocity already escapes most common reserved words used by different database vendors. Use this method to provide any additional reserved words.
	 * @param reservedWords additional reserved words to escape.
	 */
	public final void addReservedWordsToEscape(String... reservedWords) {
		Args.notEmpty(reservedWords, "Reserved words");
		for (String reservedWord : reservedWords) {
			this.reservedWordsToEscape.add(reservedWord);
		}
	}

	/**
	 * Adds user-defined reserved words that might conflict with table and column names in SQL statements.
	 * <p>When generating SQL, an {@link IdentifierEscaper} will handle name conflicts and escape any table or column names that are part of potential reserved words.
	 * <p>uniVocity already escapes most common reserved words used by different database vendors. Use this method to provide any additional reserved words.
	 * @param reservedWords additional reserved words to escape.
	 */
	public final void addReservedWordsToEscape(Collection<String> reservedWords) {
		Args.notEmpty(reservedWords, "Reserved words");
		for (String reservedWord : reservedWords) {
			this.reservedWordsToEscape.add(reservedWord);
		}
	}

	/**
	 * Returns the collection of user-defined reserved words provided with {@link #addReservedWordsToEscape(String...)}.
	 * @return an unmodifiable collection of reserved words.
	 */
	public final Collection<String> getReservedWordsToEscape() {
		return Collections.unmodifiableCollection(reservedWordsToEscape);
	}

	/**
	 * Provides a user-defined {@link IdentifierEscaper} to handle reserved word escaping when generating SQL statements.
	 * <p> This will override uniVocity's default implementation that escapes reserved words by enclosing them within double quotes(e.g. "escaped identifier").
	 * @param escape a custom implementation of {@link IdentifierEscaper}
	 */
	public final void setIdentifierEscaper(IdentifierEscaper escape) {
		Args.notNull(escape, "Implementation of com.univocity.api.entity.jdbc.IdentifierEscaper");
		this.identifierEscaper = escape;
	}

	/**
	 * Returns the user-defined {@link IdentifierEscaper} configured to handle reserved word escaping when SQL statements are generated.
	 * @return a custom implementation of {@link IdentifierEscaper}, or {@code null} if uniVocity's default implementation should be used.
	 */
	public final IdentifierEscaper getIdentifierEscaper() {
		return this.identifierEscaper;
	}

	/**
	 * Has no effect over JDBC data entities as they already support database operations.
	 */
	@Override
	public final void enableDatabaseOperationsIn(String... tableNames) {
	}

	/**
	 * Has no effect over JDBC data entities as they already support database operations.
	 */
	@Override
	public final void enableDatabaseOperationsIn(Collection<String> entityNames) {

	}

	/**
	 * Returns an empty set as JDBC data entities already support database operations.
	 */
	@Override
	public final Set<String> getDatabaseEnabledEntities() {
		return Collections.emptySet();
	}

	/**
	 * Configures this data store to load the entire database from a dump file. The dump file does not need to be compatible with the destination
	 * database. For example, you can load a dump file generated from MySQL into Oracle or Postgres.
	 *
	 * @param initialDumpLoadConfiguration the configuration of the input dump file.
	 */
	public void setInitialDumpLoadConfiguration(DumpLoadConfiguration initialDumpLoadConfiguration) {
		this.initialDumpLoadConfiguration = initialDumpLoadConfiguration;
	}

	/**
	 * Returns the configuration for this data store to initialize from a database dump file. The dump file does not need to be compatible with the destination
	 * database. For example, you can load a dump file generated from MySQL into Oracle or Postgres.
	 *
	 * @return the configuration to process and load a database dump file, or null if the database does not need to be initialized.	 */
	public DumpLoadConfiguration getInitialDumpLoadConfiguration() {
		return this.initialDumpLoadConfiguration;
	}

}
