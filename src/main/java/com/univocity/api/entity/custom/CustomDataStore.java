/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

import java.util.*;

import com.univocity.api.config.builders.*;
import com.univocity.api.engine.*;

/**
 * A CustomDataStore is responsible for managing custom data entity instances and making them available to uniVocity.
 *
 * <p>Implementation of transactional behavior in implementations of this interface (in {@link #executeInTransaction(TransactionalOperation)}) is optional.
 * However, implementors <b><i>MUST</i></b> execute the transactional operation passed in by uniVocity.
 *
 * @param <E> defines the type of data entities managed by this data store.
 *
 * @see CustomDataStoreFactory
 * @see DataIntegrationEngine
 * @see DataStoreMapping
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public interface CustomDataStore<E extends CustomReadableEntity> {

	/**
	 * Returns the data entity instances available to uniVocity. The instances can be implementations of {@link CustomReadableEntity} or {@link CustomDataEntity}
	 * @return the data entity instances this data store provides.
	 */
	public Set<E> getDataEntities();

	/**
	 * Returns the custom query instances available to uniVocity.
	 * @return the query instances this data store provides.
	 */
	public Set<? extends CustomQuery> getQueries();

	/**
	 * Adds a new query to this data entity.
	 *
	 * @param queryName name of the new query.
	 * @param query the query definition. This can be anything the data store implementation supports:
	 * a SQL string, an identifier to some custom query object in a map held by this data store, or anything required to create a new {@link CustomQuery} instance.
	 * @return the instance of a new {@link CustomQuery} created with the given parameters.
	 */
	public CustomQuery addQuery(String queryName, String query);

	/**
	 * Executes an instance of {@link TransactionalOperation}, provided by uniVocity.
	 *
	 * A possible implementation of transaction handling might look like:
	 *
	 * <p><hr><blockquote><pre>
	 * public void executeInTransaction(TransactionalOperation operation) {
	 *     try {
	 *         operation.execute();
	 *         commitChanges(); //goes through resources allocated by entities of this data store and persists all data changes.
	 *     } catch (Exception ex) {
	 *          rollbackChanges(ex); //goes through resources allocated by entities of this data store and cancels/reverts all data changes.
	 *          throw new IllegalStateException(ex);
	 *     }
	 * }
	 * </pre></blockquote><hr>
	 *
	 * If the custom data store does not implement transactions, then it can simply be written as:
	 * <p><hr><blockquote><pre>
	 *  public void executeInTransaction(TransactionalOperation operation) {
	 *      operation.execute();
	 *  }
	 * </pre></blockquote><hr>
	 *
	 * <i><b>Notes</b>
	 * <ul>
	 *  <li>This method implementation must invoke {@link TransactionalOperation#execute()}.</li>
	 *  <li>If an exception occurs, it must be rethrown so any other active transactional operation can be rolled back.
	 *   Failure in doing so will potentially result in data corruption.</li>
	 *  <li>Each data mapping in the cycle (started with {@link DataIntegrationEngine#executeCycle()}) runs on its own independent {@link TransactionalOperation#execute()}.
	 *      An exception in one of the mappings in a cycle will not revert changes made by mappings already executed.</li>
	 * </ul>
	 * </i>
	 *
	 * @param operation the transactional operation that must be executed
	 */
	public void executeInTransaction(TransactionalOperation operation);

	/**
	 * Returns the configuration object of this data store.
	 * @return the configuration of this data store
	 */
	public DataStoreConfiguration getConfiguration();
}
