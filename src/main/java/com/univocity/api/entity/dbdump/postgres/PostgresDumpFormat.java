/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.dbdump.postgres;

import com.univocity.api.entity.dbdump.*;

/**
 * Format definition of Postgres database dump scripts.
 *
 * A typical Postgres dump script may look like:
 *
 * <hr><blockquote><pre>
 *
 * SET statement_timeout = 0;
 * SET lock_timeout = 0;
 * SET client_encoding = 'UTF8';
 * SET standard_conforming_strings = on;
 * SET check_function_bodies = false;
 * SET client_min_messages = warning;
 *
 * --
 * -- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
 * --
 *
 * CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
 * ...
 *
 * --
 * -- Data for Name: city; Type: TABLE DATA; Schema: public; Owner: jbax
 * --
 *
 * COPY city (id, region_id, name, population, latitude, longitude) FROM stdin;
 * 1	5	Aixàs	\N	42.48333330	1.46666670
 * 2	5	Aixirivali	\N	42.46666670	1.50000000
 * 3	5	Aixirivall	\N	42.46666670	1.50000000
 * 4	5	Aixirvall	\N	42.46666670	1.50000000
 * ...
 *
 * \.
 *
 * </pre></blockquote><hr>
 *
 * Or
 *
 * <hr><blockquote><pre>
 * --
 * -- Name: id; Type: DEFAULT; Schema: public; Owner: jbax
 * --
 *
 * ALTER TABLE ONLY worldcities ALTER COLUMN id SET DEFAULT nextval('worldcities_id_seq'::regclass);
 *
 *
 * --
 * -- Data for Name: city; Type: TABLE DATA; Schema: public; Owner: jbax
 * --
 *
 * INSERT INTO city (id, region_id, name, population, latitude, longitude) VALUES (1, 5, 'Aixàs', NULL, 42.48333330, 1.46666670);
 * INSERT INTO city (id, region_id, name, population, latitude, longitude) VALUES (2, 5, 'Aixirivali', NULL, 42.46666670, 1.50000000);
 * INSERT INTO city (id, region_id, name, population, latitude, longitude) VALUES (3, 5, 'Aixirivall', NULL, 42.46666670, 1.50000000);
 * INSERT INTO city (id, region_id, name, population, latitude, longitude) VALUES (4, 5, 'Aixirvall', NULL, 42.46666670, 1.50000000);
 * ...
 * </pre></blockquote><hr>
 *
 * Use this class lets to configure the format of your database dump file.
 *
 * @see PostgresDumpDataStoreConfiguration
 * @see DumpDataStoreConfiguration
 * @see DumpFileFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
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
