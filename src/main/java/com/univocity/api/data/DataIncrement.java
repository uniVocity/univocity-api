/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.data;

import com.univocity.api.engine.*;

/**
 * A data increment provides a limited number of records for mapped data entities.
 * It is used by uniVocity to execute incremental data mapping cycles on limited sets of information,
 *
 * <p>This can be used to execute mappings in real-time as data increments return in-memory {@link Dataset} instances instead of loading records from a source entity.
 * <br>Use {@link DataIntegrationEngine#executeCycle(DataIncrement)} to execute a data mapping cycle against a <code>DataIncrement</code>.
 * <p> When uniVocity executes data mappings against a <code>DataIncrement</code>, it will request a {@link Dataset} calling {@link #getDataset(String, String)}.
 *     If this <code>DataIncrement</code> provides a {@link Dataset}, then it will be used to provide source data;
 *     Otherwise, an empty dataset will be used. No records of the actual source entity will be read.
 *
 * @see Dataset
 * @see DataIntegrationEngine
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface DataIncrement {

	/**
	 * Returns the dataset for an entity whose mapping is being executed as part of a data mapping cycle.
	 *
	 * @param datastoreName the name of the data store that (potentially) contains a dataset for an entity
	 * @param entityName the name of the data entity being read as a source of data in a data mapping cycle.
	 * @return the {@link Dataset} that contains a number of records of the given entity, or null if there is no dataset.
	 */
	public Dataset getDataset(String datastoreName, String entityName);

}
