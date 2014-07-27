/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

import com.univocity.api.config.*;

/**
 * A custom data store factory implementation, responsible for creating new data store instances from a configuration object.
 *
 * Instances of this class are expected to be provided in {@link EngineConfiguration#getCustomDataStoreFactories()}
 *
 * @param <C> the type of data store configurations this factory supports.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public interface CustomDataStoreFactory<C extends DataStoreConfiguration> {

	/**
	 * Creates a new custom data store implementation from a configuration object
	 * @param configuration the data store configuration
	 * @return a data store configured with the given settings.
	 */
	public CustomDataStore<?> newDataStore(C configuration);

	/**
	 * Informs uniVocity the configuration class this factory supports.
	 * <p><b>Note </b> uniVocity will not accept objects of subclasses of this class.
	 *
	 * @return the class that extends DataStoreConfiguration to provide configuration information for data stores created by this factory.
	 */
	public Class<C> getConfigurationType();

}
