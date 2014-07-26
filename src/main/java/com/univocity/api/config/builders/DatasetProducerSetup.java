/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.data.*;
import com.univocity.api.engine.*;

/**
 * The {@link DatasetProducerSetup} is used to configure the input of a {@link DatasetProducer} being registered in a {@link DataIntegrationEngine} through
 * {@link DataIntegrationEngine#addDatasetProducer(EngineScope, DatasetProducer)}.
 * 
 * @see DatasetProducer
 * @see DataIntegrationEngine
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public interface DatasetProducerSetup {

	/**
	 * Specifies the input data of a {@link DatasetProducer}. When one of the {@link Dataset}s produced is accessed in a data mapping cycle for the first time
	 * in the configured scope, the producer will read the input data and populate the datasets. 
	 * 
	 * @param dataEntity the name of the entity that provides data for the {@link DatasetProducer}. If there are duplicate names in different data stores, 
	 * the entity name must be written in the format <i><code>dataStoreName.entityName</code></i>.
	 * @param fields the fields to be read from the data entity
	 * 
	 * <p><i>This completes the configuration started in {@link DataIntegrationEngine#addDatasetProducer(EngineScope, DatasetProducer)}</i> 
	 */
	public void on(String dataEntity, String... fields);
}
