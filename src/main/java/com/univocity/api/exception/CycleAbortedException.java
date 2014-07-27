/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.exception;

/**
 * <code>CycleAbortedException</code> is the exception thrown when a data mapping cycle is aborted.
 *
 * <p> For example, if an reference to an entity is not found, and the mapping is configured to abort the process
 *     in this case, then <code>CycleAbortedException</code>  will be thrown.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class CycleAbortedException extends RuntimeException {

	private static final long serialVersionUID = -5511998083482844805L;

	/**
	 * Constructs a new CycleAbortedException exception with the specified detail message and cause.
	 * @param  message the detail message.
	 * @param  cause the cause of the exception.
	 */
	public CycleAbortedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new CycleAbortedException exception with the specified detail message, and no cause.
	 * @param  message the detail message.
	 */
	public CycleAbortedException(String message) {
		super(message);
	}

	/**
	 * Constructs a new CycleAbortedException exception with the specified cause of error.
	 * @param  cause the cause of the exception.
	 */
	public CycleAbortedException(Throwable cause) {
		super(cause);
	}
}
