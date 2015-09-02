/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.common;

/**
 * Generic interface to define classes that provide a given resource.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 * @param <T> the type of resource provided by this ResourceProvider
 */
public interface ResourceProvider<T> {
	/**
	 * Returns the resource provided by this class
	 * @return the resource provided by this class
	 */
	T getResource();
}
