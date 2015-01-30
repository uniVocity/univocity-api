/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;
import com.univocity.api.entity.custom.*;

/**
 * The {@link EntityMapping} provides builder-style configuration options for defining mappings between fields of two data entities,
 * or between values produced by expressions to a destination data entity.
 *
 * <p>Instances of this interface are obtained from a {@link DataStoreMapping} instance,
 * using either {@link DataStoreMapping#map(String, String)} or {@link DataStoreMapping#map(String)}.
 *
 * @see RowReader
 * @see DataStoreMapping
 * @see FieldMappingSetup
 * @see IdentifierMappingSetup
 * @see ReferenceMappingSetup
 * @see PersistenceSetup
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface EntityMapping {

	/**
	 * Configures the mapping of one or more fields from the source entity to the destination.
	 * @return the field mapping builder that exposes the appropriate configuration options required to map one or more fields between two data entities.
	 */
	public FieldMappingSetup value();

	/**
	 * Configures the mapping of one or more fields in the source entity to the destination fields used as identifiers.
	 * @return the identity mapping builder that exposes the appropriate configuration options required to map one or more fields used as identifiers.
	 */
	public IdentifierMappingSetup identity();

	/**
	 * Configures the mapping of one or more fields in the source entity that are used to construct a reference to a destination entity.
	 * The source fields will be used for querying uniVocity's metadata to restore values associated with the identifier of an entity. For example:
	 *
	 * <hr><blockquote><pre>
	 * //Maps fields "nbr" and "seq" from the source entity "WGT" to a destination entity "weight"
	 *
	 * EntityMapping weightMapping = mapping.map("WGT", "weight");
	 * weightMapping.identity().associate("nbr", "seq").toGeneratedId("gid");
	 * ...
	 * </pre></blockquote><hr>
	 *
	 * When the above mapping is executed, values read from <code>nbr</code> and <code>seq</code> will be associated with the generated identifier <code>gid</code>:
	 * <hr><blockquote><pre>
	 * Source entity (WGT)    Destination entity (weight)
	 *     nbr | seq                    gid
	 *     ----+----                  -------
	 *     001 | 1                       1
	 *     001 | 2                       2
	 *     002 | 1                       3
	 * </pre></blockquote><hr>
	 *
	 * With this information on the metadata, a reference mapping can be defined as:
	 *
	 * <hr><blockquote><pre>
	 * //Uses fields "nbr" and "seq" from the source entity "WGT" to obtain the identifier associated with "weight".
	 * //After getting the correct identifier of "weight", copy it to "weight_id"
	 *
	 * EntityMapping weightDetails = mapping.map("WGT", "weight_details");
	 * weightDetails.reference().using("nbr", "seq").referTo("WGT", "weight").on("weight_id");
	 * ...
	 * </pre></blockquote><hr>
	 *
	 * Using the above mapping, uniVocity will execute a query against its metadata requesting for any values associated with <code>nbr</code> and <code>seq</code>,
	 * where the source entity is <code>WGT</code> and the destination entity is <code>weight</code>. The returned identifiers for <code>weight</code> will then be
	 * associated with the <code>weight_id</code> field in <code>weight_details</code>.
	 *
	 *
	 * @return the reference mapping builder that exposes the appropriate configuration options required to map one or more
	 *         fields to references to other entities in the destination data store.
	 */
	public ReferenceMappingSetup reference();

	/**
	 * Configures the persistence settings used by this {@link EntityMapping}
	 * @return the persistence settings builder that exposes the available configuration options for this entity mapping.
	 */
	public PersistenceSetup persistence();

	/**
	 * Adds a {@link RowReader} to be executed against the input data of this entity mapping.
	 * @param rowReader the callback object that will intercept and possibly manipulate the rows
	 * 	 	  extracted from the source entity before its data is mapped to the destination.
	 */
	public void addInputRowReader(RowReader rowReader);

	/**
	 * Associates one or more {@link RowReader}s, registered in the {@link DataIntegrationEngine}, with the input data of this entity mapping.
	 * @param readerNames the names of the callback objects that will intercept and possibly manipulate the rows
	 * 	 	  extracted from the source entity before its data is mapped to the destination.
	 */
	public void addInputRowReaders(String... readerNames);

	/**
	 * Adds a {@link RowReader} to be executed against the output data of this entity mapping, before it is persisted.
	 * @param rowReader the callback object that will intercept and possibly manipulate the rows
	 * 	 	  extracted from the source entity and mapped to the destination, before they are persisted
	 */
	public void addOutputRowReader(RowReader rowReader);

	/**
	 * Associates one or more {@link RowReader}s registered, in the {@link DataIntegrationEngine}, with the output data of this entity mapping.
	 * @param readerNames the names of the callback objects that will intercept and possibly manipulate the rows
	 * 	 	   extracted from the source entity and mapped to the destination, before they are persisted
	 */
	public void addOutputRowReaders(String... readerNames);

	/**
	 * Adds a {@link RowReader} to be executed against the data persisted after the execution of this entity mapping.
	 * Values generated after insertion, if any, will be available to the {@link RowReader}.
	 * @param rowReader the callback object that will manipulate rows
	 * 	 	  extracted from the source entity, mapped and persisted into the destination
	 */
	public void addPersistedRowReader(RowReader rowReader);

	/**
	 * Associates one or more {@link RowReader}s, registered in the {@link DataIntegrationEngine},
	 * to be executed against the data persisted after the execution of this entity mapping.
	 * Values generated after insertion, if any, will be available to the {@link RowReader}.
	 * @param readerNames the names of the callback objects that will manipulate rows
	 * 	 	  extracted from the source entity, mapped and persisted into the destination
	 */
	public void addPersistedRowReaders(String... readerNames);

	public void addTransformedRowReader(RowReader rowReader);

	public void addTransformedRowReaders(String... readerNames);
	
	/**
	 * Removes a {@link RowReader} from the input data of this entity mapping.
	 * @param rowReader the {@link RowReader} instance to remove from this mapping's input
	 */
	public void removeInputRowReader(RowReader rowReader);

	/**
	 * Removes one or more {@link RowReader}s, registered in the {@link DataIntegrationEngine}, from the input data of this entity mapping.
	 * @param readerNames the names of the {@link RowReader}s to remove from this mapping's input
	 */
	public void removeInputRowReaders(String... readerNames);

	/**
	 * Removes a {@link RowReader} from the data sent to the destination entity in this mapping.
	 * @param rowReader the {@link RowReader} instance to remove from this mapping's output
	 */
	public void removeOutputRowReader(RowReader rowReader);

	/**
	 * Removes one or more {@link RowReader}s, registered in the {@link DataIntegrationEngine}, from the data sent to the destination entity in this mapping.
	 * @param readerNames the names of the {@link RowReader}s to remove from this mapping's output
	 */
	public void removeOutputRowReaders(String... readerNames);

	/**
	 * Removes a {@link RowReader} from the data persisted after the execution of this entity mapping.
	 * @param rowReader the {@link RowReader} instance to remove from this mapping's persisted data
	 */
	public void removePersistedRowReader(RowReader rowReader);

	/**
	 * Removes one or more {@link RowReader}s, registered in the {@link DataIntegrationEngine}, from the data persisted after the execution of this entity mapping.
	 * @param readerNames the names of the {@link RowReader}s to remove from this mapping's persisted data
	 */
	public void removePersistedRowReaders(String... readerNames);

	public void removeTransformedRowReader(RowReader rowReader);

	public void removeTransformedRowReaders(String... readerNames);
	
	/**
	 * Executes a process for automatic detection of mappings based on the field names of the mapped entities.
	 * Fields that are already mapped will not be used in the process.
	 *
	 * <p>Fields with similar names will be automatically associated. Underscores and spaces are ignored, for example: <code>field1</code> will
	 * be associated with <code>FIELD 1</code> or <code>FIELD_1</code>.
	 * If the field in either source or destination is an identifier, the mapping will be created using {@link IdentifierMappingSetup},
	 * otherwise a regular field copy will be created using {@link FieldMappingSetup}.
	 */
	public void autodetectMappings();

	/**
	 * Executes a process for automatic detection of mappings based on the field names of the mapped entities.
	 * Fields that are already mapped will not be used in the process.
	 *
	 * <p>A {@link NameMatcher} will be executed to match the names of each field in both entities.
	 * If the field in either source or destination is an identifier, the mapping will be created using {@link IdentifierMappingSetup},
	 * otherwise a regular field copy will be created using {@link FieldMappingSetup}.
	 *
	 *  @param nameMatcher a matcher for field names. If {@code null} the default matching algorithm will be used.
	 */
	public void autodetectMappings(NameMatcher nameMatcher);

	/**
	 * Tests if this entity mapping is enabled, in which case it can be executed in a {@link DataIntegrationEngine} cycle.
	 * @return {@code true} if this entity mapping is enabled and can be executed by a {@link DataIntegrationEngine}, otherwise {@code false}
	 */
	public boolean isEnabled();

	/**
	 * Defines whether this entity mapping is enabled, in which case it can be executed in a {@link DataIntegrationEngine} cycle.
	 * @param enabled a flag identifying whether this entity mapping can be executed by a {@link DataIntegrationEngine}.
	 */
	public void setEnabled(boolean enabled);

	/**
	 * Identifies whether data read from the source entity in this mapping can be shared and reused by the previous or the next entity mapping. Irrespective of any configuration,
	 * the input will only be shared if the source entity in the next entity mapping uses the same source entity, and this method returns {@code true} in both mappings.
	 *
	 * <p>Input sharing has performance implications and can also affect how the data mapping process is organized. For example, consider the following mappings:
	 *
	 * <hr><blockquote><pre>
	 * //Maps fields "nbr" and "seq" from the source entity "WGT" to a destination entity "weight"
	 * EntityMapping weightMapping = mapping.map("WGT", "weight");
	 * weightMapping.identity().associate("nbr", "seq").toGeneratedId("id");
	 * ...
	 *
	 * //Maps field "msre_desc" from the source entity "WGT" to a destination entity "weight_details"
	 * EntityMapping weightDetailsMapping = mapping.map("WGT", "weight_details");
	 * weightDetailsMapping.value().copy("msre_desc").to("description");
	 * ...
	 * </pre></blockquote><hr>
	 *
	 * <p>In the mappings configured above, both <code>weight</code> and <code>weight_details</code> read data from <code>WGT</code>. If input sharing is enabled in both mappings,
	 * then the {@link DataIntegrationEngine} will:
	 *
	 * <ul>
	 *  <li>Execute a single reading process to read <code>nbr</code>, <code>seq</code> and <code>msre_desc</code> from <code>WGT</code></li>
	 *  <li>Load a number of rows in memory, as specified in {@link DataStoreConfiguration#getLimitOfRowsLoadedInMemory()}</li>
	 *  <li>Execute the mappings from <code>WGT</code> to <code>weight</code> using the rows loaded so far</li>
	 *  <li>Execute the mappings from <code>WGT</code> to <code>weight_details</code> reusing the same rows</li>
	 *  <li>Load more rows and execute both mappings again, and repeat this until all rows from <code>WGT</code> were mapped to both destination entities</li>
	 * </ul>
	 *
	 * <p>If either of the mappings in the example are configured to NOT share their inputs, then the {@link DataIntegrationEngine} will read all rows from the input twice:
	 * <ul>
	 *  <li>Execute a reading process to read <code>nbr</code> and <code>seq</code> from <code>WGT</code></li>
	 *  <li>Map all rows from <code>WGT</code> into <code>weight</code></li>
	 *  <li>Execute new reading process to read <code>msre_desc</code> from <code>WGT</code></li>
	 *  <li>Map all rows from <code>WGT</code> into <code>weight_details</code></li>
	 * </ul>
	 *
	 * <p><i>Defaults to true</i>
	 * @return {@code true} if the data read from the source entity in this mapping can be shared and reused by the previous or the next entity mapping, otherwise {@code false}
	 */
	public boolean isInputSharingEnabled();

	/**
	 * Defines whether data read from the source entity in this mapping can be shared and reused by the previous or the next entity mapping. Irrespective of any configuration,
	 * the input will only be shared if the source entity in the next entity mapping uses the same source entity, and this method returns true in both mappings.
	 *
	 * <p>Input sharing has performance implications and can also affect how the data mapping process is organized. For example, consider the following mappings:</p>
	 *
	 * <hr><blockquote><pre>
	 * //Maps fields "nbr" and "seq" from the source entity "WGT" to a destination entity "weight"
	 * EntityMapping weightMapping = mapping.map("WGT", "weight");
	 * weightMapping.identity().associate("nbr", "seq").toGeneratedId("id");
	 * ...
	 *
	 * //Maps field "msre_desc" from the source entity "WGT" to a destination entity "weight_details"
	 * EntityMapping weightDetailsMapping = mapping.map("WGT", "weight_details");
	 * weightDetailsMapping.value().copy("msre_desc").to("description");
	 * ...
	 * </pre></blockquote><hr>
	 *
	 * <p>In the mappings configured above, both <code>weight</code> and <code>weight_details</code> read data from <code>WGT</code>. If input sharing is enabled in both mappings,
	 * then the {@link DataIntegrationEngine} will:
	 *
	 * <ul>
	 *  <li>Execute a single reading process to read <code>nbr</code>, <code>seq</code> and <code>msre_desc</code> from <code>WGT</code></li>
	 *  <li>Load a number of rows in memory, as specified in {@link DataStoreConfiguration#getLimitOfRowsLoadedInMemory()}</li>
	 *  <li>Execute the mappings from <code>WGT</code> to <code>weight</code> using the rows loaded so far</li>
	 *  <li>Execute the mappings from <code>WGT</code> to <code>weight_details</code> reusing the same rows</li>
	 *  <li>Load more rows and execute both mappings again, and repeat this until all rows from <code>WGT</code> were mapped to both destination entities</li>
	 * </ul>
	 *
	 * <p>If either of the mappings in the example are configured to NOT share their inputs, then the {@link DataIntegrationEngine} will read all rows from the input twice:
	 * <ul>
	 *  <li>Execute a reading process to read <code>nbr</code> and <code>seq</code> from <code>WGT</code></li>
	 *  <li>Map all rows from <code>WGT</code> into <code>weight</code></li>
	 *  <li>Execute new reading process to read <code>msre_desc</code> from <code>WGT</code></li>
	 *  <li>Map all rows from <code>WGT</code> into <code>weight_details</code></li>
	 * </ul>
	 *
	 * @param enabled a flag indicating whether data read from the source entity in this mapping can be shared and reused by the previous or the next entity mapping
	 */
	public void setInputSharingEnabled(boolean enabled);

	/**
	 * Associates a function to a given list of source field names or expressions. When reading from the source entity, the function will be executed and its result
	 * will be send to the destination fields. The given function will be executed after other function sequences already applied to these fields.
	 *
	 * @param functionName the name of the function to associate to fields of the source entity.
	 * @param sourceFieldNames field names/expressions against which the given function should be applied to.
	 */
	public void transformFields(String functionName, String... sourceFieldNames);

	public DependentEntityMappingConfig include(String queryName);
	
	public DependentEntityMappingConfig join(String queryName);

}
