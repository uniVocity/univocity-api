/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

import com.univocity.api.common.*;
import com.univocity.api.exception.*;

/**
 * This class provides configuration options for query-based JDBC data entities. Instances are accessible through a {@link JdbcDataStoreConfiguration} configuration object. 
 * <p>Queries are read-only and mapping data to one will cause an {@link IllegalConfigurationException}.
 * 
 * <p>Parameters must be prefixed with a colon, for example:
 * <p><i>SELECT col_1, col2 FROM table WHERE col_1 = <b>:param_1</b> OR col_2 = <b>:param_2</b></i>
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public final class JdbcQueryConfiguration extends BaseJdbcEntityConfiguration {

	final String queryString;
	final FileProvider queryFile;

	/**
	 * Package-protected constructor as instances of this class are only obtained from JdbcDataStoreConfiguration 
	 */
	JdbcQueryConfiguration() {
		queryString = null;
		queryFile = null;
	}

	/**
	 * Package-protected constructor as instances of this class are only obtained from JdbcDataStoreConfiguration
	 * @param queryString the SQL query to be used as an entity. 
	 */
	JdbcQueryConfiguration(String queryString) {
		Args.notBlank(queryString, "Query string");
		this.queryString = queryString;
		this.queryFile = null;
	}

	/**
	 * Package-protected constructor as instances of this class are only obtained from JdbcDataStoreConfiguration
	 * @param queryFile the file that contains a SQL query to be used as an entity 
	 */
	JdbcQueryConfiguration(FileProvider queryFile) {
		Args.notNull(queryFile, "Query file");
		this.queryFile = queryFile;
		this.queryString = null;
	}
}
