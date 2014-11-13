/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

import com.univocity.api.common.*;

/**
 * Definition of the format of a database dump script. The configurations defined in this class allow uniVocity to parse database dump scripts and load their values
 * into equivalent database schemas. The destination database does not need to be the same database used to generate the dump file. You can load a dump generated from
 * MySQL into Oracle or Postgres, for example.
 *
 * For example, a MySQL dump script will look like:
 *
 * <p><hr><blockquote><pre>
 * --
 * -- Table structure for table `worldcities`
 * --
 *
 * /*!40101 SET @saved_cs_client     = @@character_set_client * /;
 * /*!40101 SET character_set_client = utf8 * /;
 * CREATE TABLE `worldcities` (
 * `id` int(11) NOT NULL,
 * `country` char(2) DEFAULT NULL,
 * ...
 * PRIMARY KEY (`id`)
 * );
 * /*!40101 SET character_set_client = @saved_cs_client * /;
 *
 * --
 * -- Dumping data for table `worldcities`
 * --
 *
 * INSERT INTO `worldcities` VALUES (1,'ad','aixas','Aixàs','06',NULL,42.48333330,1.46666670);
 * INSERT INTO `worldcities` VALUES (2,'ad','aixirivali','Aixirivali','06',NULL,42.46666670,1.50000000);
 * INSERT INTO `worldcities` VALUES (3,'ad','aixirivall','Aixirivall','06',NULL,42.46666670,1.50000000);
 * ...
 * </pre></blockquote><hr></p>
 *
 * uniVocity can read such inputs and execute an initial database load from the dump file provided. The configurations in this class
 * allow uniVocity to correctly parse your dump file and insert the values in the database of your preference. The dump file can be
 * originated from a different database. Provided that the tables exist in the database with an equivalent structure, uniVocity will get the values from each
 * insert statement in the file and pass them to your database tables.
 *
 * @see JdbcDataStoreDumpLoadConfiguration
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com
 *
 */
public class DumpFileFormat {

	private static final String systemLineSeparatorString;
	private static final char[] systemLineSeparator;

	static {
		String lineSeparator = System.getProperty("line.separator");
		if (lineSeparator == null) {
			systemLineSeparatorString = "\n";
		} else {
			systemLineSeparatorString = lineSeparator;
		}
		systemLineSeparator = systemLineSeparatorString.toCharArray();
	}

	private String lineSeparatorString;
	private char[] lineSeparator;

	public DumpFileFormat() {
		this.lineSeparator = systemLineSeparator.clone();
		this.lineSeparatorString = systemLineSeparatorString;
	}

	private char quote = '\'';
	private char quoteEscape = '\\';
	private char delimiter = ',';
	private char scriptDelimiter = ';';
	private String recordIdentifier = "INSERT INTO ? VALUES";
	private char recordStart = '(';
	private char recordEnd = ')';

	private boolean oneInsertPerRow = true;

	/**
	 * Returns the current line separator character sequence, which can contain 1 to 2 characters. Defaults to the system's line separator sequence (usually '\r\n' in Windows, '\r' in MacOS, and '\n' in Linux/Unix).
	 * @return the sequence of 1 to 2 characters that identifies the end of a line
	 */
	public char[] getLineSeparator() {
		return lineSeparator.clone();
	}

	/**
	 * Returns the current line separator sequence as a String of 1 to 2 characters. Defaults to the system's line separator sequence (usually "\r\n" in Windows, "\r" in MacOS, and "\n" in Linux/Unix).
	 * @return the sequence of 1 to 2 characters that identifies the end of a line
	 */
	public String getLineSeparatorString() {
		return lineSeparatorString;
	}

	/**
	 * Defines the line separator sequence that should be used for parsing the dump file.
	 * @param lineSeparator a sequence of 1 to 2 characters that identifies the end of a line
	 */
	public void setLineSeparator(String lineSeparator) {
		if (lineSeparator == null || lineSeparator.isEmpty()) {
			throw new IllegalArgumentException("Line separator cannot be empty");
		}
		setLineSeparator(lineSeparator.toCharArray());
	}

