/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

import java.util.*;

import com.univocity.api.common.*;
import com.univocity.api.config.builders.*;
import com.univocity.api.engine.*;

/**
 * This is the base class of all configuration classes for data stores.
 * It provides the most basic configuration elements to enable data processing by uniVocity.
 *
 * @see CustomDataStoreFactory
 * @see DataStoreMapping
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public abstract class DataStoreConfiguration {

	private final String dataStoreName;
	private final Set<String> databaseWrappedEntities = new TreeSet<String>();

	/**
	 * Creates a new configuration object for a data store. uniVocity refers to data stores by their name (most importantly in {@link DataIntegrationEngine#map(String, String)}).
	 * @param dataStoreName the name of the data store being configured. Leading and trailing white spaces, if any, will be removed.
	 */
	public DataStoreConfiguration(String dataStoreName) {
		Args.notBlank(dataStoreName, "Data store name");
		this.dataStoreName = dataStoreName.trim();
	}

	/**
	 * Returns the name of the data store whose configuration is maintained by this object .
	 * @return the data store name.
	 */
	public final String getDataStoreName() {
		return dataStoreName;
	}

	/**
	 * Returns the entity names that must have their data loaded into an in-memory database managed by uniVocity before a each data migration cycle.
	 * @return a unmodifiable set containing the database-enabled entity names.
	 */
	public Set<String> getDatabaseEnabledEntities() {
		return Collections.unmodifiableSet(databaseWrappedEntities);
	}

	/**
	 * If this data store is not backed by a database, you can move data from your custom entities to an in-memory database managed by uniVocity.
	 * <br>This enables operations such as SQL queries that can be executed against data stored in plain text files for example.
	 * <p>By providing the names of these entities using this method, uniVocity will load all their data into an in-memory database automatically,
	 *    and any data modifications will be dumped back to the original entity at the end of each data migration cycle (started in {@link DataIntegrationEngine#executeCycle()}.
	 *
	 * @param entityNames the entity names that must have their data loaded into an in-memory database managed by uniVocity
	 */
	public void enableDatabaseOperationsIn(Collection<String> entityNames) {
		if (entityNames == null) {
			return;
		}
		for (String entityName : entityNames) {
			databaseWrappedEntities.add(entityName);
		}
	}

	/**
	 * If this data store is not backed by a database, you can move data from your custom entities to an in-memory database managed by uniVocity.
	 * <br>This enables operations such as SQL queries that can be executed against data stored in plain text files for example.
	 * <p>By providing the names of these entities using this method, uniVocity will load all their data into an in-memory database automatically,
	 *    and any data modifications will be dumped back to the original entity at the end of each data migration cycle (started in {@link DataIntegrationEngine#executeCycle()}.
	 *
	 * @param entityNames the entity names that must have their data loaded into an in-memory database managed by uniVocity
	 */
	public void enableDatabaseOperationsIn(String... entityNames) {
		if (entityNames == null) {
			return;
		}
		for (String entityName : entityNames) {
			databaseWrappedEntities.add(entityName);
		}
	}

	/**
	 * Obtains the maximum number of rows loaded in memory at a time when extracting information from entities and queries managed by this data store.
	 * @return the maximum number of rows kept in memory at any given time when reading values from any entity or query of this data store.
	 */
	public abstract int getLimitOfRowsLoadedInMemory();
}
