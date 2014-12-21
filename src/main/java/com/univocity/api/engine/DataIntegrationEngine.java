/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

import java.util.*;

import com.univocity.api.*;
import com.univocity.api.config.*;
import com.univocity.api.config.annotation.*;
import com.univocity.api.config.builders.*;
import com.univocity.api.data.*;
import com.univocity.api.entity.*;

/**
 * The <code>DataIntegrationEngine</code> is the central component of uniVocity. With it you can define data mappings between entities of different data stores,
 * execute them, and control their execution.
 *
 * <p>A <code>DataIntegrationEngine</code> depends on some essential configurations in an {@link EngineConfiguration} object
 * and can only be created via a {@link Univocity} instance.
 *
 * <p>A database with a set of tables to store metadata is required for non-trivial mappings. This configuration resides in a {@link MetadataSettings} object.
 * In case this is not configured in {@link EngineConfiguration#getMetadataSettings()},
 * the <code>DataIntegrationEngine</code> will be started with an in-memory database. Note, however, that all metadata stored in this in-memory database
 * is lost once the <code>DataIntegrationEngine</code> is shut down (using {@link Univocity#shutdown(String)}).
 *
 * <p>The <code>DataIntegrationEngine</code> manages an internal scope ({@link EngineScope}) for variables, constants, {@link FunctionCall}s, and {@link DatasetProducer}s.
 *
 * <p><b>IMPORTANT: </b> instances of <code>DataIntegrationEngine</code> are not thread safe. If multiple threads share the same instance,
 * synchronization must be managed externally.
 *
 * @see Univocity
 * @see EngineConfiguration
 * @see MetadataSettings
 * @see FunctionCall
 * @see DatasetProducer
 * @see EngineScope
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface DataIntegrationEngine {

	/**
	 * The name of the data integration engine. This name is unique and {@link Univocity} will always return the same engine instance for a given name.
	 * @return the data integration engine name.
	 */
	public String getName();

	/**
	 * Creates a mapping configuration object between entities of two data stores. This is the first step in defining data mappings.
	 * The {@link DataStoreMapping} instance is unique between two data stores and calling this method multiple times with the
	 * same data store names will cause and exception.
	 *
	 * @param sourceDataStore the name of the source data store.
	 * @param destinationDataStore the name of the destination data store. Source and destination data stores can be the same.
	 * @return a new {@link DataStoreMapping} instance.
	 */
	public DataStoreMapping map(String sourceDataStore, String destinationDataStore);

	/**
	 * Obtains the mapping configuration object between entities of two data stores.
	 *
	 * @param sourceDataStore the name of the source data store.
	 * @param destinationDataStore the name of the destination data store. Source and destination data stores can be the same.
	 * @return the existing {@link DataStoreMapping} instance associated with the given data store names, or {@code null} if no such mapping between them exists.
	 */
	public DataStoreMapping getMapping(String sourceDataStore, String destinationDataStore);

	/**
	 * Removes the mapping between two data stores. All configuration settings defined in the underlying {@link DataStoreMapping} will be lost.
	 * @param sourceDataStore the name of the source data store.
	 * @param destinationDataStore the name of the destination data store. Source and destination data stores can be the same.
	 */
	public void removeMapping(String sourceDataStore, String destinationDataStore);

	/**
	 * Adds a callback object that intercepts life cycle events produced within this engine.
	 * @param interceptor the callback object that will be notified of this engine's life cycle events.
	 */
	public void addInterceptor(EngineLifecycleInterceptor interceptor);

	/**
	 * Removes a {@link EngineLifecycleInterceptor} instance from this engine, so it stops being notified of updates in this engine's internal state.
	 * @param interceptor the callback object to be removed
	 */
	public void removeInterceptor(EngineLifecycleInterceptor interceptor);

	/**
	 * Associates a variable name to an expression. When the variable is accessed, the expression will be evaluated and the result will be
	 * reused while the scope is active. These values are lost when the scope is deactivated.
	 * For example, an expression variable associated with the {@link EngineScope#CYCLE} scope won't be accessible if a there is no mapping cycle execution in progress.
	 * Attempting to read a variable whose scope is not active will yield an {@link IllegalStateException}.
	 *
	 * <p>Acceptable expressions conform to the following format:
	 *
	 * <ul>
	 *  <li>variables and constant names are prefixed with {@code $}</li>
	 *  <li>a function call is represented by its name and a sequence of parameters, if any, between {@code ()} and separated by commas.
	 *      Additional expressions such as function calls are accepted as parameters.
	 *  </li>
	 *  <li>a single expression can return multiple values. Each value must be separated by a comma and the result will be an array of objects</li>
	 *  <li>the word "null", without quotes, will be evaluated as a null object; 'null' will be evaluated to the "null" String.
	 *  </li>
	 *  <li>any value between single quotes is interpreted as a {@code String} literal. Single quotes are only required if the {@code String} literal contains:
	 *  	<ul>
	 *  		<li>leading or trailing whitespaces.</li>
	 *  		<li>any of the following characters: <code> $ ) ' ( , </code> </li>
	 *  		<li>in case the single quote is part of the value, it must be escaped with an addition single quote:
	 *  			<p>the expression: <code>"' '' this is escaped ''  '"</code> will be parsed as the {@code String}<code>" ' this is escaped ' "</code>
	 *  		</li>
	 *  	</ul>
	 * 	</li>
	 * </ul>
	 *
	 * Example: assume the {@code concat} function concatenates any number of arguments into a {@code String}, and the variables <code>b = 1</code> and <code>e = 2</code>:
	 * <p><code>
	 * "a, $b, concat( d, $e, 'f')"
	 * </code>
	 * The expression result will be an object array with: <code>["a", 1, "d2f"]</code>.
	 *
	 * @param scope the scope of this expression variable
	 * @param name the name of the expression variable
	 * @param expression the expression to be executed when the variable name is read for the first time in its scope.
	 */
	public void addExpression(EngineScope scope, String name, String expression);

	/**
	 * Adds a custom function implementation to this engine. The result of a function call will be reused within a given scope. For example,
	 * if the function "currentTime('yyyy-mm-dd')" is associated with the {@link EngineScope#CYCLE} scope, the value returned by this particular invocation
	 * will be reused until a mapping cycle ends.
	 *
	 * <p><i><b>Note:</b> functions are accessible from any scope</i>.
	 *
	 * @param <F> the function call type
	 * @param scope the scope of values returned by the given function. The engine will retain and reuse the result of each function call into the given scope.
	 * @param name the name of the given function. This name can be used in expressions, followed by the function arguments.
	 * @param function the actual implementation of the function.
	 */
	public <F extends FunctionCall<?, ?>> void addFunction(EngineScope scope, String name, F function);

	/**
	 * Creates a function backed by an implementation of {@link java.util.Map}. Functions based on a map accept only one parameter as an argument, which is used as the key
	 * to retrieve a value from the map. This map can be modified externally at any time.
	 *
	 * <p>Examples:</p>
	 * <hr><blockquote><pre>
	 *
	 * Map&lt;String, String&gt; languages = new HashMap&lt;String, String&gt;();
	 * locales.put("en_US", "American English");
	 * locales.put("en_GB", "British English");
	 * locales.put("en_AU", "Australian English");
	 *
	 * //creates a function named "languages" with values given in the languages map
	 * engine.addMap("languages", languages);
	 *
	 * //executes "languages(en_GB)" to obtain the description and maps it to the "language_description" field in the destination entity.
	 * mapping.value().copy("{languages(en_GB)}").to("language_description");
	 *
	 * //For each value extracted from the source "lang_code", calls the "languages" function
	 * //to obtain the description then writes to "language_description" in the destination
	 * mapping.value().copy("lang_code").to("language_description").readingWith("languages");
	 * </pre></blockquote><hr>
	 *
	 * @param <F> the map type
	 * @param name the name of the function
	 * @param mapFunction the implementation of {@link java.util.Map} that will be used to store and fetch key-value pairs.
	 */
	public <F extends Map<?, ?>> void addMap(String name, F mapFunction);

	/**
	 * Configures a query to be executed against a data store as a function. The query will be accessible from any {@link EntityMapping}s of this engine.
	 *
	 * The result of a query-based function call will be reused within a given scope. For example
	 * if query named as "selectLocaleId" is associated with the {@link EngineScope#CYCLE} scope, the value returned by multiple invocations of
	 * "selectLocaleId('en_US')" will be reused until the end of a data mapping cycle.
	 *
	 * <p><i><b>Note:</b> The query-based function will be accessible from any scope</i>.</p>
	 *
	 * @param scope the scope of values returned by the query. The engine will retain and reuse the result of each query call within the given scope.
	 * @param queryName the name of the given query. This name can be used in expressions, followed by the query arguments.
	 * @return an object used to properly configure the query as a function.
	 */
	public QuerySetup addQuery(EngineScope scope, String queryName);

	/**
	 * Sets or adds then initializes a variable in the current scope. To read the value of a variable in expressions, prepend it with $.
	 * For example: <code>"$en_US"</code> reads the value of the variable "en_US".
	 *
	 * <p>The scope of the new variable depends on the current active scope (see {@link EngineScope}).</p>
	 *
	 * @param name the name of the new variable
	 * @param value the value of the variable to be set/created in the current scope.
	 */
	public void setVariable(String name, Object value);

	/**
	 * Sets or adds then initializes a variable in the persistent scope ({@link EngineScope#PERSISTENT}). If no {@link ScopeStorageProvider} is defined
	 * in {@link EngineConfiguration#getPersistentScopeStorageProvider()}, the variable will be added/set using {@link EngineScope#APPLICATION}.
	 *
	 * <p>To read the value of a variable in an expression, prepend it with $.
	 * For example: <code>"$en_US"</code> reads the value of the variable "en_US".</p>
	 *
	 * @param name the name of the new variable added to the {@link EngineScope#PERSISTENT} scope
	 * @param value the value of the variable to be set/created in the persistent scope.
	 */
	public void setPersistentVariable(String name, Object value);

	/**
	 * Adds a constant value that can accessed from any scope. To read the value of a constant in expressions, prepend it with $.
	 * For example: <code>"$en_US"</code> reads the value of the constant "en_US".
	 *
	 * <p>Constants cannot have their value modified.</p>
	 *
	 * @param name the name of the new constant
	 * @param value the value of the constant
	 */
	public void setConstant(String name, Object value);

	/**
	 * Reads the value of a variable or constant registered to this engine
	 *
	 * @param name the name of the variable
	 * @return the value of the variable
	 */
	public Object readVariable(String name);

	/**
	 * Creates new functions based on the methods annotated with {@link FunctionWrapper}. Object instances provided by the user are required,
	 * as the object might have an internal state that should be updated with each function call.
	 * @param objectsWithFunctions a sequence of objects whose classes define one or more methods annotated by with {@link FunctionWrapper}.
	 */
	public void addFunctions(Object... objectsWithFunctions);

	/**
	 * Associates a {@link RowReader} implementation class to a name and a scope. When required, a row reader instance will be instantiated using the class's default
	 * constructor. This instance will be reused within the given scope and destroyed when the scope is deactivated.
	 *
	 * @param scope the row reader scope that determines when its instances will be destroyed.
	 * @param name the name of the given row reader.
	 * @param reader the implementation of a {@link RowReader} to be instantiated when required.
	 */
	public void addRowReader(EngineScope scope, String name, Class<? extends RowReader> reader);

	/**
	 * Associates a {@link RowReader} instance to name. As this is a user-provided instance, it is the user's
	 * responsibility to determine how its internal state, if any, should be managed.
	 *
	 * @param name the name of the given row reader
	 * @param reader an instance of a row reader, managed by the user.
	 */
	public void addRowReader(String name, RowReader reader);

	/**
	 * Adds a new {@link Dataset} to this engine. The given dataset can contain data and be used as a source or, if the dataset implements
	 * {@link ModifiableDataset}, as the destination entity in entity mappings. The user can create his/her own implementation of this interface.
	 *
	 *
	 * <p>As a convenience, many useful dataset implementations can be obtained through a {@link DatasetFactory}.
	 *
	 * @param name the name of the dataset
	 * @param dataset the dataset implementation
	 */
	public void addDataset(String name, Dataset dataset);

	/**
	 * Adds and configures a {@link DatasetProducer} for reading information from an entity accessible through this engine, and producing different
	 * datasets as a result. The resulting datasets can then be used in entity mappings. The dataset producer is associated with a scope, therefore
	 * the datasets will be kept while the scope is active.
	 *
	 * @param scope the scope of the datasets produced by the given producer
	 * @param producer the object responsible for producing datasets.
	 * @return a {@link DatasetProducerSetup} object for configuring the inputs and outputs of the given dataset producer.
	 */
	public DatasetProducerSetup addDatasetProducer(EngineScope scope, DatasetProducer producer);

	/**
	 * Disables data modifications on a given set of records of a data entity that were already mapped.
	 * <br>This will create a flag in uniVocity metadata tables for the given records, which prevents
	 * data modifications on all rows already mapped.
	 *
	 * <p><i><b>Note: </b></i>This only takes effect if the entity mapping configuration uses uniVocity metadata information (i.e. {@link PersistenceSetup#usingMetadata()}).</p>
	 *
	 * @param dataEntityName the name of the data entity to have some records disabled for updates.
	 * @param dataset the dataset containing the identifiers of a mapped destination entity.
	 */
	public void disableUpdateOnRecords(String dataEntityName, Dataset dataset);

	/**
	 * Disables data modifications to all records of this data entity that were already mapped.
	 * New records introduced after another mapping cycle will be enabled for updates.
	 * <br>This will create a flag in uniVocity metadata tables for the given entity, which prevents
	 * data modifications on all rows already mapped.
	 *
	 * <p><i><b>Note: </b></i>This only takes effect if the entity mapping configuration uses uniVocity metadata information (i.e. {@link PersistenceSetup#usingMetadata()}).</p>
	 *
	 * @param dataEntityName the name of the data entity to have all records already mapped disabled for updates.
	 */
	public void disableUpdateOnAllRecords(String dataEntityName);

	/**
	 * Re-enables data modifications on a given set records of a data entity. This reverts the metadata changes made
	 * with {@link #disableUpdateOnRecords(String, Dataset)} or {@link #disableUpdateOnAllRecords(String)} for the given records.
	 *
	 * @param dataEntityName the name of the data entity to have some records enabled for updates.
	 * @param dataset the dataset containing the identifiers of a mapped destination entity.
	 */
	public void enableUpdateOnRecords(String dataEntityName, Dataset dataset);

	/**
	 * Re-enables data modifications on all records of a data entity. This will any metadata changes made
	 * with {@link #disableUpdateOnRecords(String, Dataset)} or {@link #disableUpdateOnAllRecords(String)}.
	 *
	 * @param dataEntityName the name of the data entity to have all of its records enabled for data updates.
	 */
	public void enableUpdateOnAllRecords(String dataEntityName);

	/**
	 * Use this method to define the correct sequence of mappings that have to be executed in order to correctly process the default data mapping cycle
	 * (started with {@link #executeCycle()} or {@link #executeCycle(DataIncrement)}).
	 *
	 * <p>By default, the mappings are executed in the order they were configured. However, entity mappings generated automatically (using
	 * {@link DataStoreMapping#autodetectMappings()}) won't have the correct mapping sequence.</p>
	 *
	 * <p>This might be required to ensure data exclusions and updates occur in the proper sequence (which is usually the reverse order of insertion) and that
	 * references to other entities are correctly populated.</p>
	 *
	 * <p>If there are duplicate names from different data stores, these names must be written in the format <i><code>dataStoreName.entityName</code></i>.</p>
	 *
	 * @param sequenceOfDestinationEntities the sequence of destination fields to be mapped. Not all destination entities need to be declared here. The ones that appear
	 * in the sequence will be executed first, in the given order. Omitted data entities will have their mappings executed after the give sequence of mappings took place.
	 */
	public void setMappingSequence(String... sequenceOfDestinationEntities);

	/**
	 * Executes a data mapping cycle with all mappings configured in this engine (i.e. via {@link #map(String, String)}).
	 * A transactional operation for all mappings in this cycle will be created.
	 */
	public void executeCycle();

	/**
	 * Executes a data mapping cycle against a {@link DataIncrement} object with all mappings configured in this engine (i.e. via {@link #map(String, String)}).
	 * <p>The data increment is used in place of one or more source data entities.</p>
	 *
	 * @param increment The increment with data changes for one or more source entities that should be applied to the destination using the configured mappings.
	 */
	public void executeCycle(DataIncrement increment);

	/**
	 * Executes a data mapping cycle against the selected destination entities. If there are duplicate names from different data stores, these names must be written in
	 * the format <i><code>dataStoreName.entityName</code></i>. The mappings will be executed in the same order the given entities are declared.
	 *
	 * @param destinationEntities the sequence of destination entities to receive data from the mappings.
	 */
	public void executeCycle(String... destinationEntities);

	/**
	 * Executes a data mapping cycle against a {@link DataIncrement} object and a selection of destination entities.
	 * If there are duplicate names from different data stores, these names must be written in the format <i><code>dataStoreName.entityName</code></i>.
	 * The mappings will be executed in the same order the given entities are declared.
	 *
	 * <p>The data increment is used in place of one or more source data entities.</p>
	 *
	 * @param increment the object with additional data to be used in place of one or more source data entities in this cycle.
	 * @param destinationEntities the sequence of destination entities to receive data from the mappings.
	 */
	public void executeCycle(DataIncrement increment, String... destinationEntities);

	/**
	 * Executes a data mapping cycle with all mappings configured in this engine (i.e. via {@link #map(String, String)}).
	 * @param transactionConfig the configuration that defines how transactions should be created while executing mappings in this cycle.
	 */
	public void executeCycle(Transactions transactionConfig);

	/**
	 * Executes a data mapping cycle against a {@link DataIncrement} object with all mappings configured in this engine (i.e. via {@link #map(String, String)}).
	 * <p>The data increment is used in place of one or more source data entities.</p>
	 *
	 * @param transactionConfig the configuration that defines how transactions should be created while executing mappings in this cycle.
	 * @param increment The increment with data changes for one or more source entities that should be applied to the destination using the configured mappings.
	 */
	public void executeCycle(Transactions transactionConfig, DataIncrement increment);

	/**
	 * Executes a data mapping cycle against the selected destination entities. If there are duplicate names from different data stores, these names must be written in
	 * the format <i><code>dataStoreName.entityName</code></i>. The mappings will be executed in the same order the given entities are declared.
	 *
	 * @param transactionConfig the configuration that defines how transactions should be created while executing mappings in this cycle.
	 * @param destinationEntities the sequence of destination entities to receive data from the mappings.
	 */
	public void executeCycle(Transactions transactionConfig, String... destinationEntities);

	/**
	 * Executes a data mapping cycle against a {@link DataIncrement} object and a selection of destination entities.
	 * If there are duplicate names from different data stores, these names must be written in the format <i><code>dataStoreName.entityName</code></i>.
	 * The mappings will be executed in the same order the given entities are declared.
	 *
	 * <p>The data increment is used in place of one or more source data entities.</p>
	 *
	 * @param transactionConfig the configuration that defines how transactions should be created while executing mappings in this cycle.
	 * @param increment the object with additional data to be used in place of one or more source data entities in this cycle.
	 * @param destinationEntities the sequence of destination entities to receive data from the mappings.
	 */
	public void executeCycle(Transactions transactionConfig, DataIncrement increment, String... destinationEntities);

	/**
	 * Get an accessible data entity which provides direct access to the underlying data store.
	 * @param entityName the name of a data entity managed by this {@code DataIntegrationEngine}
	 * @return an {@link Entity} for the given entity name.
	 */
	public Entity getEntity(String entityName);
}
