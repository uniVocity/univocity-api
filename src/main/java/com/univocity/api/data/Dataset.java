/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.data;

/**
 * A dataset exposes a number of records to be mapped into entities in uniVocity. It is essentially a matrix-like structure with 
 * rows and columns.
 *
 * @see DatasetFactory
 * @see DataIncrement
 * @see DatasetProducer
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface Dataset {

	/**
	 * Provides names of each field in the records of this dataset, in a sequence corresponding to their order.     
	 * @return the field names.
	 */
	public String[] getFieldNames();

	/**
	 * Informs what field names in {@link #getFieldNames()} should be used to identify each record. 
	 * @return the identifier names.
	 */
	public String[] getIdentifiers();

	/**
	 * Returns the rows stored in this dataset.
	 * @return the dataset rows
	 */
	public Iterable<Object[]> getRows();

	/**
	 * Returns the number of rows in this dataset.
	 * @return the number of rows in this dataset, or -1 if it cannot be determined.
	 */
	public int size();

}
