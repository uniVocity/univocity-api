/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

/**
 * A class responsible for escaping table and column names that can be reserved words in SQL statements.
 *
 * <p>For example, a column named <i>max</i> must be escaped so it doesn't conflict with the <i>max function</i> provided by most (if not all) databases.
 * <br>Additionally a table might have columns whose names contain white spaces such as "purchase date", or even repeated column names in different case: "Max", "mAx".
 * <br>For these cases, most databases require enclosing the column name within quotes. A valid select statement will then be:
 * <p><i>SELECT "Max", "mAx, "purchase date" FROM table</i>
 *
 * <p>Before producing an SQL statement, uniVocity will identify whether a table or column name requires escaping.
 * If it is required, the {@link #escape(String)} method will be called to obtain a column name.
 *
 * <p>uniVocity will escape most common reserved words used by different database vendors, but you might want to provide additional identifiers.
 * <br>Use {@link JdbcDataStoreConfiguration#addReservedWordsToEscape(String...)} to provide any additional reserved words to be escaped in your JDBC data store.
 *
 * <p>If {@link #alwaysEscape()} returns {@code true}, then the SQL statements produced by uniVocity for this JDBC data store will always escape column and table names.
 *
 * @see JdbcDataStoreConfiguration
 * @see SqlProducer
 * @see DefaultEscaper
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public abstract class IdentifierEscaper {

	/**
	 * Escapes identifier names to avoid producing SQL statements with conflicting names.
	 * @param identifier The table or column name that must be escaped
	 * @return the escaped version of the identifier
	 */
	public abstract String escape(String identifier);

	/**
	 * Should uniVocity always escape all identifiers in SQL statements?
	 *
	 * @return {@code true} if all identifiers must be escaped by default, otherwise {@code false}.
	 */
	public abstract boolean alwaysEscape();
}
