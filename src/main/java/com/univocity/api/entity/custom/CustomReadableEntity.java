/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

import java.util.*;

import com.univocity.api.config.builders.*;
import com.univocity.api.entity.*;

/**
 * The CustomReadableEntity is the most basic data entity a user can define.
 * It provides the essential methods to allow data extraction from a user-managed resource.
 *
 * Instances of this interface are expected to be provided by {@link CustomDataStore} in {@link CustomDataStore#getDataEntities()}
 *
 * @see CustomDataStore
 * @see EntityMapping
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public interface CustomReadableEntity {

	/**
	 * Returns this entity name. uniVocity refers to entities of a data store by their name (most importantly in {@link DataStoreMapping#map(String, String)}).
	 * @return the entity name.
	 */
	public String getEntityName();

	/**
	 * Initializes a reading process in this data entity.
	 * A list of field names of interest are provided by uniVocity.
	 * Subsequent calls to {@link ReadingProcess#readNext()} must return rows with values for the requested fields.
	 * @param fieldNames the fields selected by uniVocity that identify what values to extract from each record in the {@link ReadingProcess}.
	 * @return a {@link ReadingProcess} object is responsible for reading values for the selected fields in each record contained by this entity.
	 */
	public ReadingProcess prepareToRead(String[] fieldNames);

	/**
	 * Informs uniVocity of what fields are available from this data entity.
	 * @return a set of instances of {@link DefaultEntityField} which contain details about each field in the records stored by this data entity.
	 */
	public Set<? extends DefaultEntityField> getFields();
}
