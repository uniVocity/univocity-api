/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

import com.univocity.api.common.*;
import com.univocity.api.entity.*;

/**
 * This class provides configuration options for JDBC data entities. Instances are accessible through a {@link JdbcDataStoreConfiguration} configuration object.
 *
 * @see DatabaseCapabilities
 * @see BaseJdbcEntityConfiguration
 * @see JdbcDataStoreConfiguration
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class JdbcEntityConfiguration extends BaseJdbcEntityConfiguration {

	private Integer batchSize;
	private SqlProducer sqlProducer = null;
	GeneratedKeyRetrieval generatedKeyRetrieval;
	String generatedKeyColumn;
	String[] otherGeneratedColumnsToRetrieve;
	String trackingColumn;
	String processIdentificationColumn;

	enum GeneratedKeyRetrieval {
		Statement,
		StatementBatch,
		Query,
		StringColumn,
		NumericColumns,
	}

	/**
	 * Package protected constructor as instances of this class are only obtained from JdbcDataStoreConfiguration
	 */
	JdbcEntityConfiguration() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void copyDefaultsFrom(Configuration defaultsObject) {
		super.copyDefaultsFrom(defaultsObject);
		JdbcEntityConfiguration defaults = (JdbcEntityConfiguration) defaultsObject;
		if (this.batchSize == null) {
			this.batchSize = defaults.getBatchSize();
		}
	}

	/**
	 * Obtains the number of rows to be persisted in a single batch execution.
	 * <p>This setting has an effect only when {@link DatabaseCapabilities#isBatchSupported()} evaluates to true.
	 * <p>Batching database operations greatly improves performance in general, but you might want to adjust the batch size to better control memory usage and batch duration.
	 * <p><i>Defaults to 10,000 rows</i>
	 * @return the batch size to use when persisting values into the configured JDBC data entity.
	 * @see DatabaseCapabilities
	 */
	public final int getBatchSize() {
		if (batchSize == null) {
			return 10000;
		}
		return this.batchSize;
	}

	/**
	 * Defines the number of rows to be persisted in a single batch execution.
	 * <p>This setting has an effect only when {@link DatabaseCapabilities#isBatchSupported()} evaluates to true.
	 * <p>Batching database operations greatly improves performance in general, but you might want to adjust the batch size to better control memory usage and batch duration.
	 * @param batchSize the batch size to use when persisting values into the configured JDBC data entity.
	 * @see DatabaseCapabilities
	 */
	public final void setBatchSize(int batchSize) {
		Args.positive(batchSize, "Batch size");
		this.batchSize = batchSize;
	}

	/**
	 * Specifies auto-generated keys should be extracted using the JDBC driver's
	 * {@link java.sql.Statement#getGeneratedKeys()} method when new rows are inserted into this entity.
	 * @param insertInBatch a flag indicating whether to perform insert operations in batch or not. This has the following implications:
	 * <ul>
	 * 	<li><i>if true: </i>insert operations will be batched and generated keys will be returned in a single ResultSet by your JDBC driver.
			<br><b>Your JDBC driver might not support that.</b> If that's the case and you need to have batch insertion for performance reasons,
			    you might want to update your JDBC driver, or if it is not possible, use one of the following auto-generated key retrieval strategies:
			<ul>
				<li>{@link #retrieveGeneratedKeysUsingQuery(String, String...)}</li>
				<li>{@link #retrieveGeneratedKeysUsingStringColumn(String, String, String...)}</li>
				<li>{@link #retrieveGeneratedKeysUsingNumericColumns(String, String, String, String...)}</li>
			</ul>
		</li>
	 *  <li><i>if false:</i> insert operations will not be batched. For each row to insert, a single insert statement will be executed,
	 *         an the auto-generated key for the inserted row will be read using {@link java.sql.Statement#getGeneratedKeys()}.
	 *         <br>As this involves many database roundtrips, performance is much worse than inserting in batch and then extracting all generated keys in one go.
	 *         <br>If you are experiencing performance issues you might want to study the possibility of using the alternative strategies for generated key extraction provided by this class.
	 *  </li>
	 * </ul>
	 *
	 * @see  java.sql.Statement
	 */
	public final void retrieveGeneratedKeysUsingStatement(boolean insertInBatch) {
		if (insertInBatch) {
			generatedKeyRetrieval = GeneratedKeyRetrieval.StatementBatch;
		} else {
			generatedKeyRetrieval = GeneratedKeyRetrieval.Statement;
		}
	}

	/**
	 * Specifies auto-generated keys should be extracted using a query after a batch of row inserts took place. You can safely use this if your transaction isolation level is set to {@link java.sql.Connection#TRANSACTION_SERIALIZABLE}.
	 *
	 * <p>This strategy performs the following sequence of operations to fetch generated keys:
	 * <ul>
	 * 	<li>Obtains the last generated identifier from this table</li>
	 *  <li>Performs all insert operations in batch</li>
	 *  <li>Queries all rows where the identifier is greater than the one obtained before insertion, and sorts them in ascending order</li>
	 *  <li>Assigns each identifier retrieved to each inserted row, in order of insertion</li>
	 *  <li><i>This assumes identifiers are generated in sequential order, following the same order of insertion.</i></li>
	 * </ul>
	 *
	 * <p>The transaction isolation level used by your JDBC data entities is configured in {@link JdbcDataStoreConfiguration#setTransactionIsolationLevel(int)}.
	 *
	 * <p>If you can't use the {@link java.sql.Connection#TRANSACTION_SERIALIZABLE} transaction isolation level, then this strategy have data consistency implications you must be aware of:
	 * <ul>
	 * 	<li>When inserting rows to this entity, you must ensure no other process is modifying the database table.</li>
	 *  <li>An exception will be thrown in case the key extraction query produces more (or less) results than the number of rows inserted, but you are still at risk of getting inconsistent data if another process adds or removes rows to the same table at the same time</li>
	 *  <li>Consider using another generated key extraction strategy if there is any risk of data corruption.</li>
	 * </ul>
	 *
	 * @param generatedKeyColumn the name of the auto-generated key column of this table.
	 * @param otherGeneratedColumnsToRetrieve the names of other columns with auto-generated values you are interested in retrieving as well.
	 */
	public final void retrieveGeneratedKeysUsingQuery(String generatedKeyColumn, String... otherGeneratedColumnsToRetrieve) {
		Args.notBlank(generatedKeyColumn, "Generated key column name");
		generatedKeyRetrieval = GeneratedKeyRetrieval.Query;
		this.generatedKeyColumn = generatedKeyColumn;
		this.otherGeneratedColumnsToRetrieve = otherGeneratedColumnsToRetrieve;
	}

	/**
	 * Specifies auto-generated keys should be extracted using an additional column, assigned to uniVocity, for tracking rows in a batch of insert operations.
	 *
	 * <p>This strategy performs the following sequence of operations to fetch generated keys:
	 * <ul>
	 * 	<li>Creates an identifier for this batch</li>
	 *  <li>Modifies each row before adding it to the batch, by setting a sequential tracking value for the tracking column</li>
	 *  <li>Performs all insert operations and executes a query that fetches all rows with the batch identifier</li>
	 *  <li>Assigns each identifier retrieved to each inserted row</li>
	 *  <li>Updates the table to set the tracking columns with the generated batch identifier to null</li>
	 *  <li><i>This strategy depends on a VARCHAR column with a length of at least 70 characters</i></li>
	 * </ul>
	 *
	 * @param trackingColumn the name of the column used by uniVocity to provide row tracking information.
	 * @param generatedKeyColumn the name of the auto-generated key column of this table.
	 * @param otherGeneratedColumnsToRetrieve the names of other columns with auto-generated values you are interested in retrieving as well.
	 */
	public final void retrieveGeneratedKeysUsingStringColumn(String trackingColumn, String generatedKeyColumn, String... otherGeneratedColumnsToRetrieve) {
		Args.notBlank(trackingColumn, "Tracking column name");
		Args.notBlank(generatedKeyColumn, "Generated key column name");
		generatedKeyRetrieval = GeneratedKeyRetrieval.StringColumn;
		this.generatedKeyColumn = generatedKeyColumn;
		this.trackingColumn = trackingColumn;
		this.otherGeneratedColumnsToRetrieve = otherGeneratedColumnsToRetrieve;
	}

	/**
	 * Specifies auto-generated keys should be extracted using two additional columns, assigned to uniVocity, for tracking rows in a batch of insert operations.
	 *
	 * <p>This strategy performs the following sequence of operations to fetch generated keys:
	 * <ul>
	 * 	<li>Creates an identifier for this batch</li>
	 *  <li>Modifies each row before adding it to the batch, by setting a sequential tracking value for the tracking column</li>
	 *  <li>Performs all insert operations and executes a query that fetches all rows with the batch identifier</li>
	 *  <li>Assigns each identifier retrieved to each inserted row</li>
	 *  <li>Updates the table to set the batch identification and row tracking columns to null</li>
	 *  <li><i>This strategy depends on two INTEGER columns</i></li>
	 * </ul>
	 *
	 * @param processIdentificationColumn the name of the column used by uniVocity to identify the batch process.
	 * @param trackingColumn the name of the column used by uniVocity to identify the index of each row added to the batch.
	 * @param generatedKeyColumn the name of the auto-generated key column of this table.
	 * @param otherGeneratedColumnsToRetrieve the names of other columns with auto-generated values you are interested in retrieving as well.
	 */
	public final void retrieveGeneratedKeysUsingNumericColumns(String processIdentificationColumn, String trackingColumn, String generatedKeyColumn, String... otherGeneratedColumnsToRetrieve) {
		Args.notBlank(processIdentificationColumn, "Process identification column name");
		Args.notBlank(trackingColumn, "Tracking column name");
		Args.notBlank(generatedKeyColumn, "Generated key column name");
		generatedKeyRetrieval = GeneratedKeyRetrieval.NumericColumns;
		this.generatedKeyColumn = generatedKeyColumn;
		this.otherGeneratedColumnsToRetrieve = otherGeneratedColumnsToRetrieve;
		this.trackingColumn = trackingColumn;
		this.processIdentificationColumn = processIdentificationColumn;
	}

	/**
	 * Obtains the custom {@link SqlProducer} responsible for generating user-specific SQL statements when reading or writing data to this entity.
	 * @return the custom {@link SqlProducer}
	 */
	public final SqlProducer getSqlProducer() {
		return sqlProducer;
	}

	/**
	 * Defines a custom {@link SqlProducer} responsible for generating user-specific SQL statements when reading or writing data to this entity.
	 * @param sqlProducer the {@link SqlProducer}
	 */
	public final void setSqlProducer(SqlProducer sqlProducer) {
		this.sqlProducer = sqlProducer;
	}
}
