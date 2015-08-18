/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.dbdump.postgres;

import java.io.*;
import java.nio.charset.*;

import com.univocity.api.common.*;
import com.univocity.api.config.builders.*;
import com.univocity.api.entity.dbdump.*;
import com.univocity.api.entity.jdbc.*;

/**
 * A data store capable of handling Postgres dump files. Table names defined within the database dump script can be used as entities in
 * entity mappings (i.e. through a {@link DataStoreMapping}) as with any other regular data entity. However, only read operations are available.
 *
 * Instances of this class can also be used to initialize any database represented by a {@link JdbcDataStoreConfiguration}, by using its
 * {@link JdbcDataStoreConfiguration#setInitialDumpLoadConfiguration(DumpLoadConfiguration)}.
 *
 * @see PostgresDumpFormat
 * @see DumpLoadConfiguration
 * @see JdbcDataStoreConfiguration
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class PostgresDumpDataStoreConfiguration extends DumpDataStoreConfiguration<PostgresDumpFormat> {

	/**
	 * Creates a Postgres dump configuration using a user-provided {@link java.io.Reader} to read a database dump.
	 * @param dataStoreName the name of this data store.
	 * @param inputProvider a {@link ReaderProvider}, defined by the user, that provides instances of {@link java.io.Reader} for processing a database dump.
	 */
	public PostgresDumpDataStoreConfiguration(String dataStoreName, ReaderProvider inputProvider) {
		super(dataStoreName, inputProvider);
	}

	/**
	 * Creates a Postgres dump configuration using a file. The data store name will be the name of the file, without the file extension.
	 * The file will be read with the default system encoding.
	 * @param file the dump File
	 */
	public PostgresDumpDataStoreConfiguration(File file) {
		super(file);
	}

	/**
	 * Creates a Postgres dump configuration using a file. The file will be read with the default system encoding.
	 * @param dataStoreName the name of this data store.
	 * @param file the dump File
	 */
	public PostgresDumpDataStoreConfiguration(String dataStoreName, File file) {
		super(dataStoreName, file);
	}

	/**
	 * Creates a Postgres dump configuration using the file represented by a given path. The file will be read with the default system encoding.
	 * @param dataStoreName the name of this data store.
	 * @param pathToFile the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 */
	public PostgresDumpDataStoreConfiguration(String dataStoreName, String pathToFile) {
		super(dataStoreName, pathToFile);
	}

	/**
	 * Creates a Postgres dump configuration using a file. The data store name will be the name of the file, without the file extension. The file will be read using the given encoding
	 * @param file the dump File
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public PostgresDumpDataStoreConfiguration(File file, Charset encoding) {
		super(file, encoding);
	}

	/**
	 * Creates a Postgres dump configuration using a file. The file will be read using the given encoding
	 * @param dataStoreName the name of this data store.
	 * @param file the dump File
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public PostgresDumpDataStoreConfiguration(String dataStoreName, File file, Charset encoding) {
		super(dataStoreName, file, encoding);
	}

	/**
	 * Creates a Postgres dump configuration using the file represented by a given path. The file will be read with the given encoding
	 * @param dataStoreName the name of this data store.
	 * @param pathToFile the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public PostgresDumpDataStoreConfiguration(String dataStoreName, String pathToFile, Charset encoding) {
		super(dataStoreName, pathToFile, encoding);
	}

	/**
	 * Creates a Postgres dump configuration using a file. The data store name will be the name of the file, without the file extension.
	 * The file will be read using the given encoding
	 * @param file the dump File
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public PostgresDumpDataStoreConfiguration(File file, String encoding) {
		super(file, encoding);
	}

	/**
	 * Creates a Postgres dump configuration using a file. The file will be read using the given encoding
	 * @param dataStoreName the name of this data store.
	 * @param file the dump File
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public PostgresDumpDataStoreConfiguration(String dataStoreName, File file, String encoding) {
		super(dataStoreName, file, encoding);
	}

	/**
	 * Creates a Postgres dump configuration using the file represented by a given path. The file will be read with the given encoding
	 * @param dataStoreName the name of this data store.
	 * @param pathToFile the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 * @param encoding the encoding that must be used to read from the given file.
	 */
	public PostgresDumpDataStoreConfiguration(String dataStoreName, String pathToFile, String encoding) {
		super(dataStoreName, pathToFile, encoding);
	}

	@Override
	protected final PostgresDumpFormat newDefaultFormat() {
		return new PostgresDumpFormat();
	}

}
