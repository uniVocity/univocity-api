/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api;

import com.univocity.api.data.*;
import com.univocity.api.engine.*;

/**
 * The <code>UnivocityFactoryProvider</code> is used to obtain actual implementations of {@link DatasetFactory} and {@link DataIntegrationEngineFactory}
 * from univocity.jar. It is used internally by the {@link Univocity} class.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface UnivocityFactoryProvider {

	/**
	 * Returns a {@link DatasetFactory} provided by uniVocity.
	 * @return a {@link DatasetFactory}
	 */
	DatasetFactory getDatasetFactory();

	/**
	 * Returns a {@link DataIntegrationEngineFactory} provided by uniVocity.
	 * @return a {@link DataIntegrationEngineFactory}
	 */
	DataIntegrationEngineFactory getDataIntegrationEngineFactory();


	/**
	 * Returns an object implementation provided by uniVocity. This is for internal use only.
	 *
	 * @param builderType the interface of a builder entry point
	 * @param args any arguments required to initialize the builder.
	 *
	 * @return a builder implementation provided by uniVocity.
	 * @param <T> the type of builder to instantiate.
	 */
	<T> T build(Class<T> builderType, Object ... args);

	/**
	 * Executes an internal command whose implementation is provided by uniVocity. For internal use only.
	 * @param command the command to be executed
	 * @param args any arguments required to perform the operation
	 * @param <T> the type of the return value produced by the operation
	 * @return the result of the given command.
	 */
	<T> T execute(String command, Object ... args);
}
