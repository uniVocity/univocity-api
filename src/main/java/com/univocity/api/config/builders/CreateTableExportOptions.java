/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.entity.jdbc.*;

import java.util.*;

/**
 * Specific configuration options of a an export operation to create table scripts, as defined by {@link Export#asCreateTableScript(com.univocity.api.entity.jdbc.DatabaseDialect)}.
 *
 * The configuration methods available here determine how the script output should be generated.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface CreateTableExportOptions extends ExportOutput {

	/**
	 * The create table scripts in this export operation should not contain {@code PRIMARY KEY} constraints on table definitions.
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions noPrimaryKeyConstraint();

	/**
	 * Defines the escape sequence to escape table and column names that may conflict with database reserved words.
	 * @param escapeSequence the characters to introduce around the identifiers
	 * @param always flag to indicate whether or not all table and column names should be escaped
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions escapeIdentifiersWith(String escapeSequence, boolean always);

	/**
	 * Defines the escape sequence to escape table and column names that may conflict with database reserved words.
	 * @param escaper a user provided class to handle how to escape reserved words.
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions escapeIdentifiersWith(IdentifierEscaper escaper);

	/**
	 * Provides additional identifiers to escape, on top of the default identifier list provided by uniVocity.
	 * @param identifiersToEscape a user provided list of reserved words to escape
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions escapeIdentifiers(String... identifiersToEscape);

	/**
	 * The create table scripts in this export operation should not contain {@code NOT NULL} constraints on column definitions.
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions noNotNullConstraint();

	/**
	 * The create table scripts in this export operation should not contain {@code DEFAULT &lt;value&gt;} on column definitions.
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions noDefaults();

	/**
	 * The create table scripts in this export operation should not contain generated (nor identity) primary keys.
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions noGeneratedIds();

	/**
	 * The create table scripts in this export operation should not be followed by create index statements. Create index statements will only be produced when exporting from JDBC data entities.
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions noIndexes();

	/**
	 * The scripts in this export operation should not contain {@code FOREIGN KEY} constraints following table definitions.
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions noForeignKeyConstraints();

	/**
	 * The scripts in this export operation should not contain {@code UNIQUE} constraints following table definitions.
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions noUniqueConstraints();

	/**
	 * The scripts in this export operation should have the length of any identifiers - such as table and index names - restricted.
	 * @return further options to configure this export operation.
	 */
	CreateTableExportOptions restrictIdentifierLengthTo(int maxLength);

	/**
	 * Exports the result of this create table {@link Export} operation as a list of {@code String}s.
	 * Each element will be a "create table" DDL statement for an individual table.
	 * @return an object containing the result of the create table {@link Export} operation
	 */
	@Override
	List<String> toObject();

	/**
	 * Exports the result of this create table {@link Export} operation as a script.
	 * @return an script containing the result of the create table {@link Export} operation
	 */
	String toScript();

}
