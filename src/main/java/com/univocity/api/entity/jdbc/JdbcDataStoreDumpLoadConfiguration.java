/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

import java.io.*;
import java.nio.charset.*;
import java.sql.*;

import com.univocity.api.common.*;

/**
 * This class provides basic settings that control how to read a database dump file and load its data into a destination database.
 * The dump file can be produced by a database implementation that does not match the destination.
 * For example, you can load a dump file generated from MySQL into Oracle or Postgres.
 *
 * @see JdbcDataStoreConfiguration
 * @see DumpFileFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com
 *
 */
public class JdbcDataStoreDumpLoadConfiguration {
	private int inputBufferSize = 1024 * 1024;
	private boolean readInputOnSeparateThread = Runtime.getRuntime().availableProcessors() > 1;

	private final DumpFileFormat format = new DumpFileFormat();
	private int maxCharsPerColumn = 8192;
	private int maxColumns = 512;

	private boolean processDDLScripts = true;
	private boolean parameterConversionEnabled = false;
	private int batchSize = 10000;

	final FileProvider dumpFile;

	/**
	 * Creates database dump load configuration using a file. The file will be read with the default system encoding.
	 * @param file the dump File
	 */
	public JdbcDataStoreDumpLoadConfiguration(File file) {
		dumpFile = new FileProvider(file);
	}

	/**
	 * Creates database dump load configuration using the file represented by a given path. The file will be read with the default system encoding.
	 * @param pathToFile the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 */
	public JdbcDataStoreDumpLoadConfiguration(String pathToFile) {
		dumpFile = new FileProvider(pathToFile);
	}

	/**
	 * Creates database dump load configuration using a file. The file will be read using the given encoding
	 * @param file the dump File
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public JdbcDataStoreDumpLoadConfiguration(File file, Charset encoding) {
		dumpFile = new FileProvider(file, encoding);
	}

	/**
	 * Creates database dump load configuration using the file represented by a given path. The file will be read with the given encoding
	 * @param pathToFile the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public JdbcDataStoreDumpLoadConfiguration(String pathToFile, Charset encoding) {
		dumpFile = new FileProvider(pathToFile, encoding);
	}

	/**
	 * Creates database dump load configuration using a file. The file will be read using the given encoding
	 * @param file the dump File
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public JdbcDataStoreDumpLoadConfiguration(File file, String encoding) {
		dumpFile = new FileProvider(file, encoding);
	}

	/**
	 * Creates database dump load configuration using the file represented by a given path. The file will be read with the given encoding
	 * @param pathToFile the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 * @param encoding the encoding that must be used to read from the given file.
	 */

	public JdbcDataStoreDumpLoadConfiguration(String pathToFile, String encoding) {
		dumpFile = new FileProvider(pathToFile, encoding);
	}

	/**
	 * The maximum number of characters allowed for any given value being written/read. Used to avoid OutOfMemoryErrors (defaults to 8192).
	 * @return The maximum number of characters allowed for any given value being written/read
	 */
	public int getMaxCharsPerColumn() {
		return maxCharsPerColumn;
	}

	/**
	 * Defines the maximum number of characters allowed for any given value being written/read. Used to avoid OutOfMemoryErrors (defaults to 8192).
	 * @param maxCharsPerColumn The maximum number of characters allowed for any given value being written/read
	 */
	public void setMaxCharsPerColumn(int maxCharsPerColumn) {
		this.maxCharsPerColumn = maxCharsPerColumn;
	}

	/**
	 *  Returns the hard limit of how many columns a record can have (defaults to 512).
	 * 	You need this to avoid OutOfMemory errors in case of inputs that might be inconsistent with the format you are dealing width .
	 * @return The maximum number of columns a record can have.
	 */
	public int getMaxColumns() {
		return maxColumns;
	}

	/**
	 *  Defines a hard limit of how many columns a record can have (defaults to 512).
	 * 	You need this to avoid OutOfMemory errors in case of inputs that might be inconsistent with the format you are dealing width.
	 * @param maxColumns The maximum number of columns a record can have.
	 */
	public void setMaxColumns(int maxColumns) {
		this.maxColumns = maxColumns;
	}

	/**
	 * The format of the file to be parsed/written (returns the format's defaults).
	 * @return The format of the file to be parsed/written
	 */
	public DumpFileFormat getFormat() {
		return format;
	}

