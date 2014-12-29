/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;
import com.univocity.api.entity.custom.*;
import com.univocity.api.entity.text.*;

/**
 * The {@link DataStoreMapping} provides builder-style configuration options for defining mappings between entities of two data stores.
 * Instances of this interface are obtained from a {@link DataIntegrationEngine} instance, using {@link DataIntegrationEngine#map(String, String)}.
 *
 * @see EntityMapping
 * @see NameMatcher
 * @see PersistenceSetup
 * @see DataIntegrationEngine
 * @see DataStoreConfiguration
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface DataStoreMapping {

	/**
	 * Returns the name of the data store that provides entities whose data will be used as input in mappings created with {@link #map(String, String)}
	 * @return the name of the source data store
	 */
	public String getSourceDataStore();

	/**
	 * Returns the name of the destination data store whose entities will receive data mapped from the inputs configured in an {@link EntityMapping}
	 * @return the name of the destination data store
	 */
	public String getDestinationDataStore();

	/**
	 * Creates a mapping configuration object to an entity in the destination data store that does not require input from any entity in the source data store.
	 * The {@link EntityMapping} instance is unique in a data store mapping and calling this method multiple times with the
	 * same entity name will cause an exception.
	 *
	 * @param destinationEntityName the name of the entity in the destination data store.
	 * @return a new {@link EntityMapping} instance.
	 */
	public EntityMapping map(String destinationEntityName);

	/**
	 * Creates a mapping configuration object between a data entity in the source data store to another entity in the destination data store.
	 * The {@link EntityMapping} instance is unique in a data store mapping and calling this method multiple times with the
	 * same entity names will cause an exception.
	 *
	 * @param sourceEntityName the name of the entity in the source data store
	 * @param destinationEntityName the name of the entity in the destination data store.
	 * @return a new {@link EntityMapping} instance.
	 */
	public EntityMapping map(String sourceEntityName, String destinationEntityName);

	/**
	 * Obtains the mapping configuration object between two entities.
	 *
	 * @param sourceEntityName the name of the source data entity.
	 * @param destinationEntityName the name of the destination data entity.
	 * @return the existing {@link DataStoreMapping} instance associated with the given data entity names, or {@code null} if there's no mapping between them.
	 */
	public EntityMapping getMapping(String sourceEntityName, String destinationEntityName);

	/**
	 * Removes a mapping between two data entities. All configuration settings defined in the underlying {@link EntityMapping} will be lost.
	 *
	 * @param sourceEntityName the name of the source data entity.
	 * @param destinationEntityName the name of the destination data entity.
	 */
	public void removeMapping(String sourceEntityName, String destinationEntityName);

	/**
	 * Executes a process for automatic detection of mappings based on entity names and their fields. Entities that are already mapped will not be used in the process.
	 *
	 * <p>Entities and fields with similar names will be automatically associated. Underscores and spaces are ignored, for example: <code>entity1</code> will
	 * be associated with <code>ENTITY 1</code> or <code>ENTITY_1</code>. When mapping fields, if the field in either source or destination is an identifier,
	 * the mapping will be created using {@link IdentifierMappingSetup}, otherwise a regular field copy will be created using {@link FieldMappingSetup}.
	 *
	 * <p><b>Important: </b> You may want to define the correct sequence of mappings to be
	 * executed by default using {@link DataIntegrationEngine#setMappingSequence(String...)}
	 */
	public void autodetectMappings();

	/**
	 * Executes a process for automatic detection of mappings based on entity names and their fields. Entities that are already mapped will not be used in the process.
	 *
	 * <p>Entities and fields with similar names will be automatically associated. Underscores and spaces are ignored, for example: <code>entity1</code> will
	 * be associated with <code>ENTITY 1</code> or <code>ENTITY_1</code>. When mapping fields, if the field in either source or destination is an identifier,
	 * the mapping will be created using {@link IdentifierMappingSetup}, otherwise a regular field copy will be created using {@link FieldMappingSetup}.
	 *
	 * <p><b>Important: </b> You may want to define the correct sequence of mappings to be
	 * executed by default using {@link DataIntegrationEngine#setMappingSequence(String...)}
	 *
	 * @param createDestinationEntities flag indicating whether "clones" of the source entities should be created in the destination data store. Some data stores
	 * support dynamic creation of entities, such as uniVocity-provided CSV, TSV and Fixed-Width data stores (but only when an output directory is defined:
	 * see {@link TextDataStoreConfiguration#setOutputDirectory(java.io.File)}). New entities will be created with the exact same name of the source entity,
	 * and with the same fields.
	 */
	public void autodetectMappings(boolean createDestinationEntities);

	/**
	 * Executes a process for automatic detection of mappings based on entity names and their fields. Entities that are already mapped will not be used in the process.
	 *
	 * <p>A {@link NameMatcher} will be used to match names of entities and create a mapping between them. If an {@link EntityMapping} is created for
	 * matching entities, then another {@link NameMatcher} will be executed to match the names of each field in both entities.
	 * If the field in either source or destination is an identifier, the mapping will be created using {@link IdentifierMappingSetup}, otherwise a
	 * regular field copy will be created using {@link FieldMappingSetup}.
	 *
	 *  @param createDestinationEntities flag indicating whether "clones" of the source entities should be created in the destination data store. Some data stores
	 *  support dynamic creation of entities, such as uniVocity-provided CSV, TSV and Fixed-Width data stores (but only when an output directory is defined:
	 *  see {@link TextDataStoreConfiguration#setOutputDirectory(java.io.File)}). New entities will be created with the exact same name of the source entity,
	 *  and with the same fields.
	 *  @param entityNameMatcher a matcher for entity names. If {@code null}, the default matching algorithm will be used.
	 *  @param fieldNameMatcher a matcher for field names. If {@code null}, the default matching algorithm will be used.
	 *
	 */
	public void autodetectMappings(boolean createDestinationEntities, NameMatcher entityNameMatcher, NameMatcher fieldNameMatcher);

	/**
	 * Executes a process for automatic detection of mappings based on entity names and their fields. Entities that are already mapped will not be used in the process.
	 *
	 * <p>A {@link NameMatcher} will be used to match names of entities and create a mapping between them. If an {@link EntityMapping} is created for
	 * matching entities, then another {@link NameMatcher} will be executed to match the names of each field in both entities.
	 * If the field in either source or destination is an identifier, the mapping will be created using {@link IdentifierMappingSetup}, otherwise a
	 * regular field copy will be created using {@link FieldMappingSetup}.
	 *
	 *  @param entityNameMatcher a matcher for entity names. If {@code null}, the default matching algorithm will be used.
	 *  @param fieldNameMatcher a matcher for field names. If {@code null}, the default matching algorithm will be used.
	 */
	public void autodetectMappings(NameMatcher entityNameMatcher, NameMatcher fieldNameMatcher);

	/**
	 * Configures the default persistence settings used by new {@link EntityMapping} instances created from this data store mapping. This won't
	 * affect the persistence settings of existing entity mappings.
	 *
	 * <p>The default persistence configuration in the {@link PersistenceSetup} instance is:
	 * <ul>
	 * 	<li>Metadata will be used</li>
	 *  <li>Data insertion is enabled, but generated keys, if any, won't be collected</li>
	 *  <li>Data update is disabled</li>
	 *  <li>Data removal is disabled</li>
	 * </ul>
	 *
	 * @return the persistence settings builder that provides configuration defaults for new entity mappings.
	 */
	public PersistenceSetup configurePersistenceDefaults();

	/**
	 * Tests if this data store mapping is enabled, in which case the entity mappings defined in it can be executed in a {@link DataIntegrationEngine} cycle.
	 * @return {@code true} if the mappings configured in this object are enabled and can be executed by a {@link DataIntegrationEngine}, otherwise {@code false}
	 */
	public boolean isEnabled();

	/**
	 * Defines whether this data store mapping is enabled, in which case the entity mappings defined in it can be executed in a {@link DataIntegrationEngine} cycle.
	 * @param enabled a flag enabling mappings configured in this object to be executed by a {@link DataIntegrationEngine}.
	 */
	public void setEnabled(boolean enabled);

}