	/**
	 * Defines the line separator sequence that should be used for parsing the dump file.
	 * @param lineSeparator a sequence of 1 to 2 characters that identifies the end of a line
	 */
	public void setLineSeparator(char[] lineSeparator) {
		if (lineSeparator == null || lineSeparator.length == 0) {
			throw new IllegalArgumentException("Invalid line separator. Expected 1 to 2 characters");
		}
		if (lineSeparator.length > 2) {
			throw new IllegalArgumentException("Invalid line separator. Up to 2 characters are expected. Got " + lineSeparator.length + " characters.");
		}
		this.lineSeparator = lineSeparator;
		this.lineSeparatorString = new String(lineSeparator);
	}

	/**
	 * Returns the character used for escaping values where the field delimiter is part of the value. Defaults to '\''
	 * @return the quote character
	 */
	public char getQuote() {
		return quote;
	}

	/**
	 * Defines the character used for escaping values where the field delimiter is part of the value. Defaults to '\''
	 * @param quote the quote character
	 */
	public void setQuote(char quote) {
		this.quote = quote;
	}

	/**
	 * Returns the character used for escaping quotes inside an already quoted value. Defaults to '\\'
	 * @return the quote escape character
	 */
	public char getQuoteEscape() {
		return quoteEscape;
	}

	/**
	 * Defines the character used for escaping quotes inside an already quoted value. Defaults to '\\'
	 * @param quoteEscape the quote escape character
	 */
	public void setQuoteEscape(char quoteEscape) {
		this.quoteEscape = quoteEscape;
	}

	/**
	 * Returns the field delimiter character. Defaults to ','
	 * @return the field delimiter character
	 */
	public char getDelimiter() {
		return delimiter;
	}

	/**
	 * Defines the field delimiter character. Defaults to ','
	 * @param delimiter the field delimiter character
	 */
	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Returns the character used to separate individual script statements in a dump file. Defaults to ';'
	 * @return the script delimiter character
	 */
	public char getScriptDelimiter() {
		return scriptDelimiter;
	}

	/**
	 * Defines the character used to separate individual script statements in a dump file. Defaults to ';'
	 * @param scriptDelimiter the script delimiter character
	 */
	public void setScriptDelimiter(char scriptDelimiter) {
		this.scriptDelimiter = scriptDelimiter;
	}

	/**
	 * Returns the sequence of characters that precedes the start of a record, or a set of records if the {@link #isOneInsertPerRow()} evaluates to {@code false}.
	 * Defaults to: {@code "INSERT INTO ? VALUES"}. The '?' is used to indicate the location of the table name in each input record.
	 *
	 * Once the parser matches the expression, values that follow will be collected for insertion into the database.
	 * @return the expression that precedes the values of individual records of a database table.
	 */
	public String getRecordIdentifier() {
		return recordIdentifier;
	}

	/**
	 * Defines the sequence of characters that precedes the start of a record, or a set of records if the {@link #isOneInsertPerRow()} evaluates to {@code false}.
	 * Defaults to: {@code "INSERT INTO ? VALUES"}. The '?' is used to indicate the location of the table name in each input record, and is mandatory.
	 *
	 * Once the parser matches the expression, values that follow will be collected for insertion into the database.
	 * @param recordIdentifier the expression that precedes the values of individual records of a database table.
	 */
	public void setRecordIdentifier(String recordIdentifier) {
		Args.notBlank(recordIdentifier, "Record identifier");
		if (!recordIdentifier.contains("?")) {
			throw new IllegalArgumentException("Unexpected record identifier format. Expected '?' in the String to subsitute the table name in the database dump script (e.g. 'INSERT INTO ? VALUES')");
		}
		this.recordIdentifier = recordIdentifier;
	}

