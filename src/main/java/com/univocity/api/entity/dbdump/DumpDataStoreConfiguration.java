/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.dbdump;

import java.io.*;
import java.nio.charset.*;

import com.univocity.api.common.*;
import com.univocity.api.entity.custom.*;

/**
 *
 * The {@code DumpDataStoreConfiguration} provides the essential configurations of all data stores based on
 * database dump files. This is a special type of data store where data entities are loaded while the
 * dump file is read. You can create entity mappings using the names of each table in the dump file, in any order.
 *
 * <p><b>Note:</b> it is optimal to execute your entity mappings following the order in which the tables and their data appear in the dump file.</p>
 *
 * @see DumpFileFormat
 * @see DatabaseScriptCallback
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 * @param <F> The format configuration of a database dump file
 *
 */
public abstract class DumpDataStoreConfiguration<F extends DumpFileFormat> extends DataStoreConfiguration {

	private final F format = newDefaultFormat();
	private int maxCharsPerColumn = 16384;
	private int maxColumns = 512;
	private int inputBufferSize = 1024 * 1024;
	private boolean readInputOnSeparateThread = Runtime.getRuntime().availableProcessors() > 1;
	private DatabaseScriptCallback databaseScriptCallback = null;
	private int limitOfRowsLoadedInMemory = 10000;

	final FileProvider dumpFile;

	/**
	 * Creates a database dump load configuration using a file. The data store name will be the name of the file, without the file extension. 
	 * The file will be read with the default system encoding.
	 * @param file the dump File
	 */
	public DumpDataStoreConfiguration(File file) {
		this(null, file, new FileProvider(file));
	}

	/**
	 * Creates a database dump load configuration using a file. The file will be read with the default system encoding.
	 * @param dataStoreName the name of this data store.
	 * @param file the dump File
	 */
	public DumpDataStoreConfiguration(String dataStoreName, File file) {
		this(dataStoreName, file, new FileProvider(file));
	}

	/**
	 * Creates a database dump load configuration using the file represented by a given path. The file will be read with the default system encoding.
	 * @param dataStoreName the name of this data store.
	 * @param pathToFile the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 */
	public DumpDataStoreConfiguration(String dataStoreName, String pathToFile) {
		this(dataStoreName, null, new FileProvider(pathToFile));
	}

	/**
	 * Creates a database dump load configuration using a file. The data store name will be the name of the file, without the file extension. The file will be read using the given encoding
	 * @param file the dump File
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public DumpDataStoreConfiguration(File file, Charset encoding) {
		this(null, file, new FileProvider(file, encoding));
	}

	/**
	 * Creates a database dump load configuration using a file. The file will be read using the given encoding
	 * @param dataStoreName the name of this data store.
	 * @param file the dump File
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public DumpDataStoreConfiguration(String dataStoreName, File file, Charset encoding) {
		this(dataStoreName, file, new FileProvider(file, encoding));
	}

	/**
	 * Creates a database dump load configuration using the file represented by a given path. The file will be read with the given encoding
	 * @param dataStoreName the name of this data store.
	 * @param pathToFile the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public DumpDataStoreConfiguration(String dataStoreName, String pathToFile, Charset encoding) {
		this(dataStoreName, null, new FileProvider(pathToFile, encoding));
	}

	/**
	 * Creates a database dump load configuration using a file. The data store name will be the name of the file, without the file extension.
	 * The file will be read using the given encoding
	 * @param file the dump File
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public DumpDataStoreConfiguration(File file, String encoding) {
		this(null, file, new FileProvider(file, encoding));
	}

	/**
	 * Creates a database dump load configuration using a file. The file will be read using the given encoding
	 * @param dataStoreName the name of this data store.
	 * @param file the dump File
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public DumpDataStoreConfiguration(String dataStoreName, File file, String encoding) {
		this(dataStoreName, file, new FileProvider(file, encoding));
	}

	/**
	 * Creates a database dump load configuration using the file represented by a given path. The file will be read with the given encoding
	 * @param dataStoreName the name of this data store.
	 * @param pathToFile the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public DumpDataStoreConfiguration(String dataStoreName, String pathToFile, String encoding) {
		this(dataStoreName, null, new FileProvider(pathToFile, encoding));
	}

	private DumpDataStoreConfiguration(String dataStoreName, File file, FileProvider fileProvider) {
		super(Args.guessAndValidateName(dataStoreName, file, "name of database dump data store"));
		Args.validFile(file, "database dump file used by data store " + getDataStoreName());
		this.dumpFile = fileProvider;
	}

	/**
	 * The maximum number of characters allowed for any given value being written/read. Used to avoid OutOfMemoryErrors (defaults to 16384 characters).
	 * @return The maximum number of characters allowed for any given value being written/read
	 */
	public final int getMaxCharsPerColumn() {
		return maxCharsPerColumn;
	}

