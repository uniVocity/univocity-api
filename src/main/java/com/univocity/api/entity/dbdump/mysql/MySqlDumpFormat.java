/*******************************************************************************
 * Cpyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.dbdump.mysql;

import com.univocity.api.entity.dbdump.*;

/**
 * Format definition of MySQL database dump scripts.
 *
 * A typical MySQL dump script may look like:
 *
 * <hr><blockquote><pre>
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
 * -- Dumping data for table `worldcities`
 *
 * INSERT INTO `worldcities` VALUES (1,'ad','aixas','Aixàs','06',NULL,42.48333330,1.46666670);
 * INSERT INTO `worldcities` VALUES (2,'ad','aixirivali','Aixirivali','06',NULL,42.46666670,1.50000000);
 * INSERT INTO `worldcities` VALUES (3,'ad','aixirivall','Aixirivall','06',NULL,42.46666670,1.50000000);
 * ...
 * </pre></blockquote><hr>
 *
 * Or
 *
 * <hr><blockquote><pre>
 *	-- Table structure for table `city`
 *	--
 *
 * DROP TABLE IF EXISTS `city`;
 * /*!40101 SET @saved_cs_client     = @@character_set_client * /;
 * /*!40101 SET character_set_client = utf8 * /;
 * CREATE TABLE `city` (
 *  `id` int(11) NOT NULL AUTO_INCREMENT,
 * ...
 * ) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
 * /*!40101 SET character_set_client = @saved_cs_client * /;
 *
 * -- Dumping data for table `city`
 *
 * LOCK TABLES `city` WRITE;
 * /*!40000 ALTER TABLE `city` DISABLE KEYS * /;
 * INSERT INTO `city` VALUES (1,5,'Aixàs',NULL,42.48333330,1.46666670),(2,5,'Aixirivali',NULL,42.46666670,1.50000000),(3,5,'Aixirivall',NULL,42.46666670,1.50000000),
 * ...);
 * </pre></blockquote><hr>
 *
 * This class lets you configure the specific format of your database dump files.
 *
 * @see DumpDataStoreConfiguration
 * @see DumpFileFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public class MySqlDumpFormat extends DumpFileFormat {

	private char recordStart = '(';
	private char recordEnd = ')';

	public MySqlDumpFormat() {
		this.setRecordIdentifier("INSERT INTO `?` VALUES");
		this.setIdentifierEscape('`');
	}

	/**
	 * Returns the character that indicates the beginning of a sequence of values for an individual record of the table. Defaults to '('
	 * @return the character that represents the beginning of a sequence of values of a record.
	 */
	public final char getRecordStart() {
		return recordStart;
	}

	/**
	 * Defines the character that indicates the beginning of a sequence of values for an individual record of the table. Defaults to '('
	 * @param recordStart the character that represents the beginning of a sequence of values of a record.
	 */
	public final void setRecordStart(char recordStart) {
		this.recordStart = recordStart;
	}

	/**
	 * Returns the character that indicates the end of a sequence of values for an individual record of the table. Defaults to ')'
	 * @return the character that represents the end of a sequence of values of a record.
	 */
	public final char getRecordEnd() {
		return recordEnd;
	}

	/**
	 * Defines the character that indicates the end of a sequence of values for an individual record of the table. Defaults to ')'
	 * @param recordEnd the character that represents the end of a sequence of values of a record.
	 */
	public final void setRecordEnd(char recordEnd) {
		this.recordEnd = recordEnd;
	}

	/**
	 * Returns the sequence of characters that precedes the start of a record, or a set of records if {@link #isOneInsertPerRow()} evaluates to {@code false}.
	 * Defaults to: {@code "INSERT INTO `?` VALUES"}. The '?' is used to indicate the location of the table name in each input record.
	 *
	 * Once the parser matches the expression, values that follow will be collected for insertion into the database.
	 * @return the expression that precedes the values of individual records of a database table.
	 */
	@Override
	public String getRecordIdentifier() {
		return super.getRecordIdentifier();
	}

	/**
	 * Defines the sequence of characters that precedes the start of a record, or a set of records if {@link #isOneInsertPerRow()} evaluates to {@code false}.
	 * Defaults to: {@code "INSERT INTO `?` VALUES"}. The '?' is used to indicate the location of the table name in each input record, and is mandatory.
	 *
	 * Once the parser matches the expression, values that follow will be collected for insertion into the database.
	 * @param recordIdentifier the expression that precedes the values of individual records of a database table.
	 */
	@Override
	public void setRecordIdentifier(String recordIdentifier) {
		super.setRecordIdentifier(recordIdentifier);
	}

	/**
	 * Returns the character used to escape database identifiers used as table or column names.
	 * <p>For example,
	 * {@code INSERT INTO "Max" (1,2,3);} is a valid SQL statement but {@code INSERT INTO Max (1,2,3);} is not, as {@code max} is also a built-in function of the database
	 * </p>
	 * <p>Defaults to '`'</p>
	 * @return the identifier escape character
	 */
	@Override
	public char getIdentifierEscape() {
		return super.getIdentifierEscape();
	}

	/**
	 * Defines the character used to escape database identifiers used as table or column names.
	 * <p>For example,
	 * {@code INSERT INTO "Max" (1,2,3);} is a valid SQL statement but {@code INSERT INTO Max (1,2,3);} is not, as {@code max} is also a built-in function of the database
	 * </p>
	 * <p>Defaults to '`'</p>
	 *
	 * @param identifierEscape the identifier escape character
	 */
	@Override
	public void setIdentifierEscape(char identifierEscape) {
		super.setIdentifierEscape(identifierEscape);
	}
}
