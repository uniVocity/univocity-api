/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.exception;

/**
 * <code>IllegalConfigurationException</code> is the exception thrown by uniVocity to notify of an illegal configuration.
 *
 * <p> For example, if a function that does not exist is configured to be invoked when mapping data between two entities, this exception will be thrown.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class IllegalConfigurationException extends RuntimeException {
	private static final long serialVersionUID = 8697369823358345165L;

	/**
	 * Constructs a new IllegalConfigurationException exception with the specified detail message and cause.
	 * @param  message the detail message.
	 * @param  cause the cause of the exception.
	 */
	public IllegalConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new IllegalConfigurationException exception with the specified detail message, and no cause.
	 * @param  message the detail message.
	 */
	public IllegalConfigurationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new IllegalConfigurationException exception with the specified cause of error.
	 * @param  cause the cause of the exception.
	 */
	public IllegalConfigurationException(Throwable cause) {
		super(cause);
	}
}
