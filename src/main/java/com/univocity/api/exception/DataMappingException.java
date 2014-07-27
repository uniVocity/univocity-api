/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.exception;

/**
 * <code>DataMappingException</code> is the exception thrown by uniVocity to notify errors when executing a data mapping process.
 *
 * <p> For example, if a reference to an entity cannot be resolved in a data mapping execution, this exception will be thrown
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class DataMappingException extends RuntimeException {
	private static final long serialVersionUID = 4335077254167111111L;

	/**
	 * Constructs a new DataMappingException exception with the specified detail message and cause.
	 * @param  message the detail message.
	 * @param  cause the cause of the exception.
	 */
	public DataMappingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new DataMappingException exception with the specified detail message, and no cause.
	 * @param  message the detail message.
	 */
	public DataMappingException(String message) {
		super(message);
	}

	/**
	 * Constructs a new DataMappingException exception with the specified cause of error.
	 * @param  cause the cause of the exception.
	 */
	public DataMappingException(Throwable cause) {
		super(cause);
	}
}
