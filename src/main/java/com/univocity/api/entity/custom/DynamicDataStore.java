/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

import java.util.*;

import com.univocity.api.config.builders.*;
import com.univocity.api.engine.*;
import com.univocity.api.entity.*;

/**
 * A {@link CustomDataStore} that allows dynamic creation of data entities when required. uniVocity will request creation of a new data entity
 * when auto detecting mappings using {@link DataStoreMapping#autodetectMappings(boolean)} with its {@code createDestinationEntities} parameter set to {@code true}.
 *
 * @param <E> defines the type of data entities managed by this data store.
 *
 * @see CustomDataStore
 * @see CustomDataStoreFactory
 * @see DataIntegrationEngine
 * @see DataStoreMapping
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public abstract class DynamicDataStore<E extends CustomReadableEntity> implements CustomDataStore<E> {

	/**
	 * Invoked by uniVocity if it requires a destination data entity that does not exist.
	 * Implementations must creates a new data entity with the given name and fields.
	 *
	 * @param <C> Any collection of of fields (i.e. subclasses of {@link DefaultEntityField})
	 * @param <T> The type of each field
	 * @param entityName the name of the new entity to be created in this data store.
	 * @param fields the fields of the entity to be created.
	 */
	public abstract <C extends Collection<T>, T extends DefaultEntityField> void createDataEntity(String entityName, C fields);

}
