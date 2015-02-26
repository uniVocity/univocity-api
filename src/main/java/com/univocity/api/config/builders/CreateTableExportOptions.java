/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.entity.jdbc.*;

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
	public CreateTableExportOptions noPrimaryKeyConstraint();
	
	
	/**
	 * Defines the escape sequence to escape table and column names that may conflict with database reserved words. 
	 * @param escapeSequence the characters to introduce around the identifiers
	 * @param always flag to indicate whether or not all table and column names should be escaped
	 * @return further options to configure this export operation.
	 */
	public CreateTableExportOptions escapeIdentifiersWith(String escapeSequence, boolean always);

	
	/**
	 * Defines the escape sequence to escape table and column names that may conflict with database reserved words. 
	 * @param escaper a user provided class to handle how to escape reserved words.
	 * @return further options to configure this export operation.
	 */
	public CreateTableExportOptions escapeIdentifiersWith(IdentifierEscaper escaper);

	/**
	 * Provides additional identifiers to escape, on top of the default identifier list provided by uniVocity.
	 * @param identifiersToEscape a user provided list of reserved words to escape
	 * @return further options to configure this export operation.
	 */
	public CreateTableExportOptions escapeIdentifiers(String ... identifiersToEscape);
	
	/**
	 * The create table scripts in this export operation should not contain {@code NOT NULL} constraints on column definitions.
	 * @return further options to configure this export operation.
	 */
	public CreateTableExportOptions noNotNullConstraint();

	/**
	 * The create table scripts in this export operation should not contain {@code DEFAULT &lt;value&gt;} on column definitions.
	 * @return further options to configure this export operation.
	 */
	public CreateTableExportOptions noDefaults();

	/**
	 * The create table scripts in this export operation should not contain generated (nor identity) primary keys.
	 * @return further options to configure this export operation.
	 */
	public CreateTableExportOptions noGeneratedIds();

	/**
	 * Exports the result of this create table {@link Export} operation as a {@code String}. Produces the same result as the {@link #toScript()} method.
	 * @return an object containing the result of the create table {@link Export} operation
	 */
	@Override
	public String toObject();

	/**
	 * Exports the result of this create table {@link Export} operation as a script.
	 * @return an script containing the result of the create table {@link Export} operation
	 */
	public String toScript();
	
	/**
	 * The scripts in this export operation should not contain {@code FOREIGN KEY} constraints following table definitions.
	 * @return further options to configure this export operation.
	 */
	public CreateTableExportOptions noForeignKeyConstraints();
	
}
