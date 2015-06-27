/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

/**
 * A process for reading rows from a {@link CustomDataEntity}.
 * Instances of this process must created when {@link CustomReadableEntity#prepareToRead(String[])} is called from a user-provided entity implementation
 *
 * @see CustomProcess
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface ReadingProcess extends CustomProcess {

	/**
	 * Reads the next record from the entity that originated this process.
	 * The data elements must be ordered according to the sequence of field names provided when {@link CustomReadableEntity#prepareToRead(String[])} is called.
	 * @return the next row of data from the entity that originated this process, or null if all rows have been read.
	 */
	public Object[] readNext();

}