	/**
	 * Indicates whether or not a separate thread will be used to read characters from the input while parsing the database dump file
	 *  (defaults true if the number of available processors at runtime is greater than 1)
	 * 	<p>When enabled, a reading thread  will be started and load characters from the input, while the parser is processing its input buffer.
	 *     This yields better performance, especially when reading from big inputs (greater than 100 mb)
	 *  <p>When disabled, the parsing process will briefly pause so the buffer can be replenished every time it is exhausted
	 * @return true if the input should be read on a separate thread, false otherwise
	 */
	public boolean getReadInputOnSeparateThread() {
		return readInputOnSeparateThread;
	}

	/**
	 * Defines whether or not a separate thread will be used to read characters from the input while parsing the database dump file
	 * (defaults true if the number of available processors at runtime is greater than 1)
	 * 	<p>When enabled, a reading thread will be started and load characters from the input, while the
	 *     parser is processing its input buffer. This yields better performance, especially when reading from big input (greater than 100 mb)
	 *  <p>When disabled, the parsing process will briefly pause so the buffer can be replenished every time it is exhausted
	 * @param readInputOnSeparateThread the flag indicating whether or not the input should be read on a separate thread
	 */
	public void setReadInputOnSeparateThread(boolean readInputOnSeparateThread) {
		this.readInputOnSeparateThread = readInputOnSeparateThread;
	}

	/**
	 * Informs the number of characters held by the parser's buffer when processing the input (defaults to 1024*1024 characters).
	 * @return the number of characters held by the parser's buffer when processing the input
	 */
	public int getInputBufferSize() {
		return inputBufferSize;
	}

	/**
	 * Defines the number of characters held by the parser's buffer when processing the input (defaults to 1024*1024 characters).
	 * @param inputBufferSize the new input buffer size (in number of characters)
	 */
	public void setInputBufferSize(int inputBufferSize) {
		this.inputBufferSize = inputBufferSize;
	}

	/**
	 * Flag that tells uniVocity to parse the DDL scripts in the dump file and execute them accordingly. Defaults to {@code true}.
	 * If {@code false}, uniVocity will simply try to insert the data from the dump file into the corresponding database table. Please ensure the table exists.
	 * @return true if uniVocity should read the DDL scripts (such as CREATE TABLE) and execute them before attempting to insert data to the destination table.
	 */
	public boolean isProcessDDLScripts() {
		return processDDLScripts;
	}

	/**
	 * Defines whether uniVocity should parse the DDL scripts in the dump file and execute them before attempting to insert data to the destination table.
	 * If set to {@code false}, uniVocity will simply try to insert the data from the dump file into the corresponding database table. Please ensure the table exists.
	 * @param processDDLScripts true if uniVocity should read the DDL scripts (such as CREATE TABLE) and execute them before attempting to insert data to the destination table.
	 */
	public void setProcessDDLScripts(boolean processDDLScripts) {
		this.processDDLScripts = processDDLScripts;
	}

	/**
	 * Indicates whether values loaded from the input file will be converted to expected database type by uniVocity.
	 * This might be required for some JDBC drivers that won't convert values automatically when
	 * {@link PreparedStatement#setObject(int, Object)} is used.
	 *
	 * For example, on insertion if a field of type Integer is written, this setting will make uniVocity try to convert the value and
	 * then invoke {@link PreparedStatement#setInt(int, int)}.
	 *
	 * @return parameterConversionEnabled indicates whether uniVocity will convert parameter values when calling prepared statements.
	 */
	public boolean isParameterConversionEnabled() {
		return parameterConversionEnabled;
	}

	/**
	 * Attempts to convert values passed to prepared statements to the expected database type.
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
	 * Obtains the number of rows to be persisted in a single batch execution.
	 * <p>This setting has an effect only when {@link DatabaseCapabilities#isBatchSupported()} evaluates to true.
	 * <p>Batching database operations greatly improves performance in general, but you might want to adjust the batch size to better control memory usage and batch duration.
	 * <p><i>Defaults to 10,000 rows</i>
	 * @return the batch size to use when loading data from the database dump file.
	 * @see DatabaseCapabilities
	 */
	public final int getBatchSize() {
		return this.batchSize;
	}

	/**
	 * Defines the number of rows to be persisted in a single batch execution.
	 * <p>This setting has an effect only when {@link DatabaseCapabilities#isBatchSupported()} evaluates to true.
	 * <p>Batching database operations greatly improves performance in general, but you might want to adjust the batch size to better control memory usage and batch duration.
	 * @param batchSize the batch size to use when loading data from the database dump file.
	 * @see DatabaseCapabilities
	 */
	public final void setBatchSize(int batchSize) {
		Args.positive(batchSize, "Batch size");
		this.batchSize = batchSize;
	}
}
