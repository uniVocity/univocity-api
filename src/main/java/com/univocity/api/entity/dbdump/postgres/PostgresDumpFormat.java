/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.dbdump.postgres;

import com.univocity.api.entity.dbdump.*;

public class PostgresDumpFormat extends DumpFileFormat {

	/**
	 * Returns the sequence of characters that precedes the start of a record, or a set of records.
	 * A '?' character used to indicate the location of the table name in each input record.
	 * 
	 * Defaults to: 
	 * <ul>
	 * 	<li>{@code "COPY ? "} when {@link #isOneInsertPerRow()} evaluates to {@code false}</li> 
	 * 	<li>{@code "INSERT INTO ? "} when {@link #isOneInsertPerRow()} evaluates to {@code true}</li>
	 * </ul>
	 * Once the parser matches the expression, values that follow will be collected for insertion into the database.
	 * @return the expression that precedes the values of individual records of a database table.
	 */
	@Override
	public String getRecordIdentifier() {
		return super.getRecordIdentifier();
	}

	/**
	 * Defines the sequence of characters that precedes the start of a record, or a set of records.
	 * A '?' character is used to indicate the location of the table name in each input record, and is mandatory.
	 * 
	 * Defaults to: 
	 * <ul>
	 * 	<li>{@code "COPY ? "} when {@link #isOneInsertPerRow()} evaluates to {@code false}</li> 
	 * 	<li>{@code "INSERT INTO ? "} when {@link #isOneInsertPerRow()} evaluates to {@code true}</li>
	 * </ul>
	 *
	 * Once the parser matches the expression, values that follow will be collected for insertion into the database.
	 * @param recordIdentifier the expression that precedes the values of individual records of a database table.
	 */
	@Override
	public void setRecordIdentifier(String recordIdentifier) {
		super.setRecordIdentifier(recordIdentifier);
	}

	@Override
	protected String getDefaultMultiRecordIdentifier() {
		return "COPY ? ";
	}

	@Override
	protected String getDefaultSingleRecordIdentifier() {
		return "INSERT INTO ? ";
	}
}
