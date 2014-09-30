/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

import java.util.*;

import com.univocity.api.common.*;
import com.univocity.api.entity.*;

/**
 * This is the parent class for all configuration classes JDBC entities
 * It provides essential configuration settings and sensible defaults for fetching and manipulating data in a database entity.
 *
 * @see JdbcEntityConfiguration
 * @see JdbcQueryConfiguration
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
abstract class BaseJdbcEntityConfiguration extends Configuration {

	private Integer fetchSize;
	final Map<String, DefaultEntityField> entityFields = new HashMap<String, DefaultEntityField>();

	/**
	 * Obtains the number of rows to return for an open ResultSet in a each each trip to the database.
	 * <p>uniVocity passes this value directly to your database driver using {@link java.sql.Statement#setFetchSize(int)}.
	 * <p>For performance reasons, you might want to adjust the fetch size to reflect the common number of rows returned
	 *    for this entity. A big fetch size number might consume too many resources and will be excessive for a small number of records.
	 *    A small fetch size for too many rows will cause slowness (e.g. a fetch size of 100 to read 10,000 rows and will generate 100 roundtrips to the database).
	 *
	 * <p><i>Defaults to 10,000 rows</i>
	 * @return the fetch size to use when reading values from the configured JDBC data entity.
	 * @see java.sql.Statement
	 */
	public final int getFetchSize() {
		if (fetchSize == null) {
			return 10000;
		}
		return fetchSize;
	}

	/**
	 * Defines the number of rows to return for an open ResultSet in a each each trip to the database.
	 * <p>uniVocity passes this value directly to your database driver using {@link java.sql.Statement#setFetchSize(int)}.
	 * <p>For performance reasons, you might want to adjust the fetch size to reflect the common number of rows returned
	 *    for this entity. A big fetch size number might consume too many resources and will be excessive for a small number of records.
	 *    A small fetch size for too many rows will cause slowness (e.g. a fetch size of 100 to read 10,000 rows and will generate 100 roundtrips to the database).
	 *
	 * @param fetchSize the fetch size to use when reading values from the configured JDBC data entity.
	 * <br>This parameter is not validated as some database drivers accept special settings for the fetch size, such as Integer.MIN_VALUE.
	 * @see java.sql.Statement
	 */
	public final void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void copyDefaultsFrom(Configuration defaultsObject) {
		BaseJdbcEntityConfiguration defaults = (BaseJdbcEntityConfiguration) defaultsObject;

		if (this.fetchSize == null) {
			this.fetchSize = defaults.getFetchSize();
		}
	}

	/**
	 * Configures the properties of a field in this entity. uniVocity autodetects column metadata of JDBC entities, but
	 * it may not always obtain all the information it needs from the JDBC driver. For example, it can't detect autogenerated
	 * columns where values are produced by a trigger (e.g. Oracle, prior to version 12, did not have an {@code IDENTITY} type)
	 *
	 * @param fieldName the name of the field to be configured explicitly
	 * @return a {@link DefaultEntityField} object with properties that can be manually set for the given field.
	 */
	public DefaultEntityField configureField(String fieldName) {
		Args.notBlank(fieldName, "Field name");

		DefaultEntityField field = entityFields.get(fieldName.toLowerCase());
		if (field == null) {
			field = new DefaultEntityField(fieldName);
			entityFields.put(fieldName.toLowerCase(), field);
		}
		return field;
	}
}
