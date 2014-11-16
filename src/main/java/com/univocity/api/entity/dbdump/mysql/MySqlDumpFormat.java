/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
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
 * @see MySqlDumpDataStoreConfiguration
 * @see DumpDataStoreConfiguration
 * @see DumpFileFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public class MySqlDumpFormat extends DumpFileFormat {

	public MySqlDumpFormat() {
		this.setIdentifierEscape('`');
	}

	/**
	 * Returns the character used to escape database identifiers used as table or column names.
	 * <p>For example,
	 * {@code INSERT INTO `Max` (1,2,3);} is a valid SQL statement but {@code INSERT INTO Max (1,2,3);} is not, as {@code max} is also a built-in function of the database
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
	 * {@code INSERT INTO `Max` (1,2,3);} is a valid SQL statement but {@code INSERT INTO Max (1,2,3);} is not, as {@code max} is also a built-in function of the database
	 * </p>
	 * <p>Defaults to '`'</p>
	 *
	 * @param identifierEscape the identifier escape character
	 */
	@Override
	public void setIdentifierEscape(char identifierEscape) {
		super.setIdentifierEscape(identifierEscape);
	}
	
	@Override
	protected String getDefaultMultiRecordIdentifier() {
		return "INSERT INTO ? ";
	}

	@Override
	protected String getDefaultSingleRecordIdentifier() {
		return "INSERT INTO ? ";
	}
}
