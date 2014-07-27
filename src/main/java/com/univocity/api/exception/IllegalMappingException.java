/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.exception;

/**
 *
 * <code>IllegalMappingException</code> is the exception thrown by uniVocity to notify of an illegal mapping.
 *
 * <p> For example, if non-existent fields were used to define a mapping between two entities, this exception will be thrown.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class IllegalMappingException extends RuntimeException {
	private static final long serialVersionUID = -4098776845208982101L;

	/**
	 * Constructs a new IllegalMappingException exception with the specified detail message and cause.
	 * @param  message the detail message.
	 * @param  cause the cause of the exception.
	 */
	public IllegalMappingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new IllegalMappingException exception with the specified detail message, and no cause.
	 * @param  message the detail message.
	 */
	public IllegalMappingException(String message) {
		super(message);
	}

	/**
	 * Constructs a new IllegalMappingException exception with the specified cause of error.
	 * @param  cause the cause of the exception.
	 */
	public IllegalMappingException(Throwable cause) {
		super(cause);
	}
}
