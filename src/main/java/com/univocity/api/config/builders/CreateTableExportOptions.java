/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

/**
 * Specific configuration options of a an export operation to create table scripts, as defined by {@link Export#asCreateTableScript(com.univocity.api.entity.jdbc.DatabaseDialect)}.
 *
 * The configuration methods available here determine how the script output should be generated.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com
 *
 */
public interface CreateTableExportOptions extends ExportOutput {

	/**
	 * The create table scripts in this export operation should not contain {@code PRIMARY KEY} constraints on table definitions.
	 * @return further options to configure this export operation.
	 */
	public CreateTableExportOptions noPrimaryKeyConstraint();

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
}