	/**
	 * Returns the character that indicates the beginning of a sequence of values for an individual record of the table. Defaults to '('
	 * @return the character that represents the beginning of a sequence of values of a record.
	 */
	public char getRecordStart() {
		return recordStart;
	}

	/**
	 * Defines the character that indicates the beginning of a sequence of values for an individual record of the table. Defaults to '('
	 * @param recordStart the character that represents the beginning of a sequence of values of a record.
	 */
	public void setRecordStart(char recordStart) {
		this.recordStart = recordStart;
	}

	/**
	 * Returns the character that indicates the end of a sequence of values for an individual record of the table. Defaults to ')'
	 * @return the character that represents the end of a sequence of values of a record.
	 */
	public char getRecordEnd() {
		return recordEnd;
	}

	/**
	 * Defines the character that indicates the end of a sequence of values for an individual record of the table. Defaults to ')'
	 * @param recordEnd the character that represents the end of a sequence of values of a record.
	 */
	public void setRecordEnd(char recordEnd) {
		this.recordEnd = recordEnd;
	}

	/**
	 * Indicates that the database dump file has one insert statement for each individual row of a given table, e.g.
	 * <p><hr><blockquote><pre>
	 *		INSERT INTO `worldcities` VALUES (1,'ad','aixas','Aixàs','06',NULL,42.48333330,1.46666670);
	 *		INSERT INTO `worldcities` VALUES (2,'ad','aixirivali','Aixirivali','06',NULL,42.46666670,1.50000000);
	 *		INSERT INTO `worldcities` VALUES (3,'ad','aixirivall','Aixirivall','06',NULL,42.46666670,1.50000000);
	 *      ...
	 * </pre></blockquote><hr></p>
	 *
	 * Instead of:
	 * <p><hr><blockquote><pre>
	 * 		INSERT INTO `worldcities` VALUES
	 * 			(1,'ad','aixas','Aixàs','06',NULL,42.48333330,1.46666670),
	 * 			(2,'ad','aixirivali','Aixirivali','06',NULL,42.46666670,1.50000000),
	 * 			(3,'ad','aixirivall','Aixirivall','06',NULL,42.46666670,1.50000000),
	 * 			...
	 * </pre></blockquote><hr></p>
	 * @return true if the dump file contains a new insert statement for each record, otherwise false.
	 */
	public boolean isOneInsertPerRow() {
		return oneInsertPerRow;
	}

	/**
	 * Defines whether the database dump file has one insert statement for each individual row of a given table, e.g.
	 * <p><hr><blockquote><pre>
	 *		INSERT INTO `worldcities` VALUES (1,'ad','aixas','Aixàs','06',NULL,42.48333330,1.46666670);
	 *		INSERT INTO `worldcities` VALUES (2,'ad','aixirivali','Aixirivali','06',NULL,42.46666670,1.50000000);
	 *		INSERT INTO `worldcities` VALUES (3,'ad','aixirivall','Aixirivall','06',NULL,42.46666670,1.50000000);
	 *      ...
	 * </pre></blockquote><hr></p>
	 *
	 * Instead of:
	 * <p><hr><blockquote><pre>
	 * 		INSERT INTO `worldcities` VALUES
	 * 			(1,'ad','aixas','Aixàs','06',NULL,42.48333330,1.46666670),
	 * 			(2,'ad','aixirivali','Aixirivali','06',NULL,42.46666670,1.50000000),
	 * 			(3,'ad','aixirivall','Aixirivall','06',NULL,42.46666670,1.50000000),
	 * 			...
	 * </pre></blockquote><hr></p>
	 * @param oneInsertPerRow flag indicating whether or not the dump file contains a new insert statement for each record
	 */
	public void setOneInsertPerRow(boolean oneInsertPerRow) {
		this.oneInsertPerRow = oneInsertPerRow;
	}

}
