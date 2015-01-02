/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.jdbc;

import com.univocity.api.engine.*;

/**
 * Database dialects supported by uniVocity to produce table creation scripts from any data entity available in a {@link DataIntegrationEngine}.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com
 *
 */
public enum DatabaseDialect {

	/** Microsoft Access dialect */
	Access,

	/** Cach&eacute; 2007.1 dialect */
	Cache71,

	/** Cloudscape 10 dialect */
	Cloudscape_10,

	/** CUBRID (8.3.x and later) dialect */
	CUBRID,

	/** DB2/390 dialect */
	DB2_390,

	/** DB2/400 dialect */
	DB2_400,

	/** DB2 dialect */
	DB2,

	/** Derby dialect (pre-10.7) */
	Derby,

	/** Derby 10.7 dialect */
	Derby_10_7,

	/** Firebird dialect */
	Firebird,

	/** FrontBase dialect */
	FrontBase,

	/** H2 database dialect */
	H2,

	/** SQL dialect for SAP HANA column tables */
	HANAColumnStore,

	/** SQL dialect for SAP HANA row tables */
	HANARowStore,

	/** HSQLDB (HyperSQL) dialect */
	HSQLDB,

	/** HSQLDB (HyperSQL) dialect */
	HyperSQL,

	/** Informix Dynamic Server dialect */
	Informix,

	/** Ingres 10 dialect */
	Ingres_10,

	/** Ingres 9.3 dialect */
	Ingres_9,

	/**  Ingres dialect (up to 9.2) */
	Ingres,

	/** Interbase dialect */
	Interbase,

	/** JDataStore dialect */
	JDataStore,

	/** McKoi SQL dialect */
	McKoi,

	/** Mimer SQL dialect */
	MimerSQL,

	/** MySQL 5.x dialect*/
	MySQL_5,

	/** Dialect for MySQL 5 using InnoDB engine */
	MySQL_5_InnoDB,

	/** Dialect for MySQL 5 using MyISAM engine */
	MySQL_5_MyISAM,

	/** MySQL dialect for versions prior to 5.x*/
	MySQL,

	/** Dialect for MySQL using InnoDB engine (for versions prior to 5.x) */
	MySQL_InnoDB,

	/** Dialect for MySQL using the MyISAM engine (for versions prior to 5.x) */
	MySQL_MyISAM,

	/** Dialect for Oracle 10g */
	Oracle_10g,

	/** Dialect for Oracle 9i */
	Oracle_9i,

	/** Dialect for Oracle 8i */
	Oracle_8i,

	/** Pointbase dialect */
	Pointbase,

	/**  Postgres Plus dialect */
	PostgresPlus,

	/** Postgres 9 dialect */
	PostgreSQL_9,

	/**  Postgres 8.2 dialect */
	PostgreSQL_8_2,

	/**  Postgres 8.1 dialect */
	PostgreSQL_8_1,

	/** Progress dialect*/
	Progress,

	/** Unisys 2200 Relational Database (RDMS) dialect */
	RDMS_2200,

	/**  SAP DB dialect */
	SAPDB,

	/** Microsoft SQL 2005 dialect*/
	SQLServer_2005,

	/** Microsoft SQL 2008 dialect*/
	SQLServer_2008,

	/** Microsoft SQL 2012 dialect*/
	SQLServer_2012,

	/** Microsoft SQL 2000 dialect*/
	SQLServer_2000,

	/** Sybase 11.x dialect */
	Sybase_11,

	/** Sybase Anywhere dialect */
	Sybase_Anywhere,

	/** Sybase Adaptive Server Enterprise (ASE) 15.7  dialect */
	Sybase_ASE_15_7,

	/** Sybase Adaptive Server Enterprise (ASE) 15 dialect*/
	Sybase_ASE_15,

	/** Sybase dialect */
	Sybase,

	/** Teradata database dialect*/
	Teradata,

	/** TimesTen dialect */
	TimesTen,

	/** Unisys 2200 Relational Database (RDMS) dialect */
	Unisys_2200;

}