	/**
	 * Defines the maximum number of characters allowed for any given value being written/read. Used to avoid OutOfMemoryErrors (defaults to 16384 characters).
	 * @param maxCharsPerColumn The maximum number of characters allowed for any given value being written/read
	 */
	public final void setMaxCharsPerColumn(int maxCharsPerColumn) {
		this.maxCharsPerColumn = maxCharsPerColumn;
	}

	/**
	 *  Returns the hard limit of how many columns a record can have (defaults to 512 column).
	 * 	You need this to avoid OutOfMemory errors in case of inputs that might be inconsistent with the format you are dealing width .
	 * @return The maximum number of columns a record can have.
	 */
	public final int getMaxColumns() {
		return maxColumns;
	}

	/**
	 *  Defines a hard limit of how many columns a record can have (defaults to 512 columns).
	 * 	You need this to avoid OutOfMemory errors in case of inputs that might be inconsistent with the format you are dealing width.
	 * @param maxColumns The maximum number of columns a record can have.
	 */
	public final void setMaxColumns(int maxColumns) {
		this.maxColumns = maxColumns;
	}

	/**
	 * The format of the file to be parsed/written (returns the format's defaults).
	 * @return The format of the file to be parsed/written
	 */
	public final DumpFileFormat getFormat() {
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
	public final boolean getReadInputOnSeparateThread() {
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
	public final void setReadInputOnSeparateThread(boolean readInputOnSeparateThread) {
		this.readInputOnSeparateThread = readInputOnSeparateThread;
	}

	/**
	 * Informs the number of characters held by the parser's buffer when processing the input (defaults to 1024*1024 characters).
	 * @return the number of characters held by the parser's buffer when processing the input
	 */
	public final int getInputBufferSize() {
		return inputBufferSize;
	}

	/**
	 * Defines the number of characters held by the parser's buffer when processing the input (defaults to 1024*1024 characters).
	 * @param inputBufferSize the new input buffer size (in number of characters)
	 */
	public final void setInputBufferSize(int inputBufferSize) {
		this.inputBufferSize = inputBufferSize;
	}

	/**
	 * Returns the database script callback interface that can be used by the user to access DDL scripts as they are parsed from the database dump file.
	 * Defaults to {@code null}
	 * @return the database script callback
	 */
	public final DatabaseScriptCallback getDatabaseScriptCallback() {
		return databaseScriptCallback;
	}

	/**
	 * Defines a database script callback that can be used by the user to access DDL scripts as they are parsed from the database dump file.
	 * Defaults to {@code null}
	 * @param databaseScriptCallback the database script callback
	 */
	public final void setDatabaseScriptCallback(DatabaseScriptCallback databaseScriptCallback) {
		this.databaseScriptCallback = databaseScriptCallback;
	}
	
	/**
	 * Obtains the maximum number of rows loaded in memory at a time when extracting information from this database dump.
	 * <p><i>Defaults to 10,000 rows</i>
	 * @return the maximum number of rows kept in memory at any given time when reading values from any table in the database dump file
	 */
	@Override
	public final int getLimitOfRowsLoadedInMemory() {
		return limitOfRowsLoadedInMemory;
	}

	/**
	 * Defines the maximum number of rows loaded in memory at a time when extracting information from this database dump.
	 * <p><i>Defaults to 10,000 rows</i>
	 * @param rowLimit the maximum number of rows kept in memory at any given time when reading values from any table in the database dump file
	 */
	public final void setLimitOfRowsLoadedInMemory(int rowLimit) {
		Args.positive(rowLimit, "Number of rows loaded in memory by database dump data store " + getDataStoreName());
		limitOfRowsLoadedInMemory = rowLimit;
	}

	/**
	 * Creates a new instance of a database dump file format configuration.
	 * @return a new instance of a database dump file format configuration.
	 */
	protected abstract F newDefaultFormat();

}
