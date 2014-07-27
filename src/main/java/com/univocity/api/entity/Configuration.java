/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity;

/**
 * This is the root of all uniVocity entity configuration classes. It just defines the protected method
 * {@link #copyDefaultsFrom(Configuration)} which initializes undefined settings with default values provided by another configuration object.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public abstract class Configuration {

	/**
	 * Applies default values to undefined settings using a {@link Configuration} object.
	 * @param defaults a configuration object from where to obtain default settings.
	 */
	protected abstract void copyDefaultsFrom(Configuration defaults);

}
