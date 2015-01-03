/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;
import com.univocity.api.entity.jdbc.*;

/**
 * Configuration of an export operation over a set of data entities, as defined by {@link DataIntegrationEngine#exportEntities(String, String...)}.
 * The configuration methods available here determine the information to be exported.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com
 *
 */
public interface Export {

	/**
	 * Using the names and fields of a given set of data entities, generate a {@code CREATE TABLE} SQL script suitable for a given database.
	 *
	 * @param dialect the database dialect to be used to generate the scripts
	 * @return the next step of this configuration: determining the output of the export operation, through an {@link ExportOutput} configuration object.
	 */
	public ExportOutput asCreateTableScript(DatabaseDialect dialect);

	/**
	 * Using the names and fields of a given set of data entities, generate a {@code CREATE TABLE} SQL script suitable for a given database.
	 *
	 * @param dialect the database dialect to be used to generate the scripts
	 * @param schema the database schema name
	 * @param catalog the database catalog name
	 * @return the next step of this configuration: determining the output of the export operation, through an {@link ExportOutput} configuration object.
	 */
	public ExportOutput asCreateTableScript(DatabaseDialect dialect, String schema, String catalog);
}
