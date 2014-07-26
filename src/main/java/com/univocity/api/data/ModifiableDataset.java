/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.data;

/**
 * A special {@link Dataset} that allows data modifications.
 * 
 * @see Dataset
 * @see DatasetFactory
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface ModifiableDataset extends Dataset {

	/**
	 * Inserts a new record into this dataset. 
	 * @param newData the new record. The length of the array must match the number of fields in the dataset
	 */
	public void insert(Object[] newData);

	/**
	 * Updates an existing record in this dataset.
	 * @param updatedValues a row with updated values. The length of the array must match the number of fields in the dataset
	 * @param matchingValues identifies which row of this dataset must be updated. The length of the array must match the number of identifiers in the dataset.
	 */
	public void update(Object[] updatedValues, Object[] matchingValues);

	/**
	 * Removes a record from this dataset.
	 * @param matchingValues identifies which row of this dataset must be removed. The length of the array must match the number of identifiers in the dataset 
	 */
	public void delete(Object[] matchingValues);

	/**
	 * Removes all records from this dataset.
	 */
	public void deleteAll();

}
