/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.data;

import java.util.*;

import com.univocity.api.*;
import com.univocity.api.engine.*;

/**
 * A factory for creating {@link Dataset} instances backed by collections.
 * To get a <code>DatasetFactory</code> from uniVocity, use {@link Univocity#datasetFactory()}.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface DatasetFactory {

	/**
	 * Creates a new modifiable dataset backed by a collection of rows.
	 * @param rows the rows in the dataset
	 * @param identifier the name of the field that identifies rows in this dataset
	 * @param fieldNames the sequence of field names of each record in this data set.
	 * @return a new modifiable dataset.
	 */
	public ModifiableDataset newDataset(Collection<Object[]> rows, String identifier, String... fieldNames);

	/**
	 * Creates a new modifiable dataset backed by a collection of rows.
	 * @param rows the rows in the dataset
	 * @param identifiers the names of the fields that identify rows in this dataset
	 * @param fieldNames the sequence of field names of each record in this data set.
	 * @return a new modifiable dataset.
	 */
	public ModifiableDataset newDataset(Collection<Object[]> rows, String[] identifiers, String... fieldNames);

	/**
	 * Creates a new modifiable dataset, where records have a single column.
	 * <br>This dataset is backed by a collection of objects and contains only one field. This field is also the identifier.
	 * @param <T> the row type
	 * @param rows the rows in the dataset
	 * @param fieldName the name of the single field all records in this dataset have.
	 * @return a new single-column, modifiable, dataset.
	 */
	public <T extends Collection<?>> ModifiableDataset newDataset(T rows, String fieldName);

	/**
	 * Creates a new modifiable dataset, backed by a map.
	 * <p><i><b>Note </b> new records with duplicate identifier values will replace existing records.</i></p>
	 *
	 * @param map the map used to hold all records of the data set.
	 * @param identifier the field name used as identifier. The values in the identifier column will be used as keys in the map.
	 * @param fieldNames the sequence of field names of each record in this data set.
	 * @return a new modifiable dataset.
	 */
	public ModifiableDataset newDataset(Map<?, Object[]> map, String identifier, String... fieldNames);

	/**
	 * Creates a new modifiable dataset, with 2 columns, backed by a map.
	 * <p><i><b>Note </b> new records with duplicate identifier values will replace existing records.</i></p>
	 *
	 * @param map the map used to hold all records of the data set.
	 * @param identifier the field name used as identifier. Values in the identifier column will be used as keys in the map.
	 * @param fieldName field name of each value associated with an identifier of this data set.
	 * @return a new modifiable dataset.
	 */
	public ModifiableDataset newDataset(Map<?, ?> map, String identifier, String fieldName);

	/**
	 * Creates a new modifiable dataset, backed by a map.
	 * <br>The values in the identifier columns will be concatenated into Strings which will be used as the keys in the map.
	 * <p><i><b>Note </b> new records with duplicate identifier values will replace existing records.</i>
	 *
	 * @param map the map used to hold all records of the data set.
	 * @param identifiers the field names used to compose the identifier.
	 * @param fieldNames the sequence of field names of each record in this data set.
	 * @return a new modifiable dataset.
	 */
	public ModifiableDataset newDataset(Map<String, Object[]> map, String[] identifiers, String... fieldNames);

	/**
	 * Creates a new modifiable dataset, backed by a map.
	 * <br>The values in the identifier columns will be submitted to the {@link FunctionCall} to be converted into a key to be used in the map.
	 * <p><i><b>Note </b> new records with duplicate identifier values will replace existing records.</i>
	 *
	 * @param <K> the key type
	 * @param map the map used to hold all records of the data set.
	 * @param identifiers the field names used to compose the identifier.
	 * @param fieldNames the sequence of field names of each record in this data set.
	 * @param keyBuilder the function that iterates over identifier values to produce keys for the given map.
	 * @return a new modifiable dataset.
	 */
	public <K> ModifiableDataset newDataset(Map<K, Object[]> map, String[] identifiers, String[] fieldNames, FunctionCall<K, Object[]> keyBuilder);

	/**
	 * Creates a new modifiable dataset, backed by a map.
	 * <br>The values in the identifier columns will be submitted to the {@link FunctionCall} to be converted into a key to be used in the map.
	 * <p><i><b>Note </b> new records with duplicate identifier values will replace existing records.</i>
	 *
	 * @param <K> the key type
	 * @param map the map used to hold all records of the data set.
	 * @param identifiers the field names used to compose the identifier.
	 * @param fieldName field name of each value associated with an identifier of this data set.
	 * @param keyBuilder the function that iterates over identifier values to produce keys for the given map.
	 * @return a new modifiable dataset.
	 */
	public <K> ModifiableDataset newDataset(Map<K, Object> map, String[] identifiers, String fieldName, FunctionCall<K, Object[]> keyBuilder);

	/**
	 * Creates a new modifiable dataset, backed by a map.
	 * <br>The values in the identifier columns will be concatenated into Strings which will be used as the keys in the map.
	 * <p><i><b>Note </b> new records with duplicate identifier values will replace existing records.</i>
	 *
	 * @param <K> the key type
	 * @param map the map used to hold all records of the data set.
	 * @param identifiers the field names used to compose the identifier.
	 * @param fieldName field name of each value associated with an identifier of this data set.
	 * @return a new modifiable dataset.
	 */
	public <K> ModifiableDataset newDataset(Map<String, Object> map, String[] identifiers, String fieldName);

	/**
	 * Creates dummy dataset that does not store nor retrieve any data.
	 *
	 * @param identifier the name of the field used as an identifier among the fields in this dataset
	 * @param fieldNames the sequence of field names for this data set.
	 * @return a new dummy dataset.
	 */
	public ModifiableDataset newDummyDataset(String identifier, String... fieldNames);

	/**
	 * Creates dummy dataset that does not store nor retrieve any data.
	 *
	 * @param identifiers the names of the fields used as identifiers among the fields in this dataset
	 * @param fieldNames the sequence of field names for this data set.
	 * @return a new dummy dataset.
	 */
	public ModifiableDataset newDummyDataset(String[] identifiers, String... fieldNames);

}
