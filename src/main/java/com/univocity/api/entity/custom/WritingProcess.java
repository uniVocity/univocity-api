/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

/**
 * A process for writing rows to a {@link CustomDataEntity}. 
 * Instances of this process must created when {@link CustomDataEntity#prepareToWrite(String[])} is called from user-provided entity implementation 
 * 
 * @see CustomDataEntity
 * @see CustomProcess
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface WritingProcess extends CustomProcess {

	/**
	 * Inserts a row of data into the custom data entity that originated this writing process. 
	 * @param data a row with values for the fields provided by uniVocity when calling {@link CustomDataEntity#prepareToWrite(String[])}
	 */
	public void writeNext(Object[] data);

	/**
	 * Called by uniVocity after a batch of rows was persisted (using {@link #writeNext(Object[])}) to read any generated keys produced by the insertion of new rows. 
	 * @return a new instance {@link ReadingProcess} for reading auto-generated keys created for each new row, or null if no keys got generated. 
	 */
	public ReadingProcess retrieveGeneratedKeys();
}
